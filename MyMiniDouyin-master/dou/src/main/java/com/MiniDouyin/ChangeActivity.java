package com.MiniDouyin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeActivity extends AppCompatActivity {

    private EditText ID;
    private EditText NAME;
    private Button CONFIRM;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        ID = findViewById(R.id.ID);
        NAME = findViewById(R.id.NAME);
        CONFIRM = findViewById(R.id.CONFIRM);

        CONFIRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changedintent = new Intent();
                changedintent.putExtra("ID", ID.getText().toString());
                changedintent.putExtra("NAME", NAME.getText().toString());
                setResult(RESULT_OK, changedintent);
                finish();
            }
        });
    }
}
