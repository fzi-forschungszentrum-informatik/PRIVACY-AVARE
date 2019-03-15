package app.avare.yahfa;

import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_setOutPutFile;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_start;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_stop;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test3;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test4;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test5;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test6;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test7;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test8;
import app.avare.plugin.micFilterPlugin.Hook_MediaRecorder_test9;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2018/07/26
 */

public class HookInfo {

    public static String TAG = "HookMic";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = {
            Hook_MediaRecorder_start.class.getName(),
            Hook_MediaRecorder_stop.class.getName(),
            Hook_MediaRecorder_setOutPutFile.class.getName(),
            Hook_MediaRecorder_test.class.getName(),
            Hook_MediaRecorder_test3.class.getName(),
            Hook_MediaRecorder_test4.class.getName(),
            Hook_MediaRecorder_test5.class.getName(),
            Hook_MediaRecorder_test6.class.getName(),
            Hook_MediaRecorder_test7.class.getName(),
            Hook_MediaRecorder_test8.class.getName(),
            Hook_MediaRecorder_test9.class.getName()
    };
}
