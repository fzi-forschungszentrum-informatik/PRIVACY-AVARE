package app.avare.plugin.benchmarkPlugin;

import android.media.MediaCodec;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;




public class Hook_MediaCodec {
    public static String className = "android.media.MediaCodec";
    public static String methodName = "start";
    public static String methodSig = "()V";

    public static void hook(MediaCodec thiz) {
        Log.d(TAG, "MediaCodec Hooked");
        backup(thiz);

    }

    public static void backup(MediaCodec thiz) {

    }
}
