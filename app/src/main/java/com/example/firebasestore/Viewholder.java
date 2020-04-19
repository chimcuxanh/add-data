package com.example.firebasestore;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholder extends RecyclerView.ViewHolder {
    TextView mTitleTv, mDescriptionTv;
    View mView;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
                return true;
            }
        });
        //initialize with model_layout.xml
        mTitleTv = itemView.findViewById(R.id.rTitleTv);
        mDescriptionTv = itemView.findViewById(R.id.rDescriptionTv);

    }
    private Viewholder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int position);
    }
    public void  setOnClickListener(Viewholder.ClickListener clickListener)
    {
        mClickListener = clickListener;
    }
}

