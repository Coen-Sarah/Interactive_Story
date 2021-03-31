package com.coen.interactivestory.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coen.interactivestory.R;
import com.coen.interactivestory.model.Page;
import com.coen.interactivestory.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {
    private Story story;
    private ImageView storyImageView;
    private TextView storyTextView;
    private Button buttonChoice1;
    private Button buttonChoice2;
    private String name;
    private Stack<Integer> pageStack = new Stack<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        buttonChoice1 = findViewById(R.id.choice1Button);
        buttonChoice2 = findViewById(R.id.choice2Button);

        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));
        if (name==null || name.isEmpty()){
            name = "Friend";
        }

        story = new Story();
        loadPage(0);

    }

    private void loadPage(int pageNumber) {
        Page page = story.getPage(pageNumber);
        pageStack.push(pageNumber);
        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        //add user name if placeholder is present
        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        if(page.isFinalPage()){
            buttonChoice1.setVisibility(View.INVISIBLE);
            buttonChoice2.setText(R.string.playAgainButton);
            buttonChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPage(0);
                }
            });
        }else {
            loadButtons(page);
        }
    }

    private void loadButtons(Page page){
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        buttonChoice1.setVisibility(View.VISIBLE);
        buttonChoice1.setText(page.getChoice1().getTextId());
        buttonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });
        buttonChoice2.setText(page.getChoice2().getTextId());
        buttonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }
    @Override
    public void onBackPressed() {
        pageStack.pop();
        if(pageStack.isEmpty()){
            super.onBackPressed();
        }else{
            loadPage(pageStack.pop());
        }


    }
}