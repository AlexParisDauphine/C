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

####Test partie 1
###Test question 1

def test_generate_x_y(epsilon):
    n = rd.randint(2,20)
    result = p1.generate_x_y(n)
    #on teste si on a la meme norme
    assert(np.abs(np.linalg.norm(result[0]) - np.linalg.norm(result[1]))<epsilon)

def test_determine_H(epsilon):
    n = rd.randint(2,20)
    result = p1.generate_x_y(n)
    H = p1.determine_H (result[0],result[1])
    Hx = H * result[0]
    #on teste H*x = y
    for i in range(n):
        assert (np.abs(Hx[i] - result[1][i])<epsilon)
    #on teste la symetrie
    for i in range (n):
        for j in range (n):
            assert (np.abs(H[i,j] - (H.T)[i,j]) < epsilon)
    I = np.asmatrix(np.eye(n,n))
    HH = H * H
    #on teste H*H = identity
    for i in range (n):
        for j in range (n):
            assert (np.abs(HH[i,j] - I[i,j]) < epsilon)


###Test question 2
def test_produit_house_matrix(epsilon):
    n = rd.randint(2,20)
    m = rd.randint(2,20)
    result = p1.generate_x_y(n)
    H = p1.determine_H (result[0],result[1])
    U = p1.determine_U (result[0],result[1])
    M = np.asmatrix(np.random.rand(n,m))
    M = 100 * M
    assert (np.allclose(p1.produit_house_matrix(U,M),H*M))


def compare_time_produit_h(n):
    prod_h = np.asmatrix(np.zeros([n-2,1]))
    prod = np.asmatrix(np.zeros([n-2,1]))
    for i in range(0,n-2):
        result = p1.generate_x_y(i+2)
        H = p1.determine_H (result[0],result[1])
        U = p1.determine_U (result[0],result[1])
        M = np.asmatrix(np.random.rand((i+2),(i+2)))
        M = 100 * M
        start_time = time.time()
        A = p1.produit_house_matrix(U,M)
        prod_h[i] = time.time() - start_time
        start_time = time.time()
        A = H*M
        prod[i] = time.time() - start_time
    mp.plot(range(2,n),prod)
    PH = ["produit matrice Householder","produit matrice numpy"]
    mp.plot(range(2,n),prod_h)
    mp.legend(PH)
    pl.show()
