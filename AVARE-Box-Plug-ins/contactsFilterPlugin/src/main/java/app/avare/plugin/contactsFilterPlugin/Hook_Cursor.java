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

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Hook_Cursor {

    public static String className = "android.content.ContentResolver";
    public static String methodName = "query";
    public static String methodSig = "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;";

    public static Cursor hook(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.i("Hook Cursor", "Uri: " + uri);

        Cursor cursor = backup(thiz, uri, projection, selection, selectionArgs, sortOrder);
        JSONParser jp = new JSONParser();


        HookCursor hc = new HookCursor(cursor, jp);
        Log.d("CONTACTS HOOK", "cursor Hooked!!");

        return hc;
    }

    public static Cursor backup(Object thiz, Uri uri, String[] projection, String selection, String[] selectionAgs, String sortOrder) {
        Log.d("BACKUP", "Unfortunately the backup method was called");
        return null;
    }

    private static class HookCursor extends CursorWrapper {

        JSONParser parser;
        JSONArray vertical;
        JSONArray horizontal;

        private HookCursor(Cursor c, JSONParser config) {

            super(c);
            this.parser = config;
            this.vertical = this.parser.getContactsSettings("vertical");
            this.horizontal = this.parser.getContactsSettings("horizontal");
            Log.d("constructor", "Private class HookCursor Constructed!");

        }

        @Override
        public String getString(int columnIndex) {

            Log.i("Hook Cursor", "Common Data Kinds: " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            Log.i(("Hook Cursor"), "Contacts: " + ContactsContract.Contacts.DISPLAY_NAME);
            //Log.d("HookCursor", "Using HookCursor");
          /*  if (columnIndex == super.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) {
                String givenName = super.getString(super.getColumnIndex(ContactsContract.
                        CommonDataKinds.StructuredName.GIVEN_NAME));
                return givenName;
            }*/


            if (columnIndex == super.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)) {
                return "";
            }

            String s = super.getString(columnIndex);
            if (columnIndex == super.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) {
                String[] splits = s.split(" ");
                return splits[0];
            }
            return super.getString(columnIndex);
        }

        @Override
        public boolean moveToNext() {
            try {
                //Log.i("HOOK CURSOR", "Using Hook Cursor moveToNext()");
                boolean next = super.moveToNext();
                if (next) {
                    //Log.i("HOOK CURSOR", "Querying name");
                    String currentName = super.getString(this.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    if (JSONParser.jSONArrayContains(this.horizontal, currentName)) {

                        Log.i("HOOK CURSOR", "Found name, moving cursor..");

                        boolean foundNext = super.moveToNext();
                        Log.i("HOOK CURSOR", "Skipped row");
                        return foundNext;
                    }
                }
                return next;
            } catch (Exception e) {
                Log.i("HOOK CURSOR", "Exception Occured..");
                e.printStackTrace();
                return super.moveToNext();
            }
        }
    }
}
