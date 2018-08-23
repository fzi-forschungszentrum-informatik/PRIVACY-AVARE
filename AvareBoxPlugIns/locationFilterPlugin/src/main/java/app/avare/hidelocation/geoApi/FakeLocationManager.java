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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import app.avare.hidelocation.geoApi.broadcastReceiver.ProximityAlertReceiver;
import app.avare.hidelocation.geoApi.broadcastReceiver.RequestUpdatesReceiver;
import app.avare.hidelocation.util.PointGeneration;

/**
 * This class contains some of the methods {@link LocationManager} offers.
 * But these methods always fuzzify the real location according to {@link PointGeneration}
 */
public class FakeLocationManager {

    /*
        CONSTANTS
     */
    public static final String REAL_PENDING_INTENT_EXTRA = "lab.galaxy" +
            ".geoApi" +
            ".PendingIntent";
    public static final String PROXIMITY_ALERT_LAT = "lab.galaxy.geoApi.LAT";
    public final static String PROXIMITY_ALERT_LONG = "lab.galaxy.geoApi.LONG";
    public final static String PROXIMITY_ALERT_RADIUS = "lab.galaxy.geoApi" +
            ".RADIUS";

    private LocationManager locationManager;
    /**
     * global point generation class, because of two reasons:
     * <p>
     * - there should not be multiple instances, since there is only one real
     * position at a time
     * <p>
     * - pointGeneration should be accessable for Broadcast receiver, in order
     * to compute next position
     */
    private static PointGeneration pointGeneration;
    private Context context;

    private HashMap<PendingIntent, PendingIntent> real2fakeMap;
    private HashMap<LocationListener, FakeLocationListener> listenerMap;


    public FakeLocationManager(LocationManager locationManager,
                               PointGeneration pointGeneration,
                               Context context) {
        this.locationManager = locationManager;
        this.pointGeneration = pointGeneration;
        this.context = context;
        real2fakeMap = new HashMap<>();
        listenerMap = new HashMap<>();
    }

    /**
     * Returns real position after fuzzified with point generation
     * algorithm.
     *
     * @param provider As given to
     *                 {@link LocationManager#getLastKnownLocation(String)}
     * @return fuzzified real position
     * @throws SecurityException If no permission accessing location is granted.
     */
    public Location getLastKnownLocation(String provider) throws
            SecurityException {
        Location location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng pos = new LatLng(lat, lng);
        LatLng returnPos = pointGeneration.getNextPosition(pos);
        location.setLatitude(returnPos.latitude);
        location.setLongitude(returnPos.longitude);

        return location;
    }

    public void addProximityAlert(double latitude, double longitude, float
            radius, long expiration, PendingIntent intent) throws
            SecurityException {

        Intent i = new Intent(context, ProximityAlertReceiver.class);
        i.putExtra(PROXIMITY_ALERT_LAT, latitude);
        i.putExtra(PROXIMITY_ALERT_LONG, longitude);
        i.putExtra(PROXIMITY_ALERT_RADIUS, radius);

        PendingIntent fakePendingIntent = createFakePendingIntent(intent, i);
        locationManager.addProximityAlert(latitude, longitude, radius,
                expiration, fakePendingIntent);
    }

    public void requestLoctionUpdates(String provider, long minTime, float
            minDistance, LocationListener listener) throws SecurityException {
        FakeLocationListener fakeListener = new FakeLocationListener
                (pointGeneration, listener);
        //add fakeListener to locationManager
        locationManager.requestLocationUpdates(provider, minTime,
                minDistance, fakeListener);
    }

    public void requestLocationUpdates(long minTime, float minDistance,
                                       Criteria criteria, LocationListener
                                               listener, Looper looper)
            throws SecurityException {
        FakeLocationListener fakeListener = new FakeLocationListener
                (pointGeneration, listener);
        locationManager.requestLocationUpdates(minTime, minDistance,
                criteria, fakeListener, looper);
    }

    public void requestLocationUpdates(long minTime, float minDistance,
                                       Criteria criteria, PendingIntent
                                               intent) throws
            SecurityException {
        PendingIntent fakePendingIntent = createFakePendingIntent(intent,
                RequestUpdatesReceiver.class);
        locationManager.requestLocationUpdates(minTime, minDistance,
                criteria, fakePendingIntent);

    }

    public void requestLocationUpdates(String provider, long minTime, float
            minDistance, LocationListener listener, Looper looper) throws
            SecurityException {
        FakeLocationListener fakeListener = new FakeLocationListener
                (pointGeneration, listener);
        locationManager.requestLocationUpdates(provider, minTime,
                minDistance, fakeListener, looper);
    }

    public void requestLocationUpdates(String provider, long minTime, float
            minDistance, PendingIntent intent) throws SecurityException {
        PendingIntent fakePendingIntent = createFakePendingIntent(intent,
                RequestUpdatesReceiver.class);
        locationManager.requestLocationUpdates(provider, minTime,
                minDistance, fakePendingIntent);
    }

    public void removeUpdates(PendingIntent intent) {
        PendingIntent fakePendingIntent = real2fakeMap.get(intent);
        if (fakePendingIntent != null) {
            locationManager.removeUpdates(fakePendingIntent);
            real2fakeMap.remove(intent);
        }
    }

    public void removeUpdates(LocationListener listener) {
        FakeLocationListener fakeListener = listenerMap.get(listener);
        if (fakeListener == null) {
            //nothing to do
            return;
        }
        locationManager.removeUpdates(fakeListener);
        listenerMap.remove(listener);
    }

    public static PointGeneration getPointGeneration() {
        return pointGeneration;
    }

    /**
     * creates a pending intent notifying us. We can give this intent to
     * Android, to get the real location. After its modification we can fire
     * the real pending intent we got from user.
     * @param intent real intent of user
     * @param intentTargetClass target class of created intent.
     * @return pending intent targeting intentTargetClass and containing
     * intent as extra (REAL_PENDING_INTENT_EXTRA).
     */
    private PendingIntent createFakePendingIntent(PendingIntent intent,
                                                  Class<?> intentTargetClass) {
        Intent i = new Intent(context, intentTargetClass);
        i.putExtra(REAL_PENDING_INTENT_EXTRA, intent);

        return createFakePendingIntent(intent, i);
    }

    /**
     * creates a pending intent notifying us. {@link #createFakePendingIntent(PendingIntent, Intent)}
     * @param intent real intent of user
     * @param i already created fake intent
     * @return pending intent wrapping i. i has intent as extra.
     * (REAL_PENDING_INTENT_EXTRA).
     */
    private PendingIntent createFakePendingIntent(PendingIntent intent,
                                                  Intent i) {

        i.putExtra(REAL_PENDING_INTENT_EXTRA, intent);
        PendingIntent fakePendingIntent = PendingIntent.getBroadcast(context,
                0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        real2fakeMap.put(intent, fakePendingIntent);

        return fakePendingIntent;
    }

}
