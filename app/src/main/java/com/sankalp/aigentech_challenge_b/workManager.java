package com.sankalp.aigentech_challenge_b;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.sankalp.aigentech_challenge_b.data.CarData;
import com.sankalp.aigentech_challenge_b.data.carsDataHouse;
import com.sankalp.aigentech_challenge_b.data.model.dataWire;

import java.util.ArrayList;
import java.util.List;


public class workManager extends Worker {

    private static final String TAG = "MyPeriodicWork";
    List<CarData>  carDataX;
    dataWire dataWire = new dataWire();

    public workManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }



    @NonNull
    @Override
    public Result doWork() {

        carDataX = new ArrayList<>();

        Log.e(TAG, "doWork: Work is done.  "+carDataX.size()+" ");
        if (getData())
        {
            dataWire.setResultsDataWire(carDataX);
            return Result.success();

        }else
        {
            return Result.retry();
        }
    }


    private boolean getData() {


        carsDataHouse db = Room.databaseBuilder(getApplicationContext(),carsDataHouse.class,"datahouse2")
                .build();
        carDataX = db.carDAO().getAllCars();
        try {
            Thread.sleep(400);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (carDataX!=null)
            {

                Log.e("we got it","we got in workM");
                return true;
            }
        }
        return false;
    }
}
