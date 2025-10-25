package com.example.pokemontracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class PokemonRepository {
    private final Context context;

    public PokemonRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean isDuplicate(ContentValues cv){
        String sel =
                PokemonContract.PokemonEntry.COL_NATIONAL + "=? AND " +
                PokemonContract.PokemonEntry.COL_NAME + "=? AND " +
                PokemonContract.PokemonEntry.COL_SPECIES + "=? AND " +
                PokemonContract.PokemonEntry.COL_GENDER + "=? AND " +
                PokemonContract.PokemonEntry.COL_HEIGHT + "=? AND " +
                PokemonContract.PokemonEntry.COL_WEIGHT + "=? AND " +
                PokemonContract.PokemonEntry.COL_LEVEL + "=? AND " +
                PokemonContract.PokemonEntry.COL_HP + "=? AND " +
                PokemonContract.PokemonEntry.COL_ATTACK + "=? AND " +
                PokemonContract.PokemonEntry.COL_DEFENSE + "=?";
        String[] args = {
                String.valueOf(cv.getAsInteger(PokemonContract.PokemonEntry.COL_NATIONAL)),
                cv.getAsString(PokemonContract.PokemonEntry.COL_NAME),
                cv.getAsString(PokemonContract.PokemonEntry.COL_SPECIES),
                cv.getAsString(PokemonContract.PokemonEntry.COL_GENDER),
                cv.getAsString(PokemonContract.PokemonEntry.COL_HEIGHT),
                cv.getAsString(PokemonContract.PokemonEntry.COL_WEIGHT),
                String.valueOf(cv.getAsInteger(PokemonContract.PokemonEntry.COL_LEVEL)),
                String.valueOf(cv.getAsInteger(PokemonContract.PokemonEntry.COL_HP)),
                String.valueOf(cv.getAsInteger(PokemonContract.PokemonEntry.COL_ATTACK)),
                String.valueOf(cv.getAsInteger(PokemonContract.PokemonEntry.COL_DEFENSE))
        };

        String[] projection = new String[]{
                PokemonContract.PokemonEntry._ID
        };
        try(Cursor c = context.getContentResolver().query(PokemonContract.PokemonEntry.CONTENT_URI,
                projection, sel, args, null)){
            return c != null && c.moveToFirst();
        }
    }

    public Uri insertPokemon(ContentValues cv) {
        return context.getContentResolver().insert(PokemonContract.PokemonEntry.CONTENT_URI, cv);

    }
}
