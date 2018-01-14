package com.luis_santiago.cryptoconverter.tools;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luis_santiago.cryptoconverter.ConverterActivity;
import com.luis_santiago.cryptoconverter.Model.Crypto;
import com.luis_santiago.cryptoconverter.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Luis Fernando Santiago Ruiz on 1/13/18.
 */

public class CryptoCoinAdapter extends RecyclerView.Adapter<CryptoCoinAdapter.ViewHolder>{

    private ArrayList<Crypto> mListOfCriptos = new ArrayList<>();
    private Context mContext;

    public CryptoCoinAdapter(ArrayList <Crypto> anotherListOfCriptos , Context context){
        this.mListOfCriptos = anotherListOfCriptos;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout ,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Crypto criptoCurrency = mListOfCriptos.get(position);
        String localCurrency = Keys.getPreferenceCurrency(mContext);
        double value = 0.0;
        switch (localCurrency){
            case "MXN":{
                value = criptoCurrency.getValue();
                break;
            }
            case "US" :{
                value = criptoCurrency.getValue() / 20 ;
                break;
            }
        }

        NumberFormat formatter = new DecimalFormat("#0.00");

        String valueFormatted = "1 "+criptoCurrency.getUnit() + " = "+ formatter.format(value) + " "+ localCurrency;
        holder.imageCrypto.setImageResource(criptoCurrency.getDrawable());
        holder.nameCrypto.setText(criptoCurrency.getName());
        holder.valueOfCrypto.setText(valueFormatted);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , ConverterActivity.class);
                intent.putExtra(Keys.KEY_UNIT_COIN , criptoCurrency.getUnit());
                intent.putExtra(Keys.KEY_VALUE_COIN , criptoCurrency.getValue());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListOfCriptos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageCrypto;
        private TextView nameCrypto;
        private TextView valueOfCrypto;
        private CardView container;

        public ViewHolder(View v) {
            super(v);
            imageCrypto = v.findViewById(R.id.image_id);
            nameCrypto = v.findViewById(R.id.name_of_cripto);
            valueOfCrypto = v.findViewById(R.id.value_of_cripto);
            container = v.findViewById(R.id.container);
        }
    }
}
