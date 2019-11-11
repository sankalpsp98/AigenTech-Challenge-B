package com.sankalp.aigentech_challenge_b.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CarData.class},version = 1)
public abstract class carsDataHouse extends RoomDatabase {
    public abstract carDAO carDAO();
}
