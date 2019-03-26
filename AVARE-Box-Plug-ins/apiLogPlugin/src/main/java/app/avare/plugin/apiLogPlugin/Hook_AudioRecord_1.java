package app.avare.plugin.apiLogPlugin;

import android.media.AudioRecord;
import android.media.MediaSyncEvent;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if AudioRecord starts recording.
 */
public class Hook_AudioRecord_1 {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "startRecording";
    public static String methodSig = "(Landroid/media/MediaSyncEvent;)V";

    private static LogWriter logWriter;

    public static void hook(AudioRecord thiz, MediaSyncEvent mediaSyncEvent) {
        Log.d(TAG, "AudioRecord hooked");
        logWriter = new LogWriter();
        logWriter.addLine("AudioRecord called");
        backup(thiz, mediaSyncEvent);

    }

    public static void backup(AudioRecord thiz, MediaSyncEvent mediaSyncEvent) {

    }
}
