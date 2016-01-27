package projet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

class main {    

    public static void main(String[] args){
	ArrayList <Tache> taches = new ArrayList<Tache>();
	Tache tache1 = new Tache(34, 82, 100, 1);
	Tache tache2 = new Tache(23, 146, 108, 2);
	Tache tache3 = new Tache(100, 123, 215, 3);
	taches.add(tache2);
	taches.add(tache1);
	taches.add(tache3);
	ro r = new ro();
	r.glouton(taches);
	System.out.println(taches.toString());
	System.out.println("Borne primale :" + r.cout(taches));
    }
}
