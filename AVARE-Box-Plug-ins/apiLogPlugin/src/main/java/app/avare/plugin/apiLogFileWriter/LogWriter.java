package app.avare.plugin.apiLogFileWriter;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

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
 * Class to write log entries to benchmarkLog.txt
 */
public class LogWriter {

    private static String LOG_FILE_NAME = "/apiLog.txt";

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
