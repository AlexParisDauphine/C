import numpy as np
import matplotlib.pyplot as mp
import random as ra

# Remarque importante : Les fonctions situees au debut du fichier ne sont pas ameliorees. Seules les deux dernieres fonctions permettant d'integrer sont utilisees car elles ont subi une amelioration. (integrationTrapez et integrationSimpson sont les seules fonctions utilisees dans la suite du projet)

# Les differentes methodes pour effectuer une integration

# Integration par la methode des trapezes d'une fonction f sur un intervalle [infI,supI]
def trapezes_method(f,infI, supI, nbstep=100, rectangle = False, sup = False):
    # initialisation de l'aire sous la courbe a 0
    A = 0
    n = float(nbstep)
    for i in np.arange (0,n,1):
        # intervalle d'approximation de l'aire sous la courbe avec une precision de step  
        x1 = infI + (supI - infI) * (i/n)
        x2 = x1 + (supI - infI) / n
        # application de f a x1 et x2 pour determiner le coefficient d'evolution de la courbe
        y1 = f(x1)
        y2 = f(x2)
        # aire sous la courbe, etant sous la forme d'un trapeze (rectangle + triangle)
        if(rectangle == False):
            A = A + (y1 + y2) / 2 * (x2 - x1)
        else:
            if(rectangle == True):
               if(sup == False):
                   A = A + min(y1,y2) * (x2-x1)
               else:
                   if(sup == True):
                       A = A + max(y1,y2) * (x2-x1)
                   else:
                       print "erreur, mauvaise valeur sur l'argument sup"
                       return False
            else:
                print "erreur, mauvaise valeur pour les rectangle"
                return False
    return A

# Fonctions permettant de tester la methode d'integration ci-dessus
def ftest1(x):
    return x**6-3*x**4+2*x

def ftest2(x):
    return 1./x

def ftest3(x):
    return np.sqrt(x)

# print trapezes_method(ftest1, 1., 2., 1)

# Fonction de tests permettant de mettre en lumiere la difference de precision entre chaque etape
def test_trapeze(f, inf, sup,n):
    Xlist = np.arange(1, n, 1)
    Slist = [inf]
    FXlist = []
    Ylist = []
    for i in np.arange(1,n,1):
        Slist.append(Slist[i-1] + (sup - inf) / n)
    for i in np.arange(0,n,1):
        FXlist.append(f(Slist[i]))
    result = np.trapz(FXlist, Xlist)
    for i in Xlist:
        Ylist.append(result - trapezes_method(f, inf, sup, i))
    mp.plot(Xlist, Ylist)
    mp.show()
    return True

# test_trapeze(ftest1, 1., 2, 100)

def trapeze_numpy(f, inf, sup, n):
    Xlist = np.arange(0, n, 1)
    Slist = [inf]
    FXlist = []
    for i in np.arange(1,n,1):
        Slist.append(Slist[i-1] + (sup - inf) / n)
    for i in Xlist:
        FXlist.append(f(Slist[i]))
    return np.trapz(FXlist, Xlist)
    
# trapeze_numpy(ftest1, 2., 1., 100)

# Methode d'integration par regle de Simpson d'une fonction f sur un intervalle I = [infI, supI]
def simpson_method(f, infI, supI, nbstep = 100):
    A = 0
    n = float(nbstep)
    for i in np.arange(0, n, 1):
        # Mise en place d'une decoupe de l'intervalle en n segments
        x1 = infI + (supI - infI) * (i/n)
        x2 = x1 + (supI - infI) / n
        xmoy = (x2 + x1)/2.
        # Mise en place de l'approximation pour le calcul de l'aire
        y1 = f(x1)
        y2 = f(x2)
        ymoy = f(xmoy)
        # Aire sous la courbe entre infI et x2 (par approximation polynomiale)
        A = A + (((x2 - x1)/6.)*(y1+4*ymoy+y2))
    return A
# print simpson_method(ftest1, 1.,2. )
# print simpson_method(ftest2, 1., 2.);

##### Partie Amelioration du code et de la complexite #####

# Fonction effectuant l'integration par methode des trapezes en reduisant le nombre d'iterations. Elle renvoie la valeur de l'integrale ainsi que le nombre d'iterations 
def integrationTrapez(f,a,b,eps,N):
    # f fonction a integrer entre a et b
    # avec un maximum de N iterations et eps pour precision
    V = np.array([f(a),f((a+b)/2.),f(b)])
    it = 2
    somme = (b-a)*(V[0]+V[1]+V[2])/4.
    oldsomme = somme
    err = 1
    h = abs(b-a)/2.
    while (err > eps and it < N):
        h /= 2.
        somme = 0
        n = np.size(V)
        tmp = np.zeros(shape=(2*n-1))
        for i in range(n-1):
            tmp[2*i] = V[i]
            tmp[1+2*i] = f(a+h*(i*2+1))
        tmp[2*n-2] = V[n-1]
        V = tmp
        for i in range(2*(n-1)):
            somme += (V[i]+V[i+1])
        somme = somme * h /2.
        err = abs(oldsomme-somme)
        oldsomme = somme
        it += 1
    return somme,it

