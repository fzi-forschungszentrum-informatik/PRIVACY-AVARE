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
import android.database.CursorJoiner;
import android.database.CursorWrapper;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import app.avare.lib.configparser.JSONParser;

public class Hook_Cursor {

    public static String className = "android.content.ContentResolver";
    public static String methodName = "query";
    public static String methodSig = "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;";

    public static final String AVARE_GIVEN_NAME = "AVARE_GIVEN_NAME";
    public static final String AVARE_FAMILY_NAME = "AVARE_FAMILY_NAME";
    public static final String AVARE_CONTACT_ID = "AVARE_CONTACTS_ID";

    public static Cursor hook(Object thiz, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d("CPLUGIN", "Uri: " + uri);


        String[] newProjection;
        if (projection != null) {
            boolean containsContactsId = false;
            for (int i = 0; i < projection.length; i++) {
                if (projection[i].equals(ContactsContract.Data.CONTACT_ID)) {
                    containsContactsId = true;
                }
            }
            if (!containsContactsId) {
                newProjection = new String[projection.length + 1];
                for (int i = 0; i < projection.length; i++) {
                    newProjection[i] = projection[i];
                }
                newProjection[projection.length] = ContactsContract.Data.CONTACT_ID;
                for (int i = 0; i < newProjection.length; i++) {
                    Log.i("new projection", newProjection[i]);
                }
            } else {
                newProjection = projection;
            }

        } else {
            newProjection = null;
        }

        Cursor structuredNameCursor = backup(thiz, ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, ContactsContract.Data.CONTACT_ID}, ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME + " != ?", new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, "null"}, ContactsContract.Data.CONTACT_ID);

        //structuredNameCursor.moveToFirst();
        while (structuredNameCursor.moveToNext()) {
            Log.i("STRUCTURED NAME CURSOR", "ID: " + structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)));
            Log.i("STRUCTURED NAME CURSOR", "GIVEN_NAME: " + structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)));
        }

        Cursor cursor;
        try {
            cursor = backup(thiz, uri, newProjection, selection, selectionArgs, ContactsContract.Data.CONTACT_ID);
        } catch (Exception e) {
            cursor = backup(thiz, uri, newProjection, selection, selectionArgs, sortOrder);

        }

        //query a joined cursor
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("original, sName");
        String originalRaw = builder.buildQuery(newProjection, selection, null, null, sortOrder, null);


        String s = "";
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            s += cursor.getColumnName(i) + ": ";
            try {
                s += cursor.getString(i) + "\n";
            } catch (Exception e) {
                s += "exception.. \n";
            }
        }
