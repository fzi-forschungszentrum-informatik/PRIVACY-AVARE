package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.hardware.Camera;
import android.util.Log;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_open {

    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";

    private static StateMachine stateMachine;

    public static Camera hook(int id) {
        stateMachine = new StateMachine();
        if(stateMachine.getCameraState() == CameraState.BLOCKED) {
            return null;
        } else {
            Camera camera =  backup(id);
            camera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                @Override
                public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                    Log.d(TAG, "Gesichter erkannt");
                }
            });
            Log.d(TAG, "Listener set");
            return camera;
        }
    }

    public static Camera backup(int id) {
        return Camera.open(id);
    }
}
