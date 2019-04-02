package com.sofya.primenumbers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EndFragment.onEndFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EndFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private onEndFragmentInteractionListener mListener;
    TextView allResults, currentResult, bestResult;
    View rootView;
    Button btnPlayAgain, btnRules;
    ArrayList<Statistic> currListRes;

    public EndFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EndFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EndFragment newInstance(String param1, String param2) {
        EndFragment fragment = new EndFragment();
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
        rootView = inflater.inflate(R.layout.fragment_end, container, false);
        findViews();
        showResults();
        return rootView;
    }

    private void showResults() {
        currListRes = MainActivity.allResults;
        String allStringRes = "";
        currentResult.setText(currListRes.get(currListRes.size() - 1).toString());
        bestResult.setText(Collections.max(currListRes, Statistic.compareResult()).toString());
        for (Statistic currStat : currListRes) {
            allStringRes = allStringRes + currStat.toString() + "\n";
        }
        allResults.setText(allStringRes);
    }

    private void findViews() {
        btnPlayAgain = (Button) rootView.findViewById(R.id.btnPlayAgain);
        btnRules = (Button) rootView.findViewById(R.id.btnGoToRules);
        btnPlayAgain.setOnClickListener(this);
        btnRules.setOnClickListener(this);
        allResults = rootView.findViewById(R.id.allTextRusult);
        bestResult = rootView.findViewById(R.id.bestTextResult);
        currentResult = rootView.findViewById(R.id.currentTextResult);
//        textView.setText("Your result is " + GametFragment.result.toString());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEndFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onEndFragmentInteractionListener) {
            mListener = (onEndFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onEndFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        MainActivity main_act = (MainActivity) getActivity();
        if (v == btnPlayAgain)
            main_act.goToGame();
        else if (v == btnRules)
            main_act.goToRules();

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
    public interface onEndFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEndFragmentInteraction(Uri uri);
    }
}
