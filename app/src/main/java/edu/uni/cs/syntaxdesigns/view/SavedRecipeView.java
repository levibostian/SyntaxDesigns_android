package edu.uni.cs.syntaxdesigns.view;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class SavedRecipeView extends LinearLayout {

    private RecipeIdVo mRecipe;
    private TextView mRecipeName;
    private ImageView mRecipeImage;
    private TextView mRating;
    private TextView mTime;
    private ListView mListView;
    private Button mViewDirections;
    private SavedRecipeViewListener mListener;

    private Resources mResources;
    private Context mContext;

    public SavedRecipeView(Context context, RecipeIdVo recipe) {
        super(context);

        mRecipe = recipe;
        initialize(context);
    }

    public SavedRecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.saved_recipe_dialog_view, this);

        mResources = context.getResources();
        mContext = context;

        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mListView = (ListView) findViewById(R.id.ingredients_list);
        mTime = (TextView) findViewById(R.id.time_in_minutes);
        mRating = (TextView) findViewById(R.id.rating_by_stars);
        mViewDirections = (Button) findViewById(R.id.view_directions);
        mRecipeImage = (ImageView) findViewById(R.id.recipe_image);

        Picasso.with(context).load(mRecipe.images.get(0).hostedMediumUrl)
               .placeholder(R.drawable.ic_launcher)
               .error(R.drawable.ic_launcher)
               .fit()
               .centerCrop()
               .into(mRecipeImage);

        setTextViews();

        initializeListView();

        mViewDirections.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.startRecipeDetails();
            }
        });

    }

    public void setListener(SavedRecipeViewListener listener) {
        mListener = listener;
    }

    private void initializeListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, mRecipe.ingredientLines);
        mListView.setAdapter(adapter);
    }

    private void setTextViews() {
        mRecipeName.setText(mRecipe.name);
        mTime.setText(" " + mRecipe.totalTime);
        mRating.setText(" " + mRecipe.rating + " " + mResources.getString(R.string.stars));
    }

    public interface SavedRecipeViewListener {
        void startRecipeDetails();
    }
}
