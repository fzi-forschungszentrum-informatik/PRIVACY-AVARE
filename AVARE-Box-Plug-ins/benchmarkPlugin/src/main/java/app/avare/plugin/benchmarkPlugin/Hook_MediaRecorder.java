package app.avare.plugin.benchmarkPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "start";
    public static String methodSig = "()V";

    public static void hook(MediaRecorder thiz) {

        Log.d(TAG, "MediaRecorder hooked");
        backup(thiz);
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
