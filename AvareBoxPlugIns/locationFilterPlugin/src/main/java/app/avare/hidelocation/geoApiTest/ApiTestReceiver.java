package app.avare.hidelocation.geoApiTest;

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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;


public class ApiTestReceiver extends BroadcastReceiver {

    public static final String TAG = ApiTestActivity.TAG;

    public ApiTestReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "received intent");
        String msg = null;
        if (intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
            msg = ((Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED))
                    .toString();
        } else {
            msg = "Key does not exist";
        }
        Log.i(TAG, msg);
    }
}
