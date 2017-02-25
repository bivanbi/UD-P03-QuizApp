package com.example.android.p03quizapp;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.24..
 *
 * class to implement QuizInputHandler interface on RadioButton inputs
 */

public class QuizInputHandlerRadio implements QuizInputHandler {
    private String logTag = QuizInputHandlerRadio.class.getSimpleName();
    //  ArrayList for holding RadioButton objects
    //  actual answers will be read from RadioButton objects
    private ArrayList<RadioButton> radioButtons;

    /**
     * constructor for QuizInputHandlerRadio object
     * @param radioButtons is the ArrayList holding the RadioButton objects contributing to the involved quiz
     */
    public QuizInputHandlerRadio(ArrayList<RadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    /**
     * method to get ArrayList<String> of the answer checked by user. Since the input is RadioGroup,
     * ArrayList will most likely hold 0 or 1 answer.
     * @return ArrayList of checked answer or empty ArrayList if none is checked
     */
    @Override
    public ArrayList<String> getInputAnswers() {
        Log.d(logTag,"called");

        ArrayList<String> checkedAnswers = new ArrayList<>();
        for (int i = 0; i < radioButtons.size(); i++)
        {
            Log.d(logTag,"RadioButton#" + i + ": "+radioButtons.get(i).getText());
            if (radioButtons.get(i).isChecked())
            {
                Log.d(logTag,"RadioButton#" + i + ": checked");
                checkedAnswers.add(radioButtons.get(i).getText().toString());
            }
        }

        Log.d(logTag,"End: "+checkedAnswers.size()+ " answers checked: "+checkedAnswers);
        return checkedAnswers;
    }

    /**
     * method to reset the RadioGroup and belonging RadioButtons contributing to the involved quiz
     */
    @Override
    public void resetInput() {
        RadioGroup radioGroup = (RadioGroup) radioButtons.get(0).getParent();
        radioGroup.clearCheck();
    }
}
