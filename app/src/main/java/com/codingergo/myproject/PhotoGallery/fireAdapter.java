package com.codingergo.myproject.PhotoGallery;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        vholder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(vholder.image.getContext());
                dialog.show();
                dialog.setContentView(R.layout.photoviewdialog);
                TextView Title = dialog.findViewById(R.id.fullimagetitle);
                TextView desc = dialog.findViewById(R.id.imageDesc);
                ImageView buttoncancle = dialog.findViewById(R.id.fullImageCancle);
                desc.setText(imageModel.getAbout());
                ImageView fullimage = dialog.findViewById(R.id.fullImage);
                Glide.with(fullimage).load(imageModel.getUrl()).into(fullimage);
                Title.setText(imageModel.getName());
                buttoncancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

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
