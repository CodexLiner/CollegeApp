package com.codingergo.myproject.FacultyList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.nsd.NsdManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;

public class FacultyListAdapterForHod extends FirestoreRecyclerAdapter<FacultyModel , FacultyListAdapterForHod.Holder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public FacultyListAdapterForHod(@NonNull FirestoreRecyclerOptions<FacultyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull FacultyModel model) {
        String [] year = {"null","First Year" , "Second Year" , "Final Year"};
          holder.cardView.setVisibility(View.GONE);
            DocumentReference df = firestore.collection("Users").document(auth.getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (model.getBranch().equals(documentSnapshot.getString("branch")) && model.getIsUser().equals("0")){
                        holder.name.setText(model.getFullname());
                        holder.branch.setText(model.getBranch());
                        holder.year.setText(model.getSem());
                        //Todo: need to chnage teacher sem to deegre
                        Log.d("TAG", "onSuccessTeachers: "+documentSnapshot.getString("branch"));
                        holder.PromoteBtn.setVisibility(View.INVISIBLE);
                        holder.cardView.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.cardView.setVisibility(View.GONE);
                        holder.cardView.getLayoutParams().width = 0;
                        holder.cardView.getLayoutParams().height = 0;

                    }

                }
            });

        if ( getSnapshots().getSnapshot(position).getReference().getId().equals(auth.getCurrentUser().getUid())){
            holder.cardView.setVisibility(View.GONE);
            holder.cardView.getLayoutParams().width = 0;
            holder.cardView.getLayoutParams().height = 0;
        }
        holder.EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.dialog_design_line);
                dialog.show();
                Button close = dialog.findViewById(R.id.diloagButton);
                ImageView profile = dialog.findViewById(R.id.DialogImage);
                TextView address , name , roll , email , mobile ;
                address = dialog.findViewById(R.id.DialogAddress);
                name= dialog.findViewById(R.id.DialogName);
                roll= dialog.findViewById(R.id.DialogRoll);
                email = dialog.findViewById(R.id.DialogEmail);
                mobile = dialog.findViewById(R.id.DialogMobile);
                DocumentReference df = firestore.collection("Users").document(getSnapshots().getSnapshot(position).getId());
                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        address.setText(documentSnapshot.getString("address"));
                        roll.setText(documentSnapshot.getString("deegre"));
                        email.setText(documentSnapshot.getString("email"));
                        mobile.setText(documentSnapshot.getString("mobile"));
                        name.setText(documentSnapshot.getString("fullname"));
                        Glide.with(profile).load(documentSnapshot.getString("url"))
                                .placeholder(R.drawable.men)
                                .into(profile);
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });

            }
        });
        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                        .setMessage("Click on Yes to delete "+model.getFullname())
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
