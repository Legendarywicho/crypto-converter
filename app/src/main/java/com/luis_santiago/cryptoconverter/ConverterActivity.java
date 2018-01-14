package com.luis_santiago.cryptoconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.luis_santiago.cryptoconverter.tools.Keys;

import java.text.DecimalFormat;
import java.util.*;

public class ConverterActivity extends AppCompatActivity implements TextWatcher {

    private Spinner originSpinner;

    private Spinner resultSpinner;

    private EditText mEditText;

    private TextView mResultView;

    private ArrayList<String> options;

    private final String TAG = ConverterActivity.class.getSimpleName();

    private String mainUnit = "";

    private final int LOCAL_COIN_TO_CRYPTO = 1;

    private final int CRYPTO_COIN_TO_LOCAL = 0;

    private double unitValue = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        init();
        try{
            mainUnit = getIntent().getExtras().getString(Keys.KEY_UNIT_COIN);
            unitValue = getIntent().getExtras().getDouble(Keys.KEY_VALUE_COIN);
        }catch (Exception e){
            e.printStackTrace();
        }

        loadSpinnerData();
    }

    private void loadSpinnerData(){
        options = new ArrayList<>();
        options.add(mainUnit);
        options.add("MX");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(dataAdapter);
        resultSpinner.setAdapter(dataAdapter);
        resultSpinner.setSelection(1);
        resultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int positionFromResultSpinner = originSpinner.getSelectedItemPosition();
                if(positionFromResultSpinner == position){
                    if (position == LOCAL_COIN_TO_CRYPTO){
                        originSpinner.setSelection(0);
                        calculateLocalToUnit();
                    }else {
                        originSpinner.setSelection(1);
                        calculateUnitToLocal();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        originSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int positionFromResultSpinner = resultSpinner.getSelectedItemPosition();
                if(positionFromResultSpinner == position){
                    if (position == LOCAL_COIN_TO_CRYPTO){
                        resultSpinner.setSelection(0);
                        calculateLocalToUnit();
                    }else {
                        resultSpinner.setSelection(1);
                        calculateUnitToLocal();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init(){
        originSpinner = findViewById(R.id.origin_converter);
        resultSpinner = findViewById(R.id.result_converter);
        mEditText = findViewById(R.id.input_text);
        mResultView = findViewById(R.id.result_textview);
        mEditText.addTextChangedListener(this);
    }

    private void calculateUnitToLocal(){
        double currentNumber = getParseResultFromUser();
        double conversion = unitValue * currentNumber;
        String format = "$#,###.00" + "MXN";
        mResultView.setText(createFormatToShow(format , conversion));
    }

    private void calculateLocalToUnit(){
        double currentNumber = getParseResultFromUser();
        double conversion = currentNumber/unitValue;
        String format = "#,###.00 " + mainUnit;
        mResultView.setText(createFormatToShow(format , conversion));
    }

    private Double getParseResultFromUser(){
        Double currentNumber = 0.0;
        try{
            currentNumber = Double.valueOf(mEditText.getText().toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return currentNumber;
    }


    private String createFormatToShow(String format , double value){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(value);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int currentPosition = originSpinner.getSelectedItemPosition();
        switch (currentPosition){
            case CRYPTO_COIN_TO_LOCAL : {
                Log.e(TAG , "Calculate pesos to bitcoin");
                calculateUnitToLocal();
                break;
            }
            case LOCAL_COIN_TO_CRYPTO :{
                Log.e(TAG , "calculate bitcoin to pesos");
                calculateLocalToUnit();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
