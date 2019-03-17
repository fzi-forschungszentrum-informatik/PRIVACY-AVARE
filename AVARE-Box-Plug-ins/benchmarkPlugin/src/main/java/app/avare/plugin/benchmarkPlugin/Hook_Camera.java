package app.avare.plugin.benchmarkPlugin;

import android.hardware.Camera;
import android.util.Log;

import app.avare.plugin.benchmarkFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Class to write a log entry if Camera API opens a camera.
 */
public class Hook_Camera {
    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";

    private static LogWriter logWriter;

    public static Camera hook(int id) {
        Log.d(TAG, "Camera hooked");
        logWriter = new LogWriter();
        logWriter.addLine("Camera called");
        return backup(id);

    }


    public static Camera backup(int id) {
        return Camera.open(id);
    }
}
