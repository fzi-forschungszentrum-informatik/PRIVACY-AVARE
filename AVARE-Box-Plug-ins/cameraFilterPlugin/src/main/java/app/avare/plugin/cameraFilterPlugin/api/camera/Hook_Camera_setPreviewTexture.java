package app.avare.plugin.cameraFilterPlugin.api.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.CameraState;

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
