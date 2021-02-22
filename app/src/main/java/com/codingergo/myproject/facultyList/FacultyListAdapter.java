package com.codingergo.myproject.facultyList;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.os.AsyncTask;
import android.util.Half;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.Cs_Dashboard.DashBoard;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.lang3.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

public class FacultyListAdapter extends FirestoreRecyclerAdapter<FacultyModel , FacultyListAdapter.vholder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String branch;
    String name;
    String photo;
    String Tbranch;
    String[] nameSplited;

    public FacultyListAdapter(@NonNull FirestoreRecyclerOptions<FacultyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull vholder holder, int position, @NonNull FacultyModel model) {
        holder.editor.putString("TeacherName",model.getName());
        holder.editor.putString("TeacherImage", model.getPhoto());
        holder.editor.putString("TeacherBranch",model.getBranch());
        holder.editor.commit();
        branch = holder.sharedPreferences.getString("UserBranch", null);
        name = holder.sharedPreferences.getString("TeacherName", null);
        photo = holder.sharedPreferences.getString("TeacherImage", "Null Branch");
        Tbranch = holder.sharedPreferences.getString("TeacherBranch", "Null Branch");
        nameSplited = name.split("\\s+");
        Log.d("TAG", "fbranch: Faculty "+branch);
        try{

            if (branch.equals(model.getBranch())){
                holder.name.setText(StringUtils.capitalize(nameSplited[0]));
                Glide.with(holder.img).load(model.getPhoto()).placeholder(R.drawable.men).into(holder.img);
            }
            else {
                holder.linearLayout.setVisibility(View.GONE);
                Log.d("TAG", "onBindViewHolder:Second Code "+model.getName() + model.getBranch());
            }

        }catch (Exception e){
            DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    holder.editor.putString("UserBranch",documentSnapshot.getString("branch"));
                    holder.editor.commit();
                    if (documentSnapshot.getString("branch").equals(model.getBranch())){
                        nameSplited = model.getName().split("\\s+");
                        holder.name.setText(StringUtils.capitalize(nameSplited[0]));
                        Glide.with(holder.img).load(model.getPhoto()).into(holder.img);
                    }
                    else  {
                        holder.linearLayout.setVisibility(View.GONE);
                        Log.d("TAG", "onBindViewHolder:Second Code ");
                    }

                }
            });

        }
            holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.dialog_design_line);
                dialog.setCancelable(false);
                dialog.show();
                ImageView profile = dialog.findViewById(R.id.DialogImage);
                TextView DialogName , DialogBranch;
                DialogName = dialog.findViewById(R.id.DialogName);
                DialogBranch = dialog .findViewById(R.id.temp);
                DialogBranch.setText(model.getBranch());
                DialogName.setText(StringUtils.capitalize(model.getName())) ;
                Glide.with(profile).load(model.photo).into(profile);
                Button done = dialog.findViewById(R.id.diloagButton);
                done.setOnClickListener(new View.OnClickListener() {
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
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_for_rec,parent,false);
        return new vholder(view);
    }

    class vholder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView img;
        LinearLayout linearLayout;
        SharedPreferences sharedPreferences ;
        SharedPreferences.Editor editor;

        public vholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Tnamerec);
            img = itemView.findViewById(R.id.Timgrec);
            linearLayout = itemView.findViewById(R.id.t_list_line);
            sharedPreferences = itemView.getContext().getSharedPreferences("FacultyShared",Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("Fname",branch);
            editor.commit();

        }
    }
}
