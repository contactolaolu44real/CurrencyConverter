package com.example.olaolu.currencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConversionBase extends AppCompatActivity implements View.OnClickListener {

    private Spinner baseSpinner;
    private Spinner targetSpinner;
    private EditText conversionAmount;
    private TextView conversionDisplay;
    private ArrayList<String> baseArraySpinner,currencyArraySpinner;
    private String baseCurrency;
    private String choseCurrency;
    private String amountBase;
    private Button calculate;
    private float converted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_base);
        baseSpinner = findViewById(R.id.baseSpinner);
        targetSpinner = findViewById(R.id.targetSpinner);
        conversionAmount = findViewById(R.id.conversionAmount);
        conversionDisplay = findViewById(R.id.conversionDisplay);
        calculate = findViewById(R.id.btnCalculate);
        baseArraySpinner=new ArrayList<>();
        currencyArraySpinner=new ArrayList<>();

        Intent intent=getIntent();
        baseCurrency = intent.getStringExtra(CountryEquivalence.SELECTED_BASE);
        choseCurrency = intent.getStringExtra(CountryEquivalence.COUNTRY_CODE);
        amountBase = intent.getStringExtra(CountryEquivalence.CCONVERSION_AMOUNT);

        baseArraySpinner.add(baseCurrency);
        baseArraySpinner.add(choseCurrency);


        currencyArraySpinner.add(baseCurrency);
        currencyArraySpinner.add(choseCurrency);

        ArrayAdapter adapter= new ArrayAdapter(ConversionBase.this, R.layout.spinner_item,baseArraySpinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        baseSpinner.setAdapter(adapter);
        targetSpinner.setAdapter(adapter);

        calculate.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view==calculate){
            String amountTOConvert=conversionAmount.getText().toString();
            if (TextUtils.isEmpty(amountTOConvert)){
                Toast.makeText(this, "Enter amount to convert", Toast.LENGTH_SHORT).show();
            }else {
                String base=baseSpinner.getSelectedItem().toString();
                String target= targetSpinner.getSelectedItem().toString();
                if (base.equals(target)){
                    converted=Long.parseLong(amountTOConvert);
                }else if (baseSpinner.getSelectedItemPosition()==0&&!base.equals(target)){

                        float val=Float.parseFloat(amountBase);


                    converted=Float.parseFloat(amountBase)*Float.parseFloat(amountTOConvert);

                }else {
                    converted=((1/Float.parseFloat(amountBase)))*Float.parseFloat(amountTOConvert);
                }
                conversionDisplay.setText(converted+"");
            }

        }
    }
}
