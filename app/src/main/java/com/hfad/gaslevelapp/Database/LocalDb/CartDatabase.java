package com.hfad.gaslevelapp.Database.LocalDb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = {CartProducts.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    private static CartDatabase instance;
    private static RoomDatabase.Callback roomDatabase = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public static synchronized CartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, "Cart")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabase)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CartDao favoriteDao();

}
