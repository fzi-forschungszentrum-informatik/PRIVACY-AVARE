package app.avare.hidelocation.geoApi.broadcastReceiver;

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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import app.avare.hidelocation.geoApi.FakeLocationManager;
import app.avare.hidelocation.util.PointGeneration;


public class ProximityAlertReceiver extends BroadcastReceiver {

    public static final String TAG = "ProximityAlertReceiver";

    public ProximityAlertReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //get real pending intent
        if (!intent.hasExtra(FakeLocationManager.REAL_PENDING_INTENT_EXTRA)) {
            return; //there is no real intent to fire
        }
        if (!isIntentValid(intent)) {
            Log.e(TAG, "intent is not valid");
            return; //ignore this intent
        }

        PendingIntent realPendingIntent = (PendingIntent) intent
                .getParcelableExtra(FakeLocationManager
                        .REAL_PENDING_INTENT_EXTRA);
        Location real = (Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
        if (real == null) {
            //nothing to do
            return;
        }

        PointGeneration pointGeneration = FakeLocationManager
                .getPointGeneration();
        double lat = intent.getDoubleExtra(FakeLocationManager
                .PROXIMITY_ALERT_LAT, 0);
        double lng = intent.getDoubleExtra(FakeLocationManager
                .PROXIMITY_ALERT_LONG, 0);
        float radius = intent.getFloatExtra(FakeLocationManager
                .PROXIMITY_ALERT_RADIUS, 0);

        LatLng center = new LatLng(lat, lng);
        LatLng fake = pointGeneration.getNextPosition(real);
        //compute distance from center
        double dist = SphericalUtil.computeDistanceBetween(center, fake);
        if (dist <= radius) {
            //override location of intent with fake location and fire real intent
            real.setLatitude(fake.latitude);
            real.setLongitude(fake.longitude);
            intent.putExtra(LocationManager.KEY_LOCATION_CHANGED, real);
            //fire intent
            try {
                realPendingIntent.send(context, 0, intent);
            } catch (PendingIntent.CanceledException e) {
                //operation is cancelled, nothing to do
            }
        }

        //if dist > radius we do nothing
        return;
    }

    /**
     * make sure intent has all extras values needed
     * @param intent intent to check
     * @return true iff intent has all values
     */
    private boolean isIntentValid(Intent intent) {
        boolean hasLat = intent.hasExtra(FakeLocationManager
                .PROXIMITY_ALERT_LAT);
        boolean hasLong = intent.hasExtra(FakeLocationManager
                .PROXIMITY_ALERT_LONG);
        boolean hasRadius = intent.hasExtra(FakeLocationManager
                .PROXIMITY_ALERT_RADIUS);

        return hasLat || hasLong || hasRadius;
    }
}
