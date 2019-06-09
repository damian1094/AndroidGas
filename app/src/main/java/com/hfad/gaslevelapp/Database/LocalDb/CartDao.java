package com.hfad.gaslevelapp.Database.LocalDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartProducts products);

    @Query("DELETE FROM Cart")
    void deleteAllCart();

    @Query("SELECT * FROM Cart")
    LiveData<List<CartProducts>> getAllCart();

    @Query("SELECT EXISTS(SELECT 1 FROM Cart WHERE name =:id)")
    boolean getId(String id);

}
