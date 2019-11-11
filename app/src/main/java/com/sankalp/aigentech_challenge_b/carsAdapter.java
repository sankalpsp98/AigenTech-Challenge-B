package com.sankalp.aigentech_challenge_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.sankalp.aigentech_challenge_b.data.CarData;
import com.sankalp.aigentech_challenge_b.data.model.dataWire;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class carsAdapter extends BaseAdapter {
    List<CarData> carDataList;
    Context context;
    onUpadte onUpadte ;
    public carsAdapter(List<CarData> carDataList, Context context,onUpadte onUpadte) {
        this.onUpadte =onUpadte;
        this.carDataList = carDataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return carDataList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView sellerName;
        TextView carName;
        TextView carPrice;
        TextView carSeats;
        TextView carCompany;
        TextView sellerContact;
        ImageView imageView;
        CardView cardView;
        CarData carData = new CarData();
       View itemView= LayoutInflater.from(context).inflate(R.layout.list_cars,null);
        sellerName = itemView.findViewById(R.id.textView);
        carName = itemView.findViewById(R.id.textView2);
        carCompany = itemView.findViewById(R.id.textView3);
        carSeats = itemView.findViewById(R.id.textView4);
        sellerContact = itemView.findViewById(R.id.textView5);
        carPrice = itemView.findViewById(R.id.textView6);
        imageView = itemView.findViewById(R.id.imageView);
        cardView = itemView.findViewById(R.id.card);

       carData = carDataList.get(position);
       sellerName.setText("Name: "+carData.getSellerName());
       carName.setText("Model: "+carData.getCarName());
       carCompany.setText("Comp: "+carData.getCarCompany());
       carSeats.setText("Seats: "+carData.getCarSeats());
       sellerContact.setText("Phone: "+carData.getSellerContact());
       carPrice.setText(carData.carPrice);
       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,carsDetails.class);
               intent.putExtra("pos",position+"");
               context.startActivity(intent);
           }
       });
       onUpadte.updated();
        if (carData.getCarPicUrl().toString().contains("Aigenfile"))
        {

            Log.e("url check ",carData.getCarPicUrl());

         //  imageView.setImageDrawable(Drawable.createFromPath(carData.getCarPicUrl()));
            File file = new File(carData.getCarPicUrl());

            Picasso.with(context).load(file).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
        else
        {

            Picasso.with(context).load(carData.getCarPicUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
        return itemView;
    }
    public interface onUpadte
    {
        public  void updated();

    }
}
