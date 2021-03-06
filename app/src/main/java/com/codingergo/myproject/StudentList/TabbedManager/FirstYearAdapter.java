package com.codingergo.myproject.StudentList.TabbedManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingergo.myproject.R;
import com.codingergo.myproject.StudentList.StudentListModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirstYearAdapter extends FirestoreRecyclerAdapter<StudentListModel , FirstYearAdapter.Holder> {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public FirstYearAdapter(@NonNull FirestoreRecyclerOptions<StudentListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull StudentListModel model) {
        {
            String [] year = {"null","First Year" , "Second Year" , "Final Year"};
            int i = Integer.parseInt(model.getSem());
            if (model.getSem().equals("1")){
                holder.name.setText(model.getFullname());
                Glide.with(holder.ProfileImg).load(model.getUrl()).into(holder.ProfileImg);
                holder.branch.setText(model.getBranch());
            }
            else {
                holder.cardView.setVisibility(View.GONE);
                holder.cardView.getLayoutParams().width = 0;
                holder.cardView.getLayoutParams().height = 0;
            }

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
                    String []  Branch ={ "null", "1" ,"2", "3" };
                    DocumentReference df = firestore.collection("Users").document(getSnapshots().getSnapshot(position).getReference().getId());
                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (!documentSnapshot.getString("sem").equals(Branch[0])){
                                if (documentSnapshot.getString("sem").equals(Branch[1])){
                                    AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                                            .setMessage("Click on yes to change year!")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Map<String ,Object> Map = new HashMap<>();
                                                    Map.put("sem", Branch[2]);
                                                    getSnapshots().getSnapshot(position).getReference().update(Map);
                                                    Toast.makeText(holder.itemView.getContext(), ""+documentSnapshot.getString("fullname"), Toast.LENGTH_SHORT).show();
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).show();

                                }
                                else if (documentSnapshot.getString("sem").equals(Branch[2])){
                                    AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                                            .setMessage("Click on yes to change year!")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Map<String ,Object> Map = new HashMap<>();
                                                    Map.put("sem", Branch[3]);
                                                    getSnapshots().getSnapshot(position).getReference().update(Map);
                                                    Toast.makeText(holder.itemView.getContext(), ""+documentSnapshot.getString("fullname"), Toast.LENGTH_SHORT).show();
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).show();
                                }
                                else if (documentSnapshot.getString("sem").equals(Branch[3])){
                                    AlertDialog alertDialog = new AlertDialog.Builder(holder.itemView.getContext())
                                            .setMessage("Click on yes to make student ex!")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    DocumentReference df = firestore.collection("ExUser").document();
                                                    Map<String ,Object> Map = new HashMap<>();
                                                    Map.put("roll", documentSnapshot.getString("roll"));
                                                    Map.put("email", documentSnapshot.getString("email"));
                                                    Map.put("branch", documentSnapshot.getString("branch"));
                                                    Map.put("fullname", documentSnapshot.getString("fullname"));
                                                    Map.put("url", documentSnapshot.getString("url"));
                                                    df.set(Map);
                                                    //Todo:Delete User After Ex.
                                                    getSnapshots().getSnapshot(position).getReference().update(Map);
                                                    Toast.makeText(holder.itemView.getContext(), ""+documentSnapshot.getString("Successfully Removed"), Toast.LENGTH_SHORT).show();
                                                }
                                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).show();


                                }

                            }


                        }
                    });
                }
            });
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
                            roll.setText(documentSnapshot.getString("roll"));
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
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


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
