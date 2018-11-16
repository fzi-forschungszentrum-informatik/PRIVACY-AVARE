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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import android.graphics.Bitmap;
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
