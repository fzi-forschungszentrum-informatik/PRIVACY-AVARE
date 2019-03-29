package app.avare.yahfa;

import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_open;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewCallback;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewDisplay;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_setPreviewTexture;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_startPreview;
import app.avare.plugin.cameraFilterPlugin.api.camera.Hook_Camera_takePicture;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2019/03/29
 */

public class HookInfo {

    public static String TAG = "HookCam";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = new String[]{
            Hook_Camera_open.class.getName(),
            //if the preview hooks work fine, uncomment this section
            // Hook_Camera_setPreviewDisplay.class.getName(),
            // Hook_Camera_startPreview.class.getName(),
            // Hook_Camera_setPreviewTexture.class.getName(),
            // Hook_Camera_setPreviewCallback.class.getName(),
            Hook_Camera_takePicture.class.getName()
    };

}
