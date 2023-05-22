package com.example.pawhand2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostDetailsActivity extends AppCompatActivity {

    ImageView imgPost, imgUserPost, imgCurrentUser;
    TextView textPostDesc, textPostDateName, txtPostTitle,txtContactno,txtPostAddress;
    EditText editTextComment;
    Button btnaddcomment;
    String Postkey;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;
    CommentAdapter commentAdapter;
    List<CommentModel> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        imgPost=findViewById(R.id.post_detail_img);
        imgUserPost=findViewById(R.id.post_detail_user_img);
        imgCurrentUser= findViewById(R.id.post_detail_currentuser_img);
        txtPostTitle=findViewById(R.id.post_detail_title);
        textPostDesc=findViewById(R.id.post_detail_desc);
        textPostDateName=findViewById(R.id.post_detail_date_name);
        txtContactno=findViewById(R.id.post_contact);
        txtPostAddress=findViewById(R.id.post_address);
        editTextComment=findViewById(R.id.post_detail_comment);
        btnaddcomment=findViewById(R.id.post_detail_add_comment_btn);
        RvComment=findViewById(R.id.rv_comment);

        
        String postImage= getIntent().getExtras().getString("postImage");
        Glide.with(this).load(postImage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        txtPostTitle.setText("Title-"+postTitle);

        String userPostImage= getIntent().getExtras().getString("userPhoto");
        Glide.with(this).load(userPostImage).into(imgUserPost);

        String postDesciption = getIntent().getExtras().getString("description");
        textPostDesc.setText("Description-"+postDesciption);

        String contact=getIntent().getExtras().getString("postContact");
       txtContactno.setText("Contact-"+contact);

       String address = getIntent().getExtras().getString("postAddress");
       txtPostAddress.setText("Address-"+address);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        //set comment user Image;

        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imgCurrentUser);
        //getPostID
        Postkey= getIntent().getExtras().getString("postKey");

        btnaddcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference commentReference = firebaseDatabase.getReference("Comment").child(Postkey).push();
                String comment_content =editTextComment.getText().toString();
                String uid =firebaseUser.getUid();
                String username=firebaseUser.getDisplayName();
                String uimg= firebaseUser.getPhotoUrl().toString();

                CommentModel comment = new CommentModel(comment_content, uid,uimg,username);
                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PostDetailsActivity.this,"Comment Added",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostDetailsActivity.this,"Failed to add comment",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        textPostDateName.setText(new SimpleDateFormat("dd MM yyyy hh:mm aa", Locale.getDefault()).format(new Date(getIntent().getExtras().getLong("postDate"))));
        listComment=new ArrayList<>();
        RvComment.setLayoutManager(new LinearLayoutManager(this));
        RvComment.setHasFixedSize(true);
        RvComment.setAdapter(new CommentAdapter(this,listComment));
        iniRvComment();
    }

    private void iniRvComment() {

        DatabaseReference commRef= firebaseDatabase.getReference("Comment").child(Postkey);
        commRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                listComment.clear();
                for(DataSnapshot snap: snapshot.getChildren() )
                {
                     CommentModel comment= snap.getValue(CommentModel.class);
                     listComment.add(comment);
                     RvComment.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}