package app.avare.lib.configparser;

/*
        Copyright 2016-2019 AVARE project team

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

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {

    public String readFile(String filename) {
        File sdcard = Environment.getExternalStorageDirectory();
        //Log.i("Contacts Plugin", "current path: " + sdcard.getAbsolutePath());
        //TODO: this is NOT how one should access the files folder need CONTEXT!
        File filesDir = new File("/data/data/app.avare/files");
        //File f = new File(sdcard, filename);
        File f = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/exampleSettings.json");

        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(f));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
        } catch (IOException e) {
            Log.d("CPLUGIN", "Error while reading from config");
            e.printStackTrace();
        }
        Log.d("CPLUGIN", "json: " + sb);
        return sb.toString();
    }
}

