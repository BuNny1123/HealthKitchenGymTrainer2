package com.bunny.healthkitchengymtrainer.DietActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.bunny.healthkitchengymtrainer.Model.Meal;
import com.bunny.healthkitchengymtrainer.R;
import com.bunny.healthkitchengymtrainer.Utils.RecyclerAdapterDietMeals;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FridayDietFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FridayDietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FridayDietFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Meal> meal;
    ArrayList<Meal> dietArrayList;


    private OnFragmentInteractionListener mListener;

    public FridayDietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FridayDietFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FridayDietFragment newInstance(String param1, String param2) {
        FridayDietFragment fragment = new FridayDietFragment();
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
            meal = getArguments().getParcelableArrayList("MealArrayList");
        }

        dietArrayList = new ArrayList<>();
        for(Meal temp: meal){

            if(temp.getDay().equals("Friday")){
                dietArrayList.add(temp);
            }

        }


        Toast.makeText(getActivity(), "" + dietArrayList.toString() , Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friday, container, false);
//        TextView asdFri = (TextView) view.findViewById(R.id.asdFri);
//        asdFri.setText(dietArrayList.toString());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFriDietMeal);
        RecyclerAdapterDietMeals recyclerAdapterDietMeals = new RecyclerAdapterDietMeals(dietArrayList , getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(recyclerAdapterDietMeals);
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
