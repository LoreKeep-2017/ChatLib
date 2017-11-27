package org.chatlib.chatlib.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.chatlib.chatlib.R;

public class MainActivity extends Activity {

    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateButton = findViewById(R.id.create_chat_button);
        mCreateButton.setOnClickListener(new CreateButton());
    }

    private class CreateButton implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, ChatActivity.class));
        }
    }
}
