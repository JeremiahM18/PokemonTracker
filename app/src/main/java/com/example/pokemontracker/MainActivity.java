package com.example.pokemontracker;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Defaults and Ranges
    private static final int nat = 896;
    private static final int hp = 0, atk = 0, def = 0;

    private static final int natMin = 0, natMax = 1010;
    private static final int nameMin = 3, nameMax = 12;
    private static final int hpMin = 1, hpMax = 362;
    private static final int atkMin = 0, atkMax = 526;
    private static final int defMin = 10, defMax = 614;
    private static final double heightMin = 0.2, heightMax = 169.99;
    private static final double weightMin = 0.1, weightMax = 992.70;

    // Allowed characters
    private static final Pattern namPattern = Pattern.compile("^[A-Za-z. ]+$");
    private static final Pattern specPattern = Pattern.compile("^[A-Za-z. ]+$");

    // Inputs
    private EditText national, name, species, height,
            weight, iHp, iAtk, iDef;
    private RadioGroup genderGroup;
    private RadioButton male, female, unk;
    private Spinner spLevel;

    // Labels
    private TextView labelNational, labelName, labelSpecies, labelGender,
            labelHeight, labelWeight, labelLevel, labelHP, labelAtk, labelDef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);
        //setContentView(R.layout.constraint);
        //setContentView(R.layout.table);

        bindViews();

    }

    private void bindViews() {
        // Inputs
        national = findViewById(R.id.nationalInput);
        name = findViewById(R.id.nameInput);
        species = findViewById(R.id.speciesInput);
        genderGroup = findViewById(R.id.genderGroup);
        height = findViewById(R.id.heightInput);
        weight = findViewById(R.id.weightInput);
        spLevel = findViewById(R.id.levelSpinner);
        iHp = findViewById(R.id.hpInput);
        iAtk = findViewById(R.id.attackInput);
        iDef = findViewById(R.id.defenseInput);

        // Labels
        labelNational = findViewById(R.id.nationalNumber);
        labelName = findViewById(R.id.name);
        labelSpecies = findViewById(R.id.species);
        labelGender = findViewById(R.id.gender);
        labelHeight = findViewById(R.id.height);
        labelWeight = findViewById(R.id.weight);
        labelLevel = findViewById(R.id.level);
        labelHP = findViewById(R.id.hp);
        labelAtk = findViewById(R.id.attack);
        labelDef = findViewById(R.id.defense);

        // Filters for names and species
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!(Character.isLetter(c) || c == '.' || c == ' ')) return "";
            }
            return null;
        };
        name.setFilters(new InputFilter[]{filter});
        InputFilter lfilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!(Character.isLetter(c) || c == ' ')) return "";
            }
            return null;
        };
        species.setFilters(new InputFilter[]{lfilter});

        // Auto-capitalize each word
        TextWatcher watcher = new TextWatcher() {
            boolean cap;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (cap) return;
                cap = true;
                String str = capitalizeWords(s.toString());
                if (!str.equals(s.toString())) {
                    s.replace(0, s.length(), str);
                }
                cap = false;

            }

        };
        name.addTextChangedListener(watcher);
        species.addTextChangedListener(watcher);
    }

    private String capitalizeWords(String str) {
        if (str.isEmpty()) return str;
        String[] parts = str.toLowerCase(Locale.US).trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

}
