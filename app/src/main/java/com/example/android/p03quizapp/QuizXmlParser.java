package com.example.android.p03quizapp;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bivanbi on 2017.02.23..
 *
 * class to parse xml resource containing quiz data into ArrayList of QuizQuestion objects
 *
 * XML should be structured like this:
 *  <quizquestions>
 *      <quizquestion>
 *          <header_image_src>name of drawable resource</header_image_src>
 *          <question>Quiz Question Text?</question>
 *          <input_type>Radio</input_type>
 *          <answer correct="false">New York City</answer>
 *          <answer correct="true">Washington D.C.</answer>
 *          <answer correct="false">Chicago</answer>
 *          <answer correct="false">Philadelphia</answer>
 *      </quizquestion>
 *
 *      (...)
 *  </quizquestions>
 */

class QuizXmlParser {
    //  hold the last error message so calling method can include it in its error messages
    private static String lastErrorMessage = "";

    /**
     * method to parse XML data into ArrayList of QuizQuestion objects
     * declared as static so it need not be instantiated
     * @param activity is the calling activity
     * @param xmlResourceId is the resource id of XML resource to be parsed
     * @return null if parse error is occurred or ArrayList of objects if successful
     * @throws XmlPullParserException
     * @throws IOException
     */
    static ArrayList<QuizQuestion> parse(Activity activity, int xmlResourceId)
            throws XmlPullParserException, IOException
    {
        String logTag = QuizXmlParser.class.getSimpleName();
        Resources resources = activity.getResources();
        //  create an xml parser for res/xml/quiz_data.xml
        XmlResourceParser quizDataXmlParser = resources.getXml(R.xml.quiz_data);
        //  arraylist to hold tag "stack"
        ArrayList<String> xmlTagStack = new ArrayList<>();
        //  arraylist to hold QuizQuestion objects to parse XML data into
        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
        //  current QuizQuestion object we are parsing XML data into
        QuizQuestion currentQuestion = null;
        //  hold the parse attribute "correct" for <answer> tag
        boolean isCurrentAnswerCorrect = false;

        //  step to the first "event" in XML parsing and get event type for while() loop
        quizDataXmlParser.next();
        int eventType = quizDataXmlParser.getEventType();

        //  parse XML till "END_DOCUMENT" is reached
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            //  begin document. Not used, just log it.
            if(eventType == XmlPullParser.START_DOCUMENT)
            {
                Log.d(logTag,"Begin Document");
            }
            //  reached an opening tag
            else if(eventType == XmlPullParser.START_TAG)
            {
                //  get tag name
                String tagName = quizDataXmlParser.getName();
                //  and add it to tag stack so it can be retrieved later and also to detect XML errors
                xmlTagStack.add(tagName);
                Log.d(logTag,"Begin Tag "+tagName+", depth: "+xmlTagStack.size());
                Log.d(logTag,"Tag "+tagName+" has "+quizDataXmlParser.getAttributeCount()+" attribute(s)");

                // this is the beginning of a quiz question tag so create a new QuizQuestion object
                if (tagName.equals("quizquestion")){
                    currentQuestion = new QuizQuestion();
                }
                //  this is an answer tag, so add it to QuizQuestion Object
                else if(tagName.equals("answer"))
                {
                    //  make a note of the "correct" attribute of <answer> tag for later use
                    isCurrentAnswerCorrect = quizDataXmlParser.getAttributeBooleanValue(null,"correct",false);
                    if (isCurrentAnswerCorrect == true) {
                        Log.d(logTag, "Tag " + tagName + " has attribute correct = true");
                    }
                    else
                    {
                        Log.d(logTag, "Tag " + tagName + " has attribute correct = false");
                    }
                }
            }
            //  end tag
            else if(eventType == XmlPullParser.END_TAG)
            {
                String tagName = quizDataXmlParser.getName();
                if (xmlTagStack.size() < 1)
                {
                    lastErrorMessage = "Error 101: encountered END_TAG "+quizDataXmlParser.getName()+" while TagStack is empty";
                    Log.e(logTag, lastErrorMessage);
                    return null;
                }
                xmlTagStack.remove(xmlTagStack.size()-1);
                Log.d(logTag,"End Tag "+quizDataXmlParser.getName()+", depth: "+xmlTagStack.size());

                //  reached the end of a quizquestion definition, so add object to the array
                if (tagName.equals("quizquestion")){
                    if (currentQuestion != null)
                        quizQuestions.add(currentQuestion);
                    currentQuestion = null;
                }
            }
            //  text between tag begin and end - question, answers, input type
            else if(eventType == XmlPullParser.TEXT)
            {
                //  read current tag name from tag stack
                String currentTag = xmlTagStack.get(xmlTagStack.size()-1);
                //  get tag text from parser
                String text = quizDataXmlParser.getText();
                Log.d(logTag,"Text: "+text+", current tag: "+currentTag+", depth: "+xmlTagStack.size());

                //  check if QuizQuestion object is present. If not, either XML or our parser code has errors.
                if (currentQuestion == null) {
                    Log.e(logTag,"currentQuestion is not initialized! text: "+text+", current tag: "+currentTag+", depth: "+xmlTagStack.size());
                    continue;
                }

                //  process current tag - add information to QuizQuestion object
                switch (currentTag){
                    case "header_image_src":
                        int drawableResourceId = activity.getResources()
                                .getIdentifier(text, "drawable", activity.getPackageName());
                        currentQuestion.setHeaderImageResId(drawableResourceId);
                        break;
                    case "question":
                        currentQuestion.setQuestion(text);
                        break;
                    case "answer":
                        currentQuestion.addAnswer(text, isCurrentAnswerCorrect);
                        break;
                    case "input_type":
                        currentQuestion.setInputType(text);
                        break;
                    default:
                        //  unknown tag encountered
                        Log.e(logTag,"Unexpected tag "+currentTag+" with text: "+text
                                +", depth: "+xmlTagStack.size());
                        break;
                }
            }
            //  step to next parser event
            eventType = quizDataXmlParser.next();
        }
        Log.d(logTag,"End Document");
        return quizQuestions;
    }
}
