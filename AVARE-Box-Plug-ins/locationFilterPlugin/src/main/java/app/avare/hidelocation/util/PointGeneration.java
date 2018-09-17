package app.avare.hidelocation.util;

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
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import app.avare.hidelocation.validator.PolygonValidator;

/**
 * Main class responsible for generation of points.
 */
public class PointGeneration {


    public final static String TAG = "pointGeneration";

    private double radiusMax;
    private double radiusMin;
    private PolygonValidator validator;

    private LatLng lastFakePosition;
    private LatLng lastRealPosition;

    public PointGeneration(double radiusMax, double radiusMin,
                            PolygonValidator validator) {
        this.radiusMax = radiusMax;
        this.radiusMin = radiusMin;
        this.validator = validator;
    }

    private LatLng getInitialGuess(LatLng realPosition) {
        return getInitialGuessWithPhi(realPosition).getLatLng();
    }

    private LatLngWithPhi getInitialGuessWithPhi(LatLng realPosition) {
        LatLng initialGuess;
        double angle;
        do {
            angle = Math.random() * 360;
            double r = Math.random();
            double distance = radiusMin + r * (radiusMax - radiusMin);

            initialGuess = SphericalUtil.computeOffset(realPosition,
                    distance, angle);
        } while (!validator.isValid(realPosition, initialGuess));
        //TODO: does this loop always terminate?
        LatLngWithPhi latlngPhi = new LatLngWithPhi(initialGuess, angle);
        latlngPhi.setAsStartOfNewCycle();

        lastRealPosition = realPosition;
        lastFakePosition = initialGuess;

        return latlngPhi;
    }

    public LatLng getNextPosition(LatLng real) {
        if (lastFakePosition == null) {
            lastFakePosition = getInitialGuess(real);
        } else {
            lastFakePosition = getNextPosition(lastFakePosition,
                    lastRealPosition, real).getLatLng();
        }
        lastRealPosition = real;

        return lastFakePosition;
    }

    public LatLng getNextPosition(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);
        return getNextPosition(latLng);
    }

    private LatLngWithPhi getNextPosition(LatLng lastFakePosition, LatLng
            oldRealPosition, LatLng realPosition) {
        double distance = SphericalUtil.computeDistanceBetween(oldRealPosition, realPosition);
        double heading = SphericalUtil.computeHeading(lastFakePosition, realPosition);
        //updateCircle(distance);
        double distanceToFake = SphericalUtil.computeDistanceBetween(realPosition, lastFakePosition);
        if (distanceToFake + distance <= radiusMax) {
            //we can choose arbitrary position on circle since circle of fake position
            //is completely contained in circle of real position
            //note: we cannot call getInitalGuess at this point, since radius
            // is fixed
            double angle = Math.random() * 360;
            LatLng newFake = SphericalUtil.computeOffset(lastFakePosition,
                    distance,
                    angle);
            newFake = ensurePointIsInSameState(newFake, realPosition, distance);
            return new LatLngWithPhi(newFake, angle);
        }
        if (distanceToFake - distance > radiusMax) {
            //we have to start a new cicle since circle of fake position is completely
            //outside circle of real position.
            //Pick an arbitrary point within circle of real position
            return getInitialGuessWithPhi(realPosition);
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
        Pair<Double, Double> intersectionsMin = null;
        if (radiusMin > 0) {
            intersectionsMin = CircleIntersection.anglesOfIntersections(ax, ay, distance, radiusMin);
        }

        if (intersectionsMax == null) {
            //should not happen
            Log.e(TAG, "no intersection");
            //fallback: start new cycle
            return getInitialGuessWithPhi(realPosition);
        }

        double angle = pickAngle(intersectionsMax, intersectionsMin);

        Log.d(TAG, "angle = " + angle);
        //update lastFakePosition
        LatLng newFake = SphericalUtil.computeOffset(lastFakePosition,
                distance, angle);

        //ensure point is in same state as real position
        newFake = ensurePointIsInSameState(newFake, realPosition, distance);

        return new LatLngWithPhi(newFake, angle, intersectionsMax.getFirst(),
                intersectionsMax.getSecond());
    }

    private LatLng ensurePointIsInSameState(LatLng fake, LatLng real, double
            distance) {
       if (validator.isValid(real, fake)) {
           return fake;
       } else {
           LatLng newFake = tryToFindValidPosition(real, fake, distance);
           if (newFake == null) {
               return getInitialGuess(real);
           }
           return newFake;
       }
    }

    /**
     * picks a random value from the intervall [phi1, theta1] union [theta2,
     * phi2] or [phi1, phi2] if anglesMin is null. Angles must be already
     * ordered, ie. any angle in
     * [phi1, phi2]
     * for
     * instance is within circle max.
     *
     * @param anglesMax (phi1, phi2) Intersection of circle max with circle
     *                  fake
     * @param anglesMin (theta1, theta2) Intersection of circle min with circle
     *                  fake.
     * @return randomly chosen angle
     */
    private double pickAngle(Pair<Double, Double> anglesMax, Pair<Double, Double> anglesMin) {
        double theta1, theta2, phi1, phi2; //phi corresponds to anglesMax,
        phi1 = anglesMax.getFirst();
        phi2 = anglesMax.getSecond();
        if (anglesMin != null) {
            theta1 = anglesMin.getFirst();
            theta2 = anglesMin.getSecond();
        } else {
            //we have to set theta1 and theta2 such, that
            //phi1 <= theta1 <= theta2 <= phi2 is true.
            theta1 = (phi1 + phi2) / 2;
            theta2 = theta1;
        }
        // theta to anglesMin
        double length1 = Math.abs(phi1 - theta1);
        double length2 = Math.abs(phi2 - theta2);
        double p = length1 / (length1 + length2); //percentage of length

        //pick a random x in [0,1].
        double x = Math.random();
        //we have to transform this x uniformly to an alpha in [phi1, theta1]
        // union [theta2, phi2].
        double alpha;
        if (x < p) {
            alpha = phi1 + (p - x) * (theta1 - phi1);
        } else if (x > p) {
            alpha = theta2 + (x - p) * (phi2 - theta2);
        } else {
            //if x == p, x can represent either theta1 or theta2.
            //we pick one value at random.
            if (Math.random() < 0.5) {
                alpha = theta1;
            } else {
                alpha = theta2;
            }
        }

        return alpha;
    }

    /**
     * Try to find a position f such that f and real are in same state.
     * @param real specifying wheter or not real is inside polygon
     * @param fakePos coordinates of fake
     * @param radius distance of f to fake
     * @return f, if such a point was found, and null otherwise.
     */
    private LatLng tryToFindValidPosition(LatLng real,
                                          LatLng fakePos, double radius) {
        LatLng sample;
        //We sample points on circle surface with 45° stepsize
        for (double heading = 0; heading <= 315; heading += 45) {
            sample = SphericalUtil.computeOffset(fakePos, radius, heading);
            if (validator.isValid(real, sample)) {
                return sample;
            }
        }
        return null;
    }
}
