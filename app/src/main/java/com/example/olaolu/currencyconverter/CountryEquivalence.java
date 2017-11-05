package com.example.olaolu.currencyconverter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.CountryCurrencyAdapter;

/**
 * Created by Olaolu on 11/3/2017.
 */

public class CountryEquivalence extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    public static final String SELECTED_BASE = "selectedBase";
    public static final String COUNTRY_CODE = "countryCode";
    public static final String CCONVERSION_AMOUNT = "cconversionAmount";
    private Spinner baseCurrency;
    public static ListView countriesList;
    public static List countries;
    public static ArrayList<String> amount;
    private String[] currencyList;
    public static String currencyCode;
    public static int[]images;
    public static ProgressBar progressBar;
    public static TextView welcomeInf;
    private String selectedCurrency;
    private ArrayList<String> currencyCodeArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equivalence_countreies);

        currencyList = new String[]{
                "Nigeria Naira NGN",
                "Taiwan Dollar	TWD",
                "Tanzania Shilling	TZS",
                "Thailand Baht	THB",
                "Tonga Pa'anga	TOP",
                "trinidad/Tobago Dollar	TTD",
                "Tunisia Dinar	TND",
                "Turkish New Lira	TRY",
                "Namibia Dollar NAD",
                "USA Dollar	USD",
                "Uganda Shilling	UGX",
                "Ukraine Hryvnia	UAH",
                "Uruguay Peso	UYU",
                "United Arab Emirates Dirham	AED",
                "Algeria Dinar DZD",
                "Great Britain Pound GBP",
                "Vietnam Dong	VND",
                "Great Britain Pound GBP",
                "Euro EUR",
                "Argentina Peso ARS"

        };
        images= new int[]{R.drawable.nigeria,R.drawable.taiwan,R.drawable.tanzania,R.drawable.thailand,R.drawable.tonga,
        R.drawable.trinidad,R.drawable.tunisia,R.drawable.turkish,R.drawable.turkmenistan,R.drawable.usa,R.drawable.uganda,
                R.drawable.ukraine,R.drawable.uruguay,R.drawable.united_arab_emirates,R.drawable.vanuatu_vatu,R.drawable.venezuela,
                R.drawable.vietnam,R.drawable.yemen,R.drawable.zambia,R.drawable.zimbabwe};
        amount=new ArrayList<>();
        currencyCodeArray=new ArrayList<>();
        countries= Arrays.asList(currencyList);
        CountryCurrencyAdapter adapter=new CountryCurrencyAdapter(CountryEquivalence.this,countries,amount,images);

        baseCurrency = findViewById(R.id.baseCurrency);

        countriesList = findViewById(R.id.countriesListview);
        progressBar = findViewById(R.id.progressBar);
        welcomeInf = findViewById(R.id.info);
        countriesList.setAdapter(adapter);
        baseCurrency.setOnItemSelectedListener(this);
        countriesList.setOnItemClickListener(this);


        AlphaAnimation anim = new AlphaAnimation(1.0f,0.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
                welcomeInf.setText("Use the dropdown above to select your conversion base level");

            }

            public void onAnimationEnd(Animation animation)
            {
                // make invisible when animation completes, you could also remove the view from the layout

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                welcomeInf.setText("Use the dropdwon above to select your choice base level");
            }
        });

        welcomeInf.setAnimation(anim);


        List<String> paper_orientation = new ArrayList<String>();
        paper_orientation.add("-- Select conversion base -- ");
        paper_orientation.add("BTC");
        paper_orientation.add("ETH");

        //creating adapter for paper orientation spinners
        ArrayAdapter<String> baseSelectionAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, paper_orientation);

        baseSelectionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        baseCurrency.setAdapter(baseSelectionAdapter);




    }

    private void loadCountriesEquivalence(int z) {
        if (z==0){

        }else{
            amount.clear();
            currencyCodeArray.clear();
            selectedCurrency = baseCurrency.getSelectedItem().toString();
            for (int i=0;i<currencyList.length;i++){
                String country=currencyList[i];

                currencyCode = country.substring(country.length()-3,country.length());
                String url="https://min-api.cryptocompare.com/data/price?fsym="+ selectedCurrency +"&tsyms="+ currencyCode;
                currencyCodeArray.add(currencyCode);

                try {
                    URL networkURL=new URL(url);
                    ConversionClass conversionClass=new ConversionClass();

                    conversionClass.execute(networkURL);
                } catch (MalformedURLException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            loadCountriesEquivalence(i);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent= new Intent(CountryEquivalence.this,ConversionBase.class);
        intent.putExtra(SELECTED_BASE,selectedCurrency);
        intent.putExtra(COUNTRY_CODE,currencyCodeArray.get(i));
        intent.putExtra(CCONVERSION_AMOUNT,amount.get(i));
        startActivity(intent);

    }


    static class ConversionClass extends AsyncTask<URL,Integer,String>{
        @Override
        protected String doInBackground(URL... urls) {
            String price=null;
            BufferedReader bufferedReader=null;
            StringBuilder buffer=null;
            try {
                HttpURLConnection connection= (HttpURLConnection) urls[0].openConnection();
                InputStream stream=connection.getInputStream();
                buffer=new StringBuilder();
                bufferedReader=new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    buffer.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String objectString=buffer.toString();
            try {
               String[] array=objectString.split(":");
               price=array[1].substring(0,array[1].length()-1);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return price;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            welcomeInf.setVisibility(View.GONE);
            CountryEquivalence.amount.add(s);

            ArrayAdapter adapter=(ArrayAdapter)countriesList.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }
}
