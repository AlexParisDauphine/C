

import numpy as np
import random as rd
import numpy.linalg as nl
import matplotlib.pyplot as pl
from mpl_toolkits.mplot3d import Axes3D
from matplotlib import cm
import matplotlib as ml
import math
import unittest

CONDITION_ARRET = pow(10,6)

#####################################################################################

#...................Partie 1.............................................

def factcholesky (A):
    A = np.asmatrix (A)
    size = A.shape
    T = np.zeros(size)
    for i in range (len(A)):
        for j in range (i+1):
            t = 0.0
            for k in range (j):
                t = t + (T[i,k] * T[j,k])
            if i == j:
                T[i,i] = math.sqrt(A[i,i] - t)
            else:
                T[i,j] = (A[i,j] - t)/(T[j,j])
    T = np.asmatrix(T)
    return T

########......generation de matrice et vecteur..............###########


def n_identity(n):
    #retourne la matrice (n * identite)
    B = np.zeros([n,n])
    for i in range(n):
        B[i,i] = n
    return B


def generate_matrix_creuse(size, elements_not_null_ext_d):
    #elements_not_null_ext_d est un nombre pair et represente les termes extra-diagonaux non nuls
    
    B = np.zeros([size,size])
    B = np.asmatrix(B)
    # B une matrice dont les termes sont definit aleatoirement dans ]0, 1]
    
    for i in range(size):
        for j in range(size):
            B[i,j] = rd.uniform(0,1)
            if (i==j and B[i,j] == 0):
                j = j - 1
    # on choisit aleatoirement les termes nuls
    nb_zeros = size * (size - 1) - elements_not_null_ext_d
    while (nb_zeros > 0):
        l = 0 #indice de la ligne
        c = 0 #indice de la colonne
        while (l == c):
            l = rd.randint(0, size - 1)
            c = rd.randint(0, size - 1)
        #les termes de la diagonale sont non nuls car l!= c
        if (B[l,c] != 0.):
            B[l,c] = 0
            B[c,l] = 0
            nb_zeros = nb_zeros - 2
    
    S = B + B.T # S est une matrice symetrique
    S = S + n_identity(size) # on ajoute (n*identite) pour que s soit definit positive
    S = 10 * S      #on augmente les termes de S
    for i in range(size):
        for j in range(size):
            S[i,j] = int(S[i,j])
    S = np.asmatrix(S)
    return S

def generate_matrix(size):
    B = np.zeros([size,size])
    B = np.asmatrix(B)
    for i in range(size):
        for j in np.arange(i, size, 1):
            B[i,j] = rd.randint(1,10)
    S = B * B.T
    S = np.asmatrix(S)
    return S

def generate_vect(size):
    b = np.zeros([size,1])
    for i in range(size):
        b[i,0] = rd.randint(1,100)
    return b


#......................................................................................

def factcholesky_incomp (A):
    A = np.asmatrix (A)
    size = A.shape
    T = np.zeros(size)
    for i in range (len(A)):
        for j in range (i+1):
            if (A[i,j] != 0):
                t = 0.0
                for k in range(j):
                    t = t + (T[i,k] * T[j,k])
                if i == j:
                    T[i,i] = math.sqrt(A[i,i] - t)
                else:
                    T[i,j] = (A[i,j] - t)/(1.0*T[j,j])
            else:
                T[i,j] = 0.0
    T = np.matrix(T)
    return T


#........nombre de termes non nuls gagnes dans le cas de la factorisation de Cholesky incomplete par rapport a la factorisation dense..............

def nbr_termes_non_null(A):
    k = 0.0
    for i in range (len(A)):
        for j in range (len(A)):
            if (A[i,j] != 0 and i != j):
                k = k+1
    return k

def terme_non_null_gagne(N):
    nbr_cas = int((N*N-N)/2.0 +1)
    v = np.zeros([nbr_cas,1])
    for i in range (nbr_cas):
        A = generate_matrix_creuse (N, 2*i)
        T1 = factcholesky(A)
        T2 = factcholesky_incomp(A)
        v[i,0] = nbr_termes_non_null (T1) - nbr_termes_non_null (T2)
    return v

