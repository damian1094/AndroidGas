package com.hfad.gaslevelapp.Database.LocalDb;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    private CartDao cartDao;
    private LiveData<List<CartProducts>> cartList;

    public Repository(Context application){
        CartDatabase favoriteDatabase = CartDatabase.getInstance(application);
        cartDao = favoriteDatabase.favoriteDao();
        cartList = cartDao.getAllCart();
    }

    public void insert(CartProducts products){
        new Repository.InsertCartAsyncTask(cartDao).execute(products);
    }

    public void deleteAllCart(){
        new Repository.DeleteAllCartAsyncTask(cartDao).execute();
    }

    public boolean getId(int path){
        return cartDao.getId(path);
    }

    public LiveData<List<CartProducts>>getAllCarts(){
        return cartList;
    }

    private static class InsertCartAsyncTask extends AsyncTask<CartProducts, Void, Void> {
        private CartDao cartDao;

        public InsertCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(CartProducts... products) {
            cartDao.insert(products[0]);
            return null;
        }
    }

    private static class DeleteAllCartAsyncTask extends AsyncTask<Void, Void, Void>{
        private CartDao cartDao;

        public DeleteAllCartAsyncTask(CartDao cartDao) {
            this.cartDao = cartDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.deleteAllCart();
            return null;
        }
    }
}
