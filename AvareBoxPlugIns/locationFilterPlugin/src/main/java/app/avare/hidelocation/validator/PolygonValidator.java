package app.avare.hidelocation.validator;

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

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Given a list of polygons (countries) this class checks whether or not to
 * given points are valid, ie in the same polygon.
 */
public class PolygonValidator extends AbstractLatLngValidator {

    private Set<List<LatLng>> polygonSet;
    /**
     * pointer to polygon containing real position,
     * so we do not always have to search in the polygon set for
     * the right polygon
     */
    private List<LatLng> polygonOfReal;

    public PolygonValidator(List<LatLng> polygon) {
       this(Collections.singleton(polygon));
    }

    public PolygonValidator(Set<List<LatLng>> polygonSet) {
        this.polygonSet = polygonSet;
        polygonOfReal = polygonSet.iterator().next();
    }

    public boolean isValid(LatLng real, LatLng fake) {
        Log.i("info", "isValid");
       updatePolygonOfReal(real);
       return PolyUtil.containsLocation(fake, polygonOfReal, false);
    }

    private void updatePolygonOfReal(LatLng real) {
        Log.i("info", "updatePolygonOfReal");
        if (PolyUtil.containsLocation(real, polygonOfReal, false)) {
            return; //nothing to do
        }
        for (List<LatLng> polygon : polygonSet) {
            if (PolyUtil.containsLocation(real, polygon, false)) {
                polygonOfReal = polygon;
                return;
            }
        }
        //we throw an exception if we cannot determine in which state the
        // real position is.
        throw new IllegalStateException("real is in an unkown state.");
    }
}
