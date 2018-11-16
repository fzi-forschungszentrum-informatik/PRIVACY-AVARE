package app.avare.plugin.demeHookPlugin;

import android.util.Log;

import java.io.InputStream;

/**
 * Created by liuruikai756 on 31/03/2017.
 */

public class Hook_AssetManager_open {
    public static String className = "android.content.res.AssetManager";
    public static String methodName = "open";
    public static String methodSig = "(Ljava/lang/String;)Ljava/io/InputStream;";
    public static InputStream hook(Object thiz, String fileName) {
        Log.w("YAHFA", "open asset "+fileName);
        return origin(thiz, fileName);
    }

    public static InputStream origin(Object thiz, String msg) {
        Log.w("YAHFA", "should not be here");
        return null;
    }
}
