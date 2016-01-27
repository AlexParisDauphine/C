package projet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.lang.Math;
class ro {
	//sort taks
	//int compareTo(Tache);
	//construit le tableau des intervalles 0: dispo 1:occupé

	public int verif (int dateDebut, int duree, boolean intervalle[]){
	    int j = dateDebut;
	    while (j < (dateDebut + duree)){
		if(intervalle[j]){
		    j++;
		    return j;
		}
		j++;
	    }
	    return dateDebut;
	}

	public void insereTache (int dateDebut, int duree, boolean intervalle[]){
	    int j = dateDebut;
	    while (j < (dateDebut + duree)){
		intervalle[j]=true;
		j++;
	    }
	}

    public int cout(ArrayList <Tache> taches){
	int cout = 0;
	Iterator<Tache> iterator= taches.iterator();
	while (iterator.hasNext()){
	    Tache tache = iterator.next();
	    cout+= Math.max(tache.dateDebut + tache.duree - tache.livraison,0);
	}
	return cout;
    }
	

	 public void glouton (ArrayList<Tache> taches){
	    boolean [] intervalle = new boolean[100000]; 
	    Collections.sort(taches);
	    Iterator<Tache> iterator= taches.iterator();
	    while (iterator.hasNext()){
		Tache tache = iterator.next();
		int dateDebut = tache.dispo;
		int j = verif(dateDebut,tache.duree, intervalle);
		while(dateDebut != j){
		    dateDebut = j;
		    j = verif(dateDebut,tache.duree, intervalle);
		}
		tache.dateDebut = j;
		insereTache(dateDebut, tache.duree, intervalle);
	    }
	}
    
}
