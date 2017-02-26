package com.example.android.p03quizapp;

import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.24..
 * interface for handling quiz inputs: extracting answers from actual EditText, checkboxes or
 * RadioButtons and reset them upon request.
 *
 * Creating interface and different implementation for each input type might be overkill for a
 * small project such as this, but it is important to get familiar with the basic idea and this
 * is a good opportunity, so here we go.
 */

interface QuizInputHandler {
    /**
     * method to get an ArrayList of Strings of the actual answers given by the user by means of
     *  editing a text field (EditText)
     *  checking checkboxes (selecting multiple answers from a list)
     *  checking a radio button in a RadiGroup (selecting a single answer from a list)
     */
    ArrayList<String> getInputAnswers();

    /**
     * method to reset input when requested: empty EditText, uncheck checkboxes and clear RadioGroups
     */
    void resetInput();
}
