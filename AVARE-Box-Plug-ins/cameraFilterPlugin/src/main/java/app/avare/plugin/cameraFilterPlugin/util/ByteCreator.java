package app.avare.plugin.cameraFilterPlugin.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import static app.avare.yahfa.HookInfo.TAG;

public class ByteCreator {

    public enum PictureType {
        BLACK, COLORED
    }

    public byte[] getJPEGArray (PictureType type) {
        Log.d(TAG, "called");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = createBitmap(255, 255, type);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] returnArray = stream.toByteArray();
        bitmap.recycle();
        return returnArray;
    }

    public byte[] getRawArray(PictureType type) {
        return getJPEGArray(type);
    }

    public byte[] getPostViewArray(PictureType type) {
        return getJPEGArray(type);
    }

    private Bitmap createBitmap (int width, int height, PictureType type) {
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        if (type == PictureType.BLACK) {
            canvas.drawColor(Color.BLACK);
            Rect rect = new Rect(0, 0, width, height);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawRect(rect, paint);
        } else {
            int scale = width / 255;
            if (width < 255) {
                scale = 1;
            }
            for (int i = 0; i < width - 2; i++) {
                for (int j = 0; j < height - 2; j++) {
                    Paint paint = new Paint();
                    paint.setARGB(100, i / scale, j /scale, 255);
                    canvas.drawRect(i, j, i + 2.0f, j + 2.0f, paint);
                }
            }
        }

        return image;
    }
}
