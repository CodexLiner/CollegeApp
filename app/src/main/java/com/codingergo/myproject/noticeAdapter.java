package com.codingergo.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class noticeAdapter extends  FirebaseRecyclerAdapter<noticeModel , noticeAdapter.viewHolder> {


    public noticeAdapter(@NonNull FirebaseRecyclerOptions<noticeModel> options) {
        super(options);
    }

    @NonNull
    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull noticeModel model) {
        holder.name.setText(model.getName());

    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timelinerow , parent , false);

        return new viewHolder(view);
    }

    class  viewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name =( TextView)itemView.findViewById(R.id.rec_name);

        }

    }

}