import numpy as np;
import matplotlib.pyplot as mp;


def rp(x,p):
    """Calcule la representation approximative 
    d'un nombre flottant x sur p decimales"""
    numSize = np.floor(np.log10(np.abs(x)))+1
    left = np.floor(x*pow(10, p-numSize))
    right = (x*pow(10, p-numSize))-left
    if right >= 0.5:
        left = left + 1
    return left/(pow(10, p-numSize))


def summ(x, y, p):
    """Calcule la representation approximative 
    de la somme de 2 nombres flottants x et 
    y sur p decimales"""
    return rp(x+y,p)


def mult(x, y, p):
    """Calcule la representation approximative 
    du produit de 2 nombres flottants x et y 
    sur p decimales"""
    return rp(x*y,p)


def SummError(x, y, p):
    """Calcule l'erreur relative qu'il y a entre 
    une somme et sa representation approximative"""
    if(y == -x):
        return 0
    return np.abs((x+y)-summ(x,y,p))/np.abs(x+y);


def MultError(x,y,p):
    """Calcule l'erreur relative qu'il y a entre 
    un produit et sa representation approximative"""
    if(x == 0 or y == 0):
        return 0
    return np.abs((x*y)-mult(x,y,p))/np.abs(x*y);

def draw(l,t):
    """s = []"""
    k = []
    for j in l:
        for i in t:
            k.append(SummError(j,i,3))
        
        mp.plot(t,k)
        """s = []"""
        k = []
    mp.legend(("x=1", "x=2", "x=3"), 'best')
    mp.show()
x=[1,2,3]
y= np.arange(-4,0,0.01)
def log2_1(p):
    """Premiere implementation du calcul de log(2) 
    sur p decimales"""
    tmp = 1.0
    summ = 1.0
    err = 0.0
    i = 2
    while(np.abs((summ - np.log(2))) > pow(10.0, -(p+1))):
        tmp = summ
        summ = summ + (pow(-1,i+1)/(1.0*i))
        i = i + 1 
        err = err + SummError(tmp,summ,p)
    return (summ, err)
    
def log2_2(p):
    """Seconde implementation du calcul de log(2) 
    sur p decimales, en separant nombres positifs 
    et negatifs"""
    tmp = 0
    summ_odd = 0
    summ_even = 0
    err_odd = 0
    err_even = 0
    i = 1
    while (np.abs(((summ_even + summ_odd) - np.log(2))) > pow(10, -(p+1))):
        if (i % 2):
            tmp = summ_odd
            summ_odd = summ_odd + 1.0/(1.0 * i)
            err_odd = err_odd + SummError(tmp,summ_odd,p)
        else:
            tmp = summ_even
            summ_even = summ_even - 1.0/(1.0 * i)
            err_even = err_even + SummError(tmp,summ_even,p)
        i = i + 1
    summ = summ_odd + summ_even
    err = err_even + err_odd
    return (summ, err) 

