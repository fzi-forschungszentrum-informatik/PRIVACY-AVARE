/*
        Copyright 2016-2018 AVARE project team

        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).

        Files under this folder (and the subfolders) with "Created by AVARE Project ..."-Notice
	    are our work and licensed under Apache Licence, Version 2.0"

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
/* TODO: create an NPM module for this functionality*/
package app.avare.avaremanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.lody.virtual.client.core.InstallStrategy;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.InstallResult;
import com.lody.virtual.remote.InstalledAppInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import javax.annotation.Nullable;

import app.avare.avarebox.abs.ui.VUiKit;
import app.avare.avarebox.home.models.AppData;
import app.avare.avarebox.home.models.AppInfo;
import app.avare.avarebox.home.models.AppInfoLite;
import app.avare.avarebox.home.models.MultiplePackageAppData;
import app.avare.avarebox.home.models.PackageAppData;
import app.avare.avarebox.home.repo.AppRepository;
import app.avare.avarebox.home.repo.PackageAppDataStorage;
import app.avare.avarebox.splash.SplashActivity;
import android.content.res.AssetManager;

public class AvareBoxModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private AppRepository mRepo;

    public AvareBoxModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mRepo = new AppRepository(this.reactContext);
    }

    @Override
    public String getName() {
        return "AvareBox";
    }

    @ReactMethod
    public void initPlugins() {
        Toast.makeText(getReactApplicationContext(), "Initialize Plugins", Toast.LENGTH_LONG).show();
        //Todo: check whether already installed
        // Copy apks from assets directory to local files directory
        AssetManager assetManager = MainApplication.getApp().getAssets();
        String[] files = null;
        try {
            files = assetManager.list("plugins");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            copyPlugin(filename);
        }

        // get necessary Infos from apks and install as plugins
        List<PackageInfo> packageList = findAndParseAPKs(this.reactContext, new File("/data/data/app.avare/files"));
        List<AppInfo> appInfoList = convertPackageInfoToAppData(this.reactContext, packageList, false);
        for (AppInfo appInfo : appInfoList) {
            Log.d("AVAREBOX", "add Plugin: " + appInfo.packageName);
            addPlugin(appInfo);
        }
    }


    private List<PackageInfo> findAndParseAPKs(Context context, File rootDir) {
        List<PackageInfo> packageList = new ArrayList<>();

        File[] dirFiles = rootDir.listFiles();

        for (File f : dirFiles) {
            if (!f.getName().toLowerCase().endsWith(".apk"))
                continue;
            PackageInfo pkgInfo = null;
            try {
                pkgInfo = context.getPackageManager().getPackageArchiveInfo(
                        f.getAbsolutePath(), PackageManager.GET_META_DATA);
                pkgInfo.applicationInfo.sourceDir = f.getAbsolutePath();
                pkgInfo.applicationInfo.publicSourceDir = f.getAbsolutePath();
            } catch (Exception e) {
                // Ignore
            }
            if (pkgInfo != null)
                packageList.add(pkgInfo);
        }

        return packageList;
    }

    private List<AppInfo> convertPackageInfoToAppData(Context context, List<PackageInfo> pkgList, boolean fastOpen) {
        PackageManager pm = context.getPackageManager();
        List<AppInfo> list = new ArrayList<>(pkgList.size());
        String hostPkg = VirtualCore.get().getHostPkg();
        for (PackageInfo pkg : pkgList) {
            // ignore the host package
            if (hostPkg.equals(pkg.packageName)) {
                continue;
            }
            // ignore the System package
            //if (isSystemApplication(pkg)) {
            //    continue;
            //}
            ApplicationInfo ai = pkg.applicationInfo;
            boolean isHookPlugin = false;
            if(ai.metaData != null) {
                isHookPlugin = ai.metaData.getBoolean("yahfa.hook.plugin", false);
            }
            String path = ai.publicSourceDir != null ? ai.publicSourceDir : ai.sourceDir;
            if (path == null) {
                continue;
            }
            AppInfo info = new AppInfo();
            info.packageName = pkg.packageName;
            info.fastOpen = fastOpen;
            info.path = path;
            info.icon = ai.loadIcon(pm);
            info.name = ai.loadLabel(pm);
            info.isHook = isHookPlugin;
            InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(pkg.packageName, 0);
            if (installedAppInfo != null) {
                if (isHookPlugin) { // do not show hook plugin if already installed
                    continue;
                } else {
                    info.cloneCount = installedAppInfo.getInstalledUsers().length;
                }
            }
            list.add(info);
        }
        return list;
    }

    private void copyPlugin(String filename) {
        AssetManager assetManager = MainApplication.getApp().getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open("plugins/" + filename);
            File outFile = new File("/data/data/app.avare/files", filename);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void addPlugin(AppInfo appInfo) {
        Toast.makeText(getReactApplicationContext(), "Adding: " + appInfo.packageName, Toast.LENGTH_LONG).show();


        //AppInfoLite info = new AppInfoLite("lab.galaxy.contactsFilterPlugin", "/data/data/app.avare/files/..., false, true);
        AppInfoLite info = new AppInfoLite(appInfo.packageName, appInfo.path, false, true);


        class AddResult {
            private PackageAppData appData;
            private int userId;
            private boolean justEnableHidden;
        }
        AddResult addResult = new AddResult();
        VUiKit.defer().when(() -> {
            InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(info.packageName, 0);
            addResult.justEnableHidden = installedAppInfo != null;
            if (addResult.justEnableHidden && !info.isHook) {
                int[] userIds = installedAppInfo.getInstalledUsers();
                int nextUserId = userIds.length;

                for (int i = 0; i < userIds.length; i++) {
                    if (userIds[i] != i) {
                        nextUserId = i;
                        break;
                    }
                }
                addResult.userId = nextUserId;

                if (VUserManager.get().getUserInfo(nextUserId) == null) {
                    // user not exist, create it automatically.
                    String nextUserName = "Space " + (nextUserId + 1);
                    VUserInfo newUserInfo = VUserManager.get().createUser(nextUserName, VUserInfo.FLAG_ADMIN);
                    if (newUserInfo == null) {
                        throw new IllegalStateException();
                    }
                }
                boolean success = VirtualCore.get().installPackageAsUser(nextUserId, info.packageName);
                if (!success) {
                    throw new IllegalStateException();
                }
            } else {
                InstallResult res = mRepo.addVirtualApp(info);
                if (!res.isSuccess) {
                    throw new IllegalStateException();
                }
            }
        }).then((res) -> {
            addResult.appData = PackageAppDataStorage.get().acquire(info.packageName);
        }).done(res -> {
            boolean multipleVersion = addResult.justEnableHidden && addResult.userId != 0;
            if (!multipleVersion) {
                PackageAppData data = addResult.appData;
                data.isLoading = true;
                //mView.addAppToLauncher(data);
                handleOptApp(data, info.packageName, true);


                addShortcut(data);
            } else {
                MultiplePackageAppData data = new MultiplePackageAppData(addResult.appData, addResult.userId);
                data.isLoading = true;
                //mView.addAppToLauncher(data);
                handleOptApp(data, info.packageName, false);
            }
        });
    }

    @ReactMethod
    public void addApp(String packageName) {
        Toast.makeText(getReactApplicationContext(), "Adding: " + packageName, Toast.LENGTH_LONG).show();

        // Quick and Dirty copy paste way to retreive path that is needed for adding package
        List<PackageInfo> pkgList = reactContext.getPackageManager().getInstalledPackages(0);
        String path = null;
        for (PackageInfo pkg : pkgList) {
            if (pkg.packageName.equals(packageName)) {
                ApplicationInfo ai = pkg.applicationInfo;
                path = ai.publicSourceDir != null ? ai.publicSourceDir : ai.sourceDir;
                break;
            }
        }

        AppInfoLite info = new AppInfoLite(packageName, path, true, false);


        class AddResult {
            private PackageAppData appData;
            private int userId;
            private boolean justEnableHidden;
        }
        AddResult addResult = new AddResult();
        VUiKit.defer().when(() -> {
            InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(info.packageName, 0);
            addResult.justEnableHidden = installedAppInfo != null;
            if (addResult.justEnableHidden && !info.isHook) {
                int[] userIds = installedAppInfo.getInstalledUsers();
                int nextUserId = userIds.length;

                for (int i = 0; i < userIds.length; i++) {
                    if (userIds[i] != i) {
                        nextUserId = i;
                        break;
                    }
                }
                addResult.userId = nextUserId;

                if (VUserManager.get().getUserInfo(nextUserId) == null) {
                    // user not exist, create it automatically.
                    String nextUserName = "Space " + (nextUserId + 1);
                    VUserInfo newUserInfo = VUserManager.get().createUser(nextUserName, VUserInfo.FLAG_ADMIN);
                    if (newUserInfo == null) {
                        throw new IllegalStateException();
                    }
                }
                boolean success = VirtualCore.get().installPackageAsUser(nextUserId, info.packageName);
                if (!success) {
                    throw new IllegalStateException();
                }
            } else {
                InstallResult res = mRepo.addVirtualApp(info);
                if (!res.isSuccess) {
                    throw new IllegalStateException();
                }
            }
        }).then((res) -> {
            addResult.appData = PackageAppDataStorage.get().acquire(info.packageName);
        }).done(res -> {
            boolean multipleVersion = addResult.justEnableHidden && addResult.userId != 0;
            if (!multipleVersion) {
                PackageAppData data = addResult.appData;
                data.isLoading = true;
                //mView.addAppToLauncher(data);
                handleOptApp(data, info.packageName, true);


                addShortcut(data);
            } else {
                MultiplePackageAppData data = new MultiplePackageAppData(addResult.appData, addResult.userId);
                data.isLoading = true;
                //mView.addAppToLauncher(data);
                handleOptApp(data, info.packageName, false);
            }
        });






    }

    private void addShortcut(AppData data) {
        //create Launcher
        VirtualCore.OnEmitShortcutListener listener = new VirtualCore.OnEmitShortcutListener() {
            @Override
            public Bitmap getIcon(Bitmap originIcon) {
                return originIcon;
            }

            @Override
            public String getName(String originName) {
                return originName + " (Avare)";
            }
        };
        if (data instanceof PackageAppData) {
            VirtualCore.get().createShortcut(0, ((PackageAppData) data).packageName, listener);
        } else if (data instanceof MultiplePackageAppData) {
            MultiplePackageAppData appData = (MultiplePackageAppData) data;
            VirtualCore.get().createShortcut(appData.userId, appData.appInfo.packageName, listener);
        }
    }

    private void handleOptApp(AppData data, String packageName, boolean needOpt) {
        VUiKit.defer().when(() -> {
            long time = System.currentTimeMillis();
            if (needOpt) {
                try {
                    VirtualCore.get().preOpt(packageName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            time = System.currentTimeMillis() - time;
            if (time < 1500L) {
                try {
                    Thread.sleep(1500L - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).done((res) -> {
            if (data instanceof PackageAppData) {
                ((PackageAppData) data).isLoading = false;
                ((PackageAppData) data).isFirstOpen = true;
            } else if (data instanceof MultiplePackageAppData) {
                ((MultiplePackageAppData) data).isLoading = false;
                ((MultiplePackageAppData) data).isFirstOpen = true;
            }
            //mView.refreshLauncherItem(data);
        });
    }

    @ReactMethod
    public void startAvareBox() {
        ReactApplicationContext context = getReactApplicationContext();
        // Set up the intent
        Intent i = new Intent(context, SplashActivity.class);
        // Launch It
        context.startActivity(i);
    }

    @ReactMethod
    public void removeApp(String packageName) {
        Toast.makeText(getReactApplicationContext(), "Removing: " + packageName, Toast.LENGTH_LONG).show();

        // TODO: Shortcut removal doesn't work as intended
        //VirtualCore.get().removeShortcut(0, packageName, null, null);

        mRepo.removeVirtualApp(packageName, 0);

    }


}
