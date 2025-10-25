package com.example.pokemontracker.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PokemonContract {

    private PokemonContract(){
    }

    public static final String AUTHORITY = "com.example.pokemon.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_POKEMON = "pokemon";
    public static final class PokemonEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POKEMON).build();

        public static final String TABLE_NAME = "pokemon";
        public static final String COL_NATIONAL = "national";
        public static final String COL_NAME = "name";
        public static final String COL_SPECIES = "species";
        public static final String COL_GENDER = "gender";
        public static final String COL_HEIGHT = "height";
        public static final String COL_WEIGHT = "weight";
        public static final String COL_LEVEL = "level";
        public static final String COL_HP = "hp";
        public static final String COL_ATTACK = "attack";
        public static final String COL_DEFENSE = "defense";

        public static final String[] PROJECTION_ALL = {
                _ID, COL_NATIONAL, COL_NAME, COL_SPECIES, COL_GENDER, COL_HEIGHT,
                COL_WEIGHT, COL_LEVEL, COL_HP, COL_ATTACK, COL_DEFENSE
        };

    }

    public static final String TABLE_NAME = "pokemon";



}
