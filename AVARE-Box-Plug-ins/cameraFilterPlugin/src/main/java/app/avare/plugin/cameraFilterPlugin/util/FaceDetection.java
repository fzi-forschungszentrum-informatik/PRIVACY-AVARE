package app.avare.plugin.cameraFilterPlugin.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * This class reacts to a picture taken by the Android Camera API and provides an output picture with hidden faces.
 * This is just a prototyp. For productive use you can replace the Android Media FaceDetection with better solutions.
 */
public class FaceDetection implements Camera.PictureCallback {

    private Bitmap picture;
    private boolean pictureAvailable = false;
    private FaceDetector faceDetector;


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d("FaceDetection", "pictureTaken");
        BitmapFactory.Options config = new BitmapFactory.Options();
        config.inPreferredConfig = Bitmap.Config.RGB_565;
        picture = BitmapFactory.decodeByteArray(data, 0, data.length);
        faceDetector = new FaceDetector(picture.getWidth(), picture.getHeight(), 3);
        FaceDetector.Face[] faces = new FaceDetector.Face[3];
        faceDetector.findFaces(picture, faces);
        for (FaceDetector.Face face : faces) {
            if (face != null) {
                Log.d("FaceDetection", "Face detected");
                PointF midEyes = new PointF();
                face.getMidPoint(midEyes);
                drawPixels(midEyes, face.eyesDistance());
            }
        }
        pictureAvailable = true;
    }

    private void drawPixels(PointF midEyes, float eyesDistance) {
        Bitmap mutable = picture.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutable);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Log.d("Face", String.valueOf(picture.getWidth()));
        Log.d("Face", String.valueOf(midEyes));
        Log.d("Face", String.valueOf(eyesDistance));
        canvas.drawRect(midEyes.x - 400, (picture.getHeight() - midEyes.y) + 400, midEyes.x + 400, (picture.getHeight() - midEyes.y) - 400, paint);
        paint.setColor(Color.GREEN);
        canvas.drawPoint(midEyes.x, midEyes.y, paint);
        canvas.rotate(90);
        picture = mutable.copy(Bitmap.Config.ARGB_8888, true);
    }

    public boolean isPictureAvailable() {
        return pictureAvailable;
    }

    public byte[] getPixeledPicture() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] returnArray = stream.toByteArray();
        picture.recycle();
        pictureAvailable = false;
        return  returnArray;
    }

}
