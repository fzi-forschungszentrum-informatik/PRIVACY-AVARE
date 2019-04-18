package app.avare.plugin.micFilterPlugin;

import android.media.MediaRecorder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.MicrophoneState;

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
 * otherwise there will be an error regarding MediaRecorder state
 */
public class Hook_MediaRecorder_stop {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "stop";
    public static String methodSig = "()V";

    private static StateMachine stateMachine;

    public static void hook(MediaRecorder thiz) {

        stateMachine = new StateMachine();

        if (stateMachine.getMicrophoneState().equals(MicrophoneState.ENABLED)) {
            backup(thiz);
        } else {
            thiz.reset();
        }

    }

    public static void backup(MediaRecorder thiz) {
        thiz.stop();
    }
}
