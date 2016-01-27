package projet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
import java.lang.Math;
class ro2 {
	
	
	public Tache2 tache2(int t, ArrayList<Tache2> taches){
		int dureeMin = 100000;
		int livraisonMin = 100000; 
		Tache2 result = new Tache2();	
		Iterator <Tache2> iterator = taches.iterator();
		while(iterator.hasNext()){
			Tache2 tache = iterator.next();
			if(tache.dispo <= t && tache.duree <= dureeMin){
			    if (tache.duree == dureeMin){
				if(tache.livraison <= livraisonMin){
				    result = tache;	
				    dureeMin = tache.duree;
				    livraisonMin = tache.livraison;
				}
			    }
			    else{
				 result = tache;	
				 dureeMin = tache.duree;
				 livraisonMin = tache.livraison;
			    }
			}
		}
		return result;
	}
  
  
  public int [] glouton2 (ArrayList<Tache2> taches){
	  int t = 0;
	  int []intervalle = new int [100000];
	  while (!taches.isEmpty()){
		Tache2 tacheEnCours = tache2(t, taches);
		intervalle[t] = tacheEnCours.indice;
		tacheEnCours.duree --;
		if(tacheEnCours.duree <= 0){
			taches.remove(tacheEnCours);
		}
		t++;
	}
	return intervalle;
}

    public int cout(int[] solution, ArrayList<Tache2> taches){
	int []arrets = new int[taches.size()];
	Integer coutTotal = 0;
	Iterator <Tache2> iterator = taches.iterator();
	for(int i = 0; i <100000;i++){
	    if(solution[i]>0)
		arrets[solution[i] - 1] = i;
	}
	ArrayList<Integer> d_i = new ArrayList<Integer>();
	ArrayList<Integer> C_i= new ArrayList<Integer>();
	while(iterator.hasNext()){
	    Tache2 tache = iterator.next();	
	    d_i.add(tache.livraison);
	}
	for (int i=0;i<taches.size();i++){
	    C_i.add(arrets[i]);
	}
	Collections.sort(d_i);
	Collections.sort(C_i);
	for (int i = 0; i < taches.size();i++){
	    coutTotal += Math.max(C_i.get(i) - d_i.get(i) + 1,0);
	}
	
	return coutTotal;
    }
	
public void print(int []entier, int l){
	for(int i =0; i <  l; i++){
		
		System.out.print("|t =" + i + ", tache n°" + entier[i] + "|");
	
}
System.out.println("");
}
}
		
	

