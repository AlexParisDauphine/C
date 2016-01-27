#Packages importes afin de realiser les fonctions#
import numpy as np
import random
import math
import matplotlib.pyplot as mp
import unittest
from PIL import Image


import compression as p4


#Configuration de l'affichage des float avec numpy, notamment pour l'ecriture des matrices (eviter autant que possible la notation scientifique dans les matrices, ce qui les rend moins lisible)
np.set_printoptions(precision=8)
np.set_printoptions(suppress=True)


#Phrases de presentation du programme
print ""
print "Voici la partie de compression d'une image par la methode SVD."
print ""
print "Vous pouvez tester la compression sur l'image nasa(300x400), earth(500x500), curiosity(256x256) ou smiley(50x50) au rang k en tapant 'compression.test_nomdelimage(k)' (k = 30 par default) ou 'compression.test_nomdelimage_python(k)' pour utiliser la methode SVD de python."
print "Pour pouvoir compresser au rang k l'image de votre choix, tapez 'compression.affiche_compression_image('nomdelimage', k)' ou 'compression.affiche_compression_image('nomdelimage', k)' pour utiliser la methode SVD de python."
print "ATTENTION, ne compressez que des images de format PNG."
print ""


# Fonction qui affiche l'image apres compression au rang k d'une image passee en parametre avec la fonction svd de python.
def affiche_compression_image_python(image, k):
    (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B) = p4.compression_image_python(image, k)
    print "Affichage de l'image"
    p4.affiche_image (U_R*S_R*V_R, U_G*S_G*V_G, U_B*S_B*V_B)

# Fonction qui affiche l'image apres compression au rang k d'une image passee en parametre.
def affiche_compression_image(image, k):
    (U_R, S_R, V_R, U_G, S_G, V_G, U_B, S_B, V_B) = p4.compression_image(image, k)
    print "Affichage de l'image"
    p4.affiche_image (U_R*S_R*V_R, U_G*S_G*V_G, U_B*S_B*V_B)


def test_nasa_python(k = 30):
    affiche_compression_image_python("nasa.png", k)

def test_nasa(k = 30):
    affiche_compression_image("nasa.png", k)

def test_earth_python(k = 30):
    affiche_compression_image_python("earth.png", k)

def test_earth(k = 30):
    affiche_compression_image("earth.png", k)

def test_smiley_python(k = 30):
    affiche_compression_image_python("smiley.png", k)

def test_smiley(k = 30):
    affiche_compression_image("smiley.png", k)

def test_curiosity_python(k = 30):
    affiche_compression_image_python("curiosity.png", k)

def test_curiosity(k = 30):
    affiche_compression_image("curiosity.png", k)
