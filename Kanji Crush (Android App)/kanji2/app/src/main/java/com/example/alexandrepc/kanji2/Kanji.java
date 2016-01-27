package com.example.alexandrepc.kanji2;


/**
 * Classe Kanji
 */

/**
 * \file      Kanji.java
 * \version   1.0
 * \date      29/03/2015
 * \brief     Classe représentant un Kanji
 *
 * \details   Cette classe permet de manipuler et créer un Kanji.
 */

public class Kanji {
        private int id;
        private String caractere;
        private String sens;
        private String phonetique;


    /**
     * \brief       Constructeur par défaut
     * \details    Mets les attributs caractere, sens et phonetique à ""
     */
       public Kanji(){ //!Correspond à un kanji vide, pour représenter une case vide
            this.caractere = "";
            this.sens = "";
            this.phonetique = "";
        }

    /**
     * \brief       Constructeur avec paramètres
     * \details     Mets les attributs caractere, sens et phonetique
     *              aux valeurs mises en paramètre.
     * \param    caractere          caractere du Kanji
     * \param    phonetique         phonetique du Kanji
     * \param    sens               sens du Kanji
     */
        public Kanji (String caractere, String phonetique, String sens){
            this.caractere = caractere;
            this.sens = sens;
            this.phonetique = phonetique;
        }

    /**
     * \brief       Constructeur par recopie.
     * \details     Crée un nouveau Kanji à partir de celui mis en paramètre.
     * \param       kanji        Kanji à recopier.
     */
        public Kanji(Kanji kanji){
            this.caractere = kanji.caractere;
            this.sens = kanji.sens;
            this.phonetique = kanji.phonetique;
        }


        /* MIS A JOUR DU KANJI */

    /**
     * \brief       Vide le Kanji
     * \details     Mets tous les attributs du Kanji à ""
     */
        public void setNull(){
            this.caractere = "";
            this.sens = "";
            this.phonetique = "";
        }

    /**
     * \brief       Recopie le Kanji
     * \details     Recopie les attributs du kanji mis en paramètre dans le Kanji courant
     * \param       kanji         Kanji à recopier
     * \return      void
     */
        public void copyValue(Kanji kanji){
            this.caractere = kanji.caractere;
            this.sens = kanji.sens;
            this.phonetique = kanji.phonetique;
        }

    /**
     * \brief       Teste si le Kanji est vide
     * \details     Teste si le Kanji est vide en testant si l'arguement caractère est égal à ""
     * \return      True si le Kanji est vide, false s'il ne l'est pas.
     */
        public boolean isNull(){
            return caractere.equals("");
        }

    /**
     * \brief       Teste une égalité de Kanji
     * \details     Teste si le Kanji mis en paramètre est égal au Kanji en testant l'égalité
     *              des caractères.
     * \param       kj         Kanji à comparer
     * \return      True s'ils sont égaux, false sinon
     */
        public boolean isEqual(Kanji kj){
            return this.caractere.equals(kj.getCaractere());
        }

       /* GETTERS & SETTERS  */

    /**
     * \brief       Setter de l'attribut id
     * \details     Mets l'attribut id à la valeur id mis en paramètre
     * \param       id         id à mettre au Kanji
     * \return      void
     */
        public void setId (int id){
            this.id = id;
        }

    /**
     * \brief       Setter de l'attribut caractere
     * \details     Mets l'attribut caractere à la valeur caractere mis en paramètre
     * \param       id         id à mettre au Kanji
     * \return      void
     */
        public void setCaractere (String caractere){
            this.caractere = caractere;
        }

    /**
     * \brief       Setter de l'attribut sens
     * \details     Mets l'attribut sens à la valeur sens mis en paramètre
     * \param       sens         sens à mettre au Kanji
     * \return      void
     */
        public void setSens (String sens){
            this.sens = sens;
        }

    /**
     * \brief       Setter de l'attribut phonetique
     * \details     Mets l'attribut phonetique à la valeur phonetique mis en paramètre
     * \param       phonetique         phonetique à mettre au Kanji
     * \return      void
     */
        public void setPhonetique (String phonetique){
            this.phonetique = phonetique;
        }

    /**
     * \brief       Getter de l'attribut id
     * \details     Renvoi l'id du Kanji
     * \return      Un entier id correspondant à l'id du Kanji
     */
        public int getId() {
            return id;
        }

    /**
     * \brief       Getter de l'attribut id
     * \details     Renvois le caractere correspondant au Kanji
     * \return      Un string correspondant au caractere du Kanji
     */
        public String getCaractere() {
            return caractere;
        }

    /**
     * \brief       Getter de l'attribut sens
     * \details     Renvois le sens du Kanji
     * \return      Un string correspondant au sens du Kanji
     */
        public String getSens() {
            return sens;
        }

    /**
     * \brief       Getter de l'attribut phonetique
     * \details     Renvois la phonetique du Kanji
     * \return      Un string correspondant à la phonetique du Kanji
     */
        public String getPhonetique() {
               return phonetique;
        }


    /**
     * \brief       Methode toString() surchargé pour les kanjis
     * \details     Renvois les attributs du kanji dans un String
     * \return      Un String donnant : id + caractere + sens + phonetique
     */
        @Override
        public String toString() {
            return "Kanji [id=" + id + "caractere=" + caractere + ", sens=" + sens + ", phonetique=" + phonetique
                    + "]";
        }
}
