package com.luis_santiago.cryptoconverter;

import android.app.ProgressDialog;
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
import rx.functions.Func4;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CryptoCoinAdapter cryptoCoinAdapter;

    private Subscription mSubscription;

    private RecyclerView mRecycleview;

    private AdView mAdView;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleview = findViewById(R.id.recycle_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecycleview.setLayoutManager(mGridLayoutManager);
        setUpAd();
        setUpRequest();
        displayDialogue();
    }

    private void generateData(Double bitCoin , Double etherium , Double ripple , Double litcoin){
        Log.e(TAG , "IM RECIVING RIPPLE VALUE " + ripple);
        ArrayList<Crypto> cryptoArrayList = new ArrayList<>();
        cryptoArrayList.add(new Crypto("Bitcoin" , bitCoin , R.drawable.bitcoin_test_image , "BTC"));
        cryptoArrayList.add(new Crypto("Etherium" , etherium , R.drawable.ethereum , "ETH"));
        cryptoArrayList.add(new Crypto("Ripple" , ripple , R.drawable.ripple, "XRP"));
        cryptoArrayList.add(new Crypto("Litcoin" , litcoin , R.drawable.litecoin, "LTC"));
        cryptoCoinAdapter = new CryptoCoinAdapter(cryptoArrayList , MainActivity.this);
        mRecycleview.setAdapter(cryptoCoinAdapter);
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
        if(cryptoCoinAdapter !=null){
            cryptoCoinAdapter.notifyDataSetChanged();
        }
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
        Observable<Response> responseOneObservable = ApiClient.getApiClient().getLatestPricesBitCoin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Response> responseOneObservable2 = ApiClient.getApiClient().getLatestPricesEtherium()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Response> responseTwoObservable3 = ApiClient.getApiClient().getLatestPricesRipple()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<Response> responseTwoObservable4 = ApiClient.getApiClient().getLatestPricesLitecoin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observable.zip(responseOneObservable, responseOneObservable2,responseTwoObservable3 ,responseTwoObservable4 , new Func4<Response, Response, Response, Response, Object>() {
            @Override
            public Object call(Response bitcoin, Response eth, Response ripple, Response litecoin) {

                generateData(
                        Double.valueOf(bitcoin.getPayload().getHigh()),
                        Double.valueOf(eth.getPayload().getHigh()),
                        Double.valueOf(ripple.getPayload().getHigh()),
                        Double.valueOf(litecoin.getPayload().getHigh())
                );

                dialog.dismiss();
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //We load the local data
                        dialog.dismiss();
                        generateData(3000499.97 , 27533.36 , 38.00 , 5120.22);
                    }

                    @Override
                    public void onNext(Object o) {
                    }
                });
    }

    public void displayDialogue(){
        dialog = ProgressDialog.show(MainActivity.this, "Loading",
                "Loading latest values", true);
    }
}
