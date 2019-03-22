package app.avare.plugin.benchmarkPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import app.avare.plugin.benchmarkFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if MediaRecorder is prepared to record audio.
 */
public class Hook_MediaRecorder_3 {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static LogWriter logWriter;

    public static void hook(MediaRecorder thiz) {
        Log.d(TAG, "MediaRecorder start hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaRecorder called - start Recording");
        backup(thiz);
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
