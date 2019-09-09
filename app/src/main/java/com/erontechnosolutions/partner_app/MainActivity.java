package com.erontechnosolutions.partner_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import Model.Vehicle_User;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    ImageView driverimage;
    ImageButton dr_btn;
    private static final int Gallery_slection_code = 100;
    private static final int Camera_slection_code = 200;
    ImageView imageView, imageView2;
    private String cameraFilePath;
    LinearLayout basic, vehicle, rc, driver, dp;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    public static final String userid = "id";
    private FirebaseAuth firebaseAuth;
    double lat, lng;


    Button previous, next;
    Bitmap thumbnail;
    Button dpupload;

    String newuserid;

    AlertDialog alertDailog;
    boolean basciboolean = false;
    boolean vehicleboolean = false;
    boolean rcboolean = false;
    boolean driverboolean = false;
    boolean dpboolean = false;
    ImageButton rc_btn;
    LinearLayout rchorizontallayout;
    public String userChoosenTask;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private Uri camerafilePath;
    ByteArrayOutputStream bytes;
    byte[] data1;
    ImageView dps;
    EditText name, mobile_number, emailid, address, vehicle_no, vehicle_make, model, yor, pass;
    Button submit;
    String names, mobilenos, emailds, addresss, vnos, vms, vmos, yors, rcimagess, dlicesenes, dpimages, status, password;
    String userID;
    String admin;
    String message;
    String token;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    List<Vehicle_User> vehicle_users;
    int id;
    boolean rcfile, driverfile, dpfile;
    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    String location;
    String group;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        basic = findViewById(R.id.basic_layout);
        vehicle = findViewById(R.id.vehicle_layout);
        rc = findViewById(R.id.rc_layout);
        driver = findViewById(R.id.driver_layout);
        dp = findViewById(R.id.dp_layout);
        rc_btn = findViewById(R.id.rc_btn);
        rchorizontallayout = findViewById(R.id.rc);
        imageView = findViewById(R.id.rc_image);
        imageView2 = findViewById(R.id.rc_image2);
        driverimage = findViewById(R.id.driverimage);
        dr_btn = findViewById(R.id.dr_btn);
        dpupload = findViewById(R.id.dpupload);
        dps = findViewById(R.id.dp);
        name = findViewById(R.id.nameedt);
        mobile_number = findViewById(R.id.mobileedt);
        emailid = findViewById(R.id.emailedt);
        address = findViewById(R.id.addressedt);
        vehicle_no = findViewById(R.id.vehicle_no_edt);
        vehicle_make = findViewById(R.id.vehicle_make_edt);
        model = findViewById(R.id.vehicle_model_edt);
        yor = findViewById(R.id.vehicle_yor_edt);
        submit = findViewById(R.id.submit);
        pass = findViewById(R.id.returning_password);
        firebaseAuth = FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        previous = findViewById(R.id.previous_common);
        next = findViewById(R.id.next_common);
        lat = 0.0;
        lng = 0.0;

        vehicle.setVisibility(View.GONE);
        rc.setVisibility(View.GONE);
        driver.setVisibility(View.GONE);
        dp.setVisibility(View.GONE);
        basciboolean = false;
        vehicleboolean = true;
        rcboolean = false;
        driverboolean = false;
        dpboolean = false;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("vehicle_users");
        group="notifications";

      /*  if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SignInActivity.class));
        }
*/

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (basciboolean) {

                    // basic.setVisibility(View.VISIBLE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = true;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = false;

                } else if (vehicleboolean) {

                    previous.setVisibility(View.VISIBLE);
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.VISIBLE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = true;
                    driverboolean = false;
                    dpboolean = false;


                } else if (rcboolean) {

                    previous.setVisibility(View.VISIBLE);
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.VISIBLE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = false;
                    driverboolean = true;
                    dpboolean = false;

                    rc_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            rcfile = true;

                            selectImage();

                        }
                    });

                   /* imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // rcimagepic();

                            rcfile=true;

                            selectImage();
                        }
                    });

                    imageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // rcimagepic();

                            rcfile=true;

                            selectImage();
                        }
                    });
*/

                } else if (driverboolean) {
                    previous.setVisibility(View.VISIBLE);
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.VISIBLE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = true;
                    dr_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            driverfile = true;


                            selectImage();


                        }

                    });


                } else if (dpboolean) {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.VISIBLE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = false;


                    dpupload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dpfile = true;
                            selectImage();


                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            names = name.getText().toString();
                            mobilenos = mobile_number.getText().toString();
                            emailds = emailid.getText().toString();
                            addresss = address.getText().toString();
                            vnos = vehicle_no.getText().toString();
                            vms = vehicle_make.getText().toString();
                            vmos = model.getText().toString();
                            yors = yor.getText().toString();
                            password = pass.getText().toString();
                            status = "PENDING";
                            admin="admin";
                            message="MESSAGE";
                            token="Device_token";



                       /*     FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "onComplete: get Instance failed", task.getException());
                                        return;
                                    }

                                   token = task.getResult().getToken();

                                }
                            });*/
                                   if (!validateForm()) {
                                return;
                            }


                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Loading");
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(emailds, password)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                FirebaseUser user = task.getResult().getUser();


                                                userID = user.getUid();
                                                newuserid = userID;

                                                if(userID!=null){
                                                    saveData();
                                                }else{

                                                }
                                                Toast.makeText(MainActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();


                                            } else {
                                                Toast.makeText(MainActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                                                // updateUI(null);
                                            }
                                        }
                                    });
                            progressDialog.dismiss();




                        }


                    });


                }




            }

        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpboolean = true;

                if (basciboolean) {

                    previous.setVisibility(View.INVISIBLE);
                    next.setText("Next");
                    basic.setVisibility(View.VISIBLE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = true;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = false;

                } else if (vehicleboolean) {

                    previous.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    basic.setVisibility(View.VISIBLE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = true;
                    vehicleboolean = false;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = false;

                } else if (rcboolean) {

                    previous.setVisibility(View.VISIBLE);
                    next.setText("Next");
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.VISIBLE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = true;
                    rcboolean = false;
                    driverboolean = false;
                    dpboolean = false;

                } else if (driverboolean) {
                    previous.setVisibility(View.VISIBLE);
                    next.setText("Next");

                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.VISIBLE);
                    driver.setVisibility(View.GONE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = true;
                    driverboolean = false;
                    dpboolean = false;

                } else if (dpboolean) {
                    previous.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.VISIBLE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = true;
                    driverboolean = true;
                    dpboolean = false;

                }


            }


        });


    }

    private void saveData() {


        if (rcimagess.isEmpty() || dlicesenes.isEmpty() || dpimages.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Proof");
            alert.setMessage("Proof should not be empty");
            alert.setCancelable(true);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        } else {

            Vehicle_User vehicle_user=new Vehicle_User(userID,names,mobilenos,emailds,addresss,vnos,vms,vmos,yors,rcimagess,dlicesenes,dpimages,status,password,lat,lng,admin,message,token,group);

            databaseReference.child(userID).setValue(vehicle_user);


            name.setText("");
            mobile_number.setText("");
            emailid.setText("");
            address.setText("");
            vehicle_no.setText("");
            vehicle_make.setText("");
            model.setText("");
            yor.setText("");
            pass.setText("");


            imageView.setImageResource(R.drawable.placeholder);
            imageView2.setImageResource(R.drawable.placeholder);
            dps.setImageResource(R.drawable.placeholder);
            driverimage.setImageResource(R.drawable.placeholder);
            Toast.makeText(MainActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Successfully Registered!");
            builder.setMessage("You Registered Successfully,We contact you after Verification is done!!!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finishAffinity();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }




    private boolean validateForm() {

        boolean valid = true;

        String email = emailid.getText().toString();
        String pswd = pass.getText().toString();


        if (TextUtils.isEmpty(email)) {
            emailid.setError("Required!");
            valid = false;
        } else {

            emailid.setError(null);
        }

        if (TextUtils.isEmpty(pswd)) {
            pass.setError("Required!");
            valid = false;
        } else {
            pass.setError(null);
        }

        if (password.length() < 6) {
            pass.setError("Password should not less than 6");
            valid = false;
        } else {
            pass.setError(null);
        }

       /* if(null==imageView.getDrawable()){
            Toast.makeText(this, "rc image should not be  empty", Toast.LENGTH_SHORT).show();
            valid=false;
        }else {

        }
        if(null==driverimage.getDrawable()){
            Toast.makeText(this, "driver license image should not be empty", Toast.LENGTH_SHORT).show();
            valid=false;
        }else {

        }
        if(null==dps.getDrawable()){
            Toast.makeText(this, "profile  image should not be empty", Toast.LENGTH_SHORT).show();
            valid=false;
        }else {

        }*/
        return valid;


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    Bitmap userimage1=(Bitmap)data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userimage1 = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(userimage1);
                }
                break;
            case 2:
                if (resultCode==RESULT_OK){
                    Bitmap userimage2=(Bitmap)data.getExtras().get("data");
                    Bundle extras = data.getExtras();
                    userimage2 = (Bitmap) extras.get("data");
                    imageView2.setImageBitmap(userimage2);
                }

        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {

                if (rcfile) {

                    onSelectFromGalleryResult(data);
                    upLoadFileImage();
                    //  rcimagess=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                } else if (driverfile) {

                    onSelectFromGalleryResult1(data);
                    upLoadFileImage();

                } else if (dpfile) {
                    onSelectFromGalleryResult2(data);
                    upLoadFileImage();
                }
            } else if (requestCode == REQUEST_CAMERA) {

                if (rcfile) {

                    onCaptureImageResult(data);
                    upLoadImage();
                } else if (driverfile) {

                    onCaptureImageResult1(data);
                    upLoadImage();

                } else if (dpfile) {

                    onCaptureImageResult2(data);
                    upLoadImage();

                }
            }
        }
    }

    public void onCaptureImageResult(Intent data) {

        //filePath=data.getData();
        thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        data1 = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // upLoadFileImage();

        if(thumbnail!=null){
            imageView.setImageBitmap(thumbnail);
        }else{
            imageView.setImageResource(R.drawable.placeholder);
        }

      //  imageView.setImageBitmap(thumbnail);
       // Glide.with(MainActivity.this).load(thumbnail).placeholder(R.drawable.placeholder).into(imageView);


        //  rcfile=false;

    }

    public void onCaptureImageResult1(Intent data) {

        //filePath=data.getData();
        thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        data1 = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(thumbnail!=null){
            driverimage.setImageBitmap(thumbnail);
        }else{
            driverimage.setImageResource(R.drawable.placeholder);
        }

      //  driverimage.setImageBitmap(thumbnail);
      //  Glide.with(MainActivity.this).load(thumbnail).placeholder(R.drawable.placeholder).into(driverimage);


        //  driverfile=false;

    }

    public void onCaptureImageResult2(Intent data) {
        //filePath=data.getData();
        thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        data1 = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //upLoadFileImage();

        if(thumbnail!=null){
            dps.setImageBitmap(thumbnail);
        }else{
            dps.setImageResource(R.drawable.placeholder);
        }

      //  dps.setImageBitmap(thumbnail);
      //  Glide.with(MainActivity.this).load(thumbnail).placeholder(R.drawable.placeholder).into(dps);
        //   dpfile=false;

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            filePath = data.getData();

            //data1 = data.toByteArray();
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(bm!=null){
            imageView.setImageBitmap(bm);
        }else{
            imageView.setImageResource(R.drawable.placeholder);
        }

     //  Glide.with(MainActivity.this).load(bm).asBitmap().placeholder(R.drawable.placeholder).into(imageView);

       /* driverimage.setImageBitmap(bm);
        dps.setImageBitmap(bm);*/
    }

    private void onSelectFromGalleryResult1(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            filePath = data.getData();

            //data1 = data.toByteArray();
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(bm!=null){
            driverimage.setImageBitmap(bm);
        }else{
            driverimage.setImageResource(R.drawable.placeholder);
        }
        //    imageView.setImageBitmap(bm);
       // driverimage.setImageBitmap(bm);
       // Glide.with(MainActivity.this).load(bm).asBitmap().placeholder(R.drawable.placeholder).into(driverimage);
        // driverfile=false;

    }


    private void onSelectFromGalleryResult2(Intent data) {
        Bitmap bm = null;
        if (data != null) {

            filePath = data.getData();
            Log.d("dp", "onSelectFromGalleryResult2: " + filePath);


            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                Log.d("dp1", "onSelectFromGalleryResult2: " + bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(bm!=null){
            dps.setImageBitmap(bm);
        }else{
            dps.setImageResource(R.drawable.placeholder);
        }
       // dps.setImageBitmap(bm);
       // Glide.with(MainActivity.this).load(bm).asBitmap().placeholder(R.drawable.placeholder).into(dps);
        //   dpfile=false;
    }

    private void upLoadImage() {
        if (data1 != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference ref1 = storageReference.child("images/" + UUID.randomUUID().toString());
            ref1.putBytes(data1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String fileuri = uri.toString();
                            if (rcfile) {

                                //  Vehicle_User vehicle_user=new Vehicle_User(taskSnapshot.getDownloadUrl().toString());

                                if (fileuri != null) {
                                    rcimagess = fileuri;
                                } else {
                                    Toast.makeText(MainActivity.this, "Proof should not be empty                                                              ", Toast.LENGTH_SHORT).show();
                                }
                                rcfile = false;
                                progressDialog.dismiss();

                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            } else if (driverfile) {
                                //  dlicesenes = fileuri;

                                if (fileuri != null) {
                                    dlicesenes = fileuri;
                                } else {
                                    dlicesenes = "";
                                }
                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                driverfile = false;
                                progressDialog.dismiss();


                            } else if (dpfile) {
                                //dpimages = fileuri;

                                if (fileuri != null) {
                                    dpimages = fileuri;
                                } else {
                                    dpimages = "";
                                }
                                Log.d("MainActivity", "onSuccess: " + dpimages);
                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                dpfile = false;

                                progressDialog.dismiss();


                            }
                            //  progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();


                        }
                    });
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });

        }
    }

    private void upLoadFileImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            // StorageReference ref=storageReference.child("images/"+ UUID.randomUUID().toString());
            final StorageReference ref = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(filePath));
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("FilePath", "onSuccess: " + filePath);

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String fileuri = uri.toString();
                            if (rcfile) {

                                //  Vehicle_User vehicle_user=new Vehicle_User(taskSnapshot.getDownloadUrl().toString());

                                //   rcimagess = fileuri;
                                if (fileuri != null) {
                                    rcimagess = fileuri;
                                } else {
                                    rcimagess = "";
                                }
                                rcfile = false;
                                progressDialog.dismiss();

                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            } else if (driverfile) {
                                // dlicesenes = fileuri;

                                if (fileuri != null) {
                                    dlicesenes = fileuri;
                                } else {
                                    dlicesenes = "";
                                }
                                driverfile = false;
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();


                            } else if (dpfile) {
                                //  dpimages =fileuri;

                                if (fileuri != null) {
                                    dpimages = fileuri;
                                } else {
                                    dpimages = "";
                                }
                                Log.d("MainActivity", "onSuccess: " + dpimages);
                                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                dpfile = false;
                                progressDialog.dismiss();


                            }


                        }
                    });


                    //       Uri sampleUri=taskSnapshot.getDownloadUrl


                    //  Uri uri=taskSnapshot.getUploadSessionUri().t

                    //Uri sample= taskSnapshot.getDownloadUrl();


                    // Vehicle_User vehicle_user1=new Vehicle_User(rcimagess.trim(),taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    //  String uploadId = databaseReference.push().getKey();
                    // databaseReference.child(vehicle_user).setValue(vehicle_user1);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded!" + (int) progress + "%");
                }
            });
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //  updateUI(firebaseUser);
    }

    private void updateUI(FirebaseUser firebaseUser) {

        if (firebaseUser != null) {

        }
    }

    public void checkCurrentUser() {
        // [START check_current_user]



    }

    /*
                                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                                        private String role;

                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                                                Vehicle_User user = snapshot.getValue(Vehicle_User.class);

                                                                role = user.getId();
                                                                if(role!=null){
                                                                   userID=role;
                                                                }
                                                            }
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                                               });*/


}

