package projet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collections;
class main {    

    public static void main(String[] args){
	ArrayList <Tache2> taches = new ArrayList<Tache2>();
	Tache2 tache1 = new Tache2(3, 0, 5, 1);
	Tache2 tache2 = new Tache2(4,1,6, 2);
	Tache2 tache3 = new Tache2(1,3,8,3);
	taches.add(tache2);
	taches.add(tache1);
	taches.add(tache3);
	ArrayList <Tache2> taches2 = new ArrayList(taches);
	ro2 rop2 = new ro2();
	int [] resultat = rop2.glouton2(taches);
	rop2.print(resultat,12);
	System.out.println("Borne duale :" + rop2.cout(resultat, taches2));
	}
}
