package app.avare.plugin.benchmarkPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import app.avare.plugin.benchmarkFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if MediaRecorder is prepared to record audio.
 */
public class Hook_MediaRecorder_1 {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "setVideoSource";
    public static String methodSig = "(I)V";

    private static LogWriter logWriter;

    public static void hook(MediaRecorder thiz) {
        Log.d(TAG, "MediaRecorder hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaRecorder called - Video source set");
        backup(thiz);
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
