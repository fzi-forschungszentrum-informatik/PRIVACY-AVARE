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
        "app.avare.plugin.contactsFilterPlugin.Hook_Cursor"
    };
}
