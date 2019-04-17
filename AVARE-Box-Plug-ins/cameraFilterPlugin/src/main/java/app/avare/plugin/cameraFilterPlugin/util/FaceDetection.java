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

/*
 * Created by AVARE Project
 */

/*
        Copyright 2016-2019 AVARE project team
        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/



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
        picture = picture.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(picture);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Log.d("Face", String.valueOf(picture.getWidth()));
        Log.d("Face", String.valueOf(midEyes));
        Log.d("Face", String.valueOf(eyesDistance));
        canvas.drawRect(midEyes.x - 200, midEyes.y - 200, midEyes.x + 200, midEyes.y + 200, paint);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(midEyes.x, midEyes.y, 100, paint);
        canvas.rotate(90);
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
