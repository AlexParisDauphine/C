# Import
import os
import sys
import numpy as np
import matplotlib.pyplot as mp
import load_foil as fl

# Constante globale
epsilon = 0.1**2

# Variable globale (chargee a partir de foilload)
(ex, ey, ix, iy) = fl.load_foil('BACNLF.DAT');

# Aile extrados
def extrados(ix, iy):
    extrados_x = [];
    extrados_y = [];
    for i in range(0, ix.size-1):
        if(iy[i]>=0):
            extrados_x.append(ix[i]);
            extrados_y.append(iy[i]);
    return (extrados_x, extrados_y);

# Aile intrados
def intrados(ix, iy):
    intrados_x = [];
    intrados_y = [];
    for i in range(0, ix.size-1):
        if(iy[i]<=0):
            intrados_x.append(ix[i]);
            intrados_y.append(iy[i]);
    return (intrados_x, intrados_y);

# Affiche les points tels qu'ils sont donnes par foilload
def print_points(liste_x, liste_y):
    mp.plot(liste_x, liste_y, '+');
    mp.title("affichage des points originels");
    mp.xlabel("abscisse x");
    mp.ylabel("ordonnee y");
    # mp.savefig("points.png");
    #mp.clf();
    return True;

def print_curve(ex, ey, ix, iy):
    print_points(ex,ey);
    print_points(ix,iy);
    mp.savefig("skeleton.png");
    mp.show();
    return True

# print_points(ix,iy);

#
# Calcul des Splines
#

# Calcule l'ensemble des polynomes de degres trois pour chaque intervalle
def interpolation_splines(liste_x, liste_y, liste_y2):
    liste_splines = [];
    for i in range(0, liste_x.size-1):
        liste_splines.append(interpole_segment(liste_x[i],liste_y[i],liste_x[i+1],liste_y[i+1],liste_y2[i],liste_y2[i+1]));
    return liste_splines;


# Calcule le polynome entre deux points consecutifs
def interpole_segment(x1,y1,x2,y2,d0y1,d0y2):
    def spline(x):
        A = (x2 - x)/(x2 - x1);
        B = (x - x1)/(x2 - x1);
        C = (A**3 - A) * ((x2 - x1)**2) / 6;
        D = (B**3 - B) * ((x2 - x1)**2) / 6;
        return (A * y1) + (B * y2) + (C * d0y1) + (D * d0y2);
    return spline;

# Determine la derivee de ces polynomes
def spline_derive1(liste_x, liste_y, liste_y2):
    liste_spline = [];
    for i in np.arange(0,liste_x.size-1):
        liste_spline.append(polynome_derive1(liste_x[i], liste_x[i+1], liste_y[i], liste_y[i+1], liste_y2[i], liste_y2[i+1]));
    return liste_spline;

# Calcule la derivee d'un polynome de degres trois
def polynome_derive1(x1,x2,y1,y2,d1y1,d1y2):
    def spline_deriv(x):
        A = (x2 - x) / (x2 - x1);
        A2 = -1./(x2 - x1);
        B = (x - x1) / (x2 - x1);
        B2 = 1./(x2 - x1);
        C = C2=((3 * A**2 *A2) - A2)*((x2 - x1)**2) / 6;
        D2= ((3 * B**2 * B2) - B2) * ((x2 - x1)**2) / 6;
        return (A2 * y1) + (B2 * y2) + (C2 * d1y1) + (D2 * d1y2);
    return spline_deriv;

#
# Obtention des derivees secondes
#

# Systeme matriciel tridiagonal permettant de trouver la derivee seconde en chaque points

def syst_tridiag(liste_x,liste_y):
    n = len(liste_x);
    M = np.zeros([n,n]);
    # M represente les coefficients devant les derivees secondes dans la formule du numerical recipes
    N = np.zeros([n,1]);
    # N represente le terme de droite dans la formule du numerical recipes
    M2 = np.zeros([n-2,n-2]);
    N2 = np.zeros([n-2, 1]);

    for i in np.arange(1,n-1):
        N[i,0]=((liste_y[i+1]-liste_y[i])/(liste_x[i+1]-liste_x[i]))-((liste_y[i]-liste_y[i-1])/(liste_x[i]-liste_x[i-1]));
    for i in np.arange(1,n-1):
        M[i,i]=(liste_x[i+1]-liste_x[i-1])/3;
        M[i,i-1]=(liste_x[i]-liste_x[i-1])/6;
        M[i,i+1]=(liste_x[i+1]-liste_x[i])/6;

    for i in np.arange(0,n-2):
        N2[i,0] = N[i+1,0];
        for j in np.arange(0,n-2):
            M2[i,j] = M[i+1,j+1];
    return (M2,N2);

# Permet d'avoir acces aux bornes de l'intervalle lors de la derivation. (on considere que la derivee est nulle aux extremitees)

def cree_bornes(liste_y):
    liste_y_ret = np.zeros([liste_y.size+2, 1]);
    for i in np.arange(0,liste_y.size):
        liste_y_ret[i+1] = liste_y[i];
    return liste_y_ret;

# Fonction permettant d'afficher les splines sur un intervalle discretise sur epsilon

