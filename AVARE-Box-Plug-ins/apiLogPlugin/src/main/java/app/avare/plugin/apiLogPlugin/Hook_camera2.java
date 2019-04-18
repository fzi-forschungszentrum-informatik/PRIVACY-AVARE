package app.avare.plugin.apiLogPlugin;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

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
 * Class to write a log entry if camera2 API opens a camera. This method is available from API Level 28
 */
public class Hook_camera2 {
    public static String className = "android.hardware.camera2.CameraManager";
    public static String methodName = "openCamera";
    public static String methodSig = "(Ljava/lang/String;Landroid/hardware/camera2/CameraDevice$StateCallback;Landroid/os/Handler;)V";

    private static LogWriter logWriter;

    public static void hook(CameraManager thiz, String s, CameraDevice.StateCallback stateCallback, Handler handler) {
        Log.d(TAG, "camera2 hooked");
        logWriter = new LogWriter();
        logWriter.addLine("camera2 called");
        backup(thiz, s, stateCallback, handler);
    }

    public static void backup(CameraManager thiz, String s, CameraDevice.StateCallback stateCallback, Handler handler) {

    }
}
