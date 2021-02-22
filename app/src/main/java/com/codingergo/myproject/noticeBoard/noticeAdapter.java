package com.codingergo.myproject.noticeBoard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingergo.myproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class noticeAdapter extends  FirebaseRecyclerAdapter<noticeModel, noticeAdapter.viewHolder> {


    public noticeAdapter(@NonNull FirebaseRecyclerOptions<noticeModel> options) {
        super(options);
    }

    @NonNull
    @Override
    protected void onBindViewHolder(@NonNull final viewHolder holder, int position, @NonNull final noticeModel model) {
        holder.name.setText(model.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.name.getContext(), PDFViewer.class);
                intent.putExtra("Filename", model.getName());
                intent.putExtra("url", model.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.name.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_line, parent ,false);

        return new viewHolder(view);
    }

    class  viewHolder extends RecyclerView.ViewHolder {
        TextView name; TextView url;
        LinearLayout linearLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name =( TextView)itemView.findViewById(R.id.rec_name);
            linearLayout = itemView.findViewById(R.id.rec_line);
//            url =(TextView)itemView.findViewById(R.id.rec_mail);
}

    }

}