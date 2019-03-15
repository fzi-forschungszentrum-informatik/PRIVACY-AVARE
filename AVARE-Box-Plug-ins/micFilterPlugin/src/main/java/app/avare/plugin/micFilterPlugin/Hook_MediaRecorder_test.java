package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.media.MediaSyncEvent;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;




public class Hook_MediaRecorder_test {
    public static String className = "android.media.AudioRecord";
    public static String methodName = "startRecording";
    public static String methodSig = "(Landroid/media/MediaSyncEvent;)V";

    private static String filePath = null;

    public static void hook(AudioRecord thiz, MediaSyncEvent m) {
        Log.d(TAG, "Hoooooked");
        backup(thiz, m);

    }

    public static void backup(AudioRecord thiz, MediaSyncEvent m) {
       thiz.startRecording(m);
    }
}
