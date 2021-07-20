package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewEntry extends AppCompatActivity {
    private Button logout;
    private TextView username;

    private DatabaseReference rbRef;

    MainActivity usern = new MainActivity();
    String user = usern.username_;

    public static String  name, remark;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    ImageButton addEntry;
    Context context = this;

    public ArrayList<Entry> entryList = new ArrayList<>();

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

//        Toast.makeText(ViewEntry.this, entryList.size(), Toast.LENGTH_SHORT).show();

        dbRef = FirebaseDatabase.getInstance().getReference();
        getEntries();


        username = findViewById(R.id.profileUsername);
        logout = findViewById(R.id.logout);

        recyclerView = findViewById(R.id.recyclerView);
        addEntry = findViewById(R.id.btnAddEntry);

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAdd = new Intent(getApplicationContext(), AddEntry.class);
                startActivity(gotoAdd);

            }
        });


//        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClickListener(int position) {
//            }
//        });

        username.setText(user);
        //view profile change password and secret question
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoProfile = new Intent(getApplicationContext(), ChangePasswordOrSecretQ.class);
                gotoProfile.putExtra("Username", user);
                startActivity(gotoProfile);
            }
        });
        //logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewEntry.this);
                builder1.setTitle("Logout");
                builder1.setMessage("Are you sure you want to Log out?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent goBack = new Intent(context, MainActivity.class);
                                startActivity(goBack);
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
    }

    private void getEntries() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("UserEntries");
        dbRef.child("UserEntries").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot thisChild: task.getResult().getChildren()) {

                    String path = thisChild.toString();
                    int min = path.indexOf("=");
                    int max = path.indexOf(",");
                    String title = path.substring(min+2, max);
                    System.out.println("user is : "+user+" .");
                    if(title.contains(user)){
                       dbRef.child("UserEntries").child(title).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DataSnapshot> task) {
                               entryList.add(new Entry(task.getResult().child("name").getValue().toString(), task.getResult().child("remarks").getValue().toString()));


                               recyclerView.hasFixedSize();
                               layoutManager = new LinearLayoutManager(context);
                               recyclerView.setLayoutManager(layoutManager);

                               recyclerViewAdapter = new RecyclerViewAdapter(context,R.layout.entry_pattern, entryList);
                               recyclerView.setAdapter(recyclerViewAdapter);
                               }
//                           }
                       });
                    }
                }
            }
        });
    }
}