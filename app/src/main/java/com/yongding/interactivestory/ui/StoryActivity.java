package com.yongding.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongding.interactivestory.R;
import com.yongding.interactivestory.model.Choice;
import com.yongding.interactivestory.model.Page;
import com.yongding.interactivestory.model.Story;


public class StoryActivity extends Activity {

    public static final String TAG = StoryActivity.class.getSimpleName();
    private Story mStory = new Story();  //represents story book with pages to flip
    private String mName; //user's mName to insert to story
    private Page mCurrentPage;
    //widgets on mCurrentPage
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        //retrieve intent set in mainActivity
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));  //R.string.StrRes is getting string resources from strings.xml

        if(mName == null){
            mName = "Friend";
        }
        Log.d(TAG, mName);
        //set vars to be the view in activity
        mImageView = (ImageView)findViewById(R.id.storyImageView);
        mTextView = (TextView)findViewById(R.id.storyTextView);
        mChoice1 = (Button)findViewById(R.id.choiceButton1);
        mChoice2 = (Button)findViewById(R.id.choiceButton2);
        //load page0 first
        loadPage(0);
    }
    //load mCurrentPage when called
    private void loadPage(final int choice){
        mCurrentPage = mStory.getPage(choice);
        //get drawable from res folder, imageID is the drawableID we set in Story, and Page classes
        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        pageText= String.format(pageText, mName);  //insert name into formatted string such as %1$s (placeholder)
        mTextView.setText(pageText);
        //check if it is the final page
        if(!mCurrentPage.isFinal()) {
            //set the text of the button
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());
            //load next page after click
            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
        else{ //if it is final page ask for user to play again
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); //finish the current activity and get back to the main activity (previous one)
                }
            });
        }
    }
}
