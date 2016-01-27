## Import
import numpy as np
import matplotlib as mp
from lagrange import *
from splines import *
from integration import *

# Constantes
eps = 0.01
N = 100
(ex,ey,ix,iy) = fl.load_foil('BACNLF.DAT')

# Fonction retournant la longueur d'une fonction necessaire afin de calculer la longueur de la courbe associee
def length_func(f):
    def length(x):
        square = f(x)*f(x)
        return np.sqrt(1 + square)
    return length

# Fonction retournant la longueur de la courbe associee a une fonction
def curve_length(ex,ey,ix,iy,eps,N,interpolation_method):
    if(interpolation_method == "lagrange"):
        exderivatives = lagrange_derivatives(ex,ey)
        inderivatives = lagrange_derivatives(ix,iy)
    elif(interpolation_method == "splines"):
        exderivatives = np.asarray(derive_spline_def(ex,ey))
        inderivatives = np.asarray(derive_spline_def(ix,iy))
    else:
        print "Unknown method"
        return 0
    lenex = []
    lenin = []
    for i in range(0,exderivatives.size):
        lenex.append(length_func(exderivatives[i]))
    for i in range(0,inderivatives.size):
        lenin.append(length_func(inderivatives[i]))
    exsum = 0
    insum = 0
    for i in range(0,ex.size-1):
        exsum = exsum + integration(lenex[i],ex[i],ex[i+1],eps,N,"trapez")[0]
    for i in range(0,ix.size-1):
        insum = insum + integration(lenin[i],ix[i],ix[i+1],eps,N,"trapez")[0]
    return exsum + insum

# print curve_length(ex,ey,ix,iy,eps,N,"lagrange")
# print curve_length(ex,ey,ix,iy,eps,N,"splines")
# print curve_length(ex,ey,ix,iy,eps,N,"jhg")
