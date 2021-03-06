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
 * Use the {@link FinalYear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalYear extends Fragment {

    RecyclerView studentList;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FinalYearAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FinalYear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalYear.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalYear newInstance(String param1, String param2) {
        FinalYear fragment = new FinalYear();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_year, container, false);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        studentList = view.findViewById(R.id.studentList);
        studentList.setLayoutManager(new LinearLayoutManager(getContext() ,LinearLayoutManager.VERTICAL , false));
        Query query = firestore.collection("Users").orderBy("roll", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<StudentListModel> options = new FirestoreRecyclerOptions.Builder<StudentListModel>()
                .setQuery(query , StudentListModel.class).build();
        adapter = new FinalYearAdapter(options);
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