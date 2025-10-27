package com.example.pokemontracker;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemontracker.data.PokemonContract.PokemonEntry;

public class PokemonListActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        listView = findViewById(R.id.pokemonListView);
        Button goBack = findViewById(R.id.backToMain);
        goBack.setOnClickListener(v -> {
            finish();
        });


        // Define which columns to display
        String[] from = {
                PokemonEntry.COL_NATIONAL,
                PokemonEntry.COL_NAME,
                PokemonEntry.COL_SPECIES
        };

        int[] to = {
                R.id.textNational,
                R.id.textName,
                R.id.textSpecies
        };

        // Query database
        Cursor cursor = getContentResolver().query(
                PokemonEntry.CONTENT_URI,
                null, null, null,
                PokemonEntry.COL_NAME + " ASC"
        );

        adapter = new SimpleCursorAdapter(this,
                R.layout.item_pokemon, cursor, from, to, 0);

        listView.setAdapter(adapter);

        // Click to delete a Pokemon
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri deleteUri = ContentUris.withAppendedId(PokemonEntry.CONTENT_URI, id);
            int rows = getContentResolver().delete(deleteUri, null, null);

            if (rows > 0) {
                Toast.makeText(this, "Pokemon deleted", Toast.LENGTH_SHORT).show();
                refreshList();
            } else {
                Toast.makeText(this, "Error deleting Pokemon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshList() {
        Cursor cursor = getContentResolver().query(
                PokemonEntry.CONTENT_URI,
                null, null, null,
                PokemonEntry.COL_NAME + " ASC"
        );
        adapter.swapCursor(cursor);
    }
}
