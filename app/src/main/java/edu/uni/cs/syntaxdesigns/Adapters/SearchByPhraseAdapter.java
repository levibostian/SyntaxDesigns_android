package edu.uni.cs.syntaxdesigns.Adapters;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchByPhraseAdapter extends BaseArrayAdapter {

    private List<PhraseResults> mResults;
    private LayoutInflater mLayoutInflater;

    public SearchByPhraseAdapter(Context context, List<PhraseResults> results) {
        super(context, 0, results);

        mResults = results;

        mLayoutInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView recipeName;
        TextView rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.test_list_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.recipeName = (TextView) convertView.findViewById(R.id.recipe_name);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rating);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhraseResults results = mResults.get(position);

        viewHolder.recipeName.setText(results.recipeName);
        viewHolder.rating.setText(Integer.toString(results.rating));

        return convertView;
    }
}
