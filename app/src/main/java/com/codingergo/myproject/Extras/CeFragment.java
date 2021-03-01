package com.codingergo.myproject.Extras;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingergo.myproject.R;
import com.codingergo.myproject.FacultyList.FacultyListAdapter;
import com.codingergo.myproject.FacultyList.FacultyModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    CivilAdapter civilAdapter;
    FacultyListAdapter facultyListAdapter;
    FirebaseFirestore firestore;
    ShimmerFrameLayout shimmerFrameLayout;

    public CeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CeFragment newInstance(String param1, String param2) {
        CeFragment fragment = new CeFragment();
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
        View view = inflater.inflate(R.layout.fragment_ce, container, false);
        shimmerFrameLayout = view.findViewById(R.id.shimmerfrag);
        shimmerFrameLayout.startShimmer();
         recyclerView = view.findViewById(R.id.civil_rec);
         firestore = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Query Tquery = firestore.collection("Cs_Teachers");
        FirestoreRecyclerOptions<FacultyModel> Toptiions = new FirestoreRecyclerOptions.Builder<FacultyModel>()
                .setQuery(Tquery,FacultyModel.class)
                .build();
        facultyListAdapter = new FacultyListAdapter(Toptiions);
        recyclerView.setAdapter(facultyListAdapter);






//        FirebaseRecyclerOptions<CivilModel> options =
//                new FirebaseRecyclerOptions.Builder<CivilModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), CivilModel.class)
//                        .build();
//        civilAdapter = new CivilAdapter(options);
//        recyclerView.setAdapter(civilAdapter);


        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        facultyListAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        facultyListAdapter.stopListening();
    }

}