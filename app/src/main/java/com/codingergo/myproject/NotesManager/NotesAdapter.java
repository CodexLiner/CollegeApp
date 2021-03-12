package com.codingergo.myproject.NotesManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotesAdapter extends FirestoreRecyclerAdapter<NotesModel ,NotesAdapter.Holder> {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String branch , sem;

    public NotesAdapter(@NonNull FirestoreRecyclerOptions<NotesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull NotesModel model) {
        branch = holder.sharedPreferences.getString("branch",null);
        sem = holder.sharedPreferences.getString("sem", null);
        try{
            if (branch.equals(model.getBranch()) && sem.equals(model.getSem())){
                holder.subject.setText(model.getSubject());
                holder.date.setText(model.getDate());
                holder.faculty.setText(model.getFaculty());
                holder.desc.setText(model.getDesc());
            }
            else {
                holder.NotesLiniar.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.GONE);
            }

        }catch (Exception e){
            DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    holder.editor.putString("branch",documentSnapshot.getString("branch"));
                    holder.editor.putString("sem", documentSnapshot.getString("sem"));
                    holder.editor.apply();
                    if (documentSnapshot.getString("branch").equals(model.getBranch())
                            && documentSnapshot.getString("sem").equals(model.getSem())){
                        holder.subject.setText(model.getSubject());
                        holder.date.setText(model.getDate());
                        holder.faculty.setText(model.getFaculty());
                        holder.desc.setText(model.getDesc());
                    }
                    else {
                        holder.cardView.setVisibility(View.GONE);
                        holder.NotesLiniar.setVisibility(View.GONE);
                    }

                }
            });
        }
        
            holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Downloading Started", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(holder.itemView.getContext(), NotesDownloader.class);
                intent.putExtra("url",model.getUrl());
                intent.putExtra("name",model.getSubject());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.btn.getContext().startActivity(intent);
                Toast.makeText(holder.itemView.getContext(), "Downloading...", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_line, parent ,false);
        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView subject , faculty , desc , date ;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor ;
        ImageView btn;
        LinearLayout NotesLiniar ;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            sharedPreferences = itemView.getContext().getSharedPreferences("NotesAdapterPrefs", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            subject = itemView.findViewById(R.id.notesTitle);
            faculty = itemView.findViewById(R.id.notesTeacher);
            desc = itemView.findViewById(R.id.notesDesc);
            date = itemView.findViewById(R.id.notesDate);
            btn = itemView.findViewById(R.id.notesDownload);
            cardView = itemView.findViewById(R.id.notesCard);
            NotesLiniar = itemView.findViewById(R.id.NotesLiniar);

        }
    }
}
