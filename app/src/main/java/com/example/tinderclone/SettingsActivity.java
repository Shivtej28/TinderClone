package com.example.tinderclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    CircleImageView profImg;
    EditText nametext, phoneText;
    Button confirmBtn, backBtn;
    MaterialToolbar toolbar;

    private DatabaseReference mUserDb;

    private String userId, name, phone, profImgUrl, userSex;

    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        nametext = findViewById(R.id.name);
        phoneText = findViewById(R.id.phoneNo);
        confirmBtn = findViewById(R.id.confirm);
        profImg = findViewById(R.id.profileImage);
        backBtn = findViewById(R.id.back);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = mAuth.getCurrentUser().getUid();

        mUserDb = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(userId);

        getUserInfo();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        profImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

    }

    private void getUserInfo() {
        mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("Name")!=null){
                        name = map.get("Name").toString();
                        nametext.setText(name);
                    }
                    if(map.get("phone")!=null){
                        phone = map.get("phone").toString();
                        phoneText.setText(phone);
                    }
                    if(map.get("sex")!=null){
                        userSex = map.get("sex").toString();

                    }
                    if(map.get("profImageUrl")!=null){
                        profImgUrl = map.get("profImageUrl").toString();
                        Glide.with(getApplicationContext()).load(profImgUrl).into(profImg);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveUserInfo() {
        name = nametext.getText().toString();
        phone = phoneText.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("Name", name);
        userInfo.put("phone", phone);
        mUserDb.updateChildren(userInfo);
        if(resultUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference()
                    .child("ProfileImages")
                    .child(userId);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
            byte[] data = byteArrayOutputStream.toByteArray();
            UploadTask uploadTask = filePath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String downloadUrl = task.getResult().toString();
                            Map userInfo = new HashMap();
                            userInfo.put("profImageUrl", downloadUrl);
                            mUserDb.updateChildren(userInfo);

                        }
                    });
                }
            });
//            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                        }
//                    return filePath.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//                        Map userInfo = new HashMap();
//                        userInfo.put("profImageUrl", downloadUri);
//                        mCustomerDb.updateChildren(userInfo);
//
//
//                    }else {
//                        Log.i("Error", "Can't get Uri");
//                    }
//                }
//            });
//            urlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    String downloadUrl = uri.toString();
//                    Map userInfo = new HashMap();
//                    userInfo.put("profImageUrl", downloadUrl);
//                    mCustomerDb.updateChildren(userInfo);
//
//
//               }
//            });
            Toast.makeText(this, "Data Saved Suuccessfully", Toast.LENGTH_SHORT).show();
            finish();

        }else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            Log.i("URL", imageUri.toString());
            resultUri = imageUri;
            profImg.setImageURI(resultUri);


        }
    }
}