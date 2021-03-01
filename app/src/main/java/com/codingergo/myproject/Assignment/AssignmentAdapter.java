package com.codingergo.myproject.Assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingergo.myproject.NotesManager.NotesDownloader;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.lang3.StringUtils;

public class AssignmentAdapter extends FirestoreRecyclerAdapter<AssignmentModel , AssignmentAdapter.Holder> {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
   public static String branch , date , title , subject , Fname, sem;
   String h1;

    public AssignmentAdapter(@NonNull FirestoreRecyclerOptions<AssignmentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull AssignmentModel model) {
        branch = holder.sharedPreferences.getString("branch" ,null);
        if (holder.sharedPreferences.getString("branch","null")!=null){

        }
        Log.d("TAG", "modelSem: "+model.getSem());
        try{
            if (branch.equals(model.getBranch()) && sem.equals(model.getSem())) {
                holder.tile.setText(StringUtils.capitalize(model.getTitle()));
                holder.subject.setText(StringUtils.capitalize(model.getSubject()));
                holder.Fname.setText(StringUtils.capitalize(model.getFname()));
                holder.date.setText(model.getDate());
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.tile.getContext(), NotesDownloader.class);
                        intent.putExtra("url" ,model.getUrl());
                        intent.putExtra("name",model.getTitle());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        holder.linearLayout.getContext().startActivity(intent);
                        Toast.makeText(holder.itemView.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                    }
                });
            }
           else {
                holder.cardView.getLayoutParams().width =(0);
                holder.cardView.setVisibility(View.GONE);
                holder.linearLayout.setVisibility(View.GONE);
                Log.d("TAG", "secondCOde");
            }
        }catch (Exception e){
            DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    holder.editor.putString("branch" , documentSnapshot.getString("branch"));
                    holder.editor.putString("sem", documentSnapshot.getString("sem"));
                    holder.editor.apply();
                    holder.tile.setText(StringUtils.capitalize(model.getTitle()));
                    holder.subject.setText(StringUtils.capitalize(model.getSubject()));
                    holder.Fname.setText(StringUtils.capitalize(model.getFname()));
                    holder.date.setText(model.getDate());

                    if (documentSnapshot.getString("branch").equals(model.getBranch()) && documentSnapshot.getString("sem").equals(model.getSem()) ) {
                        holder.tile.setText(StringUtils.capitalize(model.getTitle()));
                        holder.subject.setText(StringUtils.capitalize(model.getSubject()));
                        holder.Fname.setText(StringUtils.capitalize(model.getFname()));
                        holder.date.setText(model.getDate());
                        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.tile.getContext(), NotesDownloader.class);
                                intent.putExtra("url" ,model.getUrl());
                                intent.putExtra("name",model.getTitle());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                holder.linearLayout.getContext().startActivity(intent);
                                Toast.makeText(holder.itemView.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                   else {
                        holder.cardView.getLayoutParams().width =(0);
                        holder.cardView.setVisibility(View.GONE);
                        holder.linearLayout.setVisibility(View.GONE);
                    }

                }
            });

        }

        holder.editor.putString("Branch",model.getBranch());
        holder.editor.putString("Date",model.getDate());
        holder.editor.putString("Subject", model.getSubject());
        holder.editor.putString("Title",model.getTitle());
        holder.editor.putString("Fname" ,model.getFname());
        holder.editor.commit();
        date = holder.sharedPreferences.getString("Date" ,null);
        title = holder.sharedPreferences.getString("Title" ,null);
        subject = holder.sharedPreferences.getString("Subject",null);
        Fname = holder.sharedPreferences.getString("Fname","null");
        branch = holder.sharedPreferences.getString("branch" ,"null");
        sem = holder.sharedPreferences.getString("sem" , "isnull");


    }

    private void BranchLoader(String b) {
       h1 =b;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_row_line,parent,false);
        return new Holder(view);
    }

    class  Holder extends RecyclerView.ViewHolder{
        TextView tile , date , subject , Fname;
        LinearLayout linearLayout;
        CardView cardView ;
        SharedPreferences sharedPreferences ;
        SharedPreferences.Editor editor;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tile = itemView.findViewById(R.id.assignmentTitle);
            date = itemView.findViewById(R.id.assignDate);
            subject = itemView.findViewById(R.id.assignSub);
            Fname = itemView.findViewById(R.id.assignName);
            linearLayout = itemView.findViewById(R.id.rec_line);
            cardView = itemView.findViewById(R.id.rec_line2);
            sharedPreferences = itemView.getContext().getSharedPreferences("AssignMentShared" , Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

        }
    }
}
