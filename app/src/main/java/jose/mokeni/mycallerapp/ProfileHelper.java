package jose.mokeni.mycallerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ProfileHelper extends SQLiteOpenHelper {
    public static final String profile_table = "Profiles";
    public static final String id_col = "ID";
    public static final String firstname_col = "Firstname";
    public static final String lastname_col = "Lastname";
    public static final String phone_col = "Phone";

    String query = "CREATE TABLE " + profile_table + " (" + id_col + " Integer Primary Key Autoincrement," +
            firstname_col + " Text not null, " + lastname_col + " Text not null, " +
            phone_col + " Text not null);";
    public ProfileHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // OnDatabaseOpening (first time)
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When we change the version, the system calls this function
        db.execSQL("DROP TABLE " + profile_table);
        onCreate(db);
    }
}
