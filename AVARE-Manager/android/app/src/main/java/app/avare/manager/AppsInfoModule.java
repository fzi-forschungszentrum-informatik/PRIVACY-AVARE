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
package app.avare.manager;

import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Nullable;

public class AppsInfoModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public AppsInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AppsInfo";
    }

    @ReactMethod
    public void getApps(Callback errorCallback, Callback successCallback) {
        try {
            List<PackageInfo> packages = this.reactContext.getPackageManager().getInstalledPackages(0);

            WritableArray jsArray = Arguments.createArray();
            for (final PackageInfo p : packages) {
                //only get non system apps
                if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    WritableMap jsObject = Arguments.createMap();
                    jsObject.putString("package", p.packageName);
                    String appName = "";
                    try {
                        appName = reactContext.getPackageManager().getApplicationInfo(p.packageName, 0)
                                .loadLabel(reactContext.getPackageManager()).toString();
                    } catch (NameNotFoundException e) {
                        appName = p.packageName;
                    }
                    jsObject.putString("name", appName);
                    jsArray.pushMap(jsObject);
                }
            }
            successCallback.invoke(jsArray);

        } catch (Exception e) {
            errorCallback.invoke(e.getMessage());

        }
    }
    //private List<String> getApps() {
    //    List<PackageInfo> packages = this.reactContext
    //        .getPackageManager()
    //        .getInstalledPackages(0);

    //    List<String> ret = new ArrayList<>();
    //    for (final PackageInfo p: packages) {
    //        ret.add(p.packageName);
    //    }
    //    return ret;
    //}

    //private List<String> getNonSystemApps() {
    //    List<PackageInfo> packages = this.reactContext
    //        .getPackageManager()
    //        .getInstalledPackages(0);

    //    List<String> ret = new ArrayList<>();
    //    for (final PackageInfo p: packages) {
    //        if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
    //            ret.add(p.packageName);
    //        }
    //    }
    //    return ret;
    //} 
    
    //@Override
    //public @Nullable Map<String, Object> getConstants() {
    //    Map<String, Object> constants = new HashMap<>();

    //    constants.put("getApps", getApps());
    //    constants.put("getNonSystemApps", getNonSystemApps());
    //    return constants;
    //}
}
