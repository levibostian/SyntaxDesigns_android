package edu.uni.cs.syntaxdesigns.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class BaseArrayAdapter<T> extends ArrayAdapter<T> {

    private LayoutInflater mInflater;

    public BaseArrayAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);

        mInflater = LayoutInflater.from(context);
    }

    protected <T extends View> T getInflatedView(View convertView, int layoutResId, ViewGroup parent) {
        if (convertView == null) {
            return (T) mInflater.inflate(layoutResId, parent, false);
        } else {
            return (T) convertView;
        }
    }
}
