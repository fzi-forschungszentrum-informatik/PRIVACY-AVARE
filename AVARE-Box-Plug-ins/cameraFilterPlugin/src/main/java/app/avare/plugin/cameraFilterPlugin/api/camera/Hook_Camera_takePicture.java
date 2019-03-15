package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.plugin.cameraFilterPlugin.util.ByteCreator;
import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.plugin.cameraFilterPlugin.util.ByteCreator.PictureType.BLACK;
import static app.avare.plugin.cameraFilterPlugin.util.ByteCreator.PictureType.COLORED;
import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_takePicture {

    public static String className = "android.hardware.Camera";
    public static String methodName = "takePicture";
    public static String methodSig = "(Landroid/hardware/Camera$ShutterCallback;Landroid/hardware/Camera$PictureCallback;" +
            "Landroid/hardware/Camera$PictureCallback;Landroid/hardware/Camera$PictureCallback;)V";

    private static StateMachine stateMachine;
    private static ByteCreator byteCreator;

    public static void hook(Camera camera, Camera.ShutterCallback shutterCallback, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
        stateMachine = new StateMachine();
        byteCreator = new ByteCreator();
        Log.d(TAG, "take picture hooked");
        if (stateMachine.getCameraState() == CameraState.ENABLED) {
            backup(camera, shutterCallback, raw, postview, jpeg);
            return;
        } else if (stateMachine.getCameraState() == CameraState.BLACK_PICTURE) {
            shutterCallback.onShutter();
          //  raw.onPictureTaken(byteCreator.getRawArray(200, 200, COLORED), camera);
         //   postview.onPictureTaken(byteCreator.getPostViewArray(200, 200, COLORED), camera);
            jpeg.onPictureTaken(byteCreator.getJPEGArray(BLACK), camera);
            return;
        } else if (stateMachine.getCameraState() == CameraState.NEUTRAL_PICTURE) {
            shutterCallback.onShutter();
            //  raw.onPictureTaken(byteCreator.getRawArray(200, 200, COLORED), camera);
            //   postview.onPictureTaken(byteCreator.getPostViewArray(200, 200, COLORED), camera);
            jpeg.onPictureTaken(byteCreator.getJPEGArray(COLORED), camera);
            return;
        } else if (stateMachine.getCameraState() == CameraState.PIXELED) {
            backup(camera, shutterCallback, raw, postview, jpeg);
            return;
        } else if (stateMachine.getCameraState() == CameraState.BLOCKED) {
            return;
        }

        backup(camera, shutterCallback, raw, postview, jpeg);
    }

    public static void backup(Camera camera, Camera.ShutterCallback shutterCallback, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
    }
}
