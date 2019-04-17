package app.avare.plugin.apiLogPlugin;

import android.media.MediaRecorder;
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
 * Class to write a log entry if MediaRecorder starts recording.
 */
public class Hook_MediaRecorder_2 {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static LogWriter logWriter;

    public static void hook(MediaRecorder thiz) {
        Log.d(TAG, "MediaRecorder start hooked");
        logWriter = new LogWriter();
        logWriter.addLine("MediaRecorder called - start Recording");
        backup(thiz);
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
