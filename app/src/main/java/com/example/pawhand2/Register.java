package com.example.pawhand2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    ImageView ImgUserpic;
    Button regButton;
    EditText userName,userMail,userPassword,userConfirmPasswrd;
    FirebaseAuth mAuth;
    Uri uri;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImgUserpic=findViewById(R.id.regUserPic);
        regButton=findViewById(R.id.regBtn);
        userName=findViewById(R.id.regName);
        userMail=findViewById(R.id.regMail);
        userPassword=findViewById(R.id.regPassword);
        userConfirmPasswrd=findViewById(R.id.regConfirmPassword);

        mAuth=FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  name=userName.getText().toString();
                String  email=userMail.getText().toString();
                String  password=userPassword.getText().toString();
                String  confirmPassword=userConfirmPasswrd.getText().toString();
                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || !password.equals(confirmPassword))
                {
                    Toast.makeText(Register.this,"Please Varify all Fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CreateUserAccount(email,name,password);
                }


            }
        });





        ImgUserpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                takeImage.launch(intent);
            }
        });

    }

    private void CreateUserAccount(String email, String name, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this,"Account Created Please wait a while.. ",Toast.LENGTH_SHORT).show();
                    updateUserInfo(name,uri,mAuth.getCurrentUser());
                }
                else
                {
                    Toast.makeText(Register.this,"Account Creation Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUserInfo(String name, Uri uri, FirebaseUser currentUser) {


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        StorageReference imageFilepath= mStorage.child(uri.getLastPathSegment());
        imageFilepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageFilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(String.valueOf(name)).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Register.this,"Register Complete",Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(Register.this, Home.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                    }
                });
            }
        });

    }


    //Image picker
    ActivityResultLauncher<Intent> takeImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->
    {
        if(result.getResultCode()==RESULT_OK && result.getData() !=null)
        {
            uri = result.getData().getData();
            ImgUserpic.setImageURI(uri);
        }
    });

}