package app.avare.plugin.benchmarkPlugin;

import android.media.MediaCodec;
import android.net.rtp.AudioGroup;
import android.util.Log;

import app.avare.plugin.benchmarkFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if camera2 API opens a camera.
 */
public class Hook_AudioGroup {
    public static String className = "android.net.rtp.AudioGroup";
    public static String methodName = "sendDtmf";
    public static String methodSig = "(I)V";

    private static LogWriter logWriter;

    public static void hook(AudioGroup thiz, int event) {
        Log.d(TAG, "AudioGroup hooked");
        logWriter = new LogWriter();
        logWriter.addLine("AudioGroup called");
        backup(thiz, event);

    }

    public static void backup(AudioGroup thiz, int event) {

    }
}
