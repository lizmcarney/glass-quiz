/**
 * @author elizabeth.carney
 * @version 1.0.0
 */

package com.lizmcarney.googleglassquiz;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.glass.widget.CardScrollAdapter;

public class ResultsAdapter extends CardScrollAdapter {
    
    /** Reference to the SummaryCard. */
    private final SummaryCard summary;
    
    private final LayoutInflater mLayoutInflater;
    private final Resources mResources;
    
    /** Nested class to hold the Summary card. */
    private class SummaryCard {
        public TextView messageView;
        public TextView scoreView;
    }

    
    /** An adapter that takes the results of a quiz and creates a summary and a list of the wrong questions.
     * @param layoutInflater 	A layoutInflater from the ResultsActivity.
     * @param resources 		A reference to the resources folder.
     */
    public ResultsAdapter(
            LayoutInflater layoutInflater,
            Resources resources) {
        mLayoutInflater = layoutInflater;
        mResources = resources;
        summary = new SummaryCard();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View summaryView;
        if (convertView != null) {
            summaryView = convertView;
        } else {
            summaryView = inflateSummaryView(parent);
        }
        updateSummaryView(summaryView);
        return summaryView;
    }

    private View inflateSummaryView(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.card_results_summary, parent);
        summary.messageView = (TextView) view.findViewById(R.id.message);
        summary.scoreView = (TextView) view.findViewById(R.id.score_summary);
        view.setTag(summary);
        return view;
    }

    private void updateSummaryView(View summaryView) {
       summary.messageView.setText(QuizModel.areAllQuestionsAnswered() ?
                R.string.great_job : R.string.game_over);
       summary.scoreView.setText(mResources.getString(R.string.score_summary, QuizModel.getCorrectAnswers(), QuizModel.getNumberOfAnsweredQuestions()));
    }

	@Override
	public int getCount() {
		return 1;
	}
	
    @Override
    public int getPosition(Object item) {
        if(item.equals(summary)){
        	return 0;
        } else return -1; 
    }

    @Override
    public Object getItem(int position) {
        return summary;
    }
}
