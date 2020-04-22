package com.example.firebasestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText mEditTextTT ,mEditTextD;
    Button mButtonSave, mButtonShow;
    ProgressDialog pd;
    FirebaseFirestore db;
    String pId, pTitle,pDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        //actionbat and it's title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add Data");
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                if(bundle != null)
                {
                    String id = pId;
                    String title  = mEditTextTT.getText().toString().trim();
                    String Description = mEditTextD.getText().toString().trim();
                    updatedata(id,title,Description);

                }
                else
                    {
                        //input data
                        String title  = mEditTextTT.getText().toString().trim();
                        String Description = mEditTextD.getText().toString().trim();
                        //function call to upload data
                        uploaddata(title,Description);

                    }



            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            actionBar.setTitle("update");
            mButtonSave.setText("update");
            //getdata
            pId = bundle.getString("pid");
            pTitle = bundle.getString("ptitle");
            pDescription = bundle.getString("pdescription");
            // set data
            mEditTextTT.setText(pTitle);
            mEditTextD.setText(pDescription);


        }
        else
            {
                actionBar.setTitle("add data");
                mButtonSave.setText("Save");
            }

        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();
            }
        });
    }

    private void updatedata(String id, String title, String description) {
        pd.setTitle("Update data....");
        pd.show();
        db.collection("Document").document(id).
                update("title",title,"description",description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploaddata(String title, String description) {
        pd.setTitle("Adding data to Firebase");
        pd.show();
        //random id for each data
        String id  = UUID.randomUUID().toString();

        Map<String,Object> doc  = new HashMap<>();
        doc.put("id",id);
        doc.put("Titile",title);
        doc.put("Description",description);
        //add this data
        db.collection("Document").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data add successfully
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "dang upload", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //this will be called if there is any error while uploading
                pd.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void anhxa() {
        mEditTextTT = findViewById(R.id.EditTitle);
        mEditTextD = findViewById(R.id.EditDescription);
        mButtonSave = findViewById(R.id.ButtonSave);
        mButtonShow = findViewById(R.id.ButtonShow);
        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
    }
}
