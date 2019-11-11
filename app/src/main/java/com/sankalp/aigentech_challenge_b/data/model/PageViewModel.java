package com.sankalp.aigentech_challenge_b.data.model;

import android.util.Log;
import android.widget.Toast;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.sankalp.aigentech_challenge_b.data.CarData;

import java.util.List;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Total Cars Available: " + input;
        }
    });

    public PageViewModel() {
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    private MutableLiveData<List<CarData>> carLiveDataM = new MutableLiveData<>();
    private LiveData<List<CarData>> carLiveData = Transformations.map(carLiveDataM, new Function<List<CarData>, List<CarData>>() {
        @Override
        public List<CarData> apply(List<CarData> input) {
            Log.e("data spotted  Live dtaa","ok ===");


            return input;
        }
    });

    public LiveData<List<CarData>> getCarLiveData() {
        return carLiveData;
    }

    public void setCarLiveData(List<CarData> carLiveDatax) {
        carLiveDataM.setValue(carLiveDatax) ;
    }



}