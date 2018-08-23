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

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for reading and validating .poly files,
 * describing the borders of a country.
 */
public class PolyReader {

    public final static String WHITE_SPACE_REGEX = "\\s+";
    public final static String END_TAG = "END";

    public final static String BASE_PATH = "europe";

    private Set<List<LatLng>> polygonSet;
    private Set<List<LatLng>> holes;

    public PolyReader(Context context) throws IOException {
        polygonSet = new HashSet<>();
        holes = new HashSet<>();
        readAllPolygons(context);
    }

    /**
     * Reads in a .poly file given by input stream.
     * @param stream input stream containing .poly file
     * @throws IOException If file does not exist or is not a proper .poly file
     */
    public PolyReader(InputStream stream) throws IOException {
        polygonSet = new HashSet<>();
        holes = new HashSet<>();
        readPoly(stream);
    }

    public PolyReader(String fileName, Context context) throws IOException {
        polygonSet = new HashSet<>();
        holes = new HashSet<>();
        InputStream stream = context.getAssets().open(fileName);
        readPoly(stream);
    }

    //the .poly files were downloaded from http://download.geofabrik.de/europe/{FILENAME}

    /*
    URL="http://download.geofabrik.de/europe.html"
    wget -nd -np -r --level=2 -A poly -R europe.poly,alps.poly,british-isles.poly,dach.poly $URL
    */

    public PolyReader() throws IOException {
        polygonSet = new HashSet<>();
        holes = new HashSet<>();
        Class aClass = PolyReader.class;
        InputStream is = aClass.getResourceAsStream("/res/raw/france.poly");
        Log.i("PolyReader:", is.toString());
        readPoly(is);
        is = aClass.getResourceAsStream("/res/raw/germany.poly");
        readPoly(is);
    }

    public Set<List<LatLng>> readAllPolygons(Context context) throws IOException {
        String[] fileNames = context.getAssets().list(BASE_PATH);
        String prefix = BASE_PATH + File.separator;
        //for testing purposes
        /*  fileNames = new HashSet<>();
        fileNames.add("germany.poly");
        fileNames.add("france.poly"); */
        InputStream stream = null;
        //get stream
        for (String fileName : fileNames) {
            if (!fileName.endsWith(".poly")) {
                continue; //ignore this file
            }
            stream = context.getAssets().open(prefix + fileName);
            readPoly(stream);
        }

        return polygonSet;
    }

    private Set<List<LatLng>> readPoly(InputStream stream) throws IOException {

        //Set<List<LatLng>> polygonSet = new HashSet<>();
        //ArrayList<LatLng> list = new ArrayList<>();

        //FileReader fileReader = new FileReader(path);

        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();

        //first line is name of file, which we ignore
        //we just ensure that line contains only one value
        String[] values = splitLine(line);
        if (values.length != 1) {
            String msg = "Error in line 1. Expected 1 value, got " + values.length;
            throwException(msg);
        }

        //Now we have a sequence of polygons. Iterate through them and extract points
        line = bufferedReader.readLine();
        List<LatLng> polygon = null;
        do {
            handlePolygon(line, bufferedReader);
            //go to next line
            line = bufferedReader.readLine();
            if (line == null) {
                throwException("Unexpected end of file!");
            }
        } while (!line.equals(END_TAG)); //marks end of file

        //close stream
        stream.close();

        //use PolyUtil to check whether a given point is within a polygon, ie list of points
        return polygonSet;
    }

    private void handlePolygon(String line, BufferedReader reader) throws IOException {
        ArrayList<LatLng> polygon = new ArrayList<>();
        //String line = reader.readLine();
        boolean isHole = false;

        if (line == null) {
            throwException("Unexpected end of file!");
        }
        String[] values = splitLine(line);

        if (values.length != 1) {
            String msg = "Error at line\n\t " + line + "\nExpected 1 value, got " + values.length;
            msg += "\nvalues = " + Arrays.toString(values);
            throwException(msg);
        }

        if (values[0].startsWith("!")) {
            isHole = true;
            //collect all holes, so user can get add them to polygon using PolygonOptions.addHole()
        }

        LatLng latLng = null;
        double lat = 0, lng = 0;

        line = reader.readLine();
        //when comparing line with END_TAG line has to be the argument,
        //since it can be null
        while (!(END_TAG.equals(line))){
            /*TODO: line starts with tab, which for some reason does not count as whitespace.
            Therefore after calling split on line values contains 3 values. Empty string at first
            //position and the two relevant values at second and third position.
            //Remove first entry from array.
            */
            if (line == null) {
                throwException("Unexpected end of file!");
            }
            values = splitLine(line);

            if (values.length != 2) {
                String msg = "Error at line\n\t " + line + "\nExpected 1 value, got " + values.length;
                throwException(msg);
            }

            //At this point line contains two values, first is longitute, second is latitude
            try {
                lng = Double.parseDouble(values[0]);
                lat = Double.parseDouble(values[1]);
            } catch (NumberFormatException e) {
                String msg = "Error occured while trying to parse double values at line\n\t" + line;
                msg += "\nvalues = " + Arrays.toString(values);
                throwException(msg);
            }

            latLng = new LatLng(lat, lng);

            //add point to list
            polygon.add(latLng);

            line = reader.readLine(); //update line
        };

        //ensure that fist and last point are the same, ie. polygon is closed
        LatLng first = polygon.get(0);
        LatLng last = polygon.get(polygon.size() - 1);
        if ((first.longitude != last.longitude) || first.latitude != last.latitude) {
            LatLng copyOfFirst = new LatLng(first.latitude, first.longitude);
            polygon.add(copyOfFirst);
        }

        if (isHole) {
            holes.add(polygon);
        } else {
            polygonSet.add(polygon);
        }

        return;
    }

    private String[] splitLine(String line) {
        //remove leading and tailing whitespace
        String trimmed = line.trim();
        //split using WHITE_SPACE_REGEX
        return trimmed.split(WHITE_SPACE_REGEX);
    }

    private void throwException(String msg) {
        throw new IllegalStateException(msg);
    }

    /**
     * returns a set of polygons described by a list of Latlng objects. Some of theses polygons may contain
     * holes which are not considered here. So make you sure you subtract holes, given by #getHoles,
     * before using the polygons.
     * @return polygons
     */
    public Set<List<LatLng>> getPolygons() {
        return polygonSet;
    }

    /**
     * returns a set of holes which some of the polygons may contain.
     * @return holes of polygons
     */
    public Set<List<LatLng>> getHoles() {
        return holes;
    }
}
