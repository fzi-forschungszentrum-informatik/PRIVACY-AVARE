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

import com.google.android.gms.maps.model.LatLng;

import app.avare.hidelocation.geoApi.FakeLocationManager;
import app.avare.hidelocation.util.PointGeneration;

public class RequestUpdatesReceiver extends BroadcastReceiver {

    public RequestUpdatesReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.hasExtra(FakeLocationManager.REAL_PENDING_INTENT_EXTRA)) {
            return; //no real intent to fire
        }
        PendingIntent realPendingIntent = (PendingIntent) intent
                .getParcelableExtra(FakeLocationManager
                        .REAL_PENDING_INTENT_EXTRA);
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
            PointGeneration pointGeneration = FakeLocationManager
                    .getPointGeneration();
            Location loc = (Location) intent.getParcelableExtra(LocationManager
                    .KEY_LOCATION_CHANGED);
            LatLng fakePos = pointGeneration.getNextPosition(loc);
            loc.setLatitude(fakePos.latitude);
            loc.setLongitude(fakePos.longitude);
            //add location to intent
            intent.putExtra(LocationManager.KEY_LOCATION_CHANGED, loc);
        }
        //fire real intent
        try {
            realPendingIntent.send(context, 0, intent);
        } catch (PendingIntent.CanceledException e) {
            //operation is cancelled, nothing to do
            return;
        }
    }

}
