package com.example.pagenationapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagenationapp.Model1.ImageModel;
import com.example.pagenationapp.R;
import com.example.pagenationapp.ZoomActivity;

import java.util.ArrayList;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ImageViewHolder> {

private Context context;
private ArrayList<ImageModel> list;

    public ImageViewAdapter(Context context, ArrayList<ImageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_card_layout,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getUrls().getRegular()).into(holder.imageview);
   holder.imageview.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent intent  = new Intent(context, ZoomActivity.class);
           intent.putExtra("image",list.get(position).getUrls().getRegular()) ;
context.startActivity(intent);
       }
   });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
ImageView imageview;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview=itemView.findViewById(R.id.imageview);
        }
    }
}
