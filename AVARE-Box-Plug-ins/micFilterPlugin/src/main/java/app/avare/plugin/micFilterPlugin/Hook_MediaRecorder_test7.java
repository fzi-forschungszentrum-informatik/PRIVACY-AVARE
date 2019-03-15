package app.avare.plugin.micFilterPlugin;

import android.media.AudioRecord;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.MediaController;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_MediaRecorder_test7 {
    public static String className = "android.media.session.MediaController";
    public static String methodName = "sendCommand";
    public static String methodSig = "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/ResultReceiver;)V";

    private static String filePath = null;

    public static void hook(MediaController thiz, String s, Bundle b, ResultReceiver r) {
        Log.d(TAG, "Hoooooked 7");
        backup(thiz, s, b, r);

    }

    public static void backup(MediaController thiz, String s, Bundle b, ResultReceiver r) {
    }


}
