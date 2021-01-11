package com.example.kepo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kepo.databinding.ActivityResultUserLayoutBinding;
import com.example.kepo.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private ArrayList<User> users;
    private Context context;


    public UserAdapter(Context context){
        this.users = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaters = LayoutInflater.from(parent.getContext());
        ActivityResultUserLayoutBinding itemLayoutBinding = ActivityResultUserLayoutBinding.inflate(layoutInflaters, parent, false);
        return new UserViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.itemLayoutBinding.setUser(user);

        holder.itemLayoutBinding.llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(v.getContext(), selectedUserActivity.class);
                getIntent.putExtra("OBJ_USER", user);
                v.getContext().startActivity(getIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateData(ArrayList<User> newUsers){
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }


    class UserViewHolder extends RecyclerView.ViewHolder{

        private ActivityResultUserLayoutBinding itemLayoutBinding;


        public UserViewHolder(@NonNull ActivityResultUserLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }
}
