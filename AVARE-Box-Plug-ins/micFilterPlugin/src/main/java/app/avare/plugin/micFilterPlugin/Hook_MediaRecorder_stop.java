package app.avare.plugin.micFilterPlugin;

import android.media.MediaRecorder;

import app.avare.statemachinelib.StateMachine;
import app.avare.statemachinelib.enums.MicrophoneState;

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
        }

    }

    public static void backup(MediaRecorder thiz) {
        thiz.stop();
    }
}
