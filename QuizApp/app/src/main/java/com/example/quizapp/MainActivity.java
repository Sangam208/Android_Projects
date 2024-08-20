package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static final String extra_key1 = "com.example.quizapp.extra.key1";
    public static final String extra_key2 = "com.example.quizapp.extra.key2";
    TextView display_questions;
    Button yes,no;
    private int index = 0,score=0;
    private final String[] myquestions = {"1. C++ was developed by Dennis Ritchie.", "2. C++ is a procedure based programming language.", "3. C++ provides STL features.", "4. C++ uses printf() function to display output.", "5. C++ allows for generic programming."};
    private final boolean[] answers = {false, false, true, false, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        display_questions = findViewById(R.id.question);
        display_questions.setText(myquestions[index]);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
//        if clicked yes button
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index <= myquestions.length-1){
                    if (answers[index]) {
                        score++;
                    }
                    index++;
                    if(index<= myquestions.length-1){
                        display_questions.setText(myquestions[index]);
                    }else{
                        checkIntent();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Restart to play again", Toast.LENGTH_SHORT).show();
                }
            }
        });
////        if clicked no button
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index <= myquestions.length-1){
                    if (!answers[index]) {
                        score++;
                    }
                    index++;
                    if(index<= myquestions.length-1){
                        display_questions.setText(myquestions[index]);
                    }else{
                        checkIntent();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Restart to play again", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void checkIntent(){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra(extra_key1,Integer.toString(score));
        intent.putExtra(extra_key2,Integer.toString(myquestions.length));
        startActivity(intent);
    }
}