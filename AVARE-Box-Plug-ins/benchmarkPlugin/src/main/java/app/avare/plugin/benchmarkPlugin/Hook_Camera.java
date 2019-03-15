package app.avare.plugin.benchmarkPlugin;

import android.hardware.Camera;
import android.util.Log;

import static app.avare.yahfa.HookInfo.TAG;




public class Hook_Camera {
    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";



    public static Camera hook(int id) {
        Log.d(TAG, "Camera hooked");
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return backup(id);

    }


    public static Camera backup(int id) {
        return Camera.open(id);
    }
}
