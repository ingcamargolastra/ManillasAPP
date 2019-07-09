package com.example.manillasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText cantidad;
    TextView valor, total;
    String materiales[], dijes[], tipos[], monedas[];
    Spinner cmbMaterial, cmbDijes, cmbTipos, cmbMonedas;
    int precios[][][] = new int[2][2][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //llamamos la función para inicializar los precios de las manillas
        inicializarPrecios();

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

    private void inicializarPrecios(){
        //Inicializamos los precios de cada manilla en dolares ($)
        //material:Cuero, dije:Martillo y tipos
        precios[0][0][0] = 100;
        precios[0][0][1] = 100;
        precios[0][0][2] = 80;
        precios[0][0][3] = 70;

        //material:Cuero, dije:Ancla y tipos
        precios[0][1][0] = 120;
        precios[0][1][1] = 120;
        precios[0][1][2] = 100;
        precios[0][1][3] = 90;

        //material:Cuerda, dije:Martillo y tipos
        precios[1][0][0] = 90;
        precios[1][0][1] = 90;
        precios[1][0][2] = 70;
        precios[1][0][3] = 50;

        //material:Cuerda, dije:Ancla y tipos
        precios[1][1][0] = 110;
        precios[1][1][1] = 110;
        precios[1][1][2] = 90;
        precios[1][1][3] = 80;
    }
}
