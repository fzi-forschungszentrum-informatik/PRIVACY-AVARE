package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.graphics.Canvas;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.yahfa.HookInfo.TAG;


public class Hook_Camera_startPreview {

    public static String className = "android.hardware.Camera";
    public static String methodName = "setPreviewDisplay";
    public static String methodSig = "(Landroid/view/SurfaceHolder.class;)V";

    private static StateMachine stateMachine;
    private static SurfaceHolder holder;

    public static void hook(Camera camera, SurfaceHolder holder) {
        Log.d(TAG, "Start preview hooked");
        stateMachine = new StateMachine();
        holder = Hook_Camera_setPreviewDisplay.getHolder();
        switch (stateMachine.getCameraState()) {
            case BLOCKED:
                return;
            case BLACK_PICTURE: blackPicture();
                break;
            case NEUTRAL_PICTURE: neutralPicture();
                break;
            case PIXELED: pixelPicture();
                break;
            case ENABLED: backup(camera, holder);
                break;
        }
    }

    private static void blackPicture() {
        Log.d(TAG, "Start drawing black Preview Frame");
        try {


        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    private static void pixelPicture() {

    }

    private static void neutralPicture() {

    }

    public static void backup(Camera camera, SurfaceHolder holder) {
    }
}
