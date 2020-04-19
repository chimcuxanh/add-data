package com.example.firebasestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    List<Model> modelList = new ArrayList<>();
    //layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    CustomAdapter adapter;
    ProgressDialog pd;
    FloatingActionButton maddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_list);
        maddBtn = findViewById(R.id.addBtn);
        pd = new ProgressDialog(this);
        mRecyclerview = findViewById(R.id.recycler_view);
        //set recyclerview view profile
        mRecyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        //show data in recyclerview
        showdata();
        maddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    private void showdata() {
        pd.setTitle("Loading data");
        pd.show();
        db.collection("Document")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // called when data is retrieved
                        pd.dismiss();
                        for (DocumentSnapshot doc:task.getResult())
                        {
                            Model model = new Model(doc.getString("id"),
                                    doc.getString("title"),
                                    doc.getString("Description"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(ListActivity.this ,modelList);
                        //set adapter to recyclerview
                        mRecyclerview.setAdapter(adapter);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // called when there is any error while loading data
                pd.dismiss();
                Toast.makeText(ListActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
