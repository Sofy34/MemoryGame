package com.sofya.primenumbers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GametFragment.onGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GametFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GametFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MediaPlayer mp = null;
    ArrayList<Integer> arr_numbers;
    ArrayList<NumberButtonClass> arr_object;
    Button plusBtn, multiplyBtn;

    boolean numClicked = false;
    int count = 0, z = 0, x, y = 0;
    int num1 = 0, num2 = 0, num3 = 0;
    Integer currentNum = 0;
    int clickerCounter = 0;
    public Integer result = 0;
    Integer state = 0;
    View rootView;
    int hideDelay = 3000;
    int resultDelay = 1000;
    TextView timerView;
    private onGameFragmentInteractionListener mListener;
    public int timeCounter = 60;
    CountDownTimer timer;
    Timer buttonTimer;
    private Integer steps = 0;

    @Override
    public void onStop() {
        super.onStop();
        buttonTimer.cancel();
        timer.cancel();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
        }
    }

    public GametFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GametFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GametFragment newInstance(String param1, String param2) {
        GametFragment fragment = new GametFragment();
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
        rootView = inflater.inflate(R.layout.fragment_game, container, false);
        findViews();
        prepareNumbers();
        startTimer();
        return rootView;
    }

    private void startTimer() {
        mp = MediaPlayer.create(getActivity(), R.raw.ticking);
        mp.setLooping(true);
        mp.start();
        timer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerView.setText(String.valueOf(timeCounter));
                timeCounter--;
            }

            public void onFinish() {
                timerView.setText("TIME IS OFF!!");
                if (mp != null && mp.isPlaying())
                    mp.stop();
                showResult();
            }
        }.start();
    }

    private void findViews() {
        ArrayList<View> allButtons;
        allButtons = ((LinearLayout) rootView.findViewById(R.id.bigLayout)).getTouchables();
        for (View currView : allButtons) {
            currView.setOnClickListener(this);
        }
        timerView = (TextView) rootView.findViewById(R.id.timerText);

    }

    @Override
    public void onClick(View v) {
        if (v == plusBtn | v == multiplyBtn)
            actionClicked(v);
        else
            number_clicked(v);
    }

    private void prepareNumbers() {

        arr_numbers = new ArrayList<Integer>();
        arr_object = new ArrayList<NumberButtonClass>();
        fillNumbersArray();
        plusBtn = (Button) rootView.findViewById(R.id.button16);
        multiplyBtn = (Button) rootView.findViewById(R.id.button17);
        num1 = num2 = num3 = 0;
    }

    private void fillNumbersArray() {
        for (int i = 0; i < 10; i++)
            arr_numbers.add(i);

        arr_numbers.add(20);
        arr_numbers.add(30);

        Collections.shuffle(arr_numbers);
    }


    private void searchNumberToShow(final Button numberButton) {
        int id_button;
//            if button was not clicked in past
        if ((id_button = findIdButtonInObjectArray(numberButton.getId())) == -1) {
            arr_object.add(new NumberButtonClass(arr_numbers.get(z), numberButton.getId()));

            numberButton.setText(arr_numbers.get(z).toString());
            z++;
//              if button was clicked in past - get it's number
        } else {
            Integer numberToShow = arr_object.get(id_button).getnumberToShow();
            numberButton.setText(numberToShow.toString());

            currentNum = numberToShow;
        }
    }


    private void hideButton(final Button numberButton) {

        buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        clickerCounter--;
                        numberButton.setText("");
                    }
                });
            }
        }, hideDelay);

    }


    private int findIdButtonInObjectArray(int id) {

        int i = 0;
        for (NumberButtonClass numberButton : arr_object) {
            if (numberButton.getbtnId() == id)

                return i;
            i++;
        }
        return -1;
    }

    private Button findButtonIdbyNumber(int number) {
        int btnId = 0;
        Button blockedBtn = null;
        for (NumberButtonClass numberButton : arr_object) {
            if (numberButton.getnumberToShow() == number)

                btnId = numberButton.getbtnId();
            blockedBtn = (Button) rootView.findViewById(btnId);
        }
        return blockedBtn;
    }


    public void actionClicked(View view) {
//        showState();
        if (!numClicked) {
            Toast.makeText(getActivity(), "First choose the number", Toast.LENGTH_LONG).show();

        } else
            switch (state) {
                case 0:
                    Toast.makeText(getActivity(), "First choose the number", Toast.LENGTH_LONG).show();

                case 1:
                case 2:
                case 3:
                    num1 = currentNum;
                    Button blockedBtn = findButtonIdbyNumber(num1);
                    blockedBtn.setEnabled(false);
                    if (view == plusBtn)
                        state = 2;
                    else if (view == multiplyBtn)
                        state = 3;
                    break;
                case 4:

                case 6:
                    if (view == plusBtn)
                        Toast.makeText(getActivity(), "You already used +!", Toast.LENGTH_LONG).show();
                    else if (view == multiplyBtn) {
                        state = 6;
//                        num2 = currentNum;
                    }
                    break;
                case 5:
                case 7:
                    if (view == plusBtn) {
                        state = 7;
//                        num2 = currentNum;
                    } else if (view == multiplyBtn)
                        Toast.makeText(getActivity(), "You already used X!", Toast.LENGTH_LONG).show();
                    break;


            }


    }

    public void showState() {
        Toast.makeText(getActivity(), "State is " + state.toString() + " cur is " + currentNum.toString(), Toast.LENGTH_SHORT).show();

    }

    public void number_clicked(View view) {
        numClicked = true;
        steps++;
//        showState();
        if (view instanceof Button && clickerCounter < 2) {
            final Button numberButton = (Button) view;
            clickerCounter++;

            switch (state) {
                case 0:

                case 1:
                    state = 1;
                    searchAndShowNumber(numberButton);
                    hideButton(numberButton);
                    break;
                case 2:

                    afterNumberTwo(numberButton);
                    state = 4;
                    break;
                case 3:

                    afterNumberTwo(numberButton);
                    state = 5;
                    break;
                case 6:
                    state = 8;
                    searchAndShowNumber(numberButton);
                    num3 = currentNum;
                    result = (num1 + num2) * num3;
                    showResult();
                    break;
                case 7:
                    state = 9;
                    searchAndShowNumber(numberButton);
                    num3 = currentNum;
                    result = num1 * num2 + num3;
                    showResult();
                    break;
                case 4:
                case 8:
                case 9:
                    Toast.makeText(getActivity(), "You already selected number! ", Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }

    private void afterNumberTwo(Button numberButton) {
        searchAndShowNumber(numberButton);
        hideButton(numberButton);
        num2 = currentNum;
        Button blockedBtn = findButtonIdbyNumber(num2);
        blockedBtn.setEnabled(false);
    }

    private void showResult() {
        DateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        MainActivity.allResults.add(new Statistic(date, steps, result));
        Timer resultTImer = new Timer();
        resultTImer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ((MainActivity) getActivity()).goToResult();

                    }
                });
            }
        }, resultDelay);
    }

    private void searchAndShowNumber(Button numberButton) {
        int id_button;


//            if button was not clicked in past
        if ((id_button = findIdButtonInObjectArray(numberButton.getId())) == -1) {
            arr_object.add(new NumberButtonClass(arr_numbers.get(z), numberButton.getId()));
            currentNum = arr_numbers.get(z);
            numberButton.setText(currentNum.toString());
            z++;
//              if button was clicked in past - get it's number
        } else {
            currentNum = arr_object.get(id_button).getnumberToShow();
            numberButton.setText(currentNum.toString());

        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGameFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onGameFragmentInteractionListener) {
            mListener = (onGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onGameFragmentInteractionListener");
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
    public interface onGameFragmentInteractionListener {
        // TODO: Update argument type and name
        void onGameFragmentInteraction(Uri uri);
    }
}
