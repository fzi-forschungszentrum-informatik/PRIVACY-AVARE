package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test5 {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "read";
    public static String methodSig = "([SI)I";

    private static String filePath = null;

    public static int hook(AudioRecord thiz, short[] s, int i) {
        Log.d(TAG, "Hoooooked 5");
        return (int) backup(thiz, s, i);

    }

    public static int backup(AudioRecord thiz, short[] s, int i) {
       return thiz.getAudioSource();
    }
}
