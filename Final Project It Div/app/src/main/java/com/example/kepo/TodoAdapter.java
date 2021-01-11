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
import com.example.kepo.databinding.TodoItemLayoutBinding;
import com.example.kepo.model.Todo;
import com.example.kepo.model.User;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.UserViewHolder>{

    private ArrayList<Todo> todos;
    private Context context;


    public TodoAdapter(Context context){
        this.todos = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaters = LayoutInflater.from(parent.getContext());
       TodoItemLayoutBinding itemLayoutBinding = TodoItemLayoutBinding.inflate(layoutInflaters, parent, false);
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

        private TodoItemLayoutBinding itemLayoutBinding;


        public UserViewHolder(@NonNull TodoItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());
            this.itemLayoutBinding = itemLayoutBinding;
        }
    }
}
