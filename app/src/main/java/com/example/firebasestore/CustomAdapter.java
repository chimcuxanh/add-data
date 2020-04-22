package com.example.firebasestore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
               public void onItemLongClick(View view, final int position) {
                   // this will be called when user long click
                   //create alertDialog
                   AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                   String[] option = {"Update","Delete"};
                   builder.setItems(option, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           if(which == 0)
                           {
                               String id = modelList.get(position).getId();
                               String title = modelList.get(position).getTitle();
                               String descrition = modelList.get(position).getDescription();
                               Intent intent = new Intent(listActivity,MainActivity.class);
                               intent.putExtra("pid",id);
                               intent.putExtra("ptitle",title);
                               intent.putExtra("pdescription",descrition);
                               listActivity.startActivity(intent);
                           }
                               if (which == 1)
                               {

                               }

                       }
                   }).create().show();

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
