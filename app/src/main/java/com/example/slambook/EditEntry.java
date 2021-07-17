package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EditEntry extends AppCompatActivity {

    ImageView img1;
    EditText edt1,edt2,edt3,edt4,edt5,edt6,edt7,edt8,edt9;
    Button btn1, btn2;
    RadioButton rb1, rb2, rb3;
    RadioGroup rg1;
    DatabaseReference reff,reff2;
    Context c = this;
    HashMap<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        init();
        edit();
        ArrayList<UserEntries> list = new ArrayList<>();
        //key ng want mo idisplay
        reff = FirebaseDatabase.getInstance().getReference().child("UserEntries");
//        reff = FirebaseDatabase.getInstance().getReference().child("UserEntries").child("4");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String name = snapshot.child("name").getValue().toString();
//                    String name = dataSnapshot.child("name").getValue().toString();
                    String remarks = snapshot.child("remarks").getValue().toString();
                    String birthday = snapshot.child("birthday").getValue().toString();
                    String gender = snapshot.child("gender").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String contactnum = snapshot.child("contactnum").getValue().toString();
                    String hobbies = snapshot.child("hobbies").getValue().toString();
                    String quote = snapshot.child("quote").getValue().toString();
                    String crush = snapshot.child("crush").getValue().toString();
                    edt1.setText(name);
                    edt2.setText(remarks);
                    edt3.setText(birthday);
                    edt4.setText(gender);
                    edt5.setText(address);
                    edt6.setText(contactnum);
                    edt7.setText(hobbies);
                    edt8.setText(quote);
                    edt9.setText(crush);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void edit() {





    }

    private void init() {
        img1 = (ImageView) findViewById(R.id.img1);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);
        edt6 = (EditText) findViewById(R.id.edt6);
        edt7 = (EditText) findViewById(R.id.edt7);
        edt8 = (EditText) findViewById(R.id.edt8);
        edt9 = (EditText) findViewById(R.id.edt9);

        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);



//            edt1.setText(result.getString(2));
//            edt2.setText(result.getString(3));
//            edt3.setText(result.getString(4));
//            edt4.setText(result.getString(5));
//            edt5.setText(result.getString(6));
//            edt6.setText(result.getString(7));
//            edt7.setText(result.getString(8));
//            edt8.setText(result.getString(9));
//            edt9.setText(result.getString(10));









        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt1.getText().toString().isEmpty() || edt2.getText().toString().isEmpty() || edt3.getText().toString().isEmpty() ||
                        edt4.getText().toString().isEmpty()||    edt7.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Info!");
                    builder.setMessage("Please fill in all required fields: ");
                    builder.setCancelable(true);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else {
                    String name = edt1.getText().toString();
                    String remarks = edt2.getText().toString();
                    String birthday = edt3.getText().toString();
                    String gender = edt4.getText().toString();
                    String address = edt5.getText().toString();
                    String contactnum = edt6.getText().toString();
                    String hobbies = edt7.getText().toString();
                    String quote = edt8.getText().toString();
                    String crush = edt9.getText().toString();
                    map.put("name",name);
                    map.put("remarks",remarks);
                    map.put("birthday",birthday);
                    map.put("gender",gender);
                    map.put("address",address);
                    map.put("contactnum",contactnum);
                    map.put("hobbies",hobbies);
                    map.put("quote",quote);
                    map.put("crush",crush);

                    //need to update this, get the fullname from the viewEntry activity and replace yung name sa next line
                    reff = FirebaseDatabase.getInstance().getReference().child("UserEntries").child(MainActivity.username_+"_"+name.replaceAll("\\s+","_"));
                    //.child("2" yan yung value ng key id na ieedit

//                    Intent data = new Intent();
//
//                    data.putExtra("name", name);
//                    data.putExtra("remarks", remarks);
//                    data.putExtra("birthday", birthday);
//                    data.putExtra("gender", gender);
//                    data.putExtra("address", address);
//                    data.putExtra("contactnum", contactnum);
//                    data.putExtra("hobbies", hobbies);
//                    data.putExtra("quote", quote);
//                    data.putExtra("crush", crush);

                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Info!")
                            .setMessage("Are you sure you want to edit this?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    data.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    setResult(RESULT_OK, data);
                                    reff.updateChildren(map);

                                    finish();


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            });
                    AlertDialog warning = builder.create();
                    warning.show();

                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setResult(RESULT_OK);

                finish();
            }
        });



    }
}