package com.example.is2011_xo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);


    }

    public void OnClick(View view) {


        EditText name1Text = findViewById(R.id.name1);
        EditText name2Text = findViewById(R.id.name2);

        String name1 = name1Text.getText().toString();
        String name2 = name2Text.getText().toString();

        if ( name1.equals("") && name2.equals("")) {
            Toast toast = Toast.makeText(this, "Заполните все поля!",Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("name1", name1);
            intent.putExtra("name2", name2);
            startActivity(intent);
        }

    }
}
