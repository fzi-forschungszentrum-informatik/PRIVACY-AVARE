package app.avare.plugin.micFilterPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;




public class Hook_MediaRecorder_setOutPutFile {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "setOutputFile";
    public static String methodSig = "(Ljava/lang/String;)V";

    private static String filePath = null;

    public static void hook(MediaRecorder thiz, String path) {
        filePath = path;
        Log.d(TAG, "blablabal"+filePath);
        backup(thiz, path);

    }

    public static String getFilePath() {
        return filePath;
    }

    public static void backup(MediaRecorder thiz, String path) {
        thiz.setOutputFile(path);
    }
}
