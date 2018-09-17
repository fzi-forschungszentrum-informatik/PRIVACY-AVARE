package app.avare.plugin.locationFilterPlugin;

/*
 * Created by AVARE Project 2018/08/23
 */

/*
        Copyright 2016-2018 AVARE project team

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

import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.google.android.gms.maps.model.LatLng;

import app.avare.hidelocation.util.PointGeneration;
import app.avare.hidelocation.util.PolyReader;
import app.avare.hidelocation.validator.PolygonValidator;
import app.avare.hidelocation.worker.Datahandler;

/*
even though fields and methods seem unused they can not be deleted, they are used by yahfa to hook calls to functions
 */

public class Hook_Location_getLatitude {
    public static String className = "android.location.Location";
    public static String methodName = "getLatitude";
    public static String methodSig = "()D";


    private static PointGeneration pointGeneration;
    private static PolyReader polyReader;
    private static LatLng currentRealPos;
    private static LatLng currentFakePos;


    public static double hook(Object thiz) {
        Log.i("YAHFA", "Location getLatitude hooked");
        double currentRealLong = Hook_Location_getLongitude.backup(thiz); //call backup to get real position
        double currentRealLat  = Hook_Location_getLatitude.backup(thiz);
        currentRealPos = new LatLng(currentRealLat, currentRealLong);


        if (!Datahandler.isInitialized()){ //if we haven't yet called this function we initialize the fake point generation

            try {
                polyReader = new PolyReader();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set<List<LatLng>> polygons = polyReader.getPolygons();
            PolygonValidator validator = new PolygonValidator(polygons);

            pointGeneration = new PointGeneration(Datahandler.getRadiusMax(), Datahandler.getRadiusMin(), validator);

            currentFakePos = pointGeneration.getNextPosition(currentRealPos);

            Datahandler.setInitialized(true);

            Log.i("HOOKING:", "current lat: " + currentRealPos.latitude);
            Log.i("HOOKING:", "current lon: " + currentRealPos.longitude);
            Log.i("HOOKING:", "fake lat: " + currentFakePos.latitude);
            Log.i("HOOKING:", "fake lon: " + currentFakePos.longitude);


        } else if (Datahandler.isInitialized()) { //we already go an initial fake, keep distance

            LatLng fakePos = pointGeneration.getNextPosition(currentRealPos);

        } else {
            //FATAL ERROR
            Log.i("YAHFA", "Fatal error, no initialization");
        }

        return currentFakePos.latitude;

    }

    public static double backup(Object thiz) {
        Log.i("YAHFA", "Location getLatitude hooked");
        return 0.0;
    }


    public static LatLng getCurrentFakePos() {
        return currentFakePos;
    }
}
