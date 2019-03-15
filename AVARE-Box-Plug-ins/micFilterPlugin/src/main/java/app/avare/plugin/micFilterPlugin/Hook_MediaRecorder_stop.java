package app.avare.plugin.micFilterPlugin;

import android.media.MediaRecorder;

import app.avare.statemachinelib.StateMachine;


public class Hook_MediaRecorder_stop {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "stop";
    public static String methodSig = "()V";

    private static StateMachine stateMachine;

    public static void hook(MediaRecorder thiz) {

    }

    public static void backup(MediaRecorder thiz) {
        thiz.stop();
    }
}
