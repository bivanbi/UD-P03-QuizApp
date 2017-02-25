package com.example.android.p03quizapp;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.24..
 * class to implement QuizInputHandler interface on EditText input
 */

public class QuizInputHandlerEditText implements QuizInputHandler {
    //  the EditText view containing user input
    private EditText input;

    /**
     * constructor for QuizInputHandlerRadio object
     * @param input is the EditText view object contributing to the involved quiz
     */
    QuizInputHandlerEditText(View input) {
        this.input = (EditText) input;
    }

    /**
     * method to get ArrayList<String> of the answer entered by user. Since the input is EditText,
     * ArrayList will most likely 1 (possibly empty) answer.
     * @return ArrayList with one element holding the answer
     */
    @Override
    public ArrayList<String> getInputAnswers() {
        ArrayList<String> inputStringArray = new ArrayList<>();
        inputStringArray.add(input.getText().toString());
        return inputStringArray;
    }

    /**
     * method to reset the EditText contributing to the involved quiz
     */
    @Override
    public void resetInput() {
        input.setText("");
    }
}
