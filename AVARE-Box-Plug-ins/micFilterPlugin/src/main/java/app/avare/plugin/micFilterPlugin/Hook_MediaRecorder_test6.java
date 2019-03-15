package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test6 {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "read";
    public static String methodSig = "([FIII)I";

    private static String filePath = null;

    public static int hook(AudioRecord thiz, float[] s, int i, int j, int k) {
        Log.d(TAG, "Hoooooked 6");
        return backup(thiz, s, i, j, k);

    }

    public static int backup(AudioRecord thiz, float[] s, int i, int j, int k) {
       return thiz.getAudioSource();
    }
}
