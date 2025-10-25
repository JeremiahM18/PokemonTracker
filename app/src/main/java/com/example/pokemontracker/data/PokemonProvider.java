package com.example.pokemontracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static com.example.pokemontracker.data.PokemonContract.*;

public class PokemonProvider extends ContentProvider {

    private static final int CODE_POKEMON = 100;
    private static final int CODE_POKEMON_ID = 101;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(PokemonContract.AUTHORITY, PokemonContract.PATH_POKEMON, CODE_POKEMON);
        uriMatcher.addURI(PokemonContract.AUTHORITY,
                PokemonContract.PATH_POKEMON + "/#", CODE_POKEMON_ID);
    }

    private PokemonDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new PokemonDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c;
        switch (uriMatcher.match(uri)) {
            case CODE_POKEMON:
                c = db.query(PokemonEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case CODE_POKEMON_ID:
                String sel = PokemonEntry._ID + "=?";
                String[] args = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c = db.query(PokemonEntry.TABLE_NAME, projection, sel,
                        args, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        if (ctx != null) {
            c.setNotificationUri(ctx.getContentResolver(), uri);
            return c;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != CODE_POKEMON) {
            throw new IllegalArgumentException("Insert not supported for " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(PokemonEntry.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }

        Uri newUri = ContentUris.withAppendedId(PokemonEntry.CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;
        switch (uriMatcher.match(uri)) {
            case CODE_POKEMON:
                rows = db.delete(PokemonEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_POKEMON_ID:
                String sel = PokemonEntry._ID + "=?";
                String[] args = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rows = db.delete(PokemonEntry.TABLE_NAME, sel, args);
                break;
            default:
                throw new IllegalArgumentException("Delete not supported for " + uri);
        }
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

}
