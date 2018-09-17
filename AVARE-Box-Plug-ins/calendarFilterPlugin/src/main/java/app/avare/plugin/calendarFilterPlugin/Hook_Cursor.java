package app.avare.plugin.calendarFilterPlugin;

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



import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

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
        Log.d("CONTACTS HOOK", "cursor Hooked!");

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

            String s = super.getString(columnIndex);
            if (columnIndex == super.getColumnIndex(CalendarContract.Events.TITLE)) { //TITLE, DTSTART, DTEND, EVENT_LOCATION

                return null; //takes a feature of an event and just returns null instead. Examples for available features are listed above.
            }
            return s;
        }
    }
}
