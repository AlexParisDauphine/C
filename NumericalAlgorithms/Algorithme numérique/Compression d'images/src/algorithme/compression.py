#Packages importes afin de realiser les fonctions#
import numpy as np
import random
import math
import matplotlib.pyplot as mp
import unittest
from PIL import Image


import bidiagonale as p2
import qr as p3


#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)


# Fonction permettant d'obtenir la compression au rang k d'une matrice carree donnee.
def compression_matrice (Mat, k):
    taille_x = Mat.shape[0]
    taille_y = Mat.shape[1]
    assert (k < min(taille_x, taille_y))
    print "Mise sous forme bidiagonale"
    (Ql, BD, Qr) = p2.forme_bidiagonale(Mat)
    print "Diagonalisation"
    (U,S,V,_) = p3.SVD_preliminaire(BD)    
    (U,S) = p3.organisation(U,S)
    nouveau_U = np.asmatrix(Ql*(U[:,0:k]))
    nouveau_S = np.asmatrix(S[0:k,0:k])
    nouveau_V = np.asmatrix((V[0:k,:])*Qr)
    return (nouveau_U, nouveau_S, nouveau_V)


# Fonction permettant d'obtenir la compression au rang k d'une matrice carree donnee avec la compression svd de python.
def compression_matrice_python (Mat, k):
    taille_x = Mat.shape[0]
    taille_y = Mat.shape[1]
    assert (k < min(taille_x, taille_y))
    (U,S,V) = np.linalg.svd(Mat, full_matrices=True)
    U=np.asmatrix(U)
    V = np.asmatrix(V)
    nouveau_S = np.asmatrix(np.zeros([k,k]))
    for i in range (k):
        nouveau_S[i,i]=S[i]
    nouveau_U = np.asmatrix(U)[:,0:k]
    nouveau_V = np.asmatrix(V)[0:k,:]
    return (nouveau_U, nouveau_S, nouveau_V)

# Fonction realisant la multiplication des matrices U*S*V avec S de taille k*k (finalement inutilisee)
def multiplication_matrice_USV_k (U, S, V, k):
    taille_x = V.shape[0]
    taille_y = U.shape[1]
    mul1 = []
    mul2 = []
    for i in range(taille_y): 
        ligne = [] 
        for j in range(k): 
            sum = 0
            for l in range(k):
                sum = sum + U[i,l] * S[l,j]
            ligne.append(sum) 
        mul1.append(ligne)
    mul1 = np.asmatrix(mul1)
    for i in range(taille_y): 
        ligne = [] 
        for j in range(taille_x): 
            sum = 0
            for l in range(k): 
                sum = sum + mul1[i,l] * V[l,j] 
            ligne.append(sum) 
        mul2.append(ligne)
    mul2 = np.asmatrix(mul2)
    return mul2


# Fonction affichant le pourcentage de compression d'une image en fonction de k et n.            
def afficher_pourcentage_compression (n):
    Compress = []
    for k in range (n):
        Compress.append( (n*n) - (2.*k*n + k*k))
    mp.plot(Compress)
    mp.show()



# Fonction permettant de calculer la distance entre 2 matrices de meme taille en comparant leur norme respective.
def distance_matrice(M1, M2):
    return (abs(np.linalg.norm(M1) - np.linalg.norm(M2)))

# Fonction permettant de montrer et de tracer l'efficacite  de la compression d'une image en fonction de k.
def efficacite_compression (image, pas = 10):
    (R,G,B) = RGB_image(image)
    taille_x = R.shape[0]
    Tableau_efficacite_R=[]
    Tableau_efficacite_G=[]
    Tableau_efficacite_B=[]
    Ord = np.arange(5,taille_x/2, pas)
    for k in range (5, taille_x/2, pas):
        (U1,S1,V1,U2,S2,V2,U3,S3,V3) = compression_image(image,k)
        Tableau_efficacite_R.append (distance_matrice(R, U1*S1*V1))
        Tableau_efficacite_G.append (distance_matrice(G, U2*S2*V2))
        Tableau_efficacite_B.append (distance_matrice(B, U3*S3*V3))
    mp.plot(Ord,np.log(Tableau_efficacite_R))
    mp.plot(Ord,np.log(Tableau_efficacite_G))
    mp.plot(Ord,np.log(Tableau_efficacite_B))
    mp.title("distance entre la matrice de depart et celle compressee en fonction de k")
    mp.xlabel("k")
    mp.ylabel("distance en fonction de la norme (echelle logarithmique)")
    mp.show()


