package com.example.manillasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText cantidad;
    TextView valor, total;
    String materiales[], dijes[], tipos[], monedas[];
    Spinner cmbMaterial, cmbDijes, cmbTipos, cmbMonedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //capturamos los objetos utilizados
        cantidad = findViewById(R.id.txtCantidad);
        cmbMaterial = (Spinner)findViewById(R.id.cmbMaterial);
        cmbDijes = findViewById(R.id.cmbDije);
        cmbTipos = findViewById(R.id.cmbTipos);
        cmbMonedas = findViewById(R.id.cmbMonedas);

        //Traemos los materiales, dijes, tipos y monedas en Arrays
        materiales = getResources().getStringArray(R.array.materiales);
        dijes = getResources().getStringArray(R.array.dijes);
        tipos = getResources().getStringArray(R.array.tipos);
        monedas = getResources().getStringArray(R.array.monedas);

        //Creamos los adapter indicando donde se va a colocar como se va a visualizar y que se va a mostrar
        ArrayAdapter<String> adapterMateriales = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, materiales);
        ArrayAdapter<String> adapterDijes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, dijes);
        ArrayAdapter<String> adapterTipos = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, tipos);
        ArrayAdapter<String> adapterMonedas = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, monedas);

        //pasamos adaptar a cada combo
        cmbMaterial.setAdapter(adapterMateriales);
        cmbDijes.setAdapter(adapterDijes);
        cmbTipos.setAdapter(adapterTipos);
        cmbMonedas.setAdapter(adapterMonedas);
    }
}
