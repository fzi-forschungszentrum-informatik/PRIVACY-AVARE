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


import android.util.Log;

public  class CircleIntersection {

    public final static String TAG = "circleIntersection";
    public static final int FULL_CIRCLE_DEG = 360;

    /**
     * private constructor in order to avoid instantiation
     */
    private CircleIntersection() {}

    /**
     * Computes intersection of two circles C and C'. Assuming center of
     * C is at (0,0).
     * @param ax x-coordinate of center of C'
     * @param ay y-coordinate of center of C'
     * @param r0 radius of C (center is at (0,0))
     * @param rA radius of C' (we denote the center with A)
     * @return Returns a pair (x1, x2), where x1 and x2 denote the
     * two intersections. If there is only one intersection-point x1 and x2 will contain the
     * same value. Returns <bf>null</bf> is circles do not intersect.
     */
    public static Pair<Point, Point> intersectCircles(double ax, double ay, double r0, double rA) {
        Log.d(TAG, "parameters: ax = " + ax + ", ay = " + ay + ",ro = " + r0 + ", rA = " + rA);
        //declare variables
        double c, cP, m, d, p, q;
        //compute line going through intersection points
        c = ax * ax + ay * ay + r0 * r0 - rA * rA; //c = ax^2 + ay^2 + r0^2 - rA^2
        m = - ax / ay;
        cP = c / (2 * ay);
        Log.d(TAG, "c = " + c + ", m = " + m + ", cP = " + cP);
        //line is defined by: y = m * x + cP
        //intersect circle C with line, solve quadratic equation
        d = cP - ay;
        double div = m * m + 1; //divisor: m^2 + 1
        Log.d(TAG, "d = " + d + ", div = " + div);
        p = (2 * m * d - 2 * ax) / div;
        q = (ax * ax + d * d - rA * rA) / div; // (ax^2 + d^2 - rA^2) / div
        Log.d(TAG, "p = " + p + ", q = " + q);
        double radicand = (p * p) / 4 - q;
        Log.d(TAG, "radicand = " + radicand);
        if (radicand < 0) {
            return null; //circles do not intersect
        }

        double sqrt = Math.sqrt(radicand);
        double minusPOver2 = - p / 2;
        Log.d(TAG, "sqrt = " + sqrt + ", minusPover2 = " + minusPOver2);
        double x1 = minusPOver2 + sqrt;
        double x2 = minusPOver2 - sqrt;
        //we get the corresponding y-values using the line equation
        double y1 = m * x1 + cP;
        double y2 = m * x2 + cP;

        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);

        Log.d(TAG, p1.toString());
        Log.d(TAG, p2.toString());

        return new Pair<> (p1, p2);
    }

    /**
     * Computes intersections of two circles C and C'. @see{intersectCircles}. But instead of
     * returning the values of the solutions x1 and x2, this method returns angles phi and phi'
     * such that shifting the northest point of C (center is at (0,0)) clockwise by phi (phi') results
     * in x1 (x2). In other words: x1 and x2 can be represented in polar coordinates.
     * @param ax x-coordinate of center of C'
     * @param ay y-coordinate of center of C'
     * @param r0 radius of C (center at (0,0))
     * @param rA radius of C' (we denote center with A)
     * @return A pair (phi, phi') of angles corresponding to the intersection-points. Returns <bf>null</bf>
     * if circles do not intersect.
     *
     */
    public static Pair<Double, Double> anglesOfIntersections(double ax, double ay, double r0, double rA) {
        Pair<Point, Point> intersections = intersectCircles(ax, ay, r0, rA);
        if (intersections == null) {
            return null;
        }
        Point p1 = intersections.first;
        Point p2 = intersections.second;
        double phi1 = pointToAngle(p1, r0) % FULL_CIRCLE_DEG;
        double phi2 = pointToAngle(p2, r0) % FULL_CIRCLE_DEG;

        boolean ascending;

        double phiTest = 0.5 * (phi1 + phi2);
        //this angle is measured clockwise from north
        //For the following computations we need the mathematical definition,
        //ie measured counter-clockwise from east
        phiTest = (90 - phiTest) % FULL_CIRCLE_DEG;
        //we need angle in radians
        phiTest = Math.toRadians(phiTest);
        //double xTest = ax + rA * Math.cos(phiTest);
        //double yTest = ay + rA * Math.sin(phiTest);
        double xTest = r0 * Math.cos(phiTest);
        double yTest = r0 * Math.sin(phiTest);

       ascending = isInside(ax, ay, xTest, yTest, rA);

        Pair<Double, Double> angles = getSortedPair(phi1, phi2, ascending);
        return angles;
    }


    /**
     * Transforms a point on a circle to corresponding angle. Angle measured clockwise from north.
     * @param point point on a circle C
     * @param radius radius of C
     * @return Corresponding angle. The values are arbitrary if the point is not on the circle
     * with specified radius.
     */
    public static double pointToAngle(Point point, double radius) {
        int quadrant = getQuadrant(point);
        double angle = 0;
        double asin = Math.toDegrees(Math.asin(point.getY() / radius));
        double acos = Math.toDegrees(Math.acos(point.getX() / radius));
        switch (quadrant) {
            case 1:
            case 4:
                angle = asin;
                break;
            case 2: angle = acos;
                break;
            case 3: angle = acos + 2 * (180 - acos);
                break;
            default: throw new IllegalStateException("quadrant not initialized properly");
        }
        //at this point angle is measured counter-clockwise from west.
        angle -= 90; //measured from north
        angle = (FULL_CIRCLE_DEG - angle) % FULL_CIRCLE_DEG; //measured clockwise
        return angle;
    }

    /**
     * Determines whether point (x,y) lies within circle with given radius
     * and center at (ax, ay).
     * @param ax x-coordinate of circle center
     * @param ay y-coordinate of circle center
     * @param x x-coordinate
     * @param y y-coordinate
     * @param radius radius of circle
     * @return true iff point inside circle or on the surface.
     */
    public static boolean isInside(double ax, double ay, double x, double y,
                                   double radius) {
        double delta_x = ax - x;
        double delta_y = ay - y;
        double dist = Math.pow(delta_x, 2) + Math.pow(delta_y, 2);
        dist = Math.sqrt(dist);

        return dist <= radius;
    }

    private static int getQuadrant(Point point) {
        double x = point.getX();
        double y = point.getY();
        if (x >= 0) {
            if (y >= 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (y >= 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * Returns sorted pair containing x and y
     * @param ascending determines sorting oreder. If true the returned pair is (min, max), otherwise
     *                  it is (max, min).
     * @return sorted pair
     */
    private static Pair<Double, Double> getSortedPair(double x, double y, boolean ascending) {
        double min, max;
        if (x <= y) {
            min = x;
            max = y;
        } else {
            max = x;
            min = y;
        }

        if (ascending) {
            return new Pair<>(min, max);
        } else {
            return new Pair<>(max, min);
        }
    }

    /**
     * Transforms an angle measured clockwise from north, to an angle measured
     * counter-clockwise from east
     * @param angle angle to transform in degrees
     * @return angle by mathematical definition
     */
    private static double transformAngleToMathematical(double angle) {
        return (90 - angle) % FULL_CIRCLE_DEG;
    }
}
