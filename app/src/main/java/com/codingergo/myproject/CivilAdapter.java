package com.codingergo.myproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CivilAdapter extends FirebaseRecyclerAdapter <CivilModel , CivilAdapter.CivilViewHolder>{


    public CivilAdapter(@NonNull FirebaseRecyclerOptions<CivilModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CivilViewHolder holder, int position, @NonNull CivilModel model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
//        holder.address.setText(model.getAddress());
//        holder.roll.setText(model.getRoll());
//        holder.mobile.setText(model.getMobile());
    }

    @NonNull
    @Override
    public CivilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timelinerow, parent , false);
        return  new CivilViewHolder(view);
    }

    class CivilViewHolder extends RecyclerView.ViewHolder{

        TextView name , email , address , mobile , roll;
        public CivilViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rec_name);
//            email =(TextView) itemView.findViewById(R.id.rec_mail);
//            address = (TextView) itemView.findViewById(R.id.addressrec);
//            mobile =(TextView) itemView.findViewById(R.id.recmob);
//            roll =(TextView) itemView.findViewById(R.id.recroll);
        }
    }
}
