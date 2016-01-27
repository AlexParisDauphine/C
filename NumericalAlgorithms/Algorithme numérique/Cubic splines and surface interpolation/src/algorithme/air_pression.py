import os
import sys
import numpy as np
import matplotlib.pyplot as mp
import load_foil as fl
import integration as p1
import splines as p2

def affecte_f(f,Vector):
    return [f(x) for x in Vector];

def airflow_family(lmbda, hmax, fonction, up=True):
    def f_lambda(x):
        if(up == True):
            return (1-lmbda)*fonction(x)+3*lmbda*hmax;
        elif (up == False):
            return (1-lmbda)*fonction(x)-3*lmbda*hmax;
        else:
            return False;
    return f_lambda;

def f(x):
    return -x*x+10;

def plot_fonction(hmax, infI, supI):
    x = np.linspace(infI, supI, 100);
    mp.plot(x, affecte_f(p2.spline_odessus,x), 'k', linewidth = 4);
    mp.plot(x, affecte_f(p2.spline_odessous, x), 'k', linewidth = 4);
    
    for i in np.arange(0.005, 3*hmax, 0.005):
        f_odessus = airflow_family(i/(3*hmax), hmax,p2.spline_odessus);
        f_odessous = airflow_family(i/(3*hmax), hmax,p2.spline_odessous, False);
        
        mp.plot(x, affecte_f(f_odessus,x), 'r');
        mp.plot(x, affecte_f(f_odessous,x), 'g');
    mp.savefig("BACNFLcolors2.png");
    mp.show();

# plot_fonction(0.056519,0.000077,0.997227)

def creer_int_dessus(hmax, i):
    def f_integrate(x):
        return np.sqrt(1+(1-i/(3*hmax))*p2.spline_der_odessus(x)*p2.spline_der_odessus(x));
    return f_integrate;

def creer_int_dessous(hmax, i):
    def f_integrate(x):
        return np.sqrt(1+(1-i/(3*hmax))*p2.spline_der_odessous(x)*p2.spline_der_odessous(x));
    return f_integrate;

def affichage_2(hmax,infI,supI,temps):
    # Initialisation
    x=np.linspace(infI,supI,100);
    mp.plot(x,affecte_f(p2.spline_odessus,x),'k',linewidth=4);
    mp.plot(x,affecte_f(p2.spline_odessous,x),'k',linewidth=4);

    for i in np.arange(0.003,3*hmax,0.003):
        # Mise en place des coordonnees en vue de faire un plot
        f_up=airflow_family(i/(3*hmax),hmax,p2.spline_odessus);
        f_down=airflow_family(i/(3*hmax),hmax,p2.spline_odessous,False);
        f=airflow_family(1,hmax,p2.spline_odessus);

        fct_a_int_up=creer_int_dessus(hmax,i);
        fct_a_int_down=creer_int_dessous(hmax,i);
        fct_f=creer_int_dessus(hmax,3*hmax);
        # Partie integration
        l_up=p1.integrationSimpson(fct_a_int_up,infI,supI,1e-3,20)[0];
        l_down=p1.integrationSimpson(fct_a_int_down,infI,supI,1e-3,20)[0];
        l=p1.integrationSimpson(fct_f,infI,supI,1e-3,20)[0];
        # Partie force de Pression dynamique
        P_up=0.5*(l_up*l_up/(temps*temps));
        P_down=0.5*(l_down*l_down/(temps*temps));
        P=0.5*(l*l/(temps*temps));
        if (i==0.003) :
            if (P_up > P_down):
                Pmax=P_up;
            else :
                Pmax=P_down;
        if (P_up>Pmax):
            Pmax=P_up;
        # Affichage des courbes
        mp.plot(x,affecte_f(f_up,x),linewidth=3,color=((P_up-P)/(Pmax-P),0.0,0.0));
        mp.plot(x,affecte_f(f_down,x),linewidth=3,color=((P_down-P)/(Pmax-P),0.0,0.0));
    mp.savefig("BACNFLcolors1.png");
    mp.show();

# Affichage beaucoup trop long et pas assez explicite. voir plus bas pour le bon affichage
# affichage_2(0.056519,0.000077,0.997227,3);

def affichage_3(hmax,infI,supI,temps):
    
    x=np.linspace(infI,supI,100)
    mp.plot(x,affecte_f(p2.spline_odessus,x),'k',linewidth=4)
    mp.plot(x,affecte_f(p2.spline_odessous,x),'k',linewidth=4)
    
    def returnColor(nb,nbmax):
        #l'ajustement par une racine n-ieme de x
        #aurait permis de renforcer les couleurs proches du maximum
        #mais cela n'est pas assez visible
        if (nb < (nbmax/3.)):
            x = (3.*nb)/nbmax
            #print x
            return (pow(x,1.), 0., 0.)
        elif (nb < ((2. * nbmax)/3.)):
            x = (3.*nb-nbmax)/nbmax
            #print x
            return (1.,pow(x,1.), 0.)
        else :
            x = (3.*nb-2.*nbmax)/nbmax
            #print x
            return (1., 1., pow(x,1.))

#pression dynamique de reference :
    P = 0.5*(supI-infI)**2./temps**2
    ite = 0

    for i in np.arange(0.003,3*hmax,0.003):
        ite += 1
        f_up=airflow_family(i/(3.*hmax),hmax,p2.spline_odessus)
        f_down=airflow_family(i/(3.*hmax),hmax,p2.spline_odessous,False)
        f=airflow_family(1,hmax,p2.spline_odessus)
        
        fct_a_int_up=creer_int_dessus(hmax,i)
        fct_a_int_down=creer_int_dessous(hmax,i)
        #fct_f=creer_int_dessus(3*hmax,hmax)

        
        l_up=p1.integrationSimpson(fct_a_int_up,infI,supI,1e-3,20)[0]
        l_down=p1.integrationSimpson(fct_a_int_down,infI,supI,1e-3,20)[0]
        #l=p1.integrationSimpson(fct_f,infI,supI,1e-3,20)[0]
   
        P_up=0.5*(l_up*l_up/(temps*temps))
        P_down=0.5*(l_down*l_down/(temps*temps))
        #P=0.5*(l*l/(temps*temps))
        if (i==0.003) :
                Pmax=P_up


        mp.plot(x,affecte_f(f_up,x),linewidth=3,color=returnColor(P_up-P,Pmax-P))
        mp.plot(x,affecte_f(f_down,x),linewidth=3,color=returnColor(P_down-P,Pmax-P))
        print "Calcul de la ligne : %d fait" %(ite)
	# difference de la longueur entre la courbe du haut sa jumelle du bas
        print "Difference de longueur X 1e10 : %d" %(abs(l_up-l_down)*1e10)
    mp.savefig("BACNLFcolors.png");
    mp.show()

temps = 0.01        
# affichage_3(0.056519,0.000077,0.997227,temps);
