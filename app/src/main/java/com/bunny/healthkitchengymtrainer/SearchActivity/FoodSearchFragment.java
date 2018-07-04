package com.bunny.healthkitchengymtrainer.SearchActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.FoodActivity.FoodActivity;
import com.bunny.healthkitchengymtrainer.Model.Foods;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterFoodItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ArrayList<Foods> foodsArrayList = new ArrayList<>(), finalFoodsArrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef,getMyRef;
    RecyclerView recyclerView;
    RecyclerAdapterFoodItems recyclerAdapter;
    TextView searchText;
    ArrayList<String> qickPicks;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FoodSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodSearchFragment newInstance(String param1, String param2) {
        FoodSearchFragment fragment = new FoodSearchFragment();
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
            //Toast.makeText(getActivity(), "" + getArguments().getParcelableArrayList("FoodSearch").toString(), Toast.LENGTH_SHORT).show();
            foodsArrayList = getArguments().getParcelableArrayList("FoodSearch");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_food_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerMenuFoodSearch);
        qickPicks = new ArrayList<>();
        getMyRef = database.getReference("GymTrainer").child(mAuth.getUid()).child("Quick Picks");
        getMyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    qickPicks.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if(foodsArrayList != null) {
            //Toast.makeText(getActivity(), "" + foodsArrayList.toString(), Toast.LENGTH_SHORT).show();
            recyclerAdapter = new RecyclerAdapterFoodItems(foodsArrayList, getActivity(), null ,qickPicks);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.setAdapter(recyclerAdapter);
        }
//        Toast.makeText(getActivity(), "" + foodsArrayList.toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "" + getArguments().getParcelableArrayList("FoodSearch").toString(), Toast.LENGTH_SHORT).show();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
