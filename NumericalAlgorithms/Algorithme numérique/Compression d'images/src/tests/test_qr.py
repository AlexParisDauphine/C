import numpy as np
import scipy as sc
import random as ra
import qr as p3
import bidiagonale as p2
import matplotlib.pyplot as mp

# Fonction permettant de creer une matrice carree aleatoire
def rand_square_Matrix(n, mini, maxi):
    M = np.zeros([n,n])
    for i in range(0,n):
        for j in range(0,n):
            M[i,j]=ra.randint(mini, maxi)
    return np.asmatrix(M)

# Fonction de test pour determiner si la decomposition svd fonctionne. n est l'argument permettant de donner le nombre de matrices sur lequel le test est effectue
def test_SVD (n, len_matrix, activate_plot = False, optimise = False):
    if(n <= 0):
        print("Erreur, veuillez entrer un nombre de test strictement positif\n")
        return "FAILED"
    else :
        Matrix_list = []
        Compare_list_S = []
        Compare_list_V = []
        Compare_list_U = []
        x = range(0,n)
        for i in range(0,n):
            Matrix_list.append(p2.forme_bidiagonale(rand_square_Matrix(len_matrix, 0,100))[2])
        if (optimise == False):
            for i in range(0,n):
                Compare_list_U.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[0]) - (p3.SVD_preliminaire(Matrix_list[i])[0])))
                Compare_list_S.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[1]) - (p3.SVD_preliminaire(Matrix_list[i])[1])))
                Compare_list_V.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[2]) - (p3.SVD_preliminaire(Matrix_list[i])[2])))
            if(activate_plot == True):
                mp.plot(x,Compare_list_U)
                mp.title("Comparaison entre U implemente et celui du svd de numpy")
                mp.show()
                mp.plot(x,Compare_list_S)
                mp.title("Comparaison entre S implemente et celui du svd de numpy")
                mp.show()
                mp.plot(x,Compare_list_V)
                mp.title("Comparaison entre V implemente et celui du svd de numpy")
                mp.show()
        else : 
            Compare_list_U.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[0]) - (SVD_optimise(Matrix_list[i])[0])))
            Compare_list_S.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[1]) - (SVD_optimise(Matrix_list[i])[1])))
            Compare_list_V.append(np.linalg.norm((np.linalg.svd(Matrix_list[i])[2]) - (SVD_optimise(Matrix_list[i])[2])))
            if(activate_plot == True):
                mp.plot(x,Compare_list_U)
                mp.title("Comparaison entre U implemente et celui du svd de numpy")
                mp.show()
                mp.plot(x,Compare_list_S)
                mp.title("Comparaison entre S implemente et celui du svd de numpy")
                mp.show()
                mp.plot(x,Compare_list_V)
                mp.title("Comparaison entre V implemente et celui du svd de numpy")
                mp.show()
        return "PASSED"



def test_QR_optimise (n, len_matrix, activate_plot = False):
    if(n <= 0):
        print("Erreur, veuillez entrer un nombre de test strictement positif\n")
        return "FAILED"
    else :
        Matrix_list = []
        Compare_list_Q = []
        Compare_list_R = []
        x = range(0,n)
        for i in range(0,n):
            Matrix_list.append(p2.forme_bidiagonale(rand_square_Matrix(len_matrix, 0,100))[2])
        for i in range(0,n):
            Compare_list_Q.append(np.linalg.norm((np.linalg.qr(Matrix_list[i])[0]) - (p3.QR_optimise(Matrix_list[i])[0])))
            Compare_list_R.append(np.linalg.norm((np.linalg.qr(Matrix_list[i])[1]) - (p3.QR_optimise(Matrix_list[i])[1])))
        if(activate_plot == True):
            mp.plot(x,Compare_list_Q)
            mp.title("Comparaison entre Q implemente et celui de numpy")
            mp.show()
            mp.plot(x,Compare_list_R)
            mp.title("Comparaison entre R implemente et celui de numpy")
            mp.show()
        return "PASSED"