# Fonction effectuant l'integration par methode de Simpson en reduisant le nombre d'iterations. Elle renvoie la valeur de l'integrale ainsi que le nombre d'iterations.
def integrationSimpson(f,a,b,eps,N):
    # f fonction a integrer entre a et b
    # avec un maximum de N iterations et eps pour precision
    V = np.array([f(a),f((a+b)/2.),f(b)])
    it = 1
    somme = (b-a)*(V[0]+4*V[1]+V[2])/6.
    oldsomme = somme
    err = 1
    h = abs(b-a)
    while (err > eps and it < N):
        h /= 2.
        somme = 0
        n = np.size(V)
        tmp = np.zeros(shape=(2*n-1))
        for i in range(n-1):
            tmp[2*i] = V[i]
            tmp[1+2*i] = f(a+h*(i+0.5))
        tmp[2*n-2] = V[n-1]
        V = tmp
        for i in range(n-1):
            somme += (V[i*2]+4*V[i*2+1]+V[i*2+2])
        somme = somme * h /6.
        err = abs(oldsomme-somme)
        oldsomme = somme
        it += 1
    return somme,it

# Fonction renvoyant le nombre d'iterations par l'integration des trapezes et par la methode d'integration de Simpson
def showItNumber():
    listX = np.zeros(11)
    listYS = np.zeros(11)
    listYT = np.zeros(11)
    for i in range(11):
        listX[i] = i+5
        listYT[i] += integrationTrapez(ftest1, 1, 2, pow(10,-(5+i)), 22)[1]
        listYT[i] += integrationTrapez(ftest2, 1, 2, pow(10,-(5+i)), 22)[1]
        listYT[i] += integrationTrapez(ftest3, 1, 2, pow(10,-(5+i)), 22)[1]
        listYT[i] /= 3.
        listYS[i] += integrationSimpson(ftest1, 1, 2, pow(10,-(5+i)), 22)[1]
        listYS[i] += integrationSimpson(ftest2, 1, 2, pow(10,-(5+i)), 22)[1]
        listYS[i] += integrationSimpson(ftest3, 1, 2, pow(10,-(5+i)), 22)[1]
        listYS[i] /= 3.
    mp.plot(listX,listYS,label="Simpson")
    mp.plot(listX,listYT,label="Trapeze")
    mp.legend(loc='upper left')
    mp.show()

# showItNumber();

# Fonction renvoyant l'erreur entre la methode de Simpson et la methode des trapezes.
def showErr():
    res1 = 89./35
    res2 = np.log(2)
    res3 = (pow(2,1.5)-1)*2/3.
    listX = np.zeros(10)
    listYS = np.zeros(10)
    listYT = np.zeros(10)
    for i in range(10):
        listX[i] = i+6
        listYT[i] += abs(integrationTrapez(ftest1, 1, 2, pow(10,-(6+i)), 22)[0] - res1)/res1
        listYT[i] += abs(integrationTrapez(ftest2, 1, 2, pow(10,-(6+i)), 22)[0] - res2)/res2
        listYT[i] += abs(integrationTrapez(ftest3, 1, 2, pow(10,-(6+i)), 22)[0] - res3)/res3
        listYT[i] /= 3.
        listYS[i] += abs(integrationSimpson(ftest1, 1, 2, pow(10,-(6+i)), 22)[0] - res1)/res1
        listYS[i] += abs(integrationSimpson(ftest2, 1, 2, pow(10,-(6+i)), 22)[0] - res2)/res2
        listYS[i] += abs(integrationSimpson(ftest3, 1, 2, pow(10,-(6+i)), 22)[0] - res3)/res3
        listYS[i] /= 3.
    mp.plot(listX,listYS,label="Simpson")
    mp.plot(listX,listYT,label="Trapeze")
    mp.legend(loc='upper right')
    mp.show()

# showErr();

# Fonction annexe

# Fonction permettant de choisir une methode a partir d'une chaine de caractere entree en parametre
def integration(f,a,b,eps,N,method_name):
    if(method_name == "trapez"):
        return integrationTrapez(f,a,b,eps,N)
    elif(method_name == "simpson"):
        return integrationSimpson(f,a,b,eps,N)
    else:
        print "Unknown method"
        return 0
