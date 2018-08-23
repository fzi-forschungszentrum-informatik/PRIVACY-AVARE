package app.avare.hidelocation.geoApi;

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

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import app.avare.hidelocation.util.PointGeneration;


public class FakeLocationListener implements LocationListener {

    private PointGeneration pointGeneration;
    private LocationListener listener;

    public FakeLocationListener(PointGeneration generation, LocationListener
            listener) {
        this.pointGeneration = generation;
        this.listener = listener;
    }

    /**
     * Override location with fake location computed by {@link PointGeneration}.
     * Notify real listener
     * @param location current location
     */
    @Override
    public void onLocationChanged(Location location) {
        LatLng fake = pointGeneration.getNextPosition(location);
        location.setLatitude(fake.latitude);
        location.setLongitude(fake.longitude);
        //fire actual listener with modified location
        listener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //nothing to do, delegate to listener
        listener.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(String provider) {
        listener.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        listener.onProviderDisabled(provider);
    }

}
