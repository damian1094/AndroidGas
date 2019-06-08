package com.hfad.gaslevelapp.Database.LocalDb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<CartProducts>> cartList;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        cartList = repository.getAllCarts();
    }
    public void insert(CartProducts products){
        repository.insert(products);
    }

    public void deleteAll(){
        repository.deleteAllCart();
    }

    public LiveData<List<CartProducts>>getAllCart(){
        return cartList;
    }
}
