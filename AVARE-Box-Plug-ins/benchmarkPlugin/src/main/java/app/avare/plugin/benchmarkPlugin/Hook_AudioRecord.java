package app.avare.plugin.benchmarkPlugin;

import android.media.AudioRecord;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_AudioRecord {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "startRecording";
    public static String methodSig = "()V";


    public static void hook(AudioRecord thiz) {
        Log.d(TAG, "AudioRecord hooked");
        backup(thiz);

    }

    public static void backup(AudioRecord thiz) {

    }
}
