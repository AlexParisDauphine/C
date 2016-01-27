package projet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;


    public class  Tache implements Comparable {
	public int dateDebut;
	public int duree;
	public int dispo;
	public int livraison;
	public int indice;
    
	public Tache (int duree, int dispo, int livraison, int indice){
	    this.dateDebut = -1;
	    this.duree = duree;
	    this.dispo = dispo;
	    this.livraison =livraison;
	    this.indice = indice;
	    
	}
	public int compareTo(Object tache){
	    Tache t = (Tache) tache;
	    if(this.duree < t.duree) {
		return -1;
	    }
	    else if (this.duree == t.duree) {
		return 0;
	    }
	    else 
		return 1;
	}
		public String toString(){
		    return ("tache n°" + this.indice +"  dateDebut : " + this.dateDebut + "\n");
	    }
    }
