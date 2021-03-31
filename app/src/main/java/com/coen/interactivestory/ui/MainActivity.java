package com.coen.interactivestory.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.coen.interactivestory.R;

public class MainActivity extends AppCompatActivity {
private TextView nameField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameField = findViewById(R.id.nameEditText);

    }
    protected void onResume(){
        super.onResume();
        nameField.setText("");
    }
    private void newActivity(String name){
        Intent intent = new Intent(this, StoryActivity.class);
        Resources resources = getResources();
        String key = resources.getString(R.string.key_name);
        intent.putExtra(key, name);
        startActivity(intent);
    }
    public void startStory(View view) {
        String name = nameField.getText().toString();
        newActivity(name);
    }
}