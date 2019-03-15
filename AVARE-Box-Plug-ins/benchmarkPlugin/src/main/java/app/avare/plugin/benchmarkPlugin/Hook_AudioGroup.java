package app.avare.plugin.benchmarkPlugin;

import android.media.MediaCodec;
import android.net.rtp.AudioGroup;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_AudioGroup {
    public static String className = "android.net.rtp.AudioGroup";
    public static String methodName = "sendDtmf";
    public static String methodSig = "(I)V";

    public static void hook(AudioGroup thiz, int event) {
        Log.d(TAG, "AudioGroup hooked");
        backup(thiz, event);

    }

    public static void backup(AudioGroup thiz, int event) {

    }
}
