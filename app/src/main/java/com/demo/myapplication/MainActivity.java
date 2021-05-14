package com.demo.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private final String url = "https://www.forbes.com/ajax/list/data?year=2020&uri=celebrities&type=person";
    private ArrayList<Celebrity> celebrityArrayList;
    private ImageView imageViewCelebrity;
    private Button buttonChoose1;
    private Button buttonChoose2;
    private Button buttonChoose3;
    private Button buttonChoose4;
    private Button[] allButtons;
    private TextView textViewPoints;
    private ActionBar actionBar;
    private Celebrity rightCelebrity;
    private int points = 0;
    private ProgressBar progressBar;
    private int life;
    private final ArrayList<Player> players = new ArrayList<>();
    Player player = new Player();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        celebrityArrayList = new ArrayList<>();
        imageViewCelebrity = findViewById(R.id.imageViewCelebrity);
        buttonChoose1 = findViewById(R.id.buttonChoose1);
        buttonChoose2 = findViewById(R.id.buttonChoose2);
        buttonChoose3 = findViewById(R.id.buttonChoose3);
        buttonChoose4 = findViewById(R.id.buttonChoose4);
        textViewPoints = findViewById(R.id.textViewPoints);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        allButtons = new Button[]{buttonChoose1, buttonChoose2, buttonChoose3, buttonChoose4};
        life = 3;
        loadingGame();
        startGame();

    }


    public void onClickCelebrityChoose(View view) {
        int id = view.getId();
        Button chooseButton;
        chooseButton = findViewById(id);
        if (chooseButton.getText() == rightCelebrity.getName()) {
            points++;
        } else {
            points--;
            life--;
        }
        startGame();
    }


    public void startGame() {

        if(life > 0 ) {
            textViewPoints.setText((String.valueOf(points)));
            int random = 0;
            Celebrity[] celebrityForGuess = new Celebrity[4];
            for (int i = 0; i < 4; i++) {
                random = (int) ((Math.random() * celebrityArrayList.size()));
                celebrityForGuess[i] = celebrityArrayList.get(random);
                allButtons[i].setText(celebrityForGuess[i].getName());

            }
            int randomOfFour = (int) (Math.random() * 4);
            rightCelebrity = celebrityForGuess[randomOfFour];
            imageViewCelebrity.setImageBitmap(rightCelebrity.getBitmap());
        } else {
            endGame();
        }

    }

    public void loadingGame() {

        progressBar.setVisibility(View.VISIBLE);
        textViewPoints.setVisibility(View.INVISIBLE);
        for (Button allButton : allButtons) {
            allButton.setVisibility(View.INVISIBLE);
        }
        imageViewCelebrity.setVisibility(View.INVISIBLE);

        DataDownloadTask downloadTask = new DataDownloadTask(this, findViewById(android.R.id.content).getRootView());
        try {
            celebrityArrayList = downloadTask.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (celebrityArrayList.size() == 100) {
            progressBar.setVisibility(View.INVISIBLE);
            imageViewCelebrity.setVisibility(View.VISIBLE);
            for (Button allButton : allButtons) {
                allButton.setVisibility(View.VISIBLE);
            }
            textViewPoints.setVisibility(View.VISIBLE);


        }



    }

    public void endGame() {
        player.setPoints(points);
        players.add(player);



    }

}


