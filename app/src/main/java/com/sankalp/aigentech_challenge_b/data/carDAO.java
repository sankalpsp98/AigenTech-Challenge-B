package com.sankalp.aigentech_challenge_b.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface carDAO {

    @Query("SELECT *FROM CarData")

    List<CarData> getAllCars();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CarData...CarData);
}
