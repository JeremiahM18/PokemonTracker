package com.example.pokemontracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.pokemontracker.data.PokemonContract.PokemonEntry;


public class PokemonDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pokemon.db";
    private static final int DB_VERSION = 1;

    public PokemonDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE " + PokemonEntry.TABLE_NAME + " ("
                + PokemonEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PokemonEntry.COL_NATIONAL + "INTEGER NOT NULL, " +
                PokemonEntry.COL_NAME + "TEXT NOT NULL, " +
                PokemonEntry.COL_SPECIES + "TEXT NOT NULL, " +
                PokemonEntry.COL_GENDER + "TEXT NOT NULL, " +
                PokemonEntry.COL_HEIGHT + "TEXT NOT NULL, " +
                PokemonEntry.COL_WEIGHT + "TEXT NOT NULL, " +
                PokemonEntry.COL_LEVEL + "INTEGER NOT NULL, " +
                PokemonEntry.COL_HP + "INTEGER NOT NULL, " +
                PokemonEntry.COL_ATTACK + "INTEGER NOT NULL, " +
                PokemonEntry.COL_DEFENSE + "INTEGER NOT NULL, " +
                // Prevent exact duplicates
                "UNIQUE (" +
                PokemonEntry.COL_NATIONAL + ", " +
                PokemonEntry.COL_NAME + ", " +
                PokemonEntry.COL_SPECIES + ", " +
                PokemonEntry.COL_GENDER + ", " +
                PokemonEntry.COL_HEIGHT + ", " +
                PokemonEntry.COL_WEIGHT + ", " +
                PokemonEntry.COL_LEVEL + ", " +
                PokemonEntry.COL_HP + ", " +
                PokemonEntry.COL_ATTACK + ", " +
                PokemonEntry.COL_DEFENSE + ") ON CONFLICT IGNORE" +
                ");";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PokemonEntry.TABLE_NAME);
        onCreate(db);
    }
}
