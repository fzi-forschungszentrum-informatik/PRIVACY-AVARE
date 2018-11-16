package app.avare.yahfa;

/**
 * Created by liuruikai756 on 31/03/2017.
 * Edited by AVARE Project 2018/07/26
 */

public class HookInfo {
    static {
        System.loadLibrary("helloJni");
    }
    public static String[] hookItemNames = {
        "app.avare.plugin.demeHookPlugin.Hook_AssetManager_open",
        "app.avare.plugin.Hook_URL_openConnection",
        "app.avare.plugin.Hook_File_init"
    };
}
