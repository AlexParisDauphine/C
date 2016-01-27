import integration as p1
import splines as p2
import air_pression as p3
import lagrange as lg
import curve_length as c
def test_p1():
    print "Tests sur la partie d'interpolation\n"
    print "Affichage de l'ensemble de points"
    p2.print_curve(p2.ex, p2.ey, p2.ix, p2.iy);
    print "Affichage de l'approximation par splines de BACNLF.DAT"
    p2.affichage_spline(p2.ex, p2.ey, p2.ix, p2.iy);
    print "Test d'affichage sur les derivees"
    p2.test_affiche_spline()
    print "Test de la fonction verifiant la continuite C2"
    p2.c2_test(p2.ex, p2.ey);
    print "Fin de la fonction de tests sur la partie 1\n";
    return True

def test_p2(infI = -5, supI = 5., value_cons = 1):
    print "Test des methodes d'integration\n"
    print "Test des methodes d'integration sur une fonction constante"
    def ftest_const(value_cons):
        return value_cons;
    print "Rectangles Inf"
    print p1.trapezes_method(ftest_const, -5., 5., 100, True);
    print "Rectangles Sup"
    print p1.trapezes_method(ftest_const, -5., 5., 100, True, True);
    print "Methode des trapezes"
    print p1.integrationTrapez(ftest_const, -5., 5., 0.001, 100);
    print "Methode de Simpson"
    print p1.integrationSimpson(ftest_const, -5., 5., 0.001, 100);
    print "Methode de Boole"
    print p1.bool_rule(ftest_const, -5., 5., 100)
    print "Fin des tests sur fonction constante"
    print "Tests sur fonction generique x:->x2"
    def ftest_gen(x):
        return x**2;
    print "Methode des rectangles"
    print "Rectangles Inf"
    print p1.trapezes_method(ftest_gen, -5., 5., 100, True);
    print "Rectangles Sup"
    print p1.trapezes_method(ftest_gen, -5., 5., 100, True, True);
    print "Methode des trapezes"
    print p1.integrationTrapez(ftest_gen, -5., 5., 0.001, 100);
    print "Methode de Simpson"
    print p1.integrationSimpson(ftest_gen, -5., 5., 0.001, 100);
    print "Methode de Boole"
    print p1.bool_rule(ftest_gen, -5., 5., 100)
    print "Fin des tests sur fonctions generiques"
    
    print "Attention, temps avant affichage : 2 minutes" 
    print "Comparaison du nombre d'iterations sur les methodes de Simpson et des Trapezes"
    p1.showItNumber();
    print "Attention, temps avant affichage : 2 minutes"
    print "Comparaison de l'erreur entre la methode de Simpson et la methode des Trapezes"
    p1.showErr();
    print "Fin des tests sur la partie 2\n"
    return True

# ne placer True que si le temps d'execution n'est pas un probleme
def test_p3(p_temps = False):
    print "Tests sur la partie 3\n"
    print "Affichage de l'action de l'air par pression dynamique sur l'aile"
    p3.plot_fonction(0.056519,0.000077,0.997227)
    if(p_temps == True):
        print "Attention, le temps d'execution peut durer plusieurs minutes (entre 1 min 30 et 2 min)"
        p3.affichage_2(0.056519,0.000077,0.997227,p3.temps)
    print "Affichage propre et rapide (30 sec a 1 minute) de l'action de la pression dynamique sur l'aile"
    p3.affichage_3(0.056519,0.000077,0.997227,p3.temps)
    print "Fin de la fonction de tests sur la partie 3\n"
    return True;

def test_lagrange():
    print "Tests sur l'interpolation de Lagrange"
    print "Affichage de la courbe a partir de l'interpolation de lagrange"
    lg.print_all_lagrange(lg.ix,lg.iy,lg.ex,lg.ey,lg.eps)
    print "Longueur de la courbe issue de l'interpolation de lagrange"
    print c.curve_length(c.ex,c.ey,c.ix,c.iy,c.eps,c.N,"lagrange")
    print "Longueur de la courbe issue de l'interpolation par les splines"
    print c.curve_length(c.ex,c.ey,c.ix,c.iy,c.eps,c.N,"splines")
    print "Retour dans le cas ou la methode d'interpolation n'existe pas"
    print c.curve_length(c.ex,c.ey,c.ix,c.iy,c.eps,c.N,"jhg")
    print "Fin des tests sur l'interpolation par methode de Lagrange"
    return True
