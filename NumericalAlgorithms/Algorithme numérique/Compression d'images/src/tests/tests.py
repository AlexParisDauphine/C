import numpy as np
import random as rd
import numpy.linalg as nl
import matplotlib.pyplot as mp
from mpl_toolkits.mplot3d import Axes3D
import math
import unittest
import time
import matplotlib.pyplot as pl

import test_householder as tp1
import test_bidiagonale as tp2
import test_qr as tp3

Epsilon = 0.000001



#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)


###############################
def test_householder_bidiagonale(n = 100):
    for i in range(n):
        tp2.test_forme_bidiagonale(Epsilon)
    print "test forme bidiagonale : OK"
    for i in range (n):
        tp1.test_generate_x_y(Epsilon)
    print "test generation x, y : OK"
    for i in range (n):
        tp1.test_determine_H(Epsilon)
    print "test determinisation H : OK"
    for i in range (n):
        tp1.test_produit_house_matrix(Epsilon)
    print "test de produit de matrice de Householder : OK"


#### Test de comparaison produit de matrices pour comparer la complexite du produit numpy et celle de Householder.
def compare_time_produit(n = 50):
    tp1.compare_time_produit_h(n)

def test_qr_svd(n = 20):
    tp3.test_QR_optimise(n,10,True)
    tp3.test_SVD(n, 10, True, False) 

###############################
