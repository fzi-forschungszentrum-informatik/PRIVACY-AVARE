package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.plugin.cameraFilterPlugin.util.ByteCreator;
import app.avare.plugin.cameraFilterPlugin.util.FaceDetection;
import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

import static app.avare.plugin.cameraFilterPlugin.util.ByteCreator.PictureType.BLACK;
import static app.avare.plugin.cameraFilterPlugin.util.ByteCreator.PictureType.COLORED;
import static app.avare.yahfa.HookInfo.TAG;

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




/**
 * This method is called if an app desires to get a picture from the camera.
 * Regarding to the intervention steps the callbacks methods from the callee are called with different data.
 */
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
            jpeg.onPictureTaken(byteCreator.getJPEGArray(BLACK), camera);
            return;
        } else if (stateMachine.getCameraState() == CameraState.NEUTRAL_PICTURE) {
            shutterCallback.onShutter();
            jpeg.onPictureTaken(byteCreator.getJPEGArray(COLORED), camera);
            return;
        } else if (stateMachine.getCameraState() == CameraState.PIXELED) {
            FaceDetection faceDetection = new FaceDetection();
            backup(camera, shutterCallback, raw, postview, faceDetection);
            //Busy waiting, think about better solutions...
            while (!faceDetection.isPictureAvailable());
            jpeg.onPictureTaken(faceDetection.getPixeledPicture(), camera);
            return;
        } else if (stateMachine.getCameraState() == CameraState.BLOCKED) {
            return;
        }

        backup(camera, shutterCallback, raw, postview, jpeg);
    }

    public static void backup(Camera camera, Camera.ShutterCallback shutterCallback, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg) {
    }
}
