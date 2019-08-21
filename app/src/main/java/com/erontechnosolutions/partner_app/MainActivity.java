package com.erontechnosolutions.partner_app;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView driverimage;
    ImageButton dr_btn;
    private static final int Gallery_slection_code = 100;
    private static final int Camera_slection_code = 200;
    ImageView imageView;
    private String cameraFilePath;
    LinearLayout basic, vehicle, rc, driver, dp;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    Button previous, next;

    AlertDialog alertDailog;
    boolean basciboolean = false;
    boolean vehicleboolean = false;
    boolean rcboolean = false;
    boolean driverboolean = false;
    boolean dpboolean = false;
    ImageButton rc_btn;
    LinearLayout rchorizontallayout;
    public String userChoosenTask;


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
        driverimage=findViewById(R.id.driverimage);
        dr_btn=findViewById(R.id.dr_btn);


        previous = findViewById(R.id.previous_common);
        next = findViewById(R.id.next_common);

        vehicle.setVisibility(View.GONE);
        rc.setVisibility(View.GONE);
        driver.setVisibility(View.GONE);
        dp.setVisibility(View.GONE);
        basciboolean = false;
        vehicleboolean = true;
        rcboolean = false;
        driverboolean = false;
        dpboolean = false;


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


                            selectImage();

                        }
                    });

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


                            selectImage();

                        }
                    });

                } else if (dpboolean) {
                    previous.setVisibility(View.VISIBLE);
                    next.setText("Submit");
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
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleboolean = true;

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

                    previous.setVisibility(View.VISIBLE);
                    next.setText("Next");
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
                    next.setText("Submit");
                    basic.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                    rc.setVisibility(View.GONE);
                    driver.setVisibility(View.VISIBLE);
                    dp.setVisibility(View.GONE);
                    basciboolean = false;
                    vehicleboolean = false;
                    rcboolean = false;
                    driverboolean = true;
                    dpboolean = false;

                }


            }


        });


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

   /* private void selectImage1() {
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
    }*/

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

   /* private void galleryIntent1() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
*/
    private void cameraIntent1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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

   /* private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimetypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, Gallery_slection_code);
    }*/

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


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }


    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, Camera_slection_code);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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


        imageView.setImageBitmap(thumbnail);
        driverimage.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(bm);
    }
}

