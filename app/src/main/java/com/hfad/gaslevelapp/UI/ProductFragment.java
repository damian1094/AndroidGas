package com.hfad.gaslevelapp.UI;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hfad.gaslevelapp.Adapters.ProductAdapter;
import com.hfad.gaslevelapp.Database.LocalDb.CartProducts;
import com.hfad.gaslevelapp.Database.LocalDb.CartViewModel;
import com.hfad.gaslevelapp.Database.LocalDb.Repository;
import com.hfad.gaslevelapp.Database.RemoteDb.ApiRetrofit;
import com.hfad.gaslevelapp.Database.RemoteDb.Product;
import com.hfad.gaslevelapp.Database.RemoteDb.RetrofitInterface;
import com.hfad.gaslevelapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment implements ProductAdapter.CartListener {
    private ActionBar actionBar;
    private ProgressBar progressBar;
    private SwipeRefreshLayout layout;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product>productList;
    private RetrofitInterface apiRetrofit;
    private GridLayoutManager gridLayoutManager;
    private ImageView cart;
    private TextView cartCounter;
    private CartViewModel cartViewModel;
    private Repository repository;


    public ProductFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setToolBar();
        initialize(view);
        getData();
        refresh();
        goToCart();
        return view;
    }

    private void setToolBar() {
        if (getActivity() != null)
            actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if (getActivity() !=null)
       // ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(R.string.order_product);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home ){
            if (getActivity() != null)
                getActivity().onBackPressed();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }


    private void initialize(View view){
        progressBar = view.findViewById(R.id.progress_loading);
        layout = view.findViewById(R.id.refresh_product);
        recyclerView = view.findViewById(R.id.view_products);
        apiRetrofit = ApiRetrofit.getApi().create(RetrofitInterface.class);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        cart = view.findViewById(R.id.cart_store);
        //Toolbar toolbar = view.findViewById(R.id.toolbar_fragment);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        repository = new Repository(getContext());
        adapter = new ProductAdapter(getContext(), this);
        cartCounter = view.findViewById(R.id.cart_add);
    }

    private void refresh(){
        layout.setColorSchemeColors(getResources().getColor(R.color.red),getResources()
                        .getColor(R.color.green),getResources().getColor(R.color.orange));
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void getData(){
        Call<List<Product>> call = apiRetrofit.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull
                    Response<List<Product>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                layout.setRefreshing(false);
                productList = response.body();
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter.setProductList(productList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                layout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToCart(){
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void passDataToCart(String productName, String weight, String price) {
        CartProducts products = new CartProducts(productName, weight, price);
        if (!repository.getId(productName)) {
            cartViewModel.insert(products);
            Toast.makeText(getContext(), "Successful added", Toast.LENGTH_LONG).show();
            int counter = 0;
            updateCart(counter);
        }
        else {
            Toast.makeText(getContext(), "Already added", Toast.LENGTH_LONG).show();
            cartViewModel.deleteAll();
            cartCounter.setText(String.valueOf(0));
        }
    }

    private void updateCart(int counter) {
        int no = counter + 1;
        cartCounter.setText(String.valueOf(no));
    }
}
