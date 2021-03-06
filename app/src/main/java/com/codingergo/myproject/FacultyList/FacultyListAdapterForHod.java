package com.codingergo.myproject.FacultyList;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.codingergo.myproject.StudentList.StudentListAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;

public class FacultyListAdapterForHod extends FirestoreRecyclerAdapter<FacultyModel , FacultyListAdapterForHod.Holder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public FacultyListAdapterForHod(@NonNull FirestoreRecyclerOptions<FacultyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull FacultyModel model) {
        String [] year = {"null","First Year" , "Second Year" , "Final Year"};

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
