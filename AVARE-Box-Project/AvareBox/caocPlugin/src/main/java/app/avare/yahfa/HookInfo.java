package app.avare.yahfa;

import app.avare.plugin.caocPlugin.Hook_Activity_onCreate;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2019/04/24
 */

public class HookInfo {

    public static String TAG = "CaocHook";

    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = {
            Hook_Activity_onCreate.class.getName()
    };
}
