package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.util.Log;

import java.nio.ByteBuffer;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test9 {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "read";
    public static String methodSig = "(Ljava/nio/ByteBuffer;I)I";

    private static String filePath = null;

    public static int hook(AudioRecord thiz, ByteBuffer s, int i) {
        Log.d(TAG, "Hoooooked 9");
        return backup(thiz, s, i);

    }

    public static int backup(AudioRecord thiz, ByteBuffer s, int i) {
       return thiz.getAudioSource();
    }
}
