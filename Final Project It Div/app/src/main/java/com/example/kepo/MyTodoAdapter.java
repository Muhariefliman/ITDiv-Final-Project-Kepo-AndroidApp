package com.example.kepo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kepo.databinding.ActivityMyTodoBinding;
import com.example.kepo.databinding.ActivityResultUserLayoutBinding;
import com.example.kepo.databinding.MyTodoItemLayoutBinding;
import com.example.kepo.databinding.TodoItemLayoutBinding;
import com.example.kepo.model.Todo;
import com.example.kepo.model.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyTodoAdapter extends RecyclerView.Adapter<MyTodoAdapter.UserViewHolder>{

    private ArrayList<Todo> todos;
    private Context context;
    private ArrayList<Todo> selectedTodo;



    public MyTodoAdapter(Context context){
        this.todos = new ArrayList<>();
        this.context = context;
        this.selectedTodo = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaters = LayoutInflater.from(parent.getContext());
        MyTodoItemLayoutBinding itemLayoutBinding = MyTodoItemLayoutBinding.inflate(layoutInflaters, parent, false);
        return new UserViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.itemLayoutBinding.setTodo(todo);

        holder.itemLayoutBinding.llTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(v.getContext(), DetailTodoActivity.class);
                getIntent.putExtra("OBJ_TODO", todo);
                v.getContext().startActivity(getIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void updateData(ArrayList<Todo> newTodo){
        todos.clear();
        todos.addAll(newTodo);
        notifyDataSetChanged();
    }


    class UserViewHolder extends RecyclerView.ViewHolder{

        private MyTodoItemLayoutBinding itemLayoutBinding;


        public UserViewHolder(@NonNull MyTodoItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }
}
