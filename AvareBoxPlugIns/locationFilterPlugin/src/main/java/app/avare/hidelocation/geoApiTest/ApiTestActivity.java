package app.avare.hidelocation.geoApiTest;

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

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import app.avare.hidelocation.geoApi.FakeLocationManager;
import app.avare.hidelocation.util.PointGeneration;
import app.avare.hidelocation.util.PolyReader;
import app.avare.hidelocation.validator.PolygonValidator;

/**
 * This Activity is just used for testing purposes and is not actually needed.
 * But we cannot place activites in the androidTest project.
 */
public class ApiTestActivity extends AppCompatActivity {

    public static final String ACTION = "GeoApiTest_ACTION";
    public static final String TAG = "API_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permsission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.ACCESS_FINE_LOCATION}, 42);
        } else {
            //permission granted, nothing to do
        }

        Intent intent = new Intent(this, ApiTestReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 42,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationManager locationManager = (LocationManager) getSystemService
                (Context
                .LOCATION_SERVICE);
        PolyReader reader = null;
        try {
            reader = new PolyReader(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PolygonValidator validator = new PolygonValidator(reader.getPolygons());
        PointGeneration pointGeneration = new PointGeneration(1000, 100,
                validator);

        FakeLocationManager manager = new FakeLocationManager
                (locationManager, pointGeneration,
                this);
        Location location = manager.getLastKnownLocation(LocationManager
                .GPS_PROVIDER);

        Log.i(TAG, "location: " + location.toString());

        //test Updates
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, pendingIntent);
    }

}
