package com.example.android.p03quizapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bivanbi on 2017.02.24..
 *
 * class to
 *      - check user input and display score on clicking the check answer button,
 *      - reset form on long clicking check answer button
 */
class QuizCheckAnswer implements View.OnClickListener, View.OnLongClickListener {
    //  ArrayList of QuizQuestion objects
    private ArrayList<QuizQuestion> quizQuestions;
    //  context of our activity
    private Context context;
    private String logTag = QuizCheckAnswer.class.getSimpleName();

    /**
     * constructor for QuizCheckAnswer object
     * @param context is the context of our activity
     * @param quizQuestions is the ArrayList of QuizQuestion objects
     */
    QuizCheckAnswer(Context context, ArrayList<QuizQuestion> quizQuestions) {
        this.context = context;
        this.quizQuestions = quizQuestions;
    }

    /**
     * method to check answers and display score
     * @param v is the view clicked
     */
    @Override
    public void onClick(View v) {
        Log.d(logTag,"button clicked");

        int score = 0;
        //  iterate over quiz questions, check answers and increase score if correct
        for (QuizQuestion quizQuestion: quizQuestions)
        {
            if (isAnswerCorrect(quizQuestion))
            {
                Log.i(logTag,quizQuestion.getQuestion()+": Answer is correct");
                score++;
            }
            else
            {
                Log.i(logTag,quizQuestion.getQuestion()+": Answer is incorrect");
            }
        }
        //  let the user know how many answers were correct
        Toast.makeText(context, score + " correct answers out of "+ quizQuestions.size(),Toast.LENGTH_SHORT).show();
    }

    /**
     * method to reset form on long click of check answer button
     * @param v is the view long clicked
     * @return true to let Android know long click is consumed
     */
    @Override
    public boolean onLongClick(View v) {
        Log.d(logTag,"button long clicked");
        for (QuizQuestion quizQuestion: quizQuestions)
        {
            quizQuestion.resetInput();
        }

        return true;
    }

    /**
     * Method to check if answer to a given quiz question is correct.
     * Handles single or multiple correct answers. If multiple correct answers are possibble,
     * it will only evaluate as correct if all possible correct answers are selected by user.
     * @param quizQuestion is the actual QuizQuestion object
     * @return true if answer is correct, false if not correct, no input received or error detected
     */
    private boolean isAnswerCorrect(QuizQuestion quizQuestion) {
        QuizInputHandler inputHandler = quizQuestion.getInputHandler();
        //  failsafe: all QuizQuestion objects *should* have an InputHandler at this point, but
        //  to avoid force close, check it
        if (inputHandler == null) {
            Log.e(logTag, "Cannot check answer, inputHandler is null. Question: "
                    + quizQuestion.getQuestion() + ", input type: "
                    + quizQuestion.getInputType());
            return false;
        }

        //  get list of actual answers from input handler
        ArrayList<String> givenAnswers = inputHandler.getInputAnswers();

        //  failsafe: if null is returned by inputHandler, log error
        if (givenAnswers == null)
        {
            Log.e(logTag, "Cannot check answer, inputHandler.getInputAnswer() returned null. Question: "
                    + quizQuestion.getQuestion() + ", input type: "
                    + quizQuestion.getInputType());
            return false;
        }

        //  get actual correct answer list from QuizQuestion object
        ArrayList<String> correctAnswers = quizQuestion.getCorrectAnswers();

        Log.d(logTag,"correctAnswers: "+correctAnswers);
        Log.d(logTag,"givenAnswers: "+givenAnswers);

        /*  simple checks:
         *      - if no answer is received
         *      - if the number of given answers do not match the number of correct answers,
         *  then the answer is not correct.
         */
        if ( givenAnswers.size() == 0 || givenAnswers.size() != correctAnswers.size())
        {
            Log.i(logTag,quizQuestion.getQuestion()+": no answer given or answer count ("+givenAnswers.size()
                    +") differs from correct ("+correctAnswers.size()+")");
            return false;
        }

        //  both list should be in the same order, but make sure they are.
        Collections.sort(correctAnswers);
        Collections.sort(givenAnswers);
        Log.d(logTag,"correctAnswers after sorting: "+correctAnswers);
        Log.d(logTag,"givenAnswers after sorting: "+givenAnswers);

        //  compare input to correct answers
        return givenAnswers.equals(correctAnswers);
    }
}
