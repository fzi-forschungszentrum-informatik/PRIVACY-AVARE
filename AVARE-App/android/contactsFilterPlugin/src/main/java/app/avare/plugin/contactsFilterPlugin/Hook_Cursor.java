package app.avare.plugin.contactsFilterPlugin;

/**
 * Created by AVARE Project 2018/07/26
 */


import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

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

            String s = super.getString(columnIndex);
            if (columnIndex == super.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) {
                String[] splits = s.split(" ");
               // if (splits.length == 2) {
                    return splits[0];
                //}
            }
            return s;
        }
    }
}
