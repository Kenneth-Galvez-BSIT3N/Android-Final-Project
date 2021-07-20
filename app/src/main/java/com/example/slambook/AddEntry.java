package com.example.slambook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddEntry extends AppCompatActivity {

    ImageView img1;
    EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8,othergender;
    Button btn1, btn2, btnTakePicture;
    RadioButton rb1, rb2, rb3;
    RadioGroup rg1;
    String rbselected;
    Context c = this;
    DatePickerDialog datePickerDialog;

    private static final int REQ_CODE_WRITE_READ = 4;
    private static final int REQUEST_CODE_TAKE_PHOTO = 5;
    final int REQUEST_CODE_CAMERA = 2;
    final int REQUEST_CODE_PICK = 3;
    String mCurrentPhotoPath;
    Uri mCurrentPhotoUri;
    FirebaseAuth auth;
    int UserCount = 1;
    int day, month, year;
    public static UserEntries user = new UserEntries();
    DatabaseReference reff;
    HashMap<String, Object> map = new HashMap<>();
    long maxid=0;


    String username = MainActivity.username_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        auth  = FirebaseAuth.getInstance();
        init();

        add();
        reff = FirebaseDatabase.getInstance().getReference().child("UserEntries");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UserEntries UserEntries = new UserEntries();

        Toast.makeText(c, "Connected", Toast.LENGTH_LONG).show();


    }

    private void init() {

        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);
        edt6 = (EditText) findViewById(R.id.edt6);
        edt7 = (EditText) findViewById(R.id.edt7);
        edt8 = (EditText) findViewById(R.id.edt8);
        othergender = (EditText) findViewById(R.id.othergender);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb3 = (RadioButton) findViewById(R.id.rb3);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);

        datePickerDialog = new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                edt3.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, year, month, day);

        edt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode== RESULT_OK){
            img1.setImageURI(mCurrentPhotoUri);
            mCurrentPhotoPath = mCurrentPhotoUri.toString();


        }else if (requestCode == REQUEST_CODE_PICK && resultCode== RESULT_OK){
            Uri uri = data.getData();
            c.getContentResolver().takePersistableUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            img1.setImageURI(uri);
            mCurrentPhotoPath = uri.toString();

        }
    }

    private void add() {


        View.OnClickListener GenderPindot = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;

                if (rb.getId() == R.id.rb1) {
                    rbselected = rb1.getText().toString();
                    othergender.setVisibility(View.GONE);
                } else if (rb.getId() == R.id.rb2) {
                    rbselected = rb2.getText().toString();
                    othergender.setVisibility(View.GONE);

                } else if (rb.getId() == R.id.rb3) {
                    othergender.setText("");
                    othergender.setVisibility(View.VISIBLE);



                }
            }
        };
        rb1.setOnClickListener(GenderPindot);
        rb2.setOnClickListener(GenderPindot);
        rb3.setOnClickListener(GenderPindot);


        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File tempImage = null;

                try {
                    tempImage = createImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(tempImage != null){

                    Uri uriImage = FileProvider.getUriForFile(c,
                            "com.example.slambook.fileprovider",
                            tempImage);
                    mCurrentPhotoUri = uriImage;
                    takePhoto.putExtra(MediaStore.EXTRA_OUTPUT,uriImage);

                    if(ContextCompat.checkSelfPermission(c, Manifest.permission.CAMERA)!=
                            PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(AddEntry.this,
                                new String[]{Manifest.permission.CAMERA},
                                REQUEST_CODE_CAMERA);
                    }else{
                        startActivityForResult(takePhoto,REQUEST_CODE_TAKE_PHOTO);
                    }
                }
            }


        });





        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!othergender.getText().toString().isEmpty()) {
                    rbselected = othergender.getText().toString();
                }
                if (rbselected == null || edt1.getText().toString().isEmpty() || edt2.getText().toString().isEmpty() || edt3.getText().toString().isEmpty() || edt6.getText().toString().isEmpty()
                ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Info!");
                    builder.setMessage("Please fill in all required fields: ");
                    builder.setCancelable(true);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {

                    String name = edt1.getText().toString();
                    String remarks = edt2.getText().toString();
                    String birthday = edt3.getText().toString();
                    String gender = rbselected;
                    String address = edt4.getText().toString();
                    String contactnum = edt5.getText().toString();
                    String hobbies = edt6.getText().toString();
                    String quote = edt7.getText().toString();
                    String crush = edt8.getText().toString();

                    if (mCurrentPhotoPath == null) {


                        map.put("name",name);
                        map.put("remarks",remarks);
                        map.put("birthday",birthday);
                        map.put("gender",gender);
                        map.put("address",address);
                        map.put("contactnum",contactnum);
                        map.put("hobbies",hobbies);
                        map.put("quote",quote);
                        map.put("crush",crush);
                        //mag map.put pa dito ng isa pa para sa foreign key kung sino yung user na nag add ng entry
                        //MAXid para to sa counter for unique ID nila 1 2 3 4 5 ganon auto increment
//                        reff.child(String.valueOf(maxid+1)).setValue(map);
                        reff.child(MainActivity.username_+"_"+name.replaceAll("\\s+","_")).setValue(map);





                        Toast.makeText(c, "Successful", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                         finish();
                        Intent goBack = new Intent(getApplicationContext(), ViewEntry.class);
                        startActivity(goBack);


                    } else {
                        map.put("name",name);
                        map.put("remarks",remarks);
                        map.put("birthday",birthday);
                        map.put("gender",gender);
                        map.put("address",address);
                        map.put("contactnum",contactnum);
                        map.put("hobbies",hobbies);
                        map.put("quote",quote);
                        map.put("crush",crush);
                        //mag map.put pa dito ng isa pa para sa foreign key kung sino yung user na nag add ng entry
                        reff.child(String.valueOf(maxid+1)).setValue(map);






                        Toast.makeText(c, "FAIL", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);

//                        user.setImage(mCurrentPhotoPath);
//                        user.setPersonName(name);
//                        user.setPersonRemarks(remarks);
//                        user.setPersonBirthday(birthday);
//                        user.setPersonGender(gender);
//                        user.setPersonAddress(address);
//                        user.setPersonContactNum(contactnum);
//                        user.setPersonHobbies(hobbies);
//                        user.setPersonQuote(quote);
//                        user.setPersonCrush(crush);
                        //UserEntries.setForeignKey(key);
                        //KAILANGAN PA NG FOREIGN KEY KUNG SINO YUNG NAKA LOGIN
                        Toast.makeText(c, "yawa", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        //finish();

                    }


                }
            }

        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddEntry.this, EditEntry.class);
                startActivity(i);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
    private File createImage () throws Exception {
        File tempImage = null;

        if(ContextCompat.checkSelfPermission(c,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(c,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddEntry.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQ_CODE_WRITE_READ);
        }else{
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "IMG_"+timeStamp+"_";

            File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            tempImage = File.createTempFile(fileName,".jpg",fileDir);
            mCurrentPhotoPath = tempImage.getAbsolutePath();
        }

        return tempImage;
    }
}