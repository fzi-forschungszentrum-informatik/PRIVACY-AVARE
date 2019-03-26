package app.avare.plugin.apiLogPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if MediaRecorder is prepared to record audio.
 */
public class Hook_MediaRecorder {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "setAudioSource";
    public static String methodSig = "(I)V";

    private static LogWriter logWriter;

    public static void hook(MediaRecorder thiz, int i) {
        Log.d(TAG, "MediaRecorder setAudioSource hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaRecorder called - Audio source set");
        backup(thiz, i);
    }

    public static void backup(MediaRecorder thiz, int i) {
        thiz.setAudioSource(i);
    }
}
