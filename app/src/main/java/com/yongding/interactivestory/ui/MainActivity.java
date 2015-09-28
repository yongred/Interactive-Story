package com.yongding.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yongding.interactivestory.R;


public class MainActivity extends Activity {
    //widgets on Main page
    private EditText mNameField;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //R.id.StringID to get the widgets
        mNameField = (EditText)findViewById(R.id.NameEditText);
        mStartButton = (Button)findViewById(R.id.startButton);

        mStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mNameField.getText().toString();
                startStory(name);
            }
        });
    }

    private void startStory(String name){
        //use intent to call StoryActivity class
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(getString(R.string.key_name), name);  //Extra data store in intent for retrieval use
        startActivity(intent);
    }
    //when user resume to main activity
    @Override
    protected void onResume() {
        super.onResume();
        mNameField.setText("");
    }
}








