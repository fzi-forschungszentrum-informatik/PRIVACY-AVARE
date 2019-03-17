package app.avare.plugin.benchmarkFileWriter;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

/**
 * Class to write log entries to benchmarkLog.txt
 */
public class LogWriter {

    private static String LOG_FILE_NAME = "/benchmarkLog.txt";

    /**
     * Appends a new log entry to the log file.
     *
     * @param message
     * @return true if success
     *         false an exception occured during writing
     */
    public boolean addLine(String message) {
        File sdcard = Environment.getExternalStorageDirectory();
        File f = new File( Environment.getExternalStorageDirectory().getAbsolutePath() + LOG_FILE_NAME);
        StringBuilder sb = new StringBuilder();
        if (f.exists()) {
            BufferedReader br = null;
            Log.i("JSON PARSER", f.getAbsolutePath());
            try {
                br = new BufferedReader(new java.io.FileReader(f));
                String line;
                while((line = br.readLine()) != null) {
                    Log.i("JSON PARSER", line);
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        sb.append(Calendar.getInstance().getTime().toString() + "    " + extractPackage() + "    " + message + "\n");
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(f));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String extractPackage() {
        StackTraceElement[] stackTrace;
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            stackTrace = e.getStackTrace();
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            String packageName = stackTraceElement.getClassName().substring(0, stackTraceElement.getClassName().lastIndexOf("."));
            if (!packageName.startsWith("app.avare") && !packageName.startsWith("android")) {
                return packageName;
            }
        }
        return null;
    }
}
