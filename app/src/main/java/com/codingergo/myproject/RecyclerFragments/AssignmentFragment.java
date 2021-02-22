package com.codingergo.myproject.RecyclerFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingergo.myproject.Assignment.AssignmentAdapter;
import com.codingergo.myproject.Assignment.AssignmentModel;
import com.codingergo.myproject.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.ContentValues.TAG;


public class AssignmentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView AssignmentRecycler;
    AssignmentAdapter assignmentAdapter;
    FirebaseFirestore firestore;

    public AssignmentFragment() {
        // Required empty public constructor
    }

    public static AssignmentFragment newInstance(String param1, String param2) {
        AssignmentFragment fragment = new AssignmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_assignment, container, false);
       AssignmentRecycler = view.findViewById(R.id.AssignFragRec);
       firestore = FirebaseFirestore.getInstance();
       AssignmentRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        Query query = firestore.collection("Drills");
        FirestoreRecyclerOptions<AssignmentModel> options = new FirestoreRecyclerOptions.Builder<AssignmentModel>()
                .setQuery(query , AssignmentModel.class).build();
        assignmentAdapter = new AssignmentAdapter(options);
       AssignmentRecycler.setAdapter(assignmentAdapter);
        Log.d(TAG, "onCreateView: Listimng Adapeter" );

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        assignmentAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        assignmentAdapter.stopListening();
    }
}