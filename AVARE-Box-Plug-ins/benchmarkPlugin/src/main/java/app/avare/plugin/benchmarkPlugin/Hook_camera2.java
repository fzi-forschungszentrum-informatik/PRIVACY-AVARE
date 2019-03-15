package app.avare.plugin.benchmarkPlugin;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.util.Log;

import java.util.logging.Handler;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_camera2 {
    public static String className = "android.hardware.camera2.CameraManager";
    public static String methodName = "openCamera";
    public static String methodSig = "(Ljava/lang/String;Landroid/hardware/camera2/CameraDevice$StateCallback;Landroid/os/Handler;)V";


    public static void hook(CameraManager thiz, String s, CameraDevice.StateCallback stateCallback, Handler handler) {
        Log.d(TAG, "camera2 hooked");
        backup(thiz, s, stateCallback, handler);
    }

    public static void backup(CameraManager thiz, String s, CameraDevice.StateCallback stateCallback, Handler handler) {

    }
}
