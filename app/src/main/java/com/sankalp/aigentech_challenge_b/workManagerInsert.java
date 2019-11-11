package com.sankalp.aigentech_challenge_b;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sankalp.aigentech_challenge_b.data.CarData;

import com.sankalp.aigentech_challenge_b.data.carsDataHouse;
import com.sankalp.aigentech_challenge_b.data.model.dataWire;

import java.util.ArrayList;
import java.util.List;

public class workManagerInsert extends Worker {

    private static final String TAG = "MyPeriodicWork 2";
    List<CarData> carDataX;
    dataWire dataWire =  new dataWire();

    public workManagerInsert(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        carDataX = new ArrayList<>();
        carDataX = dataWire.getResultsDataWire();

        Log.e(TAG, "doWork: Work  "+carDataX.size()+" ");
        if (setData())
        {

            Log.e(TAG, "doWork: Work is done. inserted  "+carDataX.size()+" ");
            return Result.success();

        }else
        {
            return Result.retry();
        }

    }

    private boolean setData() {
        carsDataHouse db = Room.databaseBuilder(getApplicationContext(),carsDataHouse.class,"datahouse2")
                .build();
        for (int i=0;i<carDataX.size();i++) {
            db.carDAO().insertAll(carDataX.get(i));
        }
        try {
            Thread.sleep(400);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (carDataX!=null)
            {

                Log.e("we got it","we got in workm insert");
                return true;
            }
        }


        return false;
    }
}
