package com.codingergo.myproject.AnnouncmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends FirestoreRecyclerAdapter<ModelClass , AdapterClass.Holder> {

        public AdapterClass(@NonNull FirestoreRecyclerOptions<ModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull ModelClass model) {
            holder.msg.setText(model.getMsg());
            List<String> friend = new ArrayList<>();
           
        Log.d("TAG", "onBindViewHolderFrind: "+friend);

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcment_row_line ,parent,false);
        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
            TextView msg;
        public Holder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.ChatMsg);
        }
    }
}
