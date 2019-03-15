package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.util.Log;

import java.nio.ByteBuffer;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test4 {
    public static String className = "android.media.MediaCodec";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static String filePath = null;

    public static void hook(MediaCodec thiz) {
        Log.d(TAG, "Hoooooked 4");
        backup(thiz);

    }

    public static void backup(MediaCodec thiz) {

    }
}
