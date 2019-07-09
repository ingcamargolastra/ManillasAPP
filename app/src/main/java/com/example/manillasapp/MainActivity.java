package com.example.manillasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText cantidad;
    TextView cantidadTabla, valorTabla, totalTabla;
    String materiales[], dijes[], tipos[], monedas[];
    Spinner cmbMaterial, cmbDijes, cmbTipos, cmbMonedas;
    int valorDolar, valorUnitario;
    int precios[][][] = new int[2][2][4];
    NumberFormat format = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //llamamos la función para inicializar los precios de las manillas
        inicializarPrecios();

        //traemos el precio del dolar
        valorDolar = Integer.parseInt(getResources().getString(R.string.valor_dolar));

        //capturamos los objetos utilizados
        cantidad = findViewById(R.id.txtCantidad);
        cmbMaterial = (Spinner)findViewById(R.id.cmbMaterial);
        cmbDijes = findViewById(R.id.cmbDije);
        cmbTipos = findViewById(R.id.cmbTipos);
        cmbMonedas = findViewById(R.id.cmbMonedas);
        cantidadTabla = findViewById((R.id.txtCantidadTable));
        valorTabla = findViewById((R.id.txtValorTable));
        totalTabla = findViewById((R.id.txtTotalTable));

        //añadimos el MinMaxFilter a cantidad para evitar que ingresen números muy grandes
        cantidad.setFilters(new InputFilter[]{ new MinMaxFilter("0", "100")});

        //añadimos el evento onKey Para Capturar la cantidad de manillas dinámicamente
        cantidad.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                cantidadTabla.setText(""+cantidad.getText().toString());
                totalTabla.setText("");
                return false;
            }
        });

        //Añadimos el evento a cada combo
        cmbMaterial.setOnItemSelectedListener(this);
        cmbDijes.setOnItemSelectedListener(this);
        cmbTipos.setOnItemSelectedListener(this);
        cmbMonedas.setOnItemSelectedListener(this);

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

    public void calcular(View v){
        int cant, res=0;
        if (validar()){
            cant = Integer.parseInt(cantidad.getText().toString());
            res = cant * valorUnitario;
            totalTabla.setText(""+format.format(res));
        }
    }

    public void limpiar(View v){
        initializeSpinners();
        clearErrorSpinners();
        clearTable();
        clearCantidad();
    }

    public void clearCantidad(){
        cantidad.setText("");
        cantidad.requestFocus();
        cantidad.setError(null);
    }

    public void initializeSpinners(){
        cmbMaterial.setSelection(0);
        cmbDijes.setSelection(0);
        cmbTipos.setSelection(0);
        cmbMonedas.setSelection(0);
    }

    public void clearErrorSpinners(){
        ((TextView)cmbMaterial.getSelectedView()).setError(null);
        ((TextView)cmbDijes.getSelectedView()).setError(null);
        ((TextView)cmbTipos.getSelectedView()).setError(null);
        ((TextView)cmbMonedas.getSelectedView()).setError(null);
    }

    public boolean validar(){
        if (cantidad.getText().toString().isEmpty()){
            cantidad.setError(getResources().getString(R.string.error_required));
            cantidad.requestFocus();
            return false;
        }else if(Integer.parseInt(cantidad.getText().toString())==0){
            cantidad.setError(getResources().getString(R.string.error_0));
            cantidad.selectAll();
            return false;
        }else if(cmbMaterial.getSelectedItemPosition() == 0){
            ((TextView)cmbMaterial.getSelectedView()).setError(getResources().getString(R.string.material_required));
            Toast.makeText(this,getResources().getString(R.string.material_required),Toast.LENGTH_LONG).show();
            return false;
        }else if(cmbDijes.getSelectedItemPosition() == 0){
            ((TextView)cmbDijes.getSelectedView()).setError(getResources().getString(R.string.dije_required));
            Toast.makeText(this,getResources().getString(R.string.dije_required),Toast.LENGTH_LONG).show();
            return false;
        }else if(cmbTipos.getSelectedItemPosition() == 0){
            ((TextView)cmbTipos.getSelectedView()).setError(getResources().getString(R.string.tipo_required));
            Toast.makeText(this,getResources().getString(R.string.tipo_required),Toast.LENGTH_LONG).show();
            return false;
        }else if(cmbMonedas.getSelectedItemPosition() == 0){
            ((TextView)cmbMonedas.getSelectedView()).setError(getResources().getString(R.string.moneda_required));
            Toast.makeText(this,getResources().getString(R.string.moneda_required),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void clearTable(){
        cantidadTabla.setText("");
        valorTabla.setText("");
        totalTabla.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> a, View v, int pos, long l) {
        int posM, posD, posT, posMoneda;
        posM = cmbMaterial.getSelectedItemPosition()-1;
        posD = cmbDijes.getSelectedItemPosition()-1;
        posT = cmbTipos.getSelectedItemPosition()-1;
        posMoneda = cmbMonedas.getSelectedItemPosition()-1;
        totalTabla.setText("");
        if (posM >= 0 && posD >= 0 && posT >= 0 && posMoneda >= 0 ){
            if (posMoneda == 0){
                cantidadTabla.setText(""+cantidad.getText().toString());
                valorUnitario = precios[posM][posD][posT];
                valorTabla.setText(""+format.format(valorUnitario));
            }else if(posMoneda == 1){
                cantidadTabla.setText(""+cantidad.getText().toString());
                valorUnitario = precios[posM][posD][posT]*valorDolar;
                valorTabla.setText(""+format.format(valorUnitario));
            }
            //Toast.makeText(this,""+cantidad.getText(),Toast.LENGTH_LONG).show();
        }else if (posM == 0 || posD == 0 || posT == 0 || posMoneda == 0){
            valorTabla.setText("");
            totalTabla.setText("");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
