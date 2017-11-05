package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olaolu.currencyconverter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olaolu on 11/3/2017.
 */

public class CountryCurrencyAdapter extends ArrayAdapter {
    List<String> country;
    ArrayList<String> amount;
    int []imageDrawable;
    Context context;
    private TextView countName;
    private TextView conversin;
    private ImageView image;

    public CountryCurrencyAdapter(Context context,List<String> country,ArrayList amount,int[] imageDrawable){
       super(context, R.layout.country_currency_adapter,R.id.adapterCountryName,amount);

       this.country = country;
       this.amount=amount;
       this.context=context;
       this.imageDrawable=imageDrawable;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= null;
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view= inflater.inflate(R.layout.country_currency_adapter,parent,false);
        }else {
            view=convertView;
        }
        countName = view.findViewById(R.id.adapterCountryName);
        conversin = view.findViewById(R.id.adapterPresentPrice);
        image = view.findViewById(R.id.adapterImage);
        if (country.get(position).length()>21){
            String sub=country.get(position).substring(0,15);
            String realString=country.get(position);
            countName.setText(sub+"..."+country.get(position).substring(realString.length()-3,realString.length()));
        }else {
            countName.setText(country.get(position));
        }

        conversin.setText(amount.get(position));
        image.setImageResource(imageDrawable[position]);
        return view;
    }
}
