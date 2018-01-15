package com.luis_santiago.cryptoconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.luis_santiago.cryptoconverter.Model.Crypto;
import com.luis_santiago.cryptoconverter.Model.Payload;
import com.luis_santiago.cryptoconverter.Model.Response;
import com.luis_santiago.cryptoconverter.tools.ApiClient;
import com.luis_santiago.cryptoconverter.tools.CryptoCoinAdapter;

import java.util.*;

import rx.*;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CryptoCoinAdapter cryptoCoinAdapter;

    private Subscription mSubscription;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecycleview = findViewById(R.id.recycle_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleview.setLayoutManager(mGridLayoutManager);
        ArrayList<Crypto> cryptoArrayList = new ArrayList<>();
        cryptoArrayList.add(new Crypto("Bitcoin" , 291996.85 , R.drawable.bitcoin_test_image , "BTC"));
        cryptoArrayList.add(new Crypto("Etherium" , 291996.85 , R.drawable.ethereum , "ETH"));
        cryptoArrayList.add(new Crypto("Ripple" , 291996.85 , R.drawable.litecoin, "XRP"));
        cryptoArrayList.add(new Crypto("Litcoin" , 291996.85 , R.drawable.ripple, "LTC"));
        cryptoCoinAdapter = new CryptoCoinAdapter(cryptoArrayList , MainActivity.this);
        mRecycleview.setAdapter(cryptoCoinAdapter);
        setUpAd();
        setUpRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.local_value:{
                startActivity(new Intent(MainActivity.this , SettingsActivity.class));
                break;
            }
        }
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        cryptoCoinAdapter.notifyDataSetChanged();
        if (mAdView != null) {
            mAdView.resume();
        }
    }


    private void setUpAd(){
        mAdView = findViewById(R.id.adView);
        MobileAds.initialize(this, "ca-app-pub-5461480863776866~3839257028");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        if (mAdView!= null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void setUpRequest(){
        Observable<Response> responseOneObservable = ApiClient.getApiClient().getLatestPrices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Response> responseTwoObservable = ApiClient.getApiClient().getLatestPrices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.zip(responseOneObservable, responseTwoObservable, new Func2<Response, Response, Response>() {
            @Override
            public Response call(Response response, Response response2) {
                Log.e(TAG , "I GOT THE DATA FROM TWO RESPONDS" + response.getPayload().toString());
                return response;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            Log.e(TAG , " ERRORR" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
