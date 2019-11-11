package com.sankalp.aigentech_challenge_b;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sankalp.aigentech_challenge_b.data.CarData;
import com.sankalp.aigentech_challenge_b.data.model.dataWire;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class carsDetails extends AppCompatActivity {
    List<CarData> dataList = new ArrayList<>();
    TextView sellerName;
    TextView carName;
    TextView carPrice;
    TextView carSeats;
    TextView carCompany;
    TextView sellerContact;
    ImageView imageView;
    CarData carData;
    Button button;
    static  int  pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_details);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .6));
        dataList = dataWire.getResultsDataWire();
        pos=Integer.parseInt(getIntent().getStringExtra("pos"));
        carData = dataList.get(pos);

        sellerName = findViewById(R.id.textView);
        carName = findViewById(R.id.textView2);
        carCompany = findViewById(R.id.textView3);
        carSeats = findViewById(R.id.textView4);
        sellerContact = findViewById(R.id.textView5);
        carPrice = findViewById(R.id.textView6);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button3);

        sellerName.setText("Name: "+carData.getSellerName());
        carName.setText("Model: "+carData.getCarName());
        carCompany.setText("Comp: "+carData.getCarCompany());
        carSeats.setText("Seats: "+carData.getCarSeats());
        sellerContact.setText("Phone: "+carData.sellerContact);
        sellerContact.setLinkTextColor(Color.CYAN);
        Linkify.addLinks(sellerContact, Linkify.PHONE_NUMBERS);
        carPrice.setText(carData.getCarPrice());
        if (carData.getCarPicUrl().toString().contains("Aigenfile"))
        {

            File file = new File(carData.getCarPicUrl());

            Picasso.with(getApplicationContext()).load(file).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
        else
        {

            Picasso.with(getApplicationContext()).load(carData.getCarPicUrl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent mIntent = new Intent(getApplicationContext(), emailSeller.class);
                View view = null;
                ActivityOptions activityOptions = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view=button;
                    activityOptions =ActivityOptions.makeSceneTransitionAnimation(carsDetails.this,
                            Pair.create(view,"b1"));
                }
                startActivity(mIntent,activityOptions.toBundle());

            }
        });

    }
}
