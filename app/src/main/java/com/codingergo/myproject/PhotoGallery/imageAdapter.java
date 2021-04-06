package com.codingergo.myproject.PhotoGallery;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class imageAdapter extends FirestoreRecyclerAdapter<imageModel , imageAdapter.Vholder> {

    public imageAdapter(@NonNull FirestoreRecyclerOptions<imageModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Vholder holder, int position, @NonNull imageModel model) {
        holder.name.setText(model.getName());
        Glide.with(holder.imageView.getContext()).load(model.getUrl()).into(holder.imageView);
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_2, parent,false);
        return new Vholder(view);
    }

    class Vholder extends RecyclerView.ViewHolder {
        TextView name, url;
        ImageView imageView;

        public Vholder(@NonNull View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.Galleryname);
            imageView = itemView.findViewById(R.id.galleryimage);

        }
    }
}
