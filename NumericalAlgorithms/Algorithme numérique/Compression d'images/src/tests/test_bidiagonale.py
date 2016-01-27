import numpy as np
import random as rd
import numpy.linalg as nl
import matplotlib.pyplot as mp
from mpl_toolkits.mplot3d import Axes3D
import math
import unittest
import time
import matplotlib.pyplot as pl

import bidiagonale as p2

Epsilon = 0.000001


#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)


####Test partie 2
#Cette fonction permet de verifier qu'une matrice est bidiagonale
def test_0_bidiagonale(M,epsilon):
    taille = M.shape
    A = M
    for i in range (0,taille[0]):
        for j in range(0,taille[1]):
            if((i!= j) and (j != i+1)):
                assert(np.abs(M[i,j])<epsilon)

#Cette fonction verifie la decomposition A = Qleft * BD * Qright
def test_forme_bidiagonale(epsilon):
    n = rd.randint(1,20)
    m = rd.randint(1,20)
    M = np.asmatrix(np.random.rand(n,m))
    M = 100 * M
    result = p2.forme_bidiagonale (M)
    test_0_bidiagonale(result[1],epsilon)
    for i in range (n):
        for j in range (m):
            assert (np.abs(M[i,j] - (result[0]*result[1]*result[2])[i,j])<epsilon)




################################
