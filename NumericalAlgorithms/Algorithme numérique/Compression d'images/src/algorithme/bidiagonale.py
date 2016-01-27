import numpy as np
import random as rd
import numpy.linalg as nl
import matplotlib.pyplot as mp
from mpl_toolkits.mplot3d import Axes3D
import math
import unittest
import time
import matplotlib.pyplot as pl

import householder as p1

Epsilon = 0.000001

#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)

###### Algorithme python de la partie 2 ######

def determine_Q_k(v,n,k):
    s = 0.0
    for i in range (n-1,k-1,-1):
        s = s + v[i,0] * v[i,0]
    if v[k,0] < 0:
        t = np.sqrt(s)
    else :
        t = -np.sqrt(s)
    U = np.asmatrix(np.zeros(n)).T
    if (t != 0):
        tmp = 1.0 / (np.sqrt(2*t*(t-v[k,0])))
        U[k,0] = (v[k,0] - t)*tmp
        for i in range((k+1),n):
            U[i,0] = (tmp)*v[i,0]
    return U


#cette fonction permet de calculer la forme bidiagonale pour une matrice de taille m x n
def forme_bidiagonale(A):
    taille = A.shape
    BD = A
    Qleft = np.asmatrix(np.eye(taille[0]))
    Qright = np.asmatrix(np.eye(taille[1]))
    l = taille[1]
    if (taille[0]<taille[1]):
        l = taille[0]
    for k in range (l):
        Q = determine_Q_k(BD[:,k],taille[0],k)
        BD = p1.produit_house_matrix (Q, BD)
        Qleft = p1.produit_house_matrix(Q,Qleft.T)
        Qleft = Qleft.T
        if k < (taille[1] - 2):
            P = determine_Q_k(BD[k,:].T,taille[1],k+1)
            BD = p1.produit_house_matrix(P, BD.T)
            BD = BD.T
            Qright = p1.produit_house_matrix(P,Qright)
    return (Qleft, BD, Qright)


