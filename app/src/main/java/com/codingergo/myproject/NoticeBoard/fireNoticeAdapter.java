package com.codingergo.myproject.NoticeBoard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingergo.myproject.NotesManager.NotesDownloader;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class fireNoticeAdapter extends FirestoreRecyclerAdapter<noticeModel , fireNoticeAdapter.Holder> {

    public fireNoticeAdapter(@NonNull FirestoreRecyclerOptions<noticeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull noticeModel model) {
        if (model.getDate()!=null){
//            if (model.getDate().equals("1")){
                holder.name.setText(model.getName());
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.name.getContext(), NotesDownloader.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("url", model.getUrl());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.name.getContext().startActivity(intent);
                        Toast.makeText(holder.itemView.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                    }
                });

//            }
//            else {
////                holder.linearLayout.setVisibility(View.INVISIBLE);
//            }
        }


    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_line,parent,false);
        return new Holder(v);
    }
    public void Delete(int pos){
        getSnapshots().getSnapshot(pos).getReference().delete();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout linearLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rec_name);
            linearLayout = itemView.findViewById(R.id.rec_line);

        }
    }

}
