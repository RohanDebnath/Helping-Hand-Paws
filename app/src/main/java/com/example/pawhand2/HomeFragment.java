package com.example.pawhand2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawhand2.Models.PostModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
RecyclerView postRecyclerview;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
List<PostModel> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home,container,false);
       postRecyclerview = fragmentView.findViewById(R.id.postRV);
       postList = new ArrayList<>();
       postRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
       postRecyclerview.setAdapter(new PostAdapter(getActivity(), postList));
       postRecyclerview.setHasFixedSize(true);
       firebaseDatabase=FirebaseDatabase.getInstance();
       databaseReference=firebaseDatabase.getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                postList.clear();
                for (DataSnapshot postsnap : snapshot.getChildren()) {
                    PostModel post = postsnap.getValue(PostModel.class);
                    postList.add(post);
                }
                postRecyclerview.getAdapter().notifyDataSetChanged();

//                postAdapter = new PostAdapter(getActivity(), postList);
//                postRecyclerview.setAdapter(postAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return fragmentView;
    }

    @Override
    public void onStart() {
       super.onStart();


    }
}