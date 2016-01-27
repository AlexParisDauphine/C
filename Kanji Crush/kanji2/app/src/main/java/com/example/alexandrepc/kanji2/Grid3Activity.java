package com.example.alexandrepc.kanji2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Grid3Activity extends  AbstractGrid{

    private int positionListPhonetique; // Position de la phonétique sélectionné dans la listView Phonétique
    private int positionListSens; // Position du sens sélectionné dans la listView Sens
    protected Context currentContext; // Context courant


    /**
     * \brief     Réinitialise la position du kanji courant (redéfinition)
     * \return    Pas de retour
     */
    protected void resetCurrentPosition (){
        super.resetCurrentPosition();
        positionListPhonetique = -1;
        positionListSens = -1;
    }

    /**
     * \brief     Réinitialise les couleur de la phonétique et du sens des listView
     * \return    Pas de retour
     */
    private void resetBackgroundColorList (){
        currentViewPhon.setBackgroundColor(Color.TRANSPARENT);
        currentViewSens.setBackgroundColor(Color.TRANSPARENT);
    }


    /* ONCREATE */

    /**
     * \brief     Methode principale
     * \return    Pas de retour
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positionListPhonetique = -1;
        positionListSens = -1;
        currentContext = this;

        //Création listView phonétique
        List<Map<String, String>> mapListPhon = new ArrayList<>();
        LinkedList<String> listPhon = new LinkedList<>(mainPlateau.getListAllPhonetique());

        for (int i =0; i< listPhon.size(); i++){
            if (!listPhon.get(i).equals("")) {
                mapListPhon.add(createKanji("Kanji", listPhon.get(i)));
            }
        }

        ListView listViewPhonetique = (ListView) findViewById(R.id.list);
        ListSimpleAdapter simpleAdpt = new ListSimpleAdapter(this, mapListPhon, android.R.layout.simple_list_item_2, new String[] {"Kanji"}, new int[] {android.R.id.text1});
        listViewPhonetique.setAdapter(simpleAdpt);

        listViewPhonetique.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v, int position, long id) {
                resetBackgroundColorJoker(tabButtonJoker);
                joker.resetAllJokerBomb();

                positionListPhonetique = position;

                if (isViewPhonSelected)
                    currentViewPhon.setBackgroundColor(Color.TRANSPARENT);
                isViewPhonSelected = true;

                currentViewPhon = v;
                v.setBackgroundColor(Color.rgb(153, 102, 170));

                if (positionKanji != -1 && positionListSens != -1) {

                    String phonOfGrid = currentKanji.getPhonetique();
                    String phonOfList = mainPlateau.getListAllPhonetique().get(positionListPhonetique);

                    String sensOfGrid = currentKanji.getSens();
                    String sensOfList = mainPlateau.getListAllSens().get(positionListSens);

                    final Animation animTranslate = AnimationUtils.loadAnimation(currentContext, R.anim.translate);

                    if (phonOfGrid.compareTo(phonOfList) == 0 && sensOfGrid.compareTo(sensOfList) == 0) {

                        tabButton[positionKanji].startAnimation(animTranslate);

                        classScore.goodAssociation(currentKanji,modeSelected);
                        classScore.combo();
                        classScore.printScoreActuel();
                        resetBackgroundColorGrid(tabButton);
                        mainPlateau.gravity(tabButton, positionKanji, 5);
                        resetCurrentPosition ();
                        resetBackgroundColorList();

                        if (mainPlateau.isEmpty()){
                            gameLost = false;
                            endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                        }
                    } else {

                        resetBackgroundColorGrid(tabButton);
                        classScore.badAssociation();
                        classScore.printScoreActuel();
                        mainPlateau.addRandomKanji(tabButton, nbKanjiAdd);

                        //Animation "tetris"
                        int i = 0;
                        while (i  < mainPlateau.getListCurrentKanjiTransfered().size()){
                            tabButton[mainPlateau.getListCurrentKanjiTransfered().get(i)].startAnimation(animTranslate);
                            i++;
                        }
                        mainPlateau.resetListCurrentKanjiTransfered();


                        resetCurrentPosition ();
                        resetBackgroundColorList();

                        if (mainPlateau.isFull()){
                            gameLost = true;
                            endGame(currentContext, Grid3Activity.this,Grid3Activity.class);

                        }

                    }
                }
            }
        });


        //Création listView Sens
        List<Map<String, String>> mapListSens = new ArrayList<>();
        LinkedList<String> listSens = new LinkedList<>(mainPlateau.getListAllSens());

        for (int i =0; i< listSens.size(); i++){
            if (!listSens.get(i).equals("")) {
                mapListSens.add(createKanji("Kanji", listSens.get(i)));
            }
        }

        ListView listViewSens = (ListView) findViewById(R.id.listView2);
        ListSimpleAdapter simpleAdpt2 = new ListSimpleAdapter(this, mapListSens, android.R.layout.simple_list_item_2, new String[] {"Kanji"}, new int[] {android.R.id.text1});
        listViewSens.setAdapter(simpleAdpt2);


        listViewSens.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View v, int position, long id) {
                resetBackgroundColorJoker(tabButtonJoker);
                joker.resetAllJokerBomb();

                positionListSens = position;

                if (isViewSensSelected)
                    currentViewSens.setBackgroundColor(Color.TRANSPARENT);
                isViewSensSelected = true;

                currentViewSens = v;
                v.setBackgroundColor(Color.rgb(153, 102, 170));

                if (positionKanji != -1 && positionListPhonetique != -1) {

                    String phonOfGrid = currentKanji.getPhonetique();
                    String phonOfList = mainPlateau.getListAllPhonetique().get(positionListPhonetique);

                    String sensOfGrid = currentKanji.getSens();
                    String sensOfList = mainPlateau.getListAllSens().get(positionListSens);

                    final Animation animTranslate = AnimationUtils.loadAnimation(currentContext, R.anim.translate);

                    if (phonOfGrid.compareTo(phonOfList) == 0 && sensOfGrid.compareTo(sensOfList) == 0) {

                        tabButton[positionKanji].startAnimation(animTranslate);

                        classScore.goodAssociation(currentKanji,modeSelected);
                        classScore.combo();
                        classScore.printScoreActuel();
                        resetBackgroundColorGrid(tabButton);
                        mainPlateau.gravity(tabButton, positionKanji, 5);
                        resetCurrentPosition ();
                        resetBackgroundColorList();

                        if (mainPlateau.isEmpty()){
                            gameLost = false;
                            endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                        }
                    } else {

                        resetBackgroundColorGrid(tabButton);
                        classScore.badAssociation();
                        classScore.printScoreActuel();
                        mainPlateau.addRandomKanji(tabButton, nbKanjiAdd);

                        //Animation "tetris"
                        int i = 0;
                        while (i  < mainPlateau.getListCurrentKanjiTransfered().size()){
                            tabButton[mainPlateau.getListCurrentKanjiTransfered().get(i)].startAnimation(animTranslate);
                            i++;
                        }
                        mainPlateau.resetListCurrentKanjiTransfered();

                        resetCurrentPosition ();
                        resetBackgroundColorList();

                        if (mainPlateau.isFull()){
                            gameLost = true;
                            endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                        }

                    }
                }
            }
        });


        mListCaractere.setAdapter(new ButtonAdapter(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //Classe adapteur gérant les bouttons de la grille
    public class ButtonAdapter extends BaseAdapter {
        //position est la position de l'item dans dans l'adaptateur

        private Context context;
        public ButtonAdapter(Context c){

            context = c;
        }
        public int getCount() {

            return mainPlateau.getListCarac().size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }

        //View getView(int position, View convertView, ViewGroup parent) est la plus délicate à utiliser.
        //En fait, cette méthode est appelée à chaque fois qu'un item est affiché à l'écran
        public View getView(int position, View convertView, ViewGroup parent) {
            final Button btn;

            if (convertView == null) {
                //btn = (Button) findViewById(R.id.GridButton);
                btn = new Button(context);
                btn.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (findViewById(R.id.FrameGrid).getHeight()/5.5)));
                btn.setPadding(0, 0, 0, 0);
                btn.setFocusable(false);
                btn.setClickable(false);

            } else {
                btn = (Button) convertView;
            }
            btn.setText(mainPlateau.getListCarac().get(position));
            btn.setBackgroundColor(Color.rgb(0, 0, 0));
            btn.setAlpha((float) 0.7);
            btn.setTextColor(Color.rgb(255, 226, 182));
            btn.setTextSize((float)26);
            //btn.setTextColor(Color.BLACK);
            btn.setId(position);

            final Animation animScale = AnimationUtils.loadAnimation(context, R.anim.scale);
            final Animation animTranslate = AnimationUtils.loadAnimation(context, R.anim.translate);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    resetBackgroundColorGrid(tabButton);

                    if (!btn.getText().equals("")) {

                        currentKanji = mainPlateau.findKanji(btn.getText().toString());
                        positionKanji = v.getId();

                        if (joker.isJokerBomb()){
                            //tabButtonJoker[1].setBackgroundColor(Color.WHITE);
                            //tabButtonJoker[1].setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                            tabButtonJoker[1].setAlpha(0.1F);
                            tabButton[positionKanji].startAnimation(animTranslate);
                            mainPlateau.gravity(tabButton, positionKanji, 5);
                            joker.printAnswer(currentKanji, 1);
                            profile.setJoker_bomb(-1);
                            tabButtonNbJoker[1].setText(Integer.toString(profile.getJoker_bomb()));
                            tabButtonJoker[1].setAlpha(1F);
                            joker.setJokerBomb(false);
                            resetCurrentPosition();
                            if (mainPlateau.isEmpty()) {
                                gameLost = false;
                                endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                            }
                        }else if (joker.isJokerLineBomb()){
                            //tabButtonJoker[4].setBackgroundColor(Color.WHITE);
                            tabButtonJoker[4].setAlpha(0.1F);
                            joker.jokerLineBomb(mainPlateau, positionKanji,5 , tabButton);
                            joker.setJokerLineBomb(false);
                            profile.setJoker_linebomb(-1);
                            tabButtonNbJoker[3].setText(Integer.toString(profile.getJoker_linebomb()));
                            tabButtonJoker[4].setAlpha(1F);

                            //Animation
                            int i = 0;
                            while (i  < joker.getListCurrentKanjiGravity().size()){
                                tabButton[joker.getListCurrentKanjiGravity().get(i)].startAnimation(animTranslate);
                                i++;
                            }
                            joker.resetListCurrentKanjiGravity();
                            resetCurrentPosition();

                            if (mainPlateau.isEmpty()) {
                                gameLost = false;
                                endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                            }
                        }

                        else if (joker.isJokerColumnBomb()){
                            //tabButtonJoker[3].setBackgroundColor(Color.WHITE);
                            tabButtonJoker[3].setAlpha(0.1F);
                            joker.jokerColumnBomb(mainPlateau, positionKanji, 5, tabButton);
                            joker.setJokerColumnBomb(false);
                            profile.setJoker_colbomb(-1);
                            tabButtonNbJoker[4].setText(Integer.toString(profile.getJoker_colbomb()));
                            tabButtonJoker[3].setAlpha(1F);

                            //Animation
                            int i = 0;
                            while (i  < joker.getListCurrentKanjiGravity().size()){
                                tabButton[joker.getListCurrentKanjiGravity().get(i)].startAnimation(animTranslate);
                                i++;
                            }
                            joker.resetListCurrentKanjiGravity();
                            resetCurrentPosition();

                            if (mainPlateau.isEmpty()) {
                                gameLost = false;
                                endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                            }
                        }


                        else {
                            v.startAnimation(animScale);
                            btn.setBackgroundColor(Color.rgb(153, 102, 170));


                            if (positionListPhonetique != -1 && positionListSens != -1) {
                                String phonOfGrid = currentKanji.getPhonetique();
                                String phonOfList = mainPlateau.getListAllPhonetique().get(positionListPhonetique);

                                String sensOfGrid = currentKanji.getSens();
                                String sensOfList = mainPlateau.getListAllSens().get(positionListSens);

                                if (phonOfGrid.compareTo(phonOfList) == 0 && sensOfGrid.compareTo(sensOfList) == 0) {

                                    v.startAnimation(animTranslate);

                                    classScore.goodAssociation(currentKanji, modeSelected);
                                    classScore.combo();
                                    classScore.printScoreActuel();
                                    btn.setBackgroundColor(Color.rgb(0, 0, 0));
                                    mainPlateau.gravity(tabButton, positionKanji, 5);
                                    resetCurrentPosition();
                                    resetBackgroundColorList();

                                    if (mainPlateau.isEmpty()) {
                                        gameLost = false;
                                        endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                                    }
                                } else {

                                    btn.setBackgroundColor(Color.rgb(0, 0, 0));
                                    mainPlateau.addRandomKanji(tabButton, nbKanjiAdd);

                                    //Animation "tetris"
                                    int i = 0;
                                    while (i  < mainPlateau.getListCurrentKanjiTransfered().size()){
                                        tabButton[mainPlateau.getListCurrentKanjiTransfered().get(i)].startAnimation(animTranslate);
                                        i++;
                                    }
                                    mainPlateau.resetListCurrentKanjiTransfered();
                                    classScore.badAssociation();
                                    classScore.printScoreActuel();
                                    resetCurrentPosition();
                                    resetBackgroundColorList();
                                    if (mainPlateau.isFull()) {
                                        gameLost = true;
                                        endGame(currentContext, Grid3Activity.this, Grid3Activity.class);

                                    }
                                }
                            }
                        }
                    } else {
                        positionKanji = -1;
                        currentKanji.setNull();
                    }
                }
            });


            // Refaire les classes adapteurs --> https://milesburton.com/Android_-_Building_a_ListView_with_an_OnClick_Position
            // Pour une raison inconnu position prend plusieurs fois la valeur 0 et on crée donc plusieurs bouttons
            // boolPosition permet de créer un seul boutton pour la valeur position = 0

            if (position == 0) {
                if (boolPosition) {
                    tabButton[position] = new Button(context);
                    tabButton[position] = btn;
                }
            } else {
                tabButton[position] = new Button(context);
                tabButton[position] = btn;
            }

            boolPosition = false;
            tabButton[0].setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (findViewById(R.id.FrameGrid).getHeight()/5.5)));
            return btn;


        }

    }





}
