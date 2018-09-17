package app.avare.plugin.contactsFilterPlugin;

/**
 * Created by AVARE Project 2018/07/26
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


import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/*
even though fields and methods seem unused they can not be deleted, they are used by yahfa to hook calls to functions
 */

public class Hook_Cursor {

    public static String className = "android.content.ContentResolver";
    public static String methodName = "query";
    public static String methodSig = "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;";

    public static Cursor hook(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = backup(thiz, uri, projection, selection, selectionArgs, sortOrder);

        HookCursor hc = new HookCursor(cursor);
        Log.d("CONTACTS HOOK", "cursor Hooked!!");

        return hc;
    }

    public static Cursor backup(Object thiz, Uri uri, String[] projection, String selection, String[] selectionAgs, String sortOrder) {
        Log.d("BACKUP", "Unfortunately the backup method was called");
        return null;
    }

    private static class HookCursor extends CursorWrapper {

        private HookCursor(Cursor c) {
            super(c);
            Log.d("constructor", "Private class HookCursor Constructed!");

        }

        @Override
        public String getString(int columnIndex) {
            Log.d("HookCursor", "Using HookCursor");

            /*//TODO: read json file with stored settings and apply settings appropriately
            Class aClass = Hook_Cursor.class;
            String json = null;
            try {
                InputStream is = aClass.getResourceAsStream("/res/raw/sample.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                //return null;
            }
            //return json;
            try {
                JSONObject obj = new JSONObject(json);
                //TODO
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (columnIndex == super.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)) {
                //TODO check for same LOOKUP_KEY
                return "";

            }*/



            String s = super.getString(columnIndex);
            if (columnIndex == super.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) {
                String[] splits = s.split(" ");
               // if (splits.length == 2) {
                    return splits[0]; //always returns first Element for now, might not work as expected w/ double first names without hyphens
                //}
            }
            return s;
        }
    }
}
