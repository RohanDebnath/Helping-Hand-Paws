package com.example.pawhand2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pawhand2.Models.PostModel;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

     Context mcontext;
    List<PostModel> mData;

    public PostAdapter(Context mcontext, List<PostModel> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mcontext).inflate(R.layout.row_post_item,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());
        Glide.with(mcontext).load(mData.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mcontext).load(mData.get(position).getUserpicture()).into(holder.imgPostProfile);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imgPost,imgPostProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPostProfile=itemView.findViewById(R.id.row_postProfile);
            tvTitle=itemView.findViewById(R.id.row_postName);
            imgPost=itemView.findViewById(R.id.row_postImage);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent postDetailsActivity = new Intent(mcontext,PostDetailsActivity.class);

                   int position = getAdapterPosition();
                   postDetailsActivity.putExtra("title",mData.get(position).getTitle());
                   postDetailsActivity.putExtra("postImage",mData.get(position).getPicture());
                   postDetailsActivity.putExtra("description",mData.get(position).getDescription());
                   postDetailsActivity.putExtra("postKey",mData.get(position).getPostkey());
                   postDetailsActivity.putExtra("userPhoto",mData.get(position).getUserpicture());
                   postDetailsActivity.putExtra("postDate",mData.get(position).getTimeStamp());
                   postDetailsActivity.putExtra("postContact",mData.get(position).getContact());
                   postDetailsActivity.putExtra("postAddress",mData.get(position).getAddress());

                    mcontext.startActivity(postDetailsActivity);

                }
            });



        }
    }
}