# Fonction qui retourne les matrices R G B d'une image en parametre
def RGB_image (image):
    matrice_image = mp.imread(image)
    R = matrice_image[:,:,0]
    G = matrice_image[:,:,1]
    B = matrice_image[:,:,2]
    R = np.asmatrix(R)*255
    G = np.asmatrix(G)*255
    B = np.asmatrix(B)*255
    return (R,G,B)

# Fonction qui recompose la matrice image a partir des 3 matrice R,G et B.
def recomp_RGB (R,G,B):
    matrice_image = np.zeros((R.shape[0], R.shape[1], 3), 'uint8')
    matrice_image[:,:,0] = abs(abs((-(R))+255)-255)         # permet de corriger les points < 0 et > 255
    matrice_image[:,:,1] = abs(abs((-(G))+255)-255)
    matrice_image[:,:,2] = abs(abs((-(B))+255)-255)
    return matrice_image

# Fonction qui affiche une image a partir de 3 matrices RGB en parametre.
def affiche_image(R, G, B):
    matrice_image = recomp_RGB(R,G,B)
    #matrice_image = clean (matrice_image)
    mp.imshow(matrice_image)
    mp.show()
    image = Image.fromarray(matrice_image)
    image.save('output.png')


# Fonction qui nettoie les valeurs <0 et >255. Finalement inutilisee car couteux et fonctions qui peuvent etre gerees ailleurs. 
def clean (Mat3):
    for i in range (len(Mat3)):
        for j in range (len(Mat3[0])):
            for k in range (len(Mat3[0,0])):
                if (Mat3[i,j,k]<0):
                    Mat3[i,j,k] = 0
                if (Mat3[i,j,k]>255):
                    Mat3[i,j,k] = 255
    return Mat3

# Fonction qui retourne les matrices RGB compressee au rang k d'une image passee en parametre.
def compression_image(image, k):
    print "Recuperation de l'image"
    (R,G,B) = RGB_image(image)
    print "Compression de la couleur rouge"
    (U_R, S_R, V_R) = compression_matrice(R,k)
    print "Compression de la couleur verte"
    (U_G, S_G, V_G) = compression_matrice(G,k)
    print "Compression de la couleur bleue"
    (U_B, S_B, V_B) = compression_matrice(B,k)
    return (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B)

# Fonction qui affiche l'image apres compression au rang k d'une image passee en parametre.
def affiche_compression_image(image, k):
    (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B) = compression_image(image, k)
    print "Affichage de l'image"
    affiche_image (U_R*S_R*V_R, U_G*S_G*V_G, U_B*S_B*V_B)

# Fonction qui retourne les matrices RGB compressee au rang k d'une image passee en parametre avec la fonction svd de python.
def compression_image_python(image, k):
    print "Recuperation de l'image"
    (R,G,B) = RGB_image(image)
    print "Compression de la couleur rouge"
    (U_R, S_R, V_R) = compression_matrice_python(R,k)
    print "Compression de la couleur verte"
    (U_G, S_G, V_G) = compression_matrice_python(G,k)
    print "Compression de la couleur bleue"
    (U_B, S_B, V_B) = compression_matrice_python(B,k)
    return (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B)

# Fonction qui affiche l'image apres compression au rang k d'une image passee en parametre avec la fonction svd de python.
def affiche_compression_image_python(image, k):
    (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B) = compression_image_python(image, k)
    print "Affichage de l'image"
    affiche_image (U_R*S_R*V_R, U_G*S_G*V_G, U_B*S_B*V_B)



