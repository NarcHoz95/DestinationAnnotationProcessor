package com.aranteknoloji.destination;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aranteknoloji.intent.Destination;
import com.aranteknoloji.intent.navigation.IntentLauncher;

@Destination
public class MainActivity extends AppCompatActivity {

    private final IntentLauncher intentLauncher = new IntentLauncher();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("HelloWorld");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentLauncher.launchMainActivity(MainActivity.this);
            }
        });
        setContentView(textView);
    }
}
