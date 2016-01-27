#Packages importes afin de realiser les fonctions#
import numpy as np
import random
import math
import matplotlib.pyplot as mp
import unittest

import bidiagonale as p2

#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)

Epsilon = 0.000001
Epsilon1 = 0.00001


def mat_identity (n, p):
    Mat = np.eye(n, p)
    Mat = np.asmatrix(Mat)
    return Mat


# Algorithme preliminaire prennant en entree une matrice bidiagonale
def SVD_preliminaire (S, Eps = Epsilon):
    taille_x = S.shape[0]
    taille_y = S.shape[1]
    taille_min = min(taille_x, taille_y)
    U = mat_identity (taille_x, taille_min)
    V = mat_identity (taille_min, taille_y)
    S = S[0:taille_min,0:taille_min]
    BD = S
    sum_carres = [somme_carres(S)]
    sum_carres_diag = (somme_carres_diag(S))
    while (True):
        Q1, R1 = np.linalg.qr(np.transpose(S))
        Q2, R2 = np.linalg.qr(np.transpose(R1))
        S = R2
        U = U * Q2
        V = np.transpose(Q1) * V
        sum_carres.append(somme_carres(S))
        erreur_relative = abs((sum_carres[len(sum_carres) - 2] - sum_carres[len(sum_carres) - 1])) / sum_carres_diag
        if (not(verif_invariant(U, S, V, BD, Epsilon1))):
            print "BUG"
            return 0
        if (erreur_relative < Eps):
            break
    return (U, S, V, sum_carres)

# Algorithme testant l'invariant U*S*V = BD
def verif_invariant (U, S, V, BD, Eps = Epsilon1):
    M = U*S*V
    tailleM = M.shape[0]
    if (np.linalg.norm(M) - np.linalg.norm(BD) > Eps):
        return 0
    return 1

# Algorithmes calculant la somme des carres des termes diagonaux d'une matrice.
def somme_carres_diag (S):
    taille_x = S.shape[0]
    taille_y = S.shape[1]
    sum = 0
    for i in range (taille_x):
        sum = sum + pow(S[i, i] ,2)
    return sum

# Algorithmes calculant la somme des carres des termes extra-diagonaux d'une matrice bidiagonale superieure
def somme_carres (S):
    taille_x = S.shape[0]
    taille_y = S.shape[1]
    sum = 0
    for i in range (taille_x - 1):
        sum = sum + pow(S[i, i + 1] ,2)
    return sum

# Algorithme tracant la convergence d'une matrice bidiagonale S vers une matrice diagonale
def print_convergence_bidiagonale (S):
    (_,_,_,u) = SVD_preliminaire(S, Epsilon)
    mp.plot (np.log(u))
    mp.title('Convergence exponentielle vers une matrice diagonale')
    mp.ylabel('carre des termes extradiagonaux (echelle log)')
    mp.show()



# Algorithme de decomposition QR optimise pour une matrice A bidiagonale.
def QR_optimise (A):
    taille_x = A.shape[0]
    taille_y = A.shape[1]
    taille_max = max(taille_x, taille_y)
    AprimTest = mat_identity(taille_x, taille_y)
    AprimTest[:,:]=A[:,:]
    tQ = mat_identity(taille_x, taille_y)
    for i in range (taille_x - 1):
        (Grot,Aprim,_) = p2.forme_bidiagonale( (AprimTest[i:i+2, i:i+2]))             
        Grot = Grot.T
        G = mat_identity(taille_x, taille_x)
        AprimTest[i:i+2,i:i+2]=Aprim
        G[i:i+2, i:i+2] = Grot
        tQ = G*tQ
    Q = np.transpose(tQ)
    return (Q, AprimTest)
    
# Algorithme permettant de trouver la matrice orthogonale G : 2*2 d'une matrice Q tq G*Q contient un 0 en bas a gauche. (simple resolution de 2 systemes a 2 inconnues) (Finalement inutilisee).
def rotation (Q):
    G = np.zeros([2,2], 'float32')
    G[1,1] = np.sqrt(float(pow(Q[0,0],2)) / (pow(Q[1,0],2) + pow(Q[0,0],2)))
    G[1,0] = (-G[1,1]*Q[1,0])/Q[0,0]
    G[0,1] = np.sqrt(pow(G[1,0],2) / (pow(G[1,1],2) + pow(G[1,0],2)))
    G[0,0] = (-G[0,1]*G[1,1])/G[1,0]
    return G


# Algorithme optimise prennant en entree une matrice bidiagonale
def SVD_optimise (S, Eps = Epsilon):
    taille_x = S.shape[0]
    taille_y = S.shape[1]
    taille_min = min(taille_x, taille_y)
    U = mat_identity (taille_x, taille_min)
    V = mat_identity (taille_min, taille_y)
    S = S[0:taille_min,0:taille_min]
    BD = S
    sum_carres = [somme_carres(S)]
    while (True):
        Q1, R1 = QR_optimise(np.transpose(S))
        Q2, R2 = QR_optimise(np.transpose(R1))
        S = R2
        U = U * Q2
        V = np.transpose(Q1) * V
        sum_carres.append(somme_carres(S))
        erreur_absolue = np.abs(sum_carres[len(sum_carres) - 2] - sum_carres[len(sum_carres) - 1])
        if (not(verif_invariant(U, S, V, BD))):
            print "BUG"
            return 0
        if (erreur_absolue < Eps):
            break
    return (U, S, V, sum_carres)


# Fonction reorganisant les matrices U et S de maniere a ce que les valeurs sur la diagonale de S soient positives et dans l'ordre decroissant.
def organisation (U, S):
    taille_x = S.shape[0]
    taille_y = S.shape[1]
    copieS = S
    copieU = U
    TableauDiag = []
    for i in range (taille_x):
        if (copieS[i,i] < 0):
            copieS[i, i] = - copieS[i, i]
            for j in range (taille_y):
                copieU[j, i] = -copieU[j, i]
        TableauDiag.append(copieS[i, i])
    TableauDiagTrie = list(TableauDiag)
    TableauDiagTrie.sort()
    TableauDiagTrie.reverse()
    for i in range (taille_x):
        copieS[i, i] = TableauDiagTrie[i]
        for j in range (taille_y):
            if (TableauDiagTrie[i] != 0):
                copieU[j, i] = float(float(copieU[j, i]) * (float(TableauDiag[i]) / float(TableauDiagTrie[i])))
            else : 
                copieU[j, i] = 0
    return (copieU, copieS)


