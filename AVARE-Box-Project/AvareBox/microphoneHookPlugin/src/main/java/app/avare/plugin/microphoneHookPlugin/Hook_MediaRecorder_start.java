package app.avare.plugin.microphoneHookPlugin;

import android.content.res.Resources;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;

import de.fzi.zielke.statemachine.StateMachine;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * Created by liuruikai756 on 30/03/2017.
 */

public class Hook_MediaRecorder_start {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static StateMachine stateMachine;

    public static void hook(MediaRecorder thiz) {

        Log.d(TAG,  "called");
        stateMachine = StateMachine.getInstance();
        Log.d(TAG,  stateMachine.getAppState().toString());
        switch (stateMachine.getAppState()) {
            case BLOCKED:
                break;
            case FILTERED:
                filter();
                break;
            case ENABLED:
                backup(thiz);
                break;
        }
    }

    private static void filter() {
       switch (stateMachine.getMicrophoneState()) {
           case BLOCKED:
               break;
           case NO_SOUND:
               writeSoundFile(R.raw.silence);
               break;
           case NEUTRAL_SOUND:
               writeSoundFile(R.raw.swoosh);
               break;
       }
    }

    private static void writeSoundFile(int soundID) {
        InputStream in = Resources.getSystem().openRawResource(soundID);
        OutputStream out;
        try {
            out = new FileOutputStream(new File(Hook_MediaRecorder_setOutPutFile.filePath));
            byte[] buffer = new byte[2048];
            for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
               out.write(buffer);
            }
        } catch (IOException e) {
            Log.e(TAG,"Hook Audio File writing not sucessful.");
            e.printStackTrace();
            return;
        }
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
