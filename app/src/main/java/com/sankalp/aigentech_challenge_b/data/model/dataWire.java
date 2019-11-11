package com.sankalp.aigentech_challenge_b.data.model;

import com.sankalp.aigentech_challenge_b.data.CarData;

import java.util.ArrayList;
import java.util.List;

public class dataWire {
    private static List<CarData> resultsDataWire =new ArrayList<>();

    public static List<CarData> getResultsDataWire() {
        return resultsDataWire;
    }

    public static void setResultsDataWire(List<CarData> resultsDataWire) {
        dataWire.resultsDataWire = resultsDataWire;
    }
    public static void addResultDataWire(CarData addCAR)
    {
        resultsDataWire.add(addCAR);

    }
}
