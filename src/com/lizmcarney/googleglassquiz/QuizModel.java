/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {
	/** List of questions to be used in the quiz. */
    private static List<Question> mQuestions;
    
	/** Number of answered questions, regardless of correct or incorrect. */
    private static int mAnsweredQuestions;
    
	/** Number of correctly answered questions. */
    private static int mCorrectAnswers;
    
	/** The index of the current question. */
    private static int mCurrentQuestion;
    
	/** A static reference to the QuizModel Singleton. */
    private static QuizModel instance;

    /** Private constructor. QuizModel is a Singleton. */
    private QuizModel() {
    	mQuestions = new ArrayList<Question>();
        mAnsweredQuestions = 0;
        mCorrectAnswers = 0;
        mCurrentQuestion = 0;
    }
    
    /** Gets a reference to the Singleton. If there is no reference, creates a new Singleton.
     * @return A reference to the QuizModel Singleton.
     */
    public static QuizModel getInstance(){
    	if(instance == null){
    		instance = new QuizModel();
    	}
    	return instance;
    }
    
    /** Resets the Singleton reference to an empty QuizModel. */
    public static void resetModel(){
    	QuestionAdapter.resetQuestions();
    	mQuestions = new ArrayList<Question>();
    	mAnsweredQuestions=0;
    	mCorrectAnswers=0;
    	mCurrentQuestion=0;
    }

    public static void setQuestions(List<Question> questions){
    	mQuestions = questions;
    }

    public static int getNumberOfQuestions(){
    	return mQuestions.size();
    }
    
    public static int getNumberOfAnsweredQuestions(){
    	return mAnsweredQuestions;
    }
    
    public static int getCorrectAnswers() {
        return mCorrectAnswers;
    }

    public static boolean areAllQuestionsAnswered() {
        return mAnsweredQuestions == mQuestions.size();
    }

    public static Question getCurrentQuestion() {
        return mQuestions.get(mCurrentQuestion);
    }

    public static int getCurrentQuestionIndex() {
        return mCurrentQuestion;
    }

    public static boolean isAnswerCorrect(boolean answer){
    	if(getCurrentQuestion().isTrue() == answer){
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public static boolean isQuestionAnswered(int index) {
        return mQuestions.get(index).isAnswered();
    }

    public static boolean markAnswered(boolean correct) {
    	getCurrentQuestion().answered();
    	mAnsweredQuestions++;
        if(correct){
        	mCorrectAnswers++;
        }
        return advance();
    }

    public static boolean pass() {
        return advance();
    }

    private static boolean advance() {
        if (!areAllQuestionsAnswered()) {
            do {
                mCurrentQuestion = (mCurrentQuestion + 1) % mQuestions.size();
            } while (isQuestionAnswered(mCurrentQuestion));
            return false;
        }
        return true;
    }
}
