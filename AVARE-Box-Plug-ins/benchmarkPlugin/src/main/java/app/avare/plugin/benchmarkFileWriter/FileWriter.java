package app.avare.plugin.benchmarkFileWriter;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Class to write the log files to the specified location.
 */
public class FileWriter {

    /**
     * Writes a String to the destination filename.
     *
     * @param content
     * @return true if success
     *         false an exception occured during writing
     */
    public boolean writeFile(String content) {
        File sdcard = Environment.getExternalStorageDirectory();
        File f = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/benchmarkLog.txt");

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
