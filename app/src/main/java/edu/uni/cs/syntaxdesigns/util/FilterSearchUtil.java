package edu.uni.cs.syntaxdesigns.util;

import edu.uni.cs.syntaxdesigns.R;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Inject;
import java.util.ArrayList;

public class FilterSearchUtil {

    private ArrayList<String> mWithIngredients;
    private ArrayList<String> mWithoutIngredients;
    private ArrayList<String> mWithCourse;
    private String mTime;
    private Resources mResources;

    @Inject
    public FilterSearchUtil(Context context) {
        mResources = context.getResources();

        mWithIngredients = new ArrayList<String>();
        mWithoutIngredients = new ArrayList<String>();
        mWithCourse = new ArrayList<String>();
        mTime = null;
    }

    public void withIngredient(String ingredient) {
        mWithIngredients.add(ingredient);
    }

    public void withoutIngredient(String ingredient) {
        mWithoutIngredients.add(ingredient);
    }

    public void withTime(String time) {
        mTime = getTimeInSeconds(time);
    }

    public void withCourse(String course) {
        mWithCourse.add(mResources.getString(R.string.course_header) + course);
    }

    public ArrayList<String> getWithIngredients() {
        return mWithIngredients;
    }

    public ArrayList<String> getWithoutIngredients(){
        return mWithoutIngredients;
    }

    public ArrayList<String> getWithCourse() {
        return mWithCourse;
    }

    public String getTime() {
        return mTime;
    }

    public void removeCourse(String course) {
        mWithCourse.remove(mWithCourse.indexOf(mResources.getString(R.string.course_header) + course));
    }

    public void clearFilters() {
        mWithIngredients.clear();
        mWithoutIngredients.clear();
        mWithCourse.clear();
        mTime = null;
    }

    public String getTimeInSeconds(String time) {
        Integer timeInSeconds = Integer.parseInt(time) * 60;

        return timeInSeconds.toString();
    }
}