#.......................................................................
def preconditionneur_eval(B):
    T = factcholesky(B)
    M = T*T.T
    cond_B = nl.cond(B)
    cond_M_B = nl.cond(nl.inv(M)*B)
    return (cond_M_B - cond_B)

def preconditionneur_eval_incomp(B):
    T = factcholesky_incomp(B)
    M = T*T.T
    cond_B = nl.cond(B)
    cond_M_B = nl.cond(nl.inv(M)*B)
    return (cond_M_B - cond_B)

def compare_preconditionneur (N):
    nbr_cas = int((N*N-N)/2.0 +1)
    v = np.zeros([nbr_cas,1])
    for i in range (nbr_cas):
        A = generate_matrix_creuse (N, 2*i)
        v[i,0] = preconditionneur_eval_incomp(A) - preconditionneur_eval(A)
    return v



##############################################################################
#...................Partie 2.....................



def conjgrad (A,b,x):
    #initialisation des variables
    A = np.matrix(A)
    b = np.matrix(b)
    x = np.matrix(x)
    r = np.zeros([len(b),1])
    p = np.zeros([len(b),1])
    rsold = np.zeros([1,1])
    Ap = np.zeros([len(b),1])
    rsnew = np.zeros([1,1])
    tmp = np.zeros([1,1])
    alpha = 0.0
    #implementation
    r = b - A*x
    p = r
    rsold = (r.T)*r
    for i in range (1,CONDITION_ARRET):
        Ap = A*p
        tmp = (p.T)*Ap
        alpha = (rsold[0,0])/(1.0*tmp[0,0])
        x = x + alpha * p
        r = r - alpha*Ap
        rsnew = (r.T)*r
        if (np.sqrt(rsnew[0,0])< pow(10,-10)):
            break
        p = r + (rsnew[0,0]/(1.0*rsold[0,0]))*p
        rsold = rsnew
    return x


#...............................................................

def preconditionneur(A):
    T = factcholesky_incomp (A)
    size = A.shape
    M = np.zeros(size)
    I = nl.inv(T)
    M = I.T * I
    M = np.matrix(M)
    return M


def conjgrad_preconditionneur_bis (A,b,x0,M):
    #initialisation des variables
    A = np.matrix(A)
    b = np.matrix(b)
    x0 = np.matrix(x0)
    M = np.matrix(M)
    r0 = np.zeros([len(b),1])
    p0 = np.zeros([len(b),1])
    z0 = np.zeros([len(b),1])
    x1 = np.zeros([len(b),1])
    p1 = np.zeros([len(b),1])
    z1 = np.zeros([len(b),1])
    r1 = np.zeros([len(b),1])
    Ap = np.zeros([len(b),1])
    alpha = 0.0
    beta = 0.0
    #implementation
    r0 = b - A*x0
    z0 = nl.inv(M)*r0
    p0 = z0
    for i in range (1,CONDITION_ARRET):
        Ap = A*p0
        alpha = ((r0.T)*z0)[0,0]/(1.0*((p0.T)*Ap)[0,0])
        x1 = x0 + alpha * p0
        r1 = r0 - alpha * Ap
        if ((r1.T*r1)[0,0]  < pow(10,-10)):
            return x1
        z1 = nl.inv(M)*r1
        beta = ((z1.T)*r1)[0,0]/(1.0*((z0.T)*r0)[0,0])
        p1 = z1 + beta*p0
        p0 = p1
        z0 = z1
        x0 = x1
        r0 = r1
    return x1

def conjgrad_preconditionneur (A,b,x0):
    M = preconditionneur(A)
    return conjgrad_preconditionneur_bis (A,b,x0,M)




###################################################################################

#...................Partie3......................

def vect_to_matrix (a):
    #retourne la matrice qui correspond au vecteur d'entree
    a = np.matrix(a)
    N = np.sqrt(len (a))
    N = int(N)
    B = np.zeros([N,N])
    for i in range (N):
        for j in range (N):
            B[i,j] = a[i*N+j,0]
    return B