def affiche_spline(liste_x, liste_y, eps = epsilon):
    (M,N) = syst_tridiag(liste_x, liste_y);
    spline = interpolation_splines(liste_x, liste_y, cree_bornes(np.linalg.solve(M,N)));
    X_liste = np.arange(liste_x[0], liste_x[liste_x.size-1], eps);
    Y_liste = [];
    for i in range(0, X_liste.size):
        j = 0;
        while(liste_x[j+1]<X_liste[i]):
            j += 1;
        Y_liste = Y_liste +[spline[j](X_liste[i])[0]];
    mp.plot(liste_x, liste_y, 'ro');
    mp.plot(X_liste, Y_liste, 'b', linewidth=1.0);
    return True;

# Fonction affichant les courbes interpolees a partir des splines

def affiche_total_spline():
    mp.clf();
    mp.xlabel("Valeurs de x");
    mp.ylabel("Valeurs de y");
    affiche_spline(ex, ey);
    affiche_spline(ix, iy);
    mp.show();
    return True;

# Cette fonction ne relie pas le dernier point de la spline. Une fonction qui suit relie TOUS les points de la spline. c'est affichage_spline
# affiche_total_spline()

# Fonctions annexes

def spline_def(liste_x, liste_y):
    (M,N) = syst_tridiag(liste_x, liste_y);
    spline = interpolation_splines(liste_x, liste_y, cree_bornes(np.linalg.solve(M,N)))
    return spline;

def derive_spline_def(liste_x, liste_y):
    (M,N) = syst_tridiag(liste_x, liste_y);
    spline = spline_derive1(liste_x, liste_y, cree_bornes(np.linalg.solve(M,N)));
    return spline;

def spline_odessus (x):
    i = 0;
    while(ex[i+1]<x):
        i = i+1;
    return SPLINE_UP[i](x)[0];

def spline_odessous(x):
    i = 0;
    while(ix[i+1]<x):
        i = i + 1;
    return SPLINE_DOWN[i](x)[0];

def spline_der_odessus(x):
    i = 0;
    while(ex[i+1]<x):
        i = i + 1;
    return SPLINE_UP_DERIVE[i](x)[0];

def spline_der_odessous(x):
    i = 0;
    while(ix[i+1]<x):
        i = i + 1;
    return SPLINE_DOWN_DERIVE[i](x)[0];

SPLINE_UP = spline_def(ex,ey);
SPLINE_DOWN = spline_def(ix, iy);
SPLINE_UP_DERIVE = derive_spline_def(ex, ey);
SPLINE_DOWN_DERIVE = derive_spline_def(ix, iy);

# Fonctions de test

def test_p_sup(T):
    return [spline_odessus(x) for x in T];

def test_der_p_sup(T):
    return [spline_der_odessus(x) for x in T];

def test_p_inf(T):
    return [spline_odessous(x) for x in T];

def test_der_p_inf(T):
    return [spline_der_odessous(x) for x in T];

def affichage_spline(extrados_x,extrados_y, intrados_x, intrados_y, points = True):
    mp.clf();
    if(points):
        mp.plot(extrados_x, extrados_y, '+');
        mp.plot(intrados_x, intrados_y, '+');
    listeX = np.arange(ex[0], ex[ex.size-1], 0.001);
    mp.plot(listeX, test_p_sup(listeX), label = "Courbe aille dessus");
    listeX = np.arange(ix[0], ix[ix.size-1], 0.001);
    mp.plot(listeX, test_p_inf(listeX), label = "Coube aile inferieure");
    mp.show()
    return True

# affichage_spline(ex, ey, ix, iy);

def test_affiche_spline():
    mp.clf();
    listeX = np.arange(ex[0], ex[ex.size-1], 0.001);
    mp.plot(listeX, test_p_sup(listeX), label = "Courbe aille dessus");
    listeX = np.arange(ex[6], ex[ex.size-1],0.001);
    mp.plot(listeX, test_der_p_sup(listeX), label = "Derive de l'aile superieure");
    mp.xlabel("Valeurs de x");
    mp.ylabel("Valeurs de y");
    
    listeX = np.arange(ix[0], ix[ix.size-1], 0.001);
    mp.plot(listeX, test_p_inf(listeX), label = "Coube aile inferieure");
    listeX = np.arange(ix[6], ix[ix.size-1], 0.001);
    mp.plot(listeX, test_der_p_inf(listeX), label = "Derive de l'aile inferieure");
    mp.legend(loc = "upper right");
    mp.show();
    return True

# test_affiche_spline();

# Fonctions permettant de verifier si les fonctions entrees en parametres sont C2
# Remarque importante : cette fonction ne permet de faire que des approximations... Il est possible que cette fonction renvoie True alors que la fonction entree en argument ne l'est pas...

# Fonction derivative a l'ordre 1

def deriv(f,epsilon = epsilon):
    eps=epsilon/2
    def g(x):
        return (f(x+eps)-f(x-eps))/epsilon
    return g

# Fonction testant la continuite C2    

def c2_test(x,y):
    (M,N)=syst_tridiag(x, y)
    spline=interpolation_splines(x,y, cree_bornes(np.linalg.solve(M,N)))
    X = np.arange(x[0], x[x.size-1], 0.0001)
    k=0
    for j in np.arange(0,X.size):
        i=0
        while(x[i+1]<X[j]):
            i=i+1
        if (i!=k):
            if(abs(deriv(deriv(spline[i]))(x[i])- deriv(deriv(spline[i-1]))(x[i]))>0.0001):
                print "La fonction n'est pas C2"                                        
                print i
                print deriv(spline[i])(x[i])
                print deriv(spline[i-1])(x[i])
        k=i
    print "La fonction est C2"
    return 1

# c2_test(ex,ey);
