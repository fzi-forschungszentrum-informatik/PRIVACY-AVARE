package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test8 {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "read";
    public static String methodSig = "([BII)I";

    private static String filePath = null;

    public static int hook(AudioRecord thiz, byte[] s, int i, int j) {
        Log.d(TAG, "Hoooooked 8");
        return backup(thiz, s, i, j);

    }

    public static int backup(AudioRecord thiz, byte[] s, int i, int j) {
       return thiz.getAudioSource();
    }
}