def matrix_to_vect (A):
    #retourne le vecteur qui correspond a la matrice d'entree
    A = np.matrix(A)
    N = (len (A)) * (len (A))
    b = np.zeros([N,1])
    for i in range (len(A)):
        for j in range (len(A)):
            b[i* len(A)+j,0] = A[i,j]
    return b


def matrix_Mc(N):
    #retourne la matrice Mc
    M = np.zeros([(N*N),(N*N)])
    k = 0
    for i in range (N*N):
        M[i,i] = -2.0
        if i < (N*N - N):
            M[i,N+i] = 1
        k = k + 1
        if (k != N) and (i != (N*N - 1)):
            M[i,(i+1)] = 1.0
        else:
            k = 0
    M = M + M.T
    return M

def matrix_f_radiateur(N):
    #retourne la matrice de la fonction f dans le cas d'un radiateur
    B = np.zeros([N,N])
    i = int((N*40)/100)
    k = int((N*60)/100)
    medium = int (N/2)
    for j in np.arange(i,k,1):
        B[j,medium] = -1
    return B


def matrix_f_mur(N):
    #retourne la matrice de la fonction f dans le cas d'un mur
    B = np.zeros([N,N])
    for i in range (N):
        B[0,i] = -1
    return B




########......resolution de l'equation de la chaleur........#######

n = 50 # n est un entier tq (n x n) represente le nombre de point dans le carree

x = np.zeros([(n*n), 1]) # l'inconnue initialisee a 0 de taille n*n



T = vect_to_matrix(conjgrad(1./(n*n)*matrix_Mc(n), matrix_to_vect(matrix_f_radiateur(n)), x)) # T la matrice de la fonction qui represente la solution de l'equation de la chaleur

#........affichage en 2D..........

pl.imshow(T)
pl.show()

#........affichage en 3D...........

fig = pl.figure()
ax = Axes3D(fig)
X = np.arange(0, n-1, 1)
Y = np.arange(0, n-1, 1)
X, Y = np.meshgrid(X, Y)
Z = T[X,Y]
ax.plot_surface(X, Y, Z, rstride=1, cstride=1, cmap=cm.jet)
pl.show()




####################################################################################


#............tests_cholesky..........................................
def test_cholesky(n):
    A = generate_matrix (n)
    T = factcholesky(A)
    assert (np.allclose(A,T * T.T)) #test d'egalite


#.........tests_cholesky_imcoplete_matrice_creuse...................
def test_cholesky_icomp(n):
    A = generate_matrix_creuse (n, rd.randint(0,n*n-n))
    T = factcholesky_incomp(A)
    print T * T.T
    print A


#........tests_conjgrad......................

def test_conjgrad(n):
    #n represente la taille de la matrice (du systeme lineaire)
    A = generate_matrix_creuse (n, rd.randint(0,n*n-n))
    b = generate_vect(n)
    x = np.zeros([n,1])
    Q2 = conjgrad (A,b,x)     #le resultat avec conjgrad de Ax = b
    A = np.array(A)
    b = np.array(b)
    Q1 =np.asmatrix( np.linalg.solve(A, b))   #le resultat avec linalg.solve de Ax = b
    assert (np.allclose(Q1,Q2)) #test d'egalite



#........tests_conjgrad_preconditionneur......................

def test_conjgrad_precod(n):
    #n represente la taille de la matrice (du systeme lineaire)
    A = generate_matrix_creuse (n, rd.randint(0,n*n-n))
    b = generate_vect(n)
    x = np.zeros([n,1])
    Q2 =  conjgrad_preconditionneur (A,b,x) #le resultat avec conjgrad_preconditionneur de Ax =b
    A = np.array(A)
    b = np.array(b)
    Q1 = np.asmatrix( np.linalg.solve(A, b)) #le resultat avec linalg.solve de Ax =b
    assert (np.allclose(Q1,Q2)) #test d'egalite



























































