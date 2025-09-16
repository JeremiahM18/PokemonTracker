package com.example.pokemontracker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Defaults and Ranges
    private static final int dNat = 896;
    private static final String dName = "Glastrier";
    private static final String dSpecies = "Wild Horse Pokemon";
    private static final String dHeight = "2.2 m";
    private static final String dWeight = "800.00 kg";
    private static final int hp = 0, atk = 0, def = 0;

    private static final int natMin = 0, natMax = 1010;
    private static final int nameMin = 3, nameMax = 12;
    private static final int hpMin = 1, hpMax = 362;
    private static final int atkMin = 0, atkMax = 526;
    private static final int defMin = 10, defMax = 614;
    private static final double heightMin = 0.2, heightMax = 169.99;
    private static final double weightMin = 0.1, weightMax = 992.70;

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
        attachUnits();
        setFiltersAndCaps();
        setDefaults();

        // Buttons
        Button reset = findViewById(R.id.reset);
        Button save = findViewById(R.id.save);

        reset.setOnClickListener(v -> setDefaults());

        save.setOnClickListener(v -> {
            clearLabelColors();
            String error = validateAll();
            if (error.isEmpty()) {
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

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

        // Radio Buttons
        male = findViewById(R.id.genderMale);
        female = findViewById(R.id.genderFemale);
        unk = findViewById(R.id.genderUNK);
    }

    private void setDefaults() {
        national.setText(String.valueOf(dNat));
        name.setText(dName);
        species.setText(dSpecies);
        height.setText(dHeight);
        weight.setText(dWeight);
        iHp.setText(String.valueOf(hp));
        iAtk.setText(String.valueOf(atk));
        iDef.setText(String.valueOf(def));
        // Defaults for selectors
        unk.setChecked(true);
        spLevel.setSelection(0);
        clearLabelColors();
    }

    private void clearLabelColors() {
        int normal = Color.BLACK;
        labelNational.setTextColor(normal);
        labelName.setTextColor(normal);
        labelSpecies.setTextColor(normal);
        labelGender.setTextColor(normal);
        labelHeight.setTextColor(normal);
        labelWeight.setTextColor(normal);
        labelLevel.setTextColor(normal);
        labelHP.setTextColor(normal);
        labelAtk.setTextColor(normal);
        labelDef.setTextColor(normal);
    }

    private void markError(TextView label) {
        label.setTextColor(Color.RED);
    }

    private String validateAll() {
        StringBuilder sb = new StringBuilder();

        // Empty/unselected
        if (isBlank(national)) {
            sb.append("National Number is required.\n");
            markError(labelNational);
        }
        if (isBlank(name)) {
            sb.append("Name is required.\n");
            markError(labelName);
        }
        if (isBlank(species)) {
            sb.append("Species is required.\n");
            markError(labelSpecies);
        }
        if (genderGroup.getCheckedRadioButtonId() == -1) {
            sb.append("Gender is required.\n");
            markError(labelGender);
        }
        if (isBlank(height)) {
            sb.append("Height is required.\n");
            markError(labelHeight);
        }
        if (isBlank(weight)) {
            sb.append("Weight is required.\n");
            markError(labelWeight);
        }
        if (spLevel.getSelectedItem() == null) {
            sb.append("Level is required.\n");
            markError(labelLevel);
        }
        if (isBlank(iHp)) {
            sb.append("HP is required.\n");
            markError(labelHP);
        }
        if (isBlank(iAtk)) {
            sb.append("Attack is required.\n");
            markError(labelAtk);
        }
        if (isBlank(iDef)) {
            sb.append("Defense is required.\n");
            markError(labelDef);
        }

        if (sb.length() > 0) return sb.toString();

        // National
        int n = parseInt(national.getText().toString());
        if (n < natMin || n > natMax) {
            sb.append("National Number must be between 0 and 1010.\n");
            markError(labelNational);
        }

        // Name
        String nm = name.getText().toString().trim();
        if (!nm.matches("^[A-Za-z. ']+$") || nm.length() < nameMin || nm.length() > nameMax) {
            sb.append("Name must be between 3 and 12 characters.\n");
            markError(labelName);
        }

        // Species
        String sp = species.getText().toString().trim();
        if (!sp.matches("^[A-Za-z ]+$")) {
            sb.append("Species must only contain letters and spaces.\n");
            markError(labelSpecies);
        }

        // Gender
        int gId = genderGroup.getCheckedRadioButtonId();
        String g = ((RadioButton) findViewById(gId)).getText().toString();
        if (!g.equals("Male") || g.equals("Female") || g.equals("Unknown")) {
            sb.append("Gender must be Male, Female, or Unknown.\n");
            markError(labelGender);
        }

        // Height/Weight
        double h = parseDoubleWithUnit(height.getText().toString(), "m");
        if (Double.isNaN(h) || h < heightMin || h > heightMax) {
            sb.append("Height must be between 0.2m and 169.99m.\n");
            markError(labelHeight);
        }

        double w = parseDoubleWithUnit(weight.getText().toString(), "kg");
        if (Double.isNaN(w) || w < weightMin || w > weightMax) {
            sb.append("Weight must be between 0.1kg and 992.70kg.\n");
            markError(labelWeight);
        }

        // Level 1-50
        int lvl = Integer.parseInt(spLevel.getSelectedItem().toString());
        if (lvl < 1 || lvl > 50) {
            sb.append("Level must be between 1 and 50.\n");
            markError(labelLevel);
        }

        // Stats
        int hp = parseInt(iHp.getText().toString());
        if (hp < hpMin || hp > hpMax) {
            sb.append("HP must be between 1 and 362.\n");
            markError(labelHP); }

        int atk = parseInt(iAtk.getText().toString());
        if (atk < atkMin || atk > atkMax) {
            sb.append("Attack must be between 0 and 526.\n");
            markError(labelAtk); }

        int def = parseInt(iDef.getText().toString());
        if (def < defMin || def > defMax) {
            sb.append("Defense must be between 10 and 614.\n");
            markError(labelDef); }

        return sb.toString();



    }

    private boolean isBlank(EditText edit) {
        return edit.getText() == null || edit.getText().toString().trim().isEmpty();
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    private double parseDoubleWithUnit(String s, String unit) {
        String t = s.toLowerCase(Locale.US).replace(unit, "").replaceAll("[^0-9.]", "").trim();
        try {
            return Double.parseDouble(t);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private void setFiltersAndCaps() {
        // Filters for names and species
        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!(Character.isLetter(c) || c == '.' || c == ' ')) return "";
            }
            return null;
        };
        name.setFilters(new InputFilter[]{
                filter,
                new InputFilter.LengthFilter(nameMax)
        });

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
        if (str == null || str.isEmpty()) return str;
        String[] parts = str.toLowerCase(Locale.US).trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    private void attachUnits() {
        attachUnitWatch(height, "m");
        attachUnitWatch(weight, "kg");
    }

     // Keeps the EditText value numeric with at most one '.' and
     // two decimals
    private void attachUnitWatch(EditText edit, String unit) {
        edit.addTextChangedListener(new TextWatcher() {
            boolean editing;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (editing) return;
                editing = true;

                String raw = s.toString();

                // Remove existing unit
                String noUnit = raw.endsWith(unit) ? raw.substring(0, raw.length() - unit.length()) : raw;

                // Keep only digits and decimal point
                StringBuilder clean = new StringBuilder();
                boolean dot = false;
                for (char c : noUnit.toCharArray()) {
                    if (Character.isDigit(c)) {
                        clean.append(c);
                    } else if (c == '.' && !dot) {
                        clean.append(c);
                        dot = true;
                    }
                }

                String digits = clean.toString();

                // Max 2 decimals
                int dotIndex = digits.indexOf('.');
                if (dotIndex >= 0 && dotIndex < digits.length() - 1) {
                    String before = digits.substring(0, dotIndex);
                    String after = digits.substring(dotIndex + 1);
                    if (after.length() > 2) after = after.substring(0, 2);
                    digits = before + '.' + after;
                }

                String result = digits.isEmpty() ? "" : digits + unit;

                // Replace text only if it changed
                if (!result.equals(raw)) {
                    s.replace(0, s.length(), result);
                    // Place caret before the unit
                    int caret = Math.max(0, result.length() - unit.length());
                    try {
                        edit.setSelection(caret);
                    } catch (Exception ignored) {
                    }
                }

                editing = false;
            }
        });
    }
}
