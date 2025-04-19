package com.example.calculador_resistencias;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
//import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
//import android.widget.Toast;

import static java.lang.Float.*;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Capacitance_editText;
    EditText Resistance_editText;
    EditText energy_editText     ;
    EditText tau_editText        ;
    EditText power_editText      ;
    EditText tau5_editText       ;
    Spinner spinner_capacitance  ;
    Spinner spinner_resistance   ;
    Spinner spinner_voltage      ;
    Button button_Calculate      ;
    Button button_Search         ;
    EditText Voltage_editText    ;

    Editable Resistance_Value;
    Editable Capacitance_Value;
    Editable Voltage_Value;

    String[] spinner_capacitance_items = new String[] {"pF", "nF", "uF", "mF", "F"};
    String[] spinner_resistance_items = new String[] {"Ohms", "kOhms", "MOhms"};
    String[] spinner_voltage_items = new String[]{"uV", "mV", "V", "kV"};


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Capacitance_editText = findViewById(R.id.Capacitance_editText);
        Resistance_editText  = findViewById(R.id.Resistence_editText);
        energy_editText      = findViewById(R.id.energy_editText);
        tau_editText         = findViewById(R.id.tau_editText);
        power_editText       = findViewById(R.id.power_editText);
        tau5_editText        = findViewById(R.id.tau5_editText);
        spinner_capacitance  = findViewById(R.id.spinner_Capacitance);
        spinner_resistance   = findViewById(R.id.spinner_Resistence);
        spinner_voltage      = findViewById(R.id.spinner_Voltage);
        button_Calculate     = findViewById(R.id.button_Calculate);
        Voltage_editText     = findViewById(R.id.Voltage_editText);
        button_Search        = findViewById(R.id.button_Search);

        final Calculate Result = new Calculate(null);

        ArrayAdapter<String> adapter_voltage = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinner_voltage_items);
        adapter_voltage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_voltage.setAdapter(adapter_voltage);
        spinner_voltage.setSelection(2);

        ArrayAdapter<String> adapter_capacitance = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinner_capacitance_items);
        adapter_capacitance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_capacitance.setAdapter(adapter_capacitance);
        spinner_capacitance.setSelection(2);

        ArrayAdapter<String> adapter_resistance = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinner_resistance_items);
        adapter_resistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resistance.setAdapter(adapter_resistance);
        spinner_resistance.setSelection(0);

        /*Listeners*/
        spinner_voltage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Result.Set_Prefix_Voltage(String.valueOf(spinner_voltage.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_capacitance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Result.Set_Prefix_Capacitance(String.valueOf(spinner_capacitance.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_resistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Result.Set_Prefix_Resistance(String.valueOf(spinner_resistance.getSelectedItem()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        button_Calculate.setOnClickListener(view -> {
            Resistance_Value  =  Resistance_editText.getText();
            Capacitance_Value = Capacitance_editText.getText();
            Voltage_Value     = Voltage_editText.getText();

            if(Resistance_Value.toString().matches("") ||
               Capacitance_Value.toString().matches("") ||
               Voltage_Value.toString().matches("")
            )
            {
                button_Search.setEnabled(false);
                tau_editText.setText("Wrong Value");
                tau5_editText.setText("Wrong Value");
                energy_editText.setText("Wrong Value");
                power_editText.setText("Wrong Value");
            }
            else
            {
                Result.Calculate_All(parseFloat(String.valueOf(Voltage_Value)),
                        parseFloat(String.valueOf(Capacitance_Value)),
                        parseFloat(String.valueOf(Resistance_Value))
                );
                energy_editText.setText(Result.Get_Value("J"));
                power_editText.setText(Result.Get_Value("W"));
                tau_editText.setText(Result.Get_Value("T"));
                tau5_editText.setText(Result.Get_Value("T5"));
                button_Search.setEnabled(true);
            }
        });

button_Search.setOnClickListener(view -> {
    String Resistor_String = Resistance_Value + "+" + spinner_resistance.getSelectedItem() + "+" + Result.Get_Value_Power() + Result.Get_Prefix_Power();
    String URL_Search = "https://www.google.com/search?biw=1920&bih=937&sxsrf=ALeKk039Y2wmwMi9pIyREFu5d4CFlWpiYg%3A1599421711956&ei=Dz1VX83TOYLcsAX9kpnQAw&q=resistor+"+ Resistor_String + "+&oq=resistor+" + Resistor_String + "+&gs_lcp=CgZwc3ktYWIQAxgAMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIICAAQFhAKEB46BAgAEEc6BQgAEMsBUJThAli6xgNg9tMDaABwAXgAgAG_AYgBjwSSAQMwLjOYAQCgAQGqAQdnd3Mtd2l6wAEB&sclient=psy-ab";

    Uri uri = Uri.parse(URL_Search);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
});

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}

