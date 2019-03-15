package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_setPreviewTexture {

    public static String className = "android.hardware.Camera";
    public static String methodName = "setPreviewTexture";
    public static String methodSig = "(Landroid/graphics/SurfaceTexture;)V";

    private static StateMachine stateMachine;
    private static SurfaceTexture savedSurface;

    public static void hook(Camera camera, SurfaceTexture surfaceTexture) {
        Log.d(TAG, "setPreviewTexture hooked");
        stateMachine = new StateMachine();
        if (stateMachine.getCameraState() == CameraState.BLOCKED) {
            return;
        }
        savedSurface = surfaceTexture;
        backup(camera, surfaceTexture);
    }

    public static SurfaceTexture getHolder() {
        return savedSurface;
    }

    public static void backup(Camera camera, SurfaceTexture surfaceTexture) {
    }
}
