package jose.mokeni.mycallerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProfileManager {
    SQLiteDatabase db = null;
    Context context;

    public ProfileManager(Context context){
        this.context = context;
    }

    public void open()
    {
        ProfileHelper helper = new ProfileHelper(context, "myBase.db", null, 2);
        db = helper.getWritableDatabase();
    }

    public long add(String firstname, String lastname, String phone)
    {
        long rowsAdded = 0;
        ContentValues values = new ContentValues();
        values.put(ProfileHelper.firstname_col, firstname);
        values.put(ProfileHelper.lastname_col, lastname);
        values.put(ProfileHelper.phone_col, phone);

        rowsAdded = db.insert(ProfileHelper.profile_table, null, values);
        return rowsAdded;
    }

    public ArrayList<Profile> getAllProfiles()
    {
        ArrayList<Profile> profiles_list = new ArrayList<Profile>();
        Cursor cursor = db.query(ProfileHelper.profile_table,
                new String[]{
                        ProfileHelper.id_col,
                        ProfileHelper.firstname_col,
                        ProfileHelper.lastname_col,
                        ProfileHelper.phone_col
                }, null, null, null,
                null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String firstname = cursor.getString(1);
            String lastname = cursor.getString(2);
            String phone = cursor.getString(3);

            profiles_list.add(new Profile(id, firstname, lastname, phone));
            cursor.moveToNext();
        }



        return profiles_list;
    }

    public long delete(int id)
    {
        int rowsDeleted = db.delete(ProfileHelper.profile_table, ProfileHelper.id_col + "=" + id, null);
        return rowsDeleted;
    }

    public long update(int id, String firstname, String lastname, String phone)
    {
        int rowsUpdated;

        // New values
        ContentValues values = new ContentValues();
        values.put(ProfileHelper.id_col, id);
        values.put(ProfileHelper.firstname_col, firstname);
        values.put(ProfileHelper.lastname_col, lastname);
        values.put(ProfileHelper.phone_col, phone);

        rowsUpdated = db.update(ProfileHelper.profile_table, values, ProfileHelper.id_col + "=" + id, null);
        return rowsUpdated;

    }

    public void close() throws Throwable {
        db.close();
        super.finalize();
    }
}
