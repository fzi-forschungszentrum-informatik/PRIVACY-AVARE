package app.avare.yahfa;

import app.avare.plugin.apiLogPlugin.Hook_Camera;
import app.avare.plugin.apiLogPlugin.Hook_MediaRecorder;
import app.avare.plugin.apiLogPlugin.Hook_MediaRecorder_1;
import app.avare.plugin.apiLogPlugin.Hook_MediaRecorder_2;
import app.avare.plugin.apiLogPlugin.Hook_camera2;
import app.avare.plugin.apiLogPlugin.Hook_AudioRecord;
import app.avare.plugin.apiLogPlugin.Hook_camera2_1;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2018/07/26
 */

public class HookInfo {

    public static String TAG = "Benchmark";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = {
            Hook_MediaRecorder.class.getName(),
            Hook_MediaRecorder_1.class.getName(),
            Hook_MediaRecorder_2.class.getName(),
            Hook_MediaRecorder_2.class.getName(),
            Hook_camera2.class.getName(),
            Hook_camera2_1.class.getName(),
            Hook_Camera.class.getName(),
            Hook_AudioRecord.class.getName()
    };
}
