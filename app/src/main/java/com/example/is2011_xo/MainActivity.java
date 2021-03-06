package com.example.is2011_xo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int rountCount;
    boolean activePlayer;
    private String name1;
    private String name2;

    //p1 => 0
    //p2 => 1
    //empty => 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //выигрышные позиции по строкам
            {0,3,6}, {1,4,7}, {2,5,8}, //выигрышные позиции по колонкам
            {0,4,8}, {2,4,6} //выигрышные позиции по диагонали
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length; i++) {
            String btnId = "btn_" + i;
            int resourseId = getResources().getIdentifier(btnId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourseId);
            buttons[i].setOnClickListener(this);
        }
        rountCount = 0;
        activePlayer = true;

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            name1 = arguments.get("name1").toString();
            name2 = arguments.getString("name2");
        }
    }




    @Override
    public void onClick(View view) {
        if (!((Button)view).getText().toString().equals("")) {
            return;
        }

        String btnId = view.getResources().getResourceEntryName(view.getId()); //берем id кнопки которая была нажата (btn_2)
        int gameStatePointer = Integer.parseInt(btnId.substring(btnId.length()-1, btnId.length() )); //переменная хранит только цифру кнопки

        if (activePlayer == true) { //играет первый игрок
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#c22b32"));
            gameState[gameStatePointer] = 0;
            activePlayer = false;
        }
        else { //играет второй игрок
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#2b55c2"));
            gameState[gameStatePointer] = 1;
            activePlayer = true;
        }

         rountCount++; //сколько кнопок уже было нажато

        if (checkWinner() == true) { // если есть победитель
            if (activePlayer == true) { //право хода у первого игрока

                Toast pl2 = Toast.makeText(this, "Игрок " +name2+ " выиграл!",Toast.LENGTH_LONG);
                pl2.show();
            }
            else {
                Toast pl1 = Toast.makeText(this, "Игрок " +name1+ " выиграл!",Toast.LENGTH_LONG);
                pl1.show();
            }
        }else if (rountCount == 9) {
            Toast nichya = Toast.makeText(this, "Ничья!",Toast.LENGTH_LONG);
            nichya.show();

        }
//
        resetGame.setOnClickListener(new View.OnClickListener() { //обновление  игры
            @Override
            public void onClick(View view) {
                playAgain();

            }
        });
    }

    public boolean checkWinner() { //проверка на есть ли победитель
        boolean winnerResults = false;

        for (int [] winingPoison : winningPositions) {

            if (gameState[winingPoison[0]] == gameState[winingPoison[1]] &&
                    gameState[winingPoison[1]] == gameState[winingPoison[2]] &&
                    gameState[winingPoison[0]] != 2)
            {
                winnerResults = true;
            }
        }

        return winnerResults;
    }

    public void playAgain() { // обнуление поля кнопок
        Toast reset = Toast.makeText(this, "Не хотите ли повторить?",Toast.LENGTH_LONG);
        reset.show();
        Intent i = new Intent(this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        closeActivity();
    }

    private void closeActivity() {
        this.finish();
    }
}
