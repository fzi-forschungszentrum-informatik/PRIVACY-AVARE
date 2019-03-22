package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    public static void hook(Camera camera, final SurfaceHolder holder) {
        Log.d(TAG, "setPreviewDisplay hooked");
        stateMachine = new StateMachine();
        if (stateMachine.getCameraState() == CameraState.BLOCKED) {
            return;
        }
        savedHolder = holder;

        backup(camera, new SurfaceHolder() {

            Canvas canvas;

           @Override
            public void addCallback(Callback callback) {
                holder.addCallback(callback);
            }

            @Override
            public void removeCallback(Callback callback) {
                holder.removeCallback(callback);
            }

            @Override
            public boolean isCreating() {
                return holder.isCreating();
            }

            @Override
            public void setType(int type) {
                holder.setType(type);
            }

            @Override
            public void setFixedSize(int width, int height) {
                holder.setFixedSize(width, height);
            }

            @Override
            public void setSizeFromLayout() {
                holder.setSizeFromLayout();
            }

            @Override
            public void setFormat(int format) {
                holder.setFormat(format);
            }

            @Override
            public void setKeepScreenOn(boolean screenOn) {
                holder.setKeepScreenOn(screenOn);
            }

            @Override
            public Canvas lockCanvas() {
                Log.d(TAG, "Holder lockCanvas hooked");
                canvas = holder.lockCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getWidth(), paint);
                return new Canvas();
            }

            @Override
            public Canvas lockCanvas(Rect dirty) {
                return new Canvas();
            }

            @Override
            public void unlockCanvasAndPost(Canvas canvas) {
                holder.unlockCanvasAndPost(this.canvas);
            }

            @Override
            public Rect getSurfaceFrame() {
                return holder.getSurfaceFrame();
            }

            @Override
            public Surface getSurface() {
                Log.d(TAG, "Holder getSurface hooked");
                return holder.getSurface();
            }
        });
    }

    public static SurfaceHolder getHolder() {
        return savedHolder;
    }

    public static void backup(Camera camera, SurfaceHolder holder) {
    }
}
