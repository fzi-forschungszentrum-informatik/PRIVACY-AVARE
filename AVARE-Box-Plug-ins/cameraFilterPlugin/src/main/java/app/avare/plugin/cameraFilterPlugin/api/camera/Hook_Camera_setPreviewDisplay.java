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


/*
 * Created by AVARE Project
 */

/*
        Copyright 2016-2019 AVARE project team
        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/


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

            //doesn't work actually, because method isn't called
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
