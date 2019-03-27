package app.avare.plugin.apiLogPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if MediaRecorder is prepared to record video.
 */
public class Hook_MediaRecorder_1 {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "setVideoSource";
    public static String methodSig = "(I)V";

    private static LogWriter logWriter;

    public static void hook(MediaRecorder thiz, int i) {
        Log.d(TAG, "MediaRecorder setVideoSource hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaRecorder called - Video source set");
        backup(thiz, i);
    }

    public static void backup(MediaRecorder thiz, int i) {
        thiz.setVideoSource(i);
    }
}
