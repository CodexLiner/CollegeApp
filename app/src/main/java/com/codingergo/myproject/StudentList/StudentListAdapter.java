package com.codingergo.myproject.StudentList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;

public class StudentListAdapter extends FirestoreRecyclerAdapter<StudentListModel ,StudentListAdapter.Holder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    public StudentListAdapter(@NonNull FirestoreRecyclerOptions<StudentListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull StudentListModel model) {
        String [] year = {"null","First Year" , "Second Year" , "Final Year"};
        int i = Integer.parseInt(model.getSem());
        holder.name.setText(model.getFullname());
        Glide.with(holder.ProfileImg).load(model.getUrl()).into(holder.ProfileImg);
        holder.branch.setText(model.getBranch());
        if (year!=null){
            holder.year.setText(year[i]);
        }
        else {
            holder.year.setText(model.getSem());
        }
        getSnapshots().getSnapshot(position).getReference().getId();
        if ( getSnapshots().getSnapshot(position).getReference().getId().equals(auth.getCurrentUser().getUid())){
            holder.cardView.setVisibility(View.GONE);
            holder.cardView.getLayoutParams().width = 0;
            holder.cardView.getLayoutParams().height = 0;
        }
        holder.PromoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSnapshots().getSnapshot(position).getReference().update(Collections.emptyMap());
            }
        });
        holder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Delete")
                    .setMessage("Are You Sure Want To Delete "+model.getFullname())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // getSnapshots().getSnapshot(position).getReference().delete();
                            Toast.makeText(holder.itemView.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_row_line , parent , false);

        return new Holder(v);
    }
    public void Delete(int pos){
       // getSnapshots().getSnapshot(pos).getReference().
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name , branch , year ;
        ImageView EditBtn , PromoteBtn , DeleteBtn ,ProfileImg;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentAdapterNAme);
            cardView = itemView.findViewById(R.id.cardView);
            branch = itemView.findViewById(R.id.StudentBranchList);
            PromoteBtn = itemView.findViewById(R.id.StudentPromotList);
            DeleteBtn = itemView.findViewById(R.id.StudentDeleteList);
            year = itemView.findViewById(R.id.StudentYearList);
            EditBtn = itemView.findViewById(R.id.StudentEditList);
            cardView = itemView.findViewById(R.id.cardView);
            ProfileImg = itemView.findViewById(R.id.StudentProfileList);
        }
    }
}
