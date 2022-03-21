package com.example.pagenationapp;

import static android.view.View.GONE;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagenationapp.Adapter.ImageViewAdapter;
import com.example.pagenationapp.Model1.ImageModel;
import com.example.pagenationapp.Model1.SerachModel;
import com.example.pagenationapp.Network.ServiceGenerator;
import com.example.pagenationapp.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ImageModel> list;
    private ImageViewAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    int page = 1;
    private ProgressDialog progressDialog;
    private int pagesize = 30;
    private boolean isLoading;
    private boolean isLastPage;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();

        adapter = new ImageViewAdapter(this, list);
        gridLayoutManager = new GridLayoutManager(this, 3);
        binding.rec.setLayoutManager(gridLayoutManager);
        binding.rec.setHasFixedSize(true);
        binding.rec.setAdapter(adapter);
    /*    progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("I Love You");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        imageload();
        binding.rec.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visible = gridLayoutManager.getChildCount();
                int totalitem = gridLayoutManager.getChildCount();
                int firstvisibleitem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if ((visible + firstvisibleitem >= totalitem) && firstvisibleitem >= 0 && totalitem >= pagesize) {
                        page++;
                        imageload();
                    }
                }
            }
        });
    }

    private void imageload() {
        isLoading = true;
        Requestinterface api = ServiceGenerator.createService(Requestinterface.class);
        api.getimage(page, pagesize)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {

                        binding.mainshimmer.setVisibility(GONE);
                        binding.rec.setVisibility(View.VISIBLE);
                        if (response.isSuccessful()) {
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();

                        }
                        isLoading = false;
                        //  progressDialog.dismiss();
                        binding.mainshimmer.setVisibility(GONE);


                        if (list.size() > 0) {
                            isLastPage = list.size() < pagesize;

                        } else isLastPage = true;
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        //  progressDialog.dismiss();
                        binding.mainshimmer.setVisibility(GONE);

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  progressDialog.show();
                binding.mainshimmer.setVisibility(View.VISIBLE);
                searchdata(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void searchdata(String query) {
        Requestinterface api = ServiceGenerator.createService(Requestinterface.class);
        api.searchimage(query).enqueue(new Callback<SerachModel>() {
            @Override
            public void onResponse(Call<SerachModel> call, Response<SerachModel> response) {
                //   progressDialog.dismiss();
                binding.mainshimmer.setVisibility(GONE);
                list.clear();

                if (response.isSuccessful()) {
                    list.addAll(response.body().getResults());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<SerachModel> call, Throwable t) {
                binding.mainshimmer.setVisibility(GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}