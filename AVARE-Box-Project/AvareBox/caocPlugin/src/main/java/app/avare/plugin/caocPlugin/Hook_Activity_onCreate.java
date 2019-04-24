package app.avare.plugin.caocPlugin;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import cat.ereza.customactivityoncrash.provider.CaocInitProvider;

import static app.avare.yahfa.HookInfo.TAG;


/**
 * Created by liuruikai756 on 30/03/2017.
 */

public class Hook_Activity_onCreate {
    public static String className = "android.app.Activity";
    public static String methodName = "onCreate";
    public static String methodSig = "(Landroid/os/Bundle;)V";

    public static void hook(Activity thiz, Bundle b) {

        //copied from GitHub description of CAOC Library
        //https://github.com/Ereza/CustomActivityOnCrash
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(false) //default: true
                .showErrorDetails(false) //default: true
                .showRestartButton(false) //default: true
                .logErrorOnRestart(false) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .restartActivity(null) //default: null (your app's launch activity)
                .errorActivity(null) //default: null (default error activity)
                .eventListener(null) //default: null
                .apply();
        CustomActivityOnCrash customActivityOnCrash = new CustomActivityOnCrash();
        customActivityOnCrash.install(thiz.getApplicationContext());
        Log.d(TAG, "caoc intialized");
        //call the original onCreate
        backup(thiz, b);
    }

    public static void backup(Activity thiz, Bundle b) {
    }

}
