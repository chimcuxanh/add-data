package com.example.firebasestore;

import android.content.Context;
import android.icu.text.Normalizer2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<Viewholder>
{
    ListActivity listActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;
        ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent,false);
           Viewholder viewholder = new Viewholder(itemView);
           viewholder.setOnClickListener(new Viewholder.ClickListener() {
               @Override
               public void onItemClick(View view, int position) {
                   // this will be called when user click
                   //show data in toast
                   String title  = modelList.get(position).getTitle();
                   String Descriptions = modelList.get(position).getDescription();
                   Toast.makeText(listActivity, title+"\n"+ Descriptions, Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onItemLongClick(View view, int position) {
                   // this will be called when user long click

               }
           });

        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //bind views/ set data
        holder.mTitleTv.setText(modelList.get(position).getTitle());
        holder.mDescriptionTv.setText(modelList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
