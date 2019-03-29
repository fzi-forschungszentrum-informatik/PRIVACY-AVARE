package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.hardware.Camera;
import android.util.Log;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Hooks the camera open method.
 */
public class Hook_Camera_open {

    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";

    private static StateMachine stateMachine;

    /**
     * Camera will not open if user completely block the camera.
     */
    public static Camera hook(int id) {
        stateMachine = new StateMachine();
        if(stateMachine.getCameraState() == CameraState.BLOCKED) {
            return null;
        }
        return backup(id);
    }

    public static Camera backup(int id) {
        return Camera.open(id);
    }
}
