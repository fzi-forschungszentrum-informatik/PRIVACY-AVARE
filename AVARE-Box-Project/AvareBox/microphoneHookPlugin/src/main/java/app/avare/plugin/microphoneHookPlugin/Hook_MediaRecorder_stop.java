package app.avare.plugin.microphoneHookPlugin;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import de.fzi.zielke.statemachine.StateMachine;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Created by liuruikai756 on 30/03/2017.
 */

public class Hook_MediaRecorder_stop {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "stop";
    public static String methodSig = "()V";

    private static StateMachine stateMachine;

    public static void hook(MediaRecorder thiz) {
        stateMachine = StateMachine.getInstance();
        Log.d(TAG,  stateMachine.getAppState().toString());
        switch (stateMachine.getAppState()) {
            case ENABLED:
                backup(thiz);
                break;
            case FILTERED:
                thiz.reset();
                break;
            case BLOCKED:
                thiz.reset();
                break;
        }
    }

    public static void backup(MediaRecorder thiz) {
        thiz.stop();
    }
}
