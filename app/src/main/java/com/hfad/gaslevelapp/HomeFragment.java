package com.hfad.gaslevelapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private static final String TAG = "TAG";

    private RecyclerView recyclerView;
    private List<GasObject>gasObjectList;
    private GasAdapter adapter;
    private RetrofitInterface retrofitInterface;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayout;


    public HomeFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialize(view);
        getData();
        reloadData();
        return view;
    }

    private void initialize(View view) {
        recyclerView = view.findViewById(R.id.display_gas_volume);
        retrofitInterface = ApiRetrofit.getApi().create(RetrofitInterface.class);
        progressBar = view.findViewById(R.id.loading_gas);
        refreshLayout = view.findViewById(R.id.refresh);
        linearLayout = new LinearLayoutManager(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void reloadData(){
       refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
               getResources().getColor(R.color.green),getResources().getColor(R.color.orange));
       refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               getData();
           }
       });
    }

    private void getData(){
        Call<List<GasObject>> call = retrofitInterface.getWeight();
        call.enqueue(new Callback<List<GasObject>>() {
            @Override
            public void onResponse(@NonNull Call<List<GasObject>> call,
                                   @NonNull Response<List<GasObject>> response) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                gasObjectList = response.body();
                adapter = new GasAdapter(getContext(), gasObjectList);
                recyclerView.setLayoutManager(linearLayout);
                recyclerView.setAdapter(adapter);


                Log.d(TAG, "onResponse: " + gasObjectList.size());
            }

            @Override
            public void onFailure(@NonNull Call<List<GasObject>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
