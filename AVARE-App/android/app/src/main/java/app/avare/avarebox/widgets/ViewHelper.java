package app.avare.avarebox.widgets;

import app.avare.avarebox.VApp;
import app.avare.avaremanager.MainApplication;

/**
 * @author Lody
 */
public class ViewHelper {

    public static int dip2px(float dpValue) {
        //final float scale = VApp.getApp().getResources().getDisplayMetrics().density;
        final float scale = MainApplication.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
