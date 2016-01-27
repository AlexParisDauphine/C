package com.example.alexandrepc.kanji2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ChoiceActivity extends Activity {


    private final static int NB_LVL_KANJI = 3, NB_LVL_HIRAGANA = 5, NB_LVL_KATAKANA = 2;

    //Attributes for level type selection (Hiragana, Katakana or Kanji)
    private TextView tabTextViewType[];
    int nSelectedType = 1;

    //Attributes for level selection
    private TextView tabTextViewLvl[];
    private int nEndLvl = NB_LVL_KANJI, nSelectedLvl = 1;


    //Attibutes for mode selection
    private TextView tabTextViewMode[];
    private int nSelectedMode = 1;


    private static String level;
    private static int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);


        TextView myTextView=(TextView)findViewById(R.id.textViewChoiceTitle);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/harakiri.ttf");
        myTextView.setTypeface(typeFace);


        Button btnMinusType = (Button) findViewById(R.id.buttonMinusType);
        Button btnPlusType = (Button) findViewById(R.id.buttonPlusType);
        tabTextViewType = new TextView[3];
        tabTextViewType[0] = (TextView) findViewById(R.id.textView1Type);
        tabTextViewType[1] = (TextView) findViewById(R.id.textView2Type);
        tabTextViewType[2] = (TextView) findViewById(R.id.textView3Type);

        Button btnMinusLvl = (Button) findViewById(R.id.buttonMinusLvl);
        Button btnPlusLvl = (Button) findViewById(R.id.buttonPlusLvl);
        tabTextViewLvl = new TextView[3];
        tabTextViewLvl[0] = (TextView) findViewById(R.id.textView1Lvl);
        tabTextViewLvl[1] = (TextView) findViewById(R.id.textView2Lvl);
        tabTextViewLvl[2] = (TextView) findViewById(R.id.textView3Lvl);

        Button btnMinusMode = (Button) findViewById(R.id.buttonMinusMode);
        Button btnPlusMode = (Button) findViewById(R.id.buttonPlusMode);
        tabTextViewMode = new TextView[3];
        tabTextViewMode[0] = (TextView) findViewById(R.id.textView1Mode);
        tabTextViewMode[1] = (TextView) findViewById(R.id.textView2Mode);
        tabTextViewMode[2] = (TextView) findViewById(R.id.textView3Mode);

        resetLvl();

        Button buttonPlay = (Button) findViewById(R.id.buttonselect);
        buttonPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch(nSelectedType){
                    case 0:
                        level = "Hiragana" + nSelectedLvl + ".csv";
                        break;
                    case 2:
                        level = "Katakana" + nSelectedLvl + ".csv";
                        break;
                    default:
                        level = "Kanji" + nSelectedLvl + ".csv";
                        break;
                }
                Intent intent;
                switch(nSelectedMode){
                    case 0:
                        mode = 2;
                        intent = new Intent(ChoiceActivity.this, Grid2Activity.class);
                        break;
                    case 2:
                        mode = 3;
                        intent = new Intent(ChoiceActivity.this, Grid3Activity.class);
                        break;
                    default:
                        mode = 1;
                        intent = new Intent(ChoiceActivity.this, Grid1Activity.class);
                        break;
                }
                startActivity(intent);
            }});


        btnMinusType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String tmp = tabTextViewType[0].getText().toString();
                tabTextViewType[0].setText(tabTextViewType[2].getText().toString());
                tabTextViewType[2].setText(tabTextViewType[1].getText().toString());
                tabTextViewType[1].setText(tmp);
                if(nSelectedType==0)
                    nSelectedType = 2;
                else
                    nSelectedType = --nSelectedType%3;
                actualizeSelectors();
            }
        });

        btnPlusType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String tmp = tabTextViewType[0].getText().toString();
                tabTextViewType[0].setText(tabTextViewType[1].getText().toString());
                tabTextViewType[1].setText(tabTextViewType[2].getText().toString());
                tabTextViewType[2].setText(tmp);
                nSelectedType = ++nSelectedType%3;
                actualizeSelectors();
            }
        });

        btnMinusLvl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                decreaseLvl();
            }
        });

        btnPlusLvl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                increaseLvl();
            }
        });

        btnMinusMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(nSelectedType == 1){
                    decreaseMode();
                }
            }});


        btnPlusMode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(nSelectedType == 1){
                    increaseMode();
                }
            }
        });


    }


    private void increaseLvl(){
        if (nSelectedLvl < nEndLvl) {
            nSelectedLvl++;
            tabTextViewLvl[0].setText("Level " + String.valueOf(nSelectedLvl - 1));
            tabTextViewLvl[1].setText("Level " + String.valueOf(nSelectedLvl));
            if(nSelectedLvl+1 > nEndLvl)
                tabTextViewLvl[2].setText("-");
            else
                tabTextViewLvl[2].setText("Level " + String.valueOf(nSelectedLvl + 1));
        }
    }

    private void decreaseLvl(){
        if (nSelectedLvl > 1) {
            nSelectedLvl--;
            if(nSelectedLvl < 2)
                tabTextViewLvl[0].setText("-");
            else
                tabTextViewLvl[0].setText("Level " + String.valueOf(nSelectedLvl - 1));
            tabTextViewLvl[1].setText("Level " + String.valueOf(nSelectedLvl));
            tabTextViewLvl[2].setText("Level " + String.valueOf(nSelectedLvl + 1));
        }
    }

    private void resetLvl(){
        if(nEndLvl == 1) {
            tabTextViewLvl[0].setText("-");
            tabTextViewLvl[1].setText("Level 1");
            tabTextViewLvl[2].setText("-");
            nSelectedLvl = 1;
        }
        else{
            while(nSelectedLvl+1 > nEndLvl)
                decreaseLvl();
        }
    }

    private void increaseMode(){
        String tmp = tabTextViewMode[0].getText().toString();
        tabTextViewMode[0].setText(tabTextViewMode[1].getText().toString());
        tabTextViewMode[1].setText(tabTextViewMode[2].getText().toString());
        tabTextViewMode[2].setText(tmp);
        nSelectedMode = ++nSelectedMode%3;
    }

    private void decreaseMode(){
        String tmp = tabTextViewMode[0].getText().toString();
        tabTextViewMode[0].setText(tabTextViewMode[2].getText().toString());
        tabTextViewMode[2].setText(tabTextViewMode[1].getText().toString());
        tabTextViewMode[1].setText(tmp);
        if (nSelectedMode == 0)
            nSelectedMode=2;
        else
            nSelectedMode = --nSelectedMode%3;
    }


    private void actualizeSelectors() {
        if (nSelectedType == 1) {
            tabTextViewMode[0].setText("Sens");
            tabTextViewMode[1].setText("Phonétique");
            tabTextViewMode[2].setText("Phonétique et sens");
            nEndLvl = NB_LVL_KANJI;
        } else {
            tabTextViewMode[0].setText("-");
            tabTextViewMode[1].setText("Phonétique");
            tabTextViewMode[2].setText("-");
            nSelectedMode = 1;
            if(nSelectedType == 2)
                nEndLvl = NB_LVL_KATAKANA;
            else
                nEndLvl = NB_LVL_HIRAGANA;
        }
        if(nSelectedLvl < nEndLvl)
            tabTextViewLvl[2].setText("Level " + String.valueOf(nSelectedLvl + 1));
        resetLvl();
    }



    public static String getLevel(){
        return level;
    }

    public static int getMode(){
        return mode;
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
