package app.avare.hidelocation.main;

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

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

import app.avare.hidelocation.util.CircleIntersection;
import app.avare.hidelocation.util.Pair;
import app.avare.hidelocation.validator.PolygonValidator;

public class DistanceGeneratorMain {

    private double radiusMin;
    private double radiusMax;
    private Activity ui;
    private PolygonValidator validator;

    public DistanceGeneratorMain(double radiusMin, double radiusMax, Activity
            ui) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
        this.ui = ui;
    }

    public LatLng getInitialGuess(LatLng realPosition) {
        double angle = Math.random() * 360;
        double r = Math.random();
        double distance = radiusMin + r * (radiusMax - radiusMin);

        LatLng initialGuess = SphericalUtil.computeOffset(realPosition, distance, angle);
        //updateCircle(SphericalUtil.computeDistanceBetween(realPosition, lastFakePosition));
        return initialGuess;
    }

    public LatLng getNext(LatLng lastFakePosition, LatLng oldRealPosition, LatLng realPosition) {
        double distance = SphericalUtil.computeDistanceBetween(oldRealPosition, realPosition);
        double heading = SphericalUtil.computeHeading(lastFakePosition, realPosition);
        //updateCircle(distance);
        double distanceToFake = SphericalUtil.computeDistanceBetween(realPosition, lastFakePosition);
        if (distanceToFake + distance <= radiusMax) {
            //we can choose arbitrary position on circle since circle of fake position
            //is completely contained in circle of real position
            double angle = Math.random() * 360;
            lastFakePosition = SphericalUtil.computeOffset(lastFakePosition, distance, angle);
            return lastFakePosition;
        }
        if (distanceToFake - distance > radiusMax) {
            //we have to start a new cicle since circle of fake position is completely
            //outside circle of real position.
            //Pick an arbitrary point within circle of real position
            return getInitialGuess(realPosition);
        }
        //heading is in degrees clockwise from north.
        //We need heading counter-clockwise from west.
        heading = heading - 90; //measured from west
        heading = (360 - heading) % 360; //measured counter-clockwise.
        //With heading and distance we can now compute the euclidean position
        //of realPosition relative to lastFakePosition. Meaning: using an
        //coordinate system where lastFakePosition is (0,0).
        heading = Math.toRadians(heading);
        double ax = distanceToFake * Math.cos(heading);
        double ay = distanceToFake * Math.sin(heading);
        Pair<Double, Double> intersectionsMax = CircleIntersection.anglesOfIntersections(ax, ay, distance, radiusMax);
        //Pair<Point, Point> intersectionsMin = CircleIntersection.intersectCircles(ax, ay, distance, radiusMin);
        //TODO: choose angle with respect to radiusMin
        double phiMin, phiMax;
        if (intersectionsMax == null) {
            //should not happen
            //TODO Log.e(DistanceGeneratorDemo.TAG, "no intersection");
            Toast.makeText(ui, "Got no intersection. Starting new cycle", Toast
                    .LENGTH_SHORT)
                    .show();
            //fallback: start new cycle
            return getInitialGuess(realPosition);
        }
        /*
        if (intersectionsMax.getFirst() < intersectionsMax.getSecond()) {
            phiMax = intersectionsMax.getSecond();
            phiMin = intersectionsMax.getFirst();
        } else {
            phiMax = intersectionsMax.getFirst();
            phiMin = intersectionsMax.getSecond();
        }
        */
        phiMin = intersectionsMax.getFirst();
        phiMax = intersectionsMax.getSecond();
        Log.d(CircleIntersection.TAG, "phiMin = " + phiMin + ", phiMax = " + phiMax);
        //we have to switch these boundaries, otherwise an angle outside
        //of radiusMax circle will be picked
        //double oldPhiMin = phiMin;
        //phiMin = phiMax - 360;
        //phiMax = oldPhiMin;
        Log.d(CircleIntersection.TAG, "picking angle from [ " + phiMin + " , " + phiMax + " ]");
        double angle = phiMin + Math.random() * (phiMax - phiMin);
        Log.d(CircleIntersection.TAG, "angle = " + angle);
        //update lastFakePosition
        lastFakePosition = SphericalUtil.computeOffset(lastFakePosition, distance, angle);

        Pair<Boolean, Boolean> pointsInside = arePointsInsidePolygon
                (realPosition, lastFakePosition);

        if (pointsInside.getFirst() == pointsInside.getSecond()) {
            //both points are inside or outside polygon, ie it is a
            //valid combination
            return lastFakePosition;
        } else {
            LatLng newFake = tryToFindValidPosition(pointsInside.getFirst(),
                    lastFakePosition, distance);
            if (newFake == null) {
                //start new cycle
                do {
                    newFake = getInitialGuess(realPosition);
                } while (validator.isValid(realPosition, newFake) !=
                        pointsInside
                        .getFirst());
            }

            return newFake;
        }

    }

    public void setPolygon(List<LatLng> polygon) {
        validator = new PolygonValidator(polygon);
    }

    /**
     * Checks for each of the given points if it is inside the polygon
     * @param real real position
     * @param fake fake position
     * @return A pair of boolean (isRealInside, isFakeInside)
     */
    private Pair<Boolean, Boolean> arePointsInsidePolygon(LatLng real, LatLng
            fake) {
        boolean isRealInside = validator.isValid(real, fake);
        boolean isFakeInside = validator.isValid(real, fake);

        return new Pair<>(isRealInside, isFakeInside);
    }

    /**
     * Try to find a position f satisfying real == isInside(f).
     * @param isRealInside specifying wheter or not real is inside polygon
     * @param fakePos coordinates of fake
     * @param radius distance of f to fake
     * @return f, if such a point was found, and null otherwise.
     */
    private LatLng tryToFindValidPosition(boolean isRealInside,
                                          LatLng fakePos, double radius) {
        LatLng sample;
        //We sample points on circle surface with 45° stepsize
        for (double heading = 0; heading <= 315; heading += 45) {
            sample = SphericalUtil.computeOffset(fakePos, radius, heading);
            if (validator.isValid(fakePos, sample) == isRealInside) {
                return sample;
            }
        }
        return null;
    }
}
