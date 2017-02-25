package com.example.android.p03quizapp;

import android.widget.CheckBox;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.24..
 *
 * class to implement QuizInputHandler interface on Checkbox inputs
 *
 */

public class QuizInputHandlerCheckbox implements QuizInputHandler {
    //  ArrayList holding the checkbox views related to the particular quiz question
    //  actual answers will be read from checkboxes text attribute
    private ArrayList<CheckBox> checkBoxes;

    /**
     * constructor for QuizInputHandlerCheckbox object
     * @param checkBoxes is the ArrayList holding the Checkbox objects contributing to the involved quiz
     */
    QuizInputHandlerCheckbox(ArrayList<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    /**
     * method to get ArrayList<String> of the answer(s) checked by user.
     * @return ArrayList of checked answer or empty ArrayList if none is checked
     */
    @Override
    public ArrayList<String> getInputAnswers() {
        ArrayList<String> checkedAnswers = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++)
        {
            if (checkBoxes.get(i).isChecked())
            {
                checkedAnswers.add(checkBoxes.get(i).getText().toString());
            }
        }
        return checkedAnswers;
    }

    /**
     * method to reset the checkboxes contributing to the involved quiz
     */
    @Override
    public void resetInput() {
        for (CheckBox checkBox: checkBoxes)
        {
            checkBox.setChecked(false);
        }
    }
}
