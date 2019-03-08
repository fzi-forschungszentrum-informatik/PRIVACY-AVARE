package app.avare.lib.configparser;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

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
