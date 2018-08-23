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

import com.google.android.gms.maps.model.LatLng;


public class LatLngWithPhi  {

    private LatLng latLng;
    private double phi;
    private double phiMin;
    private double phiMax;
    private boolean startOfNewCycle;

    public LatLngWithPhi(double lat, double lng, double phi) {
        this(new LatLng(lat, lng), phi, 0, 360);
    }

    public LatLngWithPhi(LatLng latLng, double phi) {
        this(latLng, phi, 0, 360);
    }

    public LatLngWithPhi(double lat, double lng, double phi, double phiMin,
                         double phiMax) {
        this(new LatLng(lat,lng), phi, phiMin, phiMax);
    }

    public LatLngWithPhi(LatLng latLng, double phi, double phiMin, double phiMax) {
        this.latLng = latLng;
        this.phi = phi;
        this.phiMin = phiMin;
        this.phiMax = phiMax;
        startOfNewCycle = false;
    }

    /**
     * This point is start of a new cycle.
     */
    public void setAsStartOfNewCycle() {
        startOfNewCycle = true;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public double getPhi() {
        return phi;
    }

    /**
     * Returns range [phiMin, phiMax] from which phi was picked.
     * @return the pair (phiMin, phiMax)
     */
    public Pair<Double, Double> getRange() {
        if (phiMin > phiMax) {
            double _phiMin = phiMin;
            phiMin = phiMax;
            phiMax = _phiMin;
        }
        return new Pair<>(phiMin, phiMax);
    }

    /**
     *
     * @return true iff phi is only point in range, ie range is [phi, phi]
     */
    public boolean isOnlyPointInRange() {
        return phiMin == phiMax;
    }

    /**
     *
     * @param epsilon tolerance
     * @return true iff phi is only point in range with respect to epsilon,
     * ie. range is subset of [phi - 0.5 epsilon, phi + 0.5 epsilon]
     */
    public boolean isOnlyPointInRange(double epsilon) {
        return Math.abs(phiMax - phiMin) <= epsilon;
    }

    public boolean isStartOfNewCycle() {
        return startOfNewCycle;
    }

}
