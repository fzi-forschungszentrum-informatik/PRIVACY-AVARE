package app.avare.plugin.microphoneHookPlugin;

import android.media.MediaRecorder;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;


/**
 * Created by liuruikai756 on 30/03/2017.
 */

public class Hook_MediaRecorder_setOutPutFile {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "setOutputFile";
    public static String methodSig = "(Ljava/lang/String;)V";

    public static String filePath;

    public static void hook(MediaRecorder thiz, String path) {
        filePath = path;
        Log.d(TAG, filePath);
        backup(thiz, path);
    }

    public static void backup(MediaRecorder thiz, String path) {
        thiz.setOutputFile(path);
    }
}
