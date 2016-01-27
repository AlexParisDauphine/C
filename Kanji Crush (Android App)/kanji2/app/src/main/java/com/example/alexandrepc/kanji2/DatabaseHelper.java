package com.example.alexandrepc.kanji2;

/**
 * Classe DatabaseHelper
 */

/**
 * \file      DatabaseHelper.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe permettant de créer une base de données
 *
 * \details   Cette classe permet de créer quatre champs (id, sens, phonétique, kanji) 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "KanjiDB3";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //!création de la table

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_KANJI_TABLE = "CREATE TABLE kanjis3 ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "caractere TEXT," +
                "sens TEXT, "+
                "phonetique TEXT )";
        db.execSQL(CREATE_KANJI_TABLE);
    }
    //!supprimer
    public void onDelete(){
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_KANJI_TABLE = "DELETE from kanjis3";
           db.execSQL(DELETE_KANJI_TABLE);
    }

    //!mettre à jour
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kanjis3");
        this.onCreate(db);
    }

    private static final String TABLE_KANJIS = "kanjis3";
    private static final String KEY_ID = "id";
    private static final String KEY_CARACTERE = "caractere";
    private static final String KEY_SENS = "sens";
    private static final String KEY_PHONETIQUE = "phonetique";

    private static final String[] COLUMNS = {KEY_ID,KEY_CARACTERE,KEY_SENS,KEY_PHONETIQUE};
    
    /**
     * \brief       Fonction ajouter kanji
     * \details     Rajoute un kanji dans la table
     * \param       kanji       kanji à rajouter dans la table
     * \return      void
     */

    public void addKanji(Kanji k){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CARACTERE, k.getCaractere());
        values.put(KEY_SENS, k.getSens());
        values.put(KEY_PHONETIQUE, k.getPhonetique());
        db.insert(TABLE_KANJIS,
                null,
                values);
        db.close();
    }

    /**
     * \brief       Fonction recupérer kanji
     * \details     Récuperer un élément de la table à partir de l'id
     * \param       id     id du kanji à récupérer
     * \return      Kanji
     */

    public Kanji getKanji(int id){
        //!on ouvre la base, et on lit chacune des lignes de la table
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_KANJIS,
                        COLUMNS,
                        " id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);
        //!Parcourt des lignes grace au curseur
        if (cursor != null)
            cursor.moveToFirst();
        Kanji k = new Kanji();
        k.setId(Integer.parseInt(cursor.getString(0)));
        k.setCaractere(cursor.getString(1));
        k.setSens(cursor.getString(2));
        k.setPhonetique(cursor.getString(3));


        return k;
    }

    /**
     * \brief       Fonction recupérer tous les kanji
     * \details     Récupère tous les kanji de la base
     * \return      liste chaînée de Kanji
     */

     public LinkedList<Kanji> getAllKanjis() {
        LinkedList<Kanji> ks = new LinkedList<Kanji>();

        String query = "SELECT  * FROM " + TABLE_KANJIS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Kanji k = null;
        if (cursor.moveToFirst()) {
            do {
                k = new Kanji();
                k.setId(Integer.parseInt(cursor.getString(0)));
                k.setCaractere(cursor.getString(1));
                k.setSens(cursor.getString(2));
                k.setPhonetique(cursor.getString(3));

                ks.add(k);
            } while (cursor.moveToNext());
        }

        return ks;
    }

    /**
     * \brief       Fonction qui parcourt un fichier csv
     * \details     Parcourt un csv en utilisant csv file
     * \param       NomDeFichier     Le nom du fichier qu'on souhaite parcourir
     * \return      void
     */

    public void ParseFile(String fileName){


        String csvFile = "Hiragana1.csv";
        BufferedReader br = null;
        String line = "";
        String cvsLinesSplitedBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Log.d("kaka", "shit");
                // use ; as separator
                String[] Kanji = line.split(cvsLinesSplitedBy);
                Log.d("aaa", "Kanji [kanji= " + Kanji[0]
                        + " , name=" + Kanji[1] + "]");


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
