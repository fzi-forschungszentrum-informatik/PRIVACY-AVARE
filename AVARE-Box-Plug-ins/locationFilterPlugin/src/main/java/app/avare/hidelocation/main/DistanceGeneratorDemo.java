package app.avare.hidelocation.main;

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

/*
package lab.galaxy.hidelocation.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lab.galaxy.hidelocation.R;
import lab.galaxy.hidelocation.util.PointGeneration;
import lab.galaxy.hidelocation.util.PolyReader;
import lab.galaxy.hidelocation.validator.PolygonValidator;

public class DistanceGeneratorDemo extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLoadedCallback, View.OnClickListener {

    public final static String TAG = "demo";

    public final static double FZI_LAT = 49.011967;
    public final static double FZI_LONG =  8.425999;

    public final static int cameraRadius = 12000; //meters
    public final static int radiusMin = 0; //no min radius at the moment
    public final static int radiusMax = 10000; //10km
    public final static double travelDistance = 2000; //distance between to real positions
    public final static double travelDirection = -90; //in degrees clockwise
    // from north

    private BitmapDescriptor red;
    private BitmapDescriptor lightRed;
    private BitmapDescriptor blue;
    private BitmapDescriptor lightBlue;

    private GoogleMap mMap;
    //private DistanceGeneratorMain posGen;
    private PointGeneration pointGeneration;
    //private CameraUpdate cameraUpdate;

    private Marker currentRealPosition;
    private Marker lastRealPosition;
    private Marker currentFakePosition;
    private Marker lastFakePosition;
    private HashSet<Marker> realMarkers;
    private HashSet<Marker> fakeMarkers;

    private Circle circleReal;
    private Circle circleFake;
    private Circle circleLastReal; //circle of former real position

    private int step;
    private boolean markersVisible;

    private Set<List<LatLng>> polygons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_generator_ui);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //register onClickListener for buttons
        Button nextStepButton = (Button) findViewById(R.id
                .nextMarkerButtonDemo);
        ImageButton resetButton = (ImageButton) findViewById(R.id
                .resetButtonDemo);
        ImageButton toggleMarkersButton = (ImageButton) findViewById(R.id
                .toggleMarkersButton);
        Button nextMarkerButton = (Button) findViewById(R.id.DemoNextMarker);
        nextStepButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        toggleMarkersButton.setOnClickListener(this);
        nextMarkerButton.setOnClickListener(this);

        step = 0;
        markersVisible = false;
        realMarkers = new HashSet<>();
        fakeMarkers = new HashSet<>();

        //posGen = new DistanceGeneratorMain(radiusMin, radiusMax, this);

    }


    */
/**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);

        //initalize bitmaps
        red = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        lightRed = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
        blue = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        lightBlue = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);

        // Add a marker in Sydney and move the camera
       */
/* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); *//*

    }

    @Override
    public void onMapLoaded() {
        LatLng pos = new LatLng(FZI_LAT, FZI_LONG);
        MarkerOptions options = new MarkerOptions().position(pos);
        currentRealPosition = mMap.addMarker(options);
        currentRealPosition.setIcon(red);
        //add marker to set
        realMarkers.add(currentRealPosition);

        LatLngBounds bounds = getBounds(pos, cameraRadius);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.moveCamera(cameraUpdate);

        //show polygons
        addPolygons();

        initialStep();
    }

   private void addPolygons() {
       PolyReader polyReader = null;
       Context context = getApplicationContext();
       try {
           polyReader = new PolyReader(getApplicationContext());
       } catch (IOException e) {
           showToast("Could not read poly files");
           e.printStackTrace();
       }
       polygons = polyReader.getPolygons();
       PolygonValidator validator = new PolygonValidator(polygons);
       pointGeneration = new PointGeneration(radiusMax, radiusMin, validator);
       for (List<LatLng> polygon : polygons) {
           PolygonOptions options = new PolygonOptions();
           options.addAll(polygon);
           mMap.addPolygon(options);
       }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.nextMarkerButtonDemo:
                nextMarkerButtonPressed();
                break;
            case R.id.resetButtonDemo:
                resetButtonPressed();
                break;
            case R.id.toggleMarkersButton:
                markersVisible = !markersVisible;
                setVisibilityOfMarkers(markersVisible);
                break;
            case R.id.DemoNextMarker:
                nextMarkerImediate();
                break;
            default:
                throw new IllegalStateException("unknown view id");
        }
    }

    private void nextMarkerImediate() {
        step = 0;
        updatePositionsAndMarkers();
        nextFakePosition();
        circleLastReal = circleReal;
        //add circle of new real position
        CircleOptions redCircle = new CircleOptions();
        redCircle.center(currentRealPosition.getPosition());
        redCircle.radius(radiusMax);
        redCircle.strokeColor(Color.RED);
        circleReal = mMap.addCircle(redCircle);
        clearScreenAndUpdateCamera();
    }

    private void setVisibilityOfMarkers(boolean visibility) {
        Log.d(TAG, "setting visibility to: " + visibility);
        for (Marker real : realMarkers) {
            real.setVisible(visibility);
        }
        for (Marker fake : fakeMarkers) {
            fake.setVisible(visibility);
        }
        //always show current markers
        currentFakePosition.setVisible(true);
        currentRealPosition.setVisible(true);

        //change label of button
        ImageButton button = (ImageButton) findViewById(R.id
                .toggleMarkersButton);
        if (visibility) {
            button.setImageResource(R.drawable.ic_visibility_black_24dp);
        } else {
            button.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        }
    }

    private void nextMarkerButtonPressed() {
        Log.d(TAG, "nextStepButton pressed");
        switch (step) {
            case 0:
                nextRealPosition();
                step = 1;
                break;
            case 1:
                nextFakePosition();
                step = 2;
                break;
            case 2:
                clearScreenAndUpdateCamera();
                step = 0;
                break;
            default:
                throw new IllegalStateException("step not defined properly");
        }
    }

    private void resetButtonPressed() {
        Log.d(TAG, "resetButton pressed");
        reset();
    }

    private void initialStep() {
        //set circle around real position
        CircleOptions redCircle = new CircleOptions();
        redCircle.center(currentRealPosition.getPosition());
        redCircle.radius(radiusMax);
        redCircle.strokeColor(Color.RED);
        circleReal = mMap.addCircle(redCircle);
        //add fake marker
        LatLng fakePos = pointGeneration.getNextPosition(currentRealPosition
                .getPosition());
        MarkerOptions options = new MarkerOptions().position(fakePos);
        options.icon(blue);
        currentFakePosition = mMap.addMarker(options);
        //add marker to set
        fakeMarkers.add(currentFakePosition);
    }

    private void nextRealPosition() {
        updatePositionsAndMarkers();
        //add circle around currentFakePosition, circleFake is not initalized yet (!)
        CircleOptions blueCircle = new CircleOptions();
        blueCircle.center(currentFakePosition.getPosition());
        blueCircle.radius(travelDistance);
        blueCircle.strokeColor(Color.BLUE);
        circleFake = mMap.addCircle(blueCircle);
        //change style of circleReal
        circleLastReal = circleReal;
        circleLastReal.setStrokeColor(Color.MAGENTA);
        circleLastReal.setStrokePattern(Collections.singletonList((PatternItem) new Dot()));
        //add circle of new real position
        CircleOptions redCircle = new CircleOptions();
        redCircle.center(currentRealPosition.getPosition());
        redCircle.radius(radiusMax);
        redCircle.strokeColor(Color.RED);
        circleReal = mMap.addCircle(redCircle);
    }

    private void nextFakePosition() {
        //LatLng currentFake = currentFakePosition.getPosition();
        //LatLng oldReal = lastRealPosition.getPosition();
        LatLng currentReal = currentRealPosition.getPosition();
        LatLng fakePos = pointGeneration.getNextPosition(currentReal);
        //update markers
        lastFakePosition = currentFakePosition;
        MarkerOptions options = new MarkerOptions();
        options.icon(blue);
        options.position(fakePos);
        currentFakePosition = mMap.addMarker(options);
        //add marker to list
        fakeMarkers.add(currentFakePosition);
    }

    private void clearScreenAndUpdateCamera() {
        lastFakePosition.setVisible(markersVisible);
        lastRealPosition.setVisible(markersVisible);
        if (circleLastReal != null) {
            circleLastReal.remove();
        }
        if (circleFake != null) {
            circleFake.remove();
        }
        //update camera
        //LatLngBounds bound = getBounds(currentRealPosition.getPosition(), radiusMax);
        CameraUpdate update = CameraUpdateFactory.newLatLng(currentRealPosition.getPosition());
        mMap.moveCamera(update);
    }

    private static LatLngBounds getBounds(LatLng center, double radius) {
        double distance = Math.sqrt(2.0) * radius;
        LatLng southwest = SphericalUtil.computeOffset(center, distance, 225.0);
        LatLng northeast = SphericalUtil.computeOffset(center, distance, 45.0);
        return new LatLngBounds(southwest, northeast);
    }

    private void updatePositionsAndMarkers() {
        lastRealPosition = currentRealPosition;
        LatLng realPos = getNextRealPositon(currentRealPosition.getPosition());
        currentRealPosition = mMap.addMarker(new MarkerOptions().position(realPos));
        //change colors of markers
        lastRealPosition.setIcon(lightRed);
        currentFakePosition.setIcon(lightBlue);
        currentRealPosition.setIcon(red);

        realMarkers.add(currentRealPosition);
    }

    private LatLng getNextRealPositon(LatLng currentRealPosition) {
        LatLng next = SphericalUtil.computeOffset(currentRealPosition,
                travelDistance,
                travelDirection);
        return next;
    }

    private void reset() {
        //remove all markers
        for (Marker real : realMarkers) {
            real.remove();
        }
        for (Marker fake : fakeMarkers) {
            fake.remove();
        }
        //clear set
        realMarkers.clear();
        fakeMarkers.clear();
        //remove circles
        if (circleFake != null) {
            circleFake.remove();
        }
        if (circleLastReal != null) {
            circleLastReal.remove();
        }
        if (circleReal != null) {
            circleReal.remove();
        }
        markersVisible = false;
        ((ImageButton) findViewById(R.id.toggleMarkersButton))
                .setImageResource(R.drawable.ic_visibility_off_black_24dp);
        //reset step
        step = 0;
        //start all over
        onMapLoaded();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
*/
