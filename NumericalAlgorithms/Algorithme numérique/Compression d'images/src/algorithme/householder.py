import numpy as np
import random as rd
import numpy.linalg as nl
import matplotlib.pyplot as mp
from mpl_toolkits.mplot3d import Axes3D
import math
import unittest
import time
import matplotlib.pyplot as pl
Epsilon = 0.000001





#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)


###### Algorithme python de la partie 1 ######
#####Question 1
#genere deux vecteur x et y de taille n et de meme norme


def egalite_vect(x,y):
    n = len(x)
    k = 1
    for i in range (n):
        if (x[i] != y[i]):
            k= 0 * k
    return k

def generate_x_y(n):
    y = np.asmatrix(np.zeros([n,1]))
    x = np.asmatrix(np.zeros([n,1]))
    while (egalite_vect(x,y)):
        x = np.asmatrix(np.random.rand(n,1))
        x = 10 * x
        y = np.asmatrix(np.random.rand(n,1))
        y = 10 * y
    tmp = np.linalg.norm(x)
    x = np.linalg.norm(y) * x
    y = tmp * y
    return (x,y)

def determine_U(x,y):
    x = np.asmatrix(x)
    y = np.asmatrix(y)
    norm = np.linalg.norm(x-y)
    U = np.asmatrix(np.zeros(x.shape[0]))
    U = (x-y)/(norm)
    return U

def determine_H(x,y):
    U = determine_U(x,y)
    I = np.asmatrix(np.eye(x.shape[0],x.shape[0]))
    H = I - 2*(U * U.T)
    return H

#####Question 2

def produit_house_vector(U,x):
    return (x - 2 * (U*(U.T * x)))



def produit_house_matrix(U,M):
    # M est une matice de taille quelconque
    taille = M.shape
    Temp = np.asmatrix(np.zeros([taille[0],taille[1]]))
    for i in range(taille[1]):
        Temp[:,i] = produit_house_vector(U,M[:,i])
    return Temp

