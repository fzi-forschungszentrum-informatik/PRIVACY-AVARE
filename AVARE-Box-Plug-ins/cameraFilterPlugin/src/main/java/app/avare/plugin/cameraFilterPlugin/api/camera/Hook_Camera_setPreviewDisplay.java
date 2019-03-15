package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_setPreviewDisplay {

    public static String className = "android.hardware.Camera";
    public static String methodName = "setPreviewDisplay";
    public static String methodSig = "(Landroid/view/SurfaceHolder;)V";

    private static StateMachine stateMachine;
    private static SurfaceHolder savedHolder;

    public static void hook(Camera camera, SurfaceHolder holder) {
        Log.d(TAG, "setPreviewDisplay hooked");
        stateMachine = new StateMachine();
        if (stateMachine.getCameraState() == CameraState.BLOCKED) {
            return;
        }
        savedHolder = holder;
        backup(camera, holder);
    }

    public static SurfaceHolder getHolder() {
        return savedHolder;
    }

    public static void backup(Camera camera, SurfaceHolder holder) {
    }
}
