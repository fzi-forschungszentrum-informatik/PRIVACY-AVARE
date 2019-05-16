package app.avare.yahfa;

import app.avare.plugin.microphoneHookPlugin.Hook_MediaRecorder_setOutPutFile;
import app.avare.plugin.microphoneHookPlugin.Hook_MediaRecorder_start;
import app.avare.plugin.microphoneHookPlugin.Hook_MediaRecorder_stop;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2018/07/26
 */

public class HookInfo {

    public static String TAG = "test123";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = {
            Hook_MediaRecorder_start.class.getName(),
            Hook_MediaRecorder_stop.class.getName(),
            Hook_MediaRecorder_setOutPutFile.class.getName()
    };
}
