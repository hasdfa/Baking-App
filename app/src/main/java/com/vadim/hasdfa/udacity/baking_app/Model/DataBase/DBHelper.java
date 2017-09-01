package com.vadim.hasdfa.udacity.baking_app.Model.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Raksha Vadim on 03.08.17, 11:40.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recipe.db";

    private static final int DATABASE_VERSION = 1;
    public static String TABLE_NAME = "Ingredients4Recipe";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS \""+TABLE_NAME+"\" (\n" +
                "  \"id\" integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  \"recipe_id\" integer NOT NULL,\n" +
                "  \"name\" text NOT NULL,\n" +
                "  \"measure\" text NOT NULL,\n" +
                "  \"quantity\" integer NOT NULL\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
