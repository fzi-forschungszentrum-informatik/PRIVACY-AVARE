package app.avare.hidelocation.worker;

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

import app.avare.lib.configparser.JSONParser;

/*
this class only stores the radius and the boolean 'initialized' -> this can probably be included in Hook_Location_getLatitude by now
also worker is probably not a good package name for this anymore
 */
public class Datahandler {

    private static boolean initialized = false;

    private static int radiusMin = 0; //no min radius at the moment
    private static int radiusMax; //reads radius from the config json


    public static int getRadiusMin() {
        return radiusMin;
    }

    public static int getRadiusMax() {
        JSONParser jp = new JSONParser();
        Log.i("Datahandler", "Returning radius: " + jp.getLocationRadius());
        return jp.getLocationRadius();
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void setInitialized(boolean initialized) {
        Datahandler.initialized = initialized;
    }
}
