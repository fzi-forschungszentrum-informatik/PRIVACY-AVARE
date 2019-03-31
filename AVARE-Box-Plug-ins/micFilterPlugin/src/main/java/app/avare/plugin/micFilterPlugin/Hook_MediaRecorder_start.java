package app.avare.plugin.micFilterPlugin;


import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import app.avare.statemachinelib.StateMachine;

import static app.avare.yahfa.HookInfo.TAG;

/**
 * hooks the start recording with the desired privacy option
 */

public class Hook_MediaRecorder_start {
    public static String className = "android.media.MediaRecorder";
    public static String methodName = "start";
    public static String methodSig = "()V";

    private static StateMachine stateMachine;

    public static void hook(MediaRecorder thiz) {
        stateMachine = new StateMachine();
        Log.d(TAG, stateMachine.getMicrophoneState().toString());
        switch (stateMachine.getMicrophoneState()) {
            case BLOCKED:
                thiz.reset();
                break;
            case NO_SOUND:
                thiz.reset();
                //writeSoundFile("silence.wav");
                break;
            case NEUTRAL_SOUND:
                thiz.reset();
                //writeSoundFile("swoosh");
                break;
            case ENABLED:
                backup(thiz);
                break;
        }
    }

    //doesn't work as assumed
    private static void writeSoundFile(String soundPath) {
        Log.d(TAG,"start write sound file");
        File f = new File(soundPath);

        InputStream in = Hook_MediaRecorder_start.class.getClassLoader().getResourceAsStream("swoosh.wav");

  /*      try {

           // Log.d(TAG, String.valueOf(test.available()));
            in =new FileInputStream(f);
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName());
            String s = "";
            for ( StackTraceElement x : e.getStackTrace()) {
                s = s + x.toString() + "\n";
            }
            Log.d(TAG, s);
            e.printStackTrace();
        }*/
        OutputStream out;
        Log.d(TAG,"start write sound file2");
        try {
            in = new FileInputStream(new File(soundPath));
            out = new FileOutputStream(new File(Hook_MediaRecorder_setOutPutFile.getFilePath()));
            byte[] buffer = new byte[2048];
            for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
               out.write(buffer);
                Log.d(TAG,"start write sound  file" + length);
            }
        } catch (Exception e) {
            Log.d(TAG,"Hook Audio File writing not sucessful.");
            e.printStackTrace();
            return;
        }
        Log.d(TAG,"Hook Audio File writing sucessful.");
    }

    public static void backup(MediaRecorder thiz) {
        thiz.start();
    }
}
