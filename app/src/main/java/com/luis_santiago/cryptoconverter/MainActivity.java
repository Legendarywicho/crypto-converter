package com.luis_santiago.cryptoconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luis_santiago.cryptoconverter.Model.Crypto;
import com.luis_santiago.cryptoconverter.tools.CryptoCoinAdapter;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecycleview = findViewById(R.id.recycle_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleview.setLayoutManager(mGridLayoutManager);

        ArrayList<Crypto> cryptoArrayList = new ArrayList<>();
        cryptoArrayList.add(new Crypto("Bitcoin" , 288130.33 , R.drawable.bitcoin_test_image , "BTC"));
        cryptoArrayList.add(new Crypto("Etherium" , 288130.33 , R.drawable.ethereum , "ETH"));
        cryptoArrayList.add(new Crypto("Ripple" , 288130.33 , R.drawable.litecoin, "XRP"));
        cryptoArrayList.add(new Crypto("Litcoin" , 288130.33 , R.drawable.ripple, "LTC"));
        CryptoCoinAdapter cryptoCoinAdapter = new CryptoCoinAdapter(cryptoArrayList);
        mRecycleview.setAdapter(cryptoCoinAdapter);
    }
}
