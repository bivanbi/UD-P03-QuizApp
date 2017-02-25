package com.example.android.p03quizapp;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.23..
 *
 * class to hold data for a Quiz Question:
 * - the question itself,
 * - input type,
 * - possible and correct answers,
 * - input handler to get answers given / checked by user
 * - provide getter and setter methods for the above data
 */

class QuizQuestion {
    private String logTag = QuizQuestion.class.getSimpleName();
    //  if the quiz has an image associated, store its resource id here
    private int headerImageResId;
    //  the actual question
    private String question;
    //  input type: EditText, Radio, Checkbox
    private String inputType;
    //  possible answers
    private ArrayList<String> answers;
    //  array of booleans to know which answers are correct and which are not
    private ArrayList<Boolean> answerIsCorrect;
    //  array of indexes of correct answers
    private ArrayList<Integer> correctAnswerIndexes;
    //  View object containing this quiz - image, question, inputs
    private View quizContainer;
    //  input handler for this quiz
    private QuizInputHandler inputHandler;

    /**
     * constructor for QuizQuestion object
     */
    QuizQuestion() {
        headerImageResId = 0;
        question = null;
        inputType = null;
        answers = new ArrayList<>();
        answerIsCorrect = new ArrayList<>();
        correctAnswerIndexes = new ArrayList<>();
        quizContainer = null;
        inputHandler = null;
    }

    /**
     * method to set header image resource id
     * @param headerImageResId is the resource id of header image
     */
    void setHeaderImageResId(int headerImageResId) {
        this.headerImageResId = headerImageResId;
    }

    /**
     * method to get header image resource id associated with this quiz
     * @return the image resource id or 0 if not set / invalid
     */
    int getHeaderImageResId() {
        return headerImageResId;
    }

    /**
     * method to set the question text for this quiz
     * @param question is the question
     */
    void setQuestion(String question) {
        this.question = question;
    }

    /**
     * method to get question text
     * @return is the string containing question text
     */
    String getQuestion() {
        return question;
    }

    /**
     * method to set quiz input type - EditText, Radio, Checkbox
     * @param inputType is the string containing input type
     */
    void setInputType(String inputType) {
        this.inputType = inputType;
    }

    /**
     * method to get quiz input type - EditText, Radio, Checkbox
     * @return is the string containing input method
     */
    String getInputType() {
        return inputType;
    }

    /**
     * method to add answer to the ArrayList of possible answers
     * @param answer is the answer text
     * @param isCorrect is true if the answer is correct
     */
    void addAnswer(String answer, boolean isCorrect) {
        if (isCorrect)
            correctAnswerIndexes.add(answers.size());
        answers.add(answer);
        answerIsCorrect.add(isCorrect);
    }

    /**
     * method to get the ArrayList of possible answers
     * @return ArrayList<String> of possible answers
     */
    ArrayList<String> getAnswers() {
        return answers;
    }

    String getAnswer(int index) {
        // check index to avoid out of bounds exception
        if (index < answers.size()) {
            return answers.get(index);
        } else {
            return null;
        }
    }

    /**
     * method to get the number of possible answers associated with this quiz
     * @return int with number of possible answers
     */
    int size() {
        return answers.size();
    }

    /**
     * method to set the View containing quiz question and input
     * @param quizContainer it the View containing this quiz
     */
    void setQuizContainer(View quizContainer) {
        this.quizContainer = quizContainer;
    }

    /**
     * method to get containing View for this quiz
     * @return the View containing this quiz
     */
    public View getQuizContainer() {
        return quizContainer;
    }

    /**
     * method to set InputHandler for this quiz
     * @param inputHandler is the InputHandler for this quiz
     */
    void setInputHandler(QuizInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    /**
     * method to get InputHandler for this quiz
     * @return the InputHandler for this quiz
     */
    QuizInputHandler getInputHandler() {
        return inputHandler;
    }

    /**
     * method to get an ArrayList containing correct answers
     * @return ArrayList<String> of correct answers
     */
    ArrayList<String> getCorrectAnswers()
    {
        ArrayList<String> correctAnswers = new ArrayList<>();
        for (int i: correctAnswerIndexes){
            correctAnswers.add(answers.get(i));
        }
        return correctAnswers;
    }

    /**
     * method to call the resetInput method of the associated InputHandler object
     */
    void resetInput() {
        if (inputHandler == null) {
            Log.e(logTag, "Cannot call resetInput, handler is null. Question: " + question + ", input type: " + inputType);
        } else {
            inputHandler.resetInput();
        }
    }

    /**
     * method to convert this object to a String for debug log etc.
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return "QuizQuestion{" +
                "headerImageResId=" + headerImageResId +
                ", question='" + question + '\'' +
                ", inputType='" + inputType + '\'' +
                ", answers=" + answers +
                ", answerIsCorrect=" + answerIsCorrect +
                ", correctAnswerIndexes=" + correctAnswerIndexes +
                '}';
    }
}

