package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.plugin.cameraFilterPlugin.util.ByteCreator;
import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_setPreviewCallback {

    public static String className = "android.hardware.Camera";
    public static String methodName = "setPreviewCallback";
    public static String methodSig = "(Landroid/hardware/Camera$PreviewCallback;)V";

    private static StateMachine stateMachine;
    private static ByteCreator byteCreator;

    public static void hook(Camera camera, Camera.PreviewCallback previewCallback) {
        Log.d(TAG, "previewCallback hooked");
        byteCreator = new ByteCreator();
        previewCallback.onPreviewFrame(byteCreator.getJPEGArray(ByteCreator.PictureType.COLORED), camera);
    }


    public static void backup(Camera camera, Camera.PreviewCallback previewCallback) {
    }
}