//        Log.i("BACKUP CURSOR content", s);
//        Log.i("BACKUP CURSOR ROW LEN", cursor.getCount() + "");
//        Log.i("STRUCT CURSOR ROW LEN", "" + structuredNameCursor.getCount());


        String contactIDKey;
        if (cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID) != -1) {
            contactIDKey = ContactsContract.Data.CONTACT_ID;
        } else {
            contactIDKey = ContactsContract.Contacts._ID;
        }

        Log.i("HOOK CURSOR", "contactIdKey = " + contactIDKey);

        String[] newColumns = new String[cursor.getColumnCount() + 3];
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            newColumns[i] = cursor.getColumnName(i);
        }
        newColumns[cursor.getColumnCount()] = AVARE_GIVEN_NAME;
        newColumns[cursor.getColumnCount() + 1] = AVARE_FAMILY_NAME;
        newColumns[cursor.getColumnCount() + 2] = AVARE_CONTACT_ID;
        MatrixCursor joinedCursor = new MatrixCursor(newColumns);

        CursorJoiner joiner = new CursorJoiner(cursor, new String[]{contactIDKey}, structuredNameCursor, new String[]{ContactsContract.Data.CONTACT_ID});
        for (CursorJoiner.Result res : joiner) {
            switch (res) {
                case LEFT:
                    Log.i("CURSOR JOINER", "left");
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String t = "";
                        t = cursor.getColumnName(i) + ": ";
                        try {
                            t += cursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER cursor", t);
                    }
                    for (int i = 0; i < structuredNameCursor.getColumnCount(); i++) {
                        String t = "";
                        t = structuredNameCursor.getColumnName(i) + ": ";
                        try {
                            t += structuredNameCursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER sCursor", t);
                    }
//                    try {
                    Object[] columns = new Object[cursor.getColumnCount() + 3];
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        columns[i] = cursor.getString(i);
                    }

                    columns[cursor.getColumnCount()] = null;
                    columns[cursor.getColumnCount() + 1] = null;
                    columns[cursor.getColumnCount() + 2] = null;
//
//
                    joinedCursor.addRow(columns);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    break;
                case RIGHT:
                    Log.i("CURSOR JOINER", "right");
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String t = "";
                        t = cursor.getColumnName(i) + ": ";
                        try {
                            t += cursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER cursor", t);
                    }
                    for (int i = 0; i < structuredNameCursor.getColumnCount(); i++) {
                        String t = "";
                        t = structuredNameCursor.getColumnName(i) + ": ";
                        try {
                            t += structuredNameCursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER sCursor", t);
                    }
//                    try {
//                    columns = new Object[cursor.getColumnCount() + 3];
//                    for (int i = 0; i < cursor.getColumnCount(); i++) {
////                            columns[i] = cursor.getString(i);
//                        columns[i] = null;
//                    }
//                        int givenNameIndex = structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
//                        Log.i("GIVEN NAME INDEX", givenNameIndex + "");
//                        String givenName = structuredNameCursor.getString(givenNameIndex);
//                        Log.i("GIVEN NAME CONTENT", givenName);
//                    columns[cursor.getColumnCount()] = columns[cursor.getColumnCount()] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
//                    columns[cursor.getColumnCount() + 1] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
//                    columns[cursor.getColumnCount() + 2] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
//                        columns[cursor.getColumnCount()] = null;
//                        columns[cursor.getColumnCount() + 1] = null;
//                        columns[cursor.getColumnCount() + 2] = null;
//
//
//                    joinedCursor.addRow(columns);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
                    break;
                case BOTH:
                    Log.i("CURSOR JOINER", "both");

                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String t = "";
                        t = cursor.getColumnName(i) + ": ";
                        try {
                            t += cursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER cursor", t);
                    }

                    for (int i = 0; i < structuredNameCursor.getColumnCount(); i++) {
                        String t = "";
                        t = structuredNameCursor.getColumnName(i) + ": ";
                        try {
                            t += structuredNameCursor.getString(i);
                        } catch (Exception e) {
                            t += "exception..";
                        }
                        Log.i("JOINER sCursor", t);
                    }
                    Log.i("JOINER divider", "-----------------------------------");

                    columns = new Object[cursor.getColumnCount() + 3];
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        columns[i] = cursor.getString(i);
                    }
                    try {
                        columns[cursor.getColumnCount()] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        columns[cursor.getColumnCount() + 1] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        columns[cursor.getColumnCount() + 2] = structuredNameCursor.getString(structuredNameCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                    } catch (Exception e) {
                        columns[cursor.getColumnCount()] = null;
                        columns[cursor.getColumnCount() + 1] = null;
                        columns[cursor.getColumnCount() + 2] = null;
                    }

                    joinedCursor.addRow(columns);
                    break;
            }
        }

//        for (int i = 0; i < cursor.getColumnCount(); i++) {
//            Log.i("ENTRIES cursor", i + ": " + cursor.getColumnName(i));
//        }
//
//        for (int i = 0; i < structuredNameCursor.getColumnCount(); i++) {
//            Log.i("ENTRIES structuredNameC", i + ": " + structuredNameCursor.getColumnName(i));
//        }
//
//        for (int i = 0; i < joinedCursor.getColumnCount(); i++) {
//            Log.i("ENTRIES joinedCursor", i + ": " + joinedCursor.getColumnName(i));
//        }

        //show contents of the joined cursor
        Log.i("JOINED CURSOR ROW SIZE", joinedCursor.getCount() + "");
        joinedCursor.moveToFirst();
        while (!joinedCursor.isAfterLast()) {
            String t = "";

            for (int i = 0; i < joinedCursor.getColumnCount(); i++) {
                t += joinedCursor.getColumnName(i) + ": ";
                try {
                    t += joinedCursor.getString(i);
                } catch (Exception e) {
                    t += "exception..";
                }
                t += "\n";
            }
            t += "------------------------------------\n";
            Log.i("JOINED CURSOR CONTENT", t);
            joinedCursor.moveToNext();

        }
        joinedCursor.moveToFirst();
        joinedCursor.moveToPrevious();


        JSONParser jp = new JSONParser();

        HookCursor hc = new HookCursor(joinedCursor, jp);
        Log.d("CONTACTS HOOK", "cursor Hooked!!");

        return hc;
    }

    public static Cursor backup(Object thiz, Uri uri, String[] projection, String selection, String[] selectionAgs, String sortOrder) {
        //Log.d("BACKUP", "Unfortunately the backup method was called");

        return null;
    }

    private static class HookCursor extends CursorWrapper {

        JSONParser parser;
        JSONArray vertical;
        JSONArray horizontal;

        private String[] notToFilterArray = new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.CONTACT_ID, ContactsContract.Data.RAW_CONTACT_ID, ContactsContract.Data.SORT_KEY_PRIMARY, AVARE_CONTACT_ID};
        private ArrayList<String> notToFilter = new ArrayList<String>();

        private HookCursor(Cursor c, JSONParser config) {

            super(c);

            //show the contents of the joined cursor
//            super.moveToFirst();
//            String content = "";
//            for (int i = 0; i < super.getColumnCount(); i++) {
//                content += super.getColumnName(i) + ": ";
//                try {
//                    content += super.getString(i) + "\n";
//                } catch (Exception e) {
//                    content += "exception.." + "\n";
//                }
//
//            }
//            Log.i("JOINED CURSOR CONTENT2", content);


            this.parser = config;
            this.vertical = this.parser.getContactsSettings("vertical");
            this.horizontal = this.parser.getContactsSettings("horizontal");
            Log.d("constructor", "Private class HookCursor Constructed!");

            for (String s : notToFilterArray) {
                notToFilter.add(s);
            }

        }

        @Override
        public String getString(int columnIndex) {
            String columnName = super.getColumnName(columnIndex);
            String status;
            try {
                JSONObject jo = this.vertical.getJSONObject(0);
                status = jo.getString("status");
                if (status.equalsIgnoreCase("enabled")) {
                    Log.i("HOOK CURSOR", "filter status == \"enabled\", skipping custom getString");

                    return super.getString(columnIndex);
                }
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            String[] mappedColumnNames = mapColumnNames(vertical);
            String s = "";
            for (int i = 0; i < mappedColumnNames.length; i++) {
                s += mappedColumnNames[i] + "\n";
            }
            //Log.i("MAPPED TABLES", s);

//            Log.i("HOOK CURSOR", "Calling Cursor getString()");
            try {
                if (JSONParser.jSONArrayContains(this.vertical, columnName) || this.stringArrayContains(mappedColumnNames, columnName) || this.notToFilter.contains(columnName)) {
//                    Log.i("VERTICAL", "Config contains " + columnName + ", returning");
                    return super.getString(columnIndex);
                } else {
//                    Log.i("VERTICAL", "Config doesn't contain " + columnName + ", not returning");
                    if (super.getColumnName(columnIndex).equals(ContactsContract.Contacts.DISPLAY_NAME) || this.notToFilter.contains(super.getColumnName(columnIndex))) {
//                        String givenName = super.getString(super.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
//                        String familyName = super.getString(super.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        String givenName = super.getString(super.getColumnIndex(AVARE_GIVEN_NAME));
                        String familyName = super.getString(super.getColumnIndex(AVARE_FAMILY_NAME));
                        String artificialDisplayName = givenName + " " + familyName;
                        return getString(super.getColumnIndex(AVARE_GIVEN_NAME)) + " " + getString(super.getColumnIndex(AVARE_FAMILY_NAME));
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";

        }

        @Override
        public boolean moveToNext() {
            String status;
            try {
                JSONObject jo = this.horizontal.getJSONObject(0);
                status = jo.getString("status");
                if (status.equalsIgnoreCase("enabled")) {
                    Log.i("HOOK CURSOR", "filter status == \"enabled\", skipping custom moveToNext()");
                    return super.moveToNext();
                }
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            try {
                Log.i("HOOK CURSOR", "Using Hook Cursor moveToNext()");
                boolean next = super.moveToNext();
                if (next) {
                    Log.i("HOOK CURSOR", "Querying ID");
                    for (int i = 0; i < this.getColumnCount(); i++) {
                        Log.i("HOOK CURSOR table", this.getColumnName(i) + ": " + this.getString(i));
                    }
                    String currentID = "";
                    currentID = this.getString(this.getColumnIndex(AVARE_CONTACT_ID));

                    if (currentID == null || currentID.equals("")) {
                        if (this.getColumnIndex(ContactsContract.Data.CONTACT_ID) > -1) {
                            currentID = this.getString(this.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                        }
                    }
                    if (currentID == null || currentID.equals("")) {
                        currentID = this.getString(this.getColumnIndex(ContactsContract.Contacts._ID));
                    }
                    Log.i("HOOK CURSOR", "Current ID: " + currentID);

                    if (currentID != null && !JSONParser.jSONArrayContains(this.horizontal, currentID)) {

                        Log.i("HOOK CURSOR", "Filtering ID, moving cursor..");

                        boolean foundNext = this.moveToNext();
                        return foundNext;
                    }
                }
                return next;
            } catch (Exception e) {
                Log.i("HOOK CURSOR", "Exception Occured..");
                e.printStackTrace();
                return this.moveToNext();
            }
        }

        String[] mapColumnNames(JSONArray preferences) {
            try {
                ArrayList<String> mappedPrefs = new ArrayList();
                for (int i = 0; i < preferences.length(); i++) {

                    String s = getMappedColumnName(preferences.get(i).toString());
                    if (!s.equals("")) {
                        mappedPrefs.add(s);
                    }
                }
                String[] mappedPrefsString = new String[mappedPrefs.size()];
                for (int i = 0; i < mappedPrefsString.length; i++) {
                    mappedPrefsString[i] = mappedPrefs.get(i);
                }
                return mappedPrefsString;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String getMappedColumnName(String preference) {
            switch (preference) {
                case "FAMILY_NAME":
                    return AVARE_FAMILY_NAME;

                case "GIVEN_NAME":
                    return AVARE_GIVEN_NAME;

                case "TYPE_BIRTHDAY":
                    return ContactsContract.CommonDataKinds.Event.START_DATE;

                case "PHOTO":
                    return ContactsContract.CommonDataKinds.Photo.PHOTO;

                case "ORGANIZATION":
                    return ContactsContract.CommonDataKinds.Organization.COMPANY;

                case "PHONE.TYPE_MOBILE":
                    return ContactsContract.CommonDataKinds.Phone.NUMBER;

                case "PHONE.TYPE_HOME":
                    return ContactsContract.CommonDataKinds.Phone.NUMBER;

                case "PHONE.TYPE_WORK":
                    return ContactsContract.CommonDataKinds.Phone.NUMBER;

                case "EMAIL.TYPE_HOME":
                case "EMAIL.TYPE_WORK":
                    return ContactsContract.CommonDataKinds.Email.ADDRESS;

                case "STREET":
                    return ContactsContract.CommonDataKinds.StructuredPostal.STREET;

                case "POSTCODE":
                    return ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE;

                case "CITY":
                    return ContactsContract.CommonDataKinds.StructuredPostal.CITY;

                case "COUNTRY":
                    return ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY;

                case "IM.PROTOCOL_SKYPE":
                case "IM.PROTOCOL_ICQ":
                    return ContactsContract.CommonDataKinds.Im.DATA;

                case "WEBSITE":
                    return ContactsContract.CommonDataKinds.Website.URL;

                case "NOTE":
                    return ContactsContract.CommonDataKinds.Note.NOTE;
            }
            return "";

        }

        private boolean stringArrayContains(String[] array, String s) {
            for (String string : array) {
                if (s.equalsIgnoreCase(string)) {
                    return true;
                }
            }
            return false;
        }
    }
}
