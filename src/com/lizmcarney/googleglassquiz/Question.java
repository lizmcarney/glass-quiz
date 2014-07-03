/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

public class Question {
	/**The Question's text */
	private String text;
	
	/**The Question's answer, in true/false */
	private boolean isTrue;
	
	/**The Question's state, in answered/unanswered */
	private boolean isAnswered;
	
	/** Constructor that creates a question that is assumed to be unanswered.
	 * @param text 		The text of the question.
	 * @param isTrue	The true/false value of the question.
	 */
	public Question(String text, boolean isTrue) {
		super();
		this.text = text;
		this.isTrue = isTrue;
		this.isAnswered = false;
	}
	
	/** Constructor that creates a question that is assumed to be unanswered.
	 * @param text 			The text of the question.
	 * @param isTrue		The true/false value of the question.
	 * @param isAnswered	Explicit boolean value representing the state of this question.
	 */
	public Question(String text, boolean isTrue, boolean isAnswered) {
		super();
		this.text = text;
		this.isTrue = isTrue;
		this.isAnswered = isAnswered;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public void setIsTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	/** Checks the Question's state, isAnswered. 
	 * @return True is the question is answered, false if the question is unanswered.
	 */
	public boolean isAnswered() {
		return isAnswered;
	}

	/** Sets the Question's state to isAnswered=true. */
	public void answered() {
		this.isAnswered = true;
	}
	
	/** Resets the Question's state back to isAnswered=false. */
	public void reset(){
		this.isAnswered = false;
	}
}
