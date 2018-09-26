package app.avare.plugin.contactsFilterPlugin;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JSONParser {
    private FileReader fr;
    private String config;
    private JSONObject configJSON;

    public JSONParser() {
        this.fr = new FileReader();
        this.config = fr.readFile("avare_demo_config.json");
        try {
            this.configJSON = new JSONObject(config);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getContactsSettings(String type) {
        try {
            JSONArray categories = this.configJSON.getJSONArray("categories");
            JSONObject category = categories.getJSONObject(0);
            JSONObject settings = category.getJSONObject("settings");
            //Log.i("JSON Parser", "Settings Config: " + settings);
            JSONObject contacts = settings.getJSONObject("contacts");
            //Log.i("JSON Parser", "Contacts Config: " + contacts);
            JSONObject filtersettings = contacts.getJSONObject("filterSettings");
            if (type.equals("vertical")) {
                JSONArray vertical = filtersettings.getJSONArray("vertical");
                return vertical;
            } else if (type.equals("horizontal")) {
                JSONArray horizontal = filtersettings.getJSONArray("horizontal");
                return horizontal;

            } else {
                Log.i("JSON Parser", "Wrong usage of getContactsSettings");
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * Utility function to check whether the JSON Array contains an element or not
    */
    public static boolean jSONArrayContains(JSONArray array, String element) throws JSONException {
        if(array.length() == 0) {
            return false;
        }
        for(int i = 0; i < array.length(); i++) {
            if (array.get(i).toString().equals(element)) {
                return true;
            }
        }
        return false;
    }

}
