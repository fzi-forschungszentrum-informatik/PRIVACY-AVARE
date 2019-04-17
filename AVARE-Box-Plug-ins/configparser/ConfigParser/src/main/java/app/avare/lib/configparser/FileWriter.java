package app.avare.lib.configparser;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/*
 * Created by AVARE Project
 */

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

/**
 * Class to write the modified JSON File to the specified location.
 */
public class FileWriter {

    private String filename;

    /**
     * Sets the location where the file will be written.
     */
    public FileWriter(String filename) {
        this.filename = filename;
    }

    /**
     * Writes a String to the destination filename.
     *
     * @param content
     * @return true if success
     *         false an exception occured during writing
     */
    public boolean writeFile(String content) {
        File sdcard = Environment.getExternalStorageDirectory();
        //TODO: this is NOT how one should access the files folder need CONTEXT!
        File filesDir = new File("/data/data/app.avare/files");
        //File f = new File(sdcard, filename);
        //for test file:
        File f = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/exampleSettings.json");

        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(f));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            Log.d("CPLUGIN", "Error while writing to config");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
