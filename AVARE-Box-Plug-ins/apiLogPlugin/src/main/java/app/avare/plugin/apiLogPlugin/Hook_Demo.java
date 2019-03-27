package app.avare.plugin.apiLogPlugin;

import android.hardware.Camera;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Copy this file and replace the method signature.
 */
public class Hook_Demo {

    //Enter the method signature here
    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";

    private static LogWriter logWriter;

    //replace "" with the message, which will be written to the log file
    private static String message = "";

    //change the method signature
    public static Camera hook(int id) {
        logWriter = new LogWriter();
        logWriter.addLine(message);

        //call the backup with correct method signature
        return backup(id);

    }

    //change the method signature
    public static Camera backup(int id) {
        return null;
    }
}
