package com.codingergo.myproject.photoGallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class fireAdapter extends FirestoreRecyclerAdapter<imageModel, fireAdapter.Vholder> {

    public fireAdapter(FirestoreRecyclerOptions<imageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(Vholder vholder, int i, imageModel imageModel) {
        Glide.with(vholder.image.getContext()).load(imageModel.getUrl()).into(vholder.image);
       // vholder.name.setText(imageModel.getName());

    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_row, parent,false);
        return new Vholder(view);
    }

    class  Vholder extends RecyclerView.ViewHolder {
        ImageView image;

        public Vholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.galleryimage);
        }
    }
}
