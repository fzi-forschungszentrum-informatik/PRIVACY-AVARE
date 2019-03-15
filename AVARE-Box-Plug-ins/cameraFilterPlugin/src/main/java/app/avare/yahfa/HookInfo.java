package app.avare.yahfa;

import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_open;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewCallback;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewDisplay;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewTexture;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_startPreview;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_takePicture;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2018/07/26
 */

public class HookInfo {

    public static String TAG = "HookCam";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = new String[]{
            /*Hook_MediaRecorder_test.class.getName(),
            Hook_MediaRecorder_test1.class.getName(),
            Hook_MediaRecorder_test2.class.getName(),
            Hook_MediaRecorder_test3.class.getName(),
            Hook_MediaRecorder_test4.class.getName(),
            Hook_MediaRecorder_test5.class.getName(),
            Hook_MediaRecorder_test6.class.getName(),
            Hook_MediaRecorder_test7.class.getName(),
            Hook_MediaRecorder_test8.class.getName(),
            Hook_MediaRecorder_test9.class.getName()*/

            Hook_Camera_open.class.getName(),
            Hook_Camera_setPreviewDisplay.class.getName(),
            Hook_Camera_startPreview.class.getName(),
            Hook_Camera_takePicture.class.getName(),
            Hook_Camera_setPreviewTexture.class.getName(),
            Hook_Camera_setPreviewCallback.class.getName()
    };

}
