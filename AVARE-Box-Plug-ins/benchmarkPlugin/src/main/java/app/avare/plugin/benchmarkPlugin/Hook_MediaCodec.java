package app.avare.plugin.benchmarkPlugin;

import android.media.MediaCodec;
import android.util.Log;

import app.avare.plugin.benchmarkFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;


/**
 * Class to write a log entry if camera2 API opens a camera.
 */
public class Hook_MediaCodec {
    public static String className = "android.media.MediaCodec";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static LogWriter logWriter;

    public static void hook(MediaCodec thiz) {
        Log.d(TAG, "MediaCodec Hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaCodec called");
        backup(thiz);

    }

    public static void backup(MediaCodec thiz) {

    }
}
