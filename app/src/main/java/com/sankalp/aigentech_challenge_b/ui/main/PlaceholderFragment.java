package com.sankalp.aigentech_challenge_b.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.sankalp.aigentech_challenge_b.HomeActivity;
import com.sankalp.aigentech_challenge_b.R;
import com.sankalp.aigentech_challenge_b.carsAdapter;
import com.sankalp.aigentech_challenge_b.data.CarData;
import com.sankalp.aigentech_challenge_b.data.carsDataHouse;
import com.sankalp.aigentech_challenge_b.data.model.PageViewModel;
import com.sankalp.aigentech_challenge_b.data.model.dataTOfragment;
import com.sankalp.aigentech_challenge_b.data.model.dataWire;
import com.sankalp.aigentech_challenge_b.genDataOnce;
import com.sankalp.aigentech_challenge_b.workManager;
import com.sankalp.aigentech_challenge_b.workManagerInsert;

import java.io.File;
import java.net.URI;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment  {






    private static final String ARG_SECTION_NUMBER = "section_number";
    View root;
    private PageViewModel pageViewModel;
    private OneTimeWorkRequest oneTimeWorkRequest2;
   public EditText sellerName;
   public EditText carName;
    EditText carPrice;
    EditText carSeats;
    EditText carCompany;
    EditText sellerContact;
    Context context;
    Button button;
    ImageButton imageButton;

    EditText urlX;
    File fileTemp=null;
    File fileFinal=null;

    List<CarData> carDataList = new ArrayList<>();
    List<CarData> cars = new ArrayList<>();
    carsAdapter carsAdapter = null;
    private OneTimeWorkRequest oneTimeWorkRequest1;
    private int CODE =1;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index=dataWire.getResultsDataWire().size();
        }
        pageViewModel.setIndex(index);
        oneTimeWorkRequest1 = new OneTimeWorkRequest.Builder(workManager.class).setInitialDelay(1, TimeUnit.SECONDS).addTag("newsWorker").build();

        oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(workManagerInsert.class).setInitialDelay(1, TimeUnit.SECONDS).addTag("newWorker2").build();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final dataWire dataWire = new dataWire();

        if (getArguments().getInt(ARG_SECTION_NUMBER)==1) {

            carDataList = dataWire.getResultsDataWire();

            root = inflater.inflate(R.layout.fragment_home, container, false);

            final TextView textView = root.findViewById(R.id.section_label);

            pageViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    Log.e("cars in ",carDataList.size()+"");
                }
            });


            ListView listView = root.findViewById(R.id.list);
            carsAdapter=new carsAdapter(carDataList, getContext(), new carsAdapter.onUpadte() {
                @Override
                public void updated() {

                    textView.setText("Total Cars Available :-"+carDataList.size());
                }
            });




            listView.setAdapter(carsAdapter);



        }else if (getArguments().getInt(ARG_SECTION_NUMBER)==2) {


            root = inflater.inflate(R.layout.create_car_ads, container, false);

            sellerName =    root.findViewById(R.id.textView8);
            carName =       root.findViewById(R.id.textView9);
            carCompany =    root.findViewById(R.id.textView10);
            carSeats =      root.findViewById(R.id.textView11);
            sellerContact = root.findViewById(R.id.textView12);
            carPrice =      root.findViewById(R.id.textView13);
            urlX =          root.findViewById(R.id.textView14);
            button = root.findViewById(R.id.button2);
            imageButton =root.findViewById(R.id.imageButton2);


                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkPermission()) {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            fileTemp = getFilePath();
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileTemp));
                            startActivityForResult(intent, CODE);
                        }

                    }
                });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("file null check",fileFinal+"---"+fileTemp);

                    if(sellerName.getText().toString().equals(""))
                    {
                        sellerName.setError("please Enter Details");
                    }else if (carName.getText().toString().equals(""))
                    {
                        carName.setError("please Enter Details");
                    }else if(carCompany.getText().toString().equals(""))
                    {
                        carCompany.setError("please Enter Details");
                    }else if(carSeats.getText().toString().equals(""))
                    {
                        carSeats.setError("please Enter Details");
                    }else if(sellerContact.getText().toString().equals(""))
                    {
                        sellerContact.setError("please Enter Details");
                    }else if (urlX.getText().toString().equals(""))
                    {

                        urlX.setError("please Enter Details or Upload pic");
                    }
                    else {
                        sellerName.setError(null);
                        carName.setError(null);
                        carCompany.setError(null);
                        carSeats.setError(null);
                        carPrice.setError(null);
                        sellerContact.setError(null);
                        urlX.setError(null);

                        if (fileFinal==null) {
                            if (urlX.getText().toString().contains(":")) {
                                CarData carData = new CarData(sellerName.getText().toString(), carName.getText().toString(), carPrice.getText().toString(), carSeats.getText().toString(), carCompany.getText().toString(), sellerContact.getText().toString(), urlX.getText().toString());
                                cars.add(carData);

                                pageViewModel.setCarLiveData(cars);
                            }
                            else {

                                urlX.setError("please Enter Valid LINK");
                            }
                        }else {
                            CarData carData = new CarData(sellerName.getText().toString(), carName.getText().toString(), carPrice.getText().toString(), carSeats.getText().toString(), carCompany.getText().toString(), sellerContact.getText().toString(), fileFinal+"");
                            cars.add(carData);

                            pageViewModel.setCarLiveData(cars);
                        }



                        //Toast.makeText(getContext(), "Data Inserted" + sellerName.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });



            urlX.setText("https://imgd.aeplcdn.com/1056x594/cw/ec/28286/BMW-X7-Right-Front-Three-Quarter-164106.jpg?wm=0");

            pageViewModel.getCarLiveData().observe(this, new Observer<List<CarData>>() {
                @Override
                public void onChanged(List<CarData> carData) {
                    Toast.makeText(getContext(),"Data In",Toast.LENGTH_SHORT).show();
                    CarData carData1 = carData.get(0);
                    dataWire.addResultDataWire(carData1);
                    Log.e("data spotted ","ok ==="+carData.size()+ carDataList.size());
                   // WorkManager.getInstance().beginWith(oneTimeWorkRequest2).enqueue();
                    carsDataHouse db = Room.databaseBuilder(getContext(),carsDataHouse.class,"datahouse2").allowMainThreadQueries()
                            .build();
                    db.carDAO().insertAll(carData1);
                    cars.clear();
                    cars.clear();
                    sellerName.setText("");
                    carName.setText("");
                    carCompany.setText("");
                    carSeats.setText("");
                    sellerContact.setText("");
                    urlX.setText("");
                    carPrice.setText("");
                    fileFinal=null;
                    fileTemp=null;
                    imageButton.setEnabled(true);
                    urlX.setEnabled(true);


                }
            });

        }
        return root;
    }

    private File getFilePath() {
        File file1;


    File file = new File("sdcard/Aigenfile");
    if (!file.exists()) {
        file.mkdir();
        file1 = new File(file, "Aigen-" + UUID.randomUUID().toString().substring(0, 6) + ".png");

    } else {
        file1 = new File(file, "Aigen-" + UUID.randomUUID().toString().substring(0, 6) + ".png");

    }



        return file1;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1&&resultCode == RESULT_OK) {
            fileFinal =fileTemp;
            Log.e("file is ",fileFinal+"");

            imageButton.setEnabled(false);
            urlX.setText(fileFinal+"");
            urlX.setEnabled(false);
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//Can add more as per requirement


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    123);

        } else {
            return  true;
        }
        return false;
    }

}