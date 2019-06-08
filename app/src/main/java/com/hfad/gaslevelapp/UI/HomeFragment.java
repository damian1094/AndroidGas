package com.hfad.gaslevelapp.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.hfad.gaslevelapp.Adapters.GasAdapter;
import com.hfad.gaslevelapp.Database.RemoteDb.ApiRetrofit;
import com.hfad.gaslevelapp.Database.RemoteDb.GasObject;
import com.hfad.gaslevelapp.Database.RemoteDb.RetrofitInterface;
import com.hfad.gaslevelapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<GasObject>gasObjectList;
    private GasAdapter adapter;
    private RetrofitInterface retrofitInterface;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayout;
    private RelativeLayout layout;
    private ActionBar toolbar;


    public HomeFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setToolBar();
        initialize(view);
        getData();
        reloadData();
        return view;
    }

    private void setToolBar() {
        if (getActivity() != null)
            toolbar = ((MainActivity)getActivity()).getSupportActionBar();
        if (toolbar != null){
            toolbar.setTitle(R.string.app_name);
            toolbar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void initialize(View view) {
        recyclerView = view.findViewById(R.id.display_gas_volume);
        retrofitInterface = ApiRetrofit.getApi().create(RetrofitInterface.class);
        progressBar = view.findViewById(R.id.loading_gas);
        refreshLayout = view.findViewById(R.id.refresh);
        linearLayout = new LinearLayoutManager(getContext());
        layout = view.findViewById(R.id.relative_layout);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_products){
            Navigation.findNavController(layout).navigate(R.id.action_homeFragment_to_productFragment);
            return true;
        }if (id == R.id.action_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?" +
                            "id=com.google.android.apps.plus");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share GMLS via"));
            return true;
        }
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
