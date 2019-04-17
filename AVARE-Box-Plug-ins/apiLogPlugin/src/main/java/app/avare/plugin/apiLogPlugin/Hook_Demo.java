package app.avare.plugin.apiLogPlugin;

import android.hardware.Camera;
import android.util.Log;

import app.avare.plugin.apiLogFileWriter.LogWriter;

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


/**
 * Copy this file and replace the method signature.
 */
public class Hook_Demo {

    //Enter the method signature here
    public static String className = "android.hardware.Camera";
    public static String methodName = "open";
    public static String methodSig = "(I)Landroid/hardware/Camera;";

    private static LogWriter logWriter;

    //replace "" with the message, which will be written to the log file
    private static String message = "";

    //change the method signature
    public static Camera hook(int id) {
        logWriter = new LogWriter();
        logWriter.addLine(message);

        //call the backup with correct method signature
        return backup(id);

    }

    //change the method signature
    public static Camera backup(int id) {
        return null;
    }
}
