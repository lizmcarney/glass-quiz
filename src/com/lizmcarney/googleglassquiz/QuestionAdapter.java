/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionAdapter {
	
	/**List of all possible questions. */
	private static List<Question> baseQuestions = new ArrayList<Question>();
	
	/**List of tutorial steps. */
	private static List<Question> tutorial = new ArrayList<Question>();
	
	/**Static reference to Singleton instance. */
	private static QuestionAdapter instance;
	
	/** Private constructor. QuestionAdapter is a Singleton. */
	private QuestionAdapter(){
	}
	
	/** Returns a reference to the Singleton instance. If the instance is null, creates a new Singleton instance.
	 * @return A refernce to the Singleton instance of the QuestionAdapter.
	 */
	protected static QuestionAdapter getInstance(){
		if(instance == null){
			instance = new QuestionAdapter();
		}
			return instance;
	}
	
	/** Adapts strings to Question objects and stores them in an ArrayList.
	 * @param text		A List of String values to become Question objects.
	 * @param isTrue	The true/false value of the Questions.
	 */
	protected static void adapt(List<String> text, boolean isTrue){
		for(int i=0; i<text.size(); i++){
			baseQuestions.add(new Question(text.get(i), isTrue));
		}
	}

	protected static List<Question> getTutorial(){
		return tutorial;
	}
	
	protected static List<Question> getRandomizedQuestions(){
		resetQuestions();
		Collections.shuffle(baseQuestions);
		return baseQuestions;
	}
	
	protected static List<Question> getRandomizedQuestions(int numQuestions){
		resetQuestions();
		Collections.shuffle(baseQuestions);
		if(numQuestions < baseQuestions.size()){
			return baseQuestions.subList(0, numQuestions);
		} else {
			return baseQuestions;
		}
	}
	
	/** Resets the Singleton instance to null. */
	protected static void resetAdapter(){
		instance = null;
	}
	
	/** Resets the isAnswered value of the Questions. */
	protected static void resetQuestions(){
		for(Question q : baseQuestions){
			q.reset();
		}
		for(Question q : tutorial){
			q.reset();
		}
	}
	
	/** Resets the isAnswered value of the tutorial Questions. */
	protected static void setTutorial(List<String> steps){
		if(instance==null){
			instance = new QuestionAdapter();
		}
		for(int i=0; i<steps.size(); i++){
			tutorial.add(new Question(steps.get(i), false));
		}
	}
}
