package com.codingergo.myproject.StudentList.TabbedManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingergo.myproject.R;
import com.codingergo.myproject.StudentList.StudentListAdapter;
import com.codingergo.myproject.StudentList.StudentListModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstYear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstYear extends Fragment {
    RecyclerView studentList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirstYearAdapter adapter;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FirstYear() {
        // Required empty public constructor
    }

    public static FirstYear newInstance(String param1, String param2) {
        FirstYear fragment = new FirstYear();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_first_year, container, false);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        studentList = view.findViewById(R.id.studentList);
        studentList.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.VERTICAL , false));
        Query query = firestore.collection("Users").orderBy("roll", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<StudentListModel> options = new FirestoreRecyclerOptions.Builder<StudentListModel>()
                .setQuery(query , StudentListModel.class).build();
        adapter = new FirstYearAdapter(options);
        studentList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}