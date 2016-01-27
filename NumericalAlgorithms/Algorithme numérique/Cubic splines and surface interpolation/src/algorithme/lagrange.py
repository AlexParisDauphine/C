## Import
import numpy as np
import matplotlib.pyplot as mp
import load_foil as fl

# Constantes
eps = 0.1**2
(ex, ey, ix, iy) = fl.load_foil('BACNLF.DAT')

#Fonction mettant en equation les polynomes interpolateurs de lagrange
def lagrange_polynomial(x1,x2,y1,y2):
    def lagrange(x):
        A = (x-x2)/(x1-x2)
        B = (x-x1)/(x2-x1)
        C = y1*A + y2*B
        return C
    return lagrange

# Fonction permettant de mettre en place es derivees 
def lagrange_derivative(x1,x2,y1,y2):
    def deriv(x):
        A = 1/(x1-x2)
        B = 1/(x2-x1)
        C = y1*A + y2*B
        return C
    return deriv

# Fonction effectuant l'interpolation de Lagrange
def lagrange_interpolation(ix,iy):
    list_of_polynomials = []
    for j in range(0,ix.size-1):
        list_of_polynomials.append(lagrange_polynomial(ix[j],ix[j+1],iy[j],iy[j+1]))
    return np.asarray(list_of_polynomials)

# Fonction mettant les derivees issue de la methode de lagrange dans une liste
def lagrange_derivatives(ix,iy):
    list_of_derivatives = []
    for j in range (0,ix.size-1):
        list_of_derivatives.append(lagrange_derivative(ix[j],ix[j+1],iy[j],iy[j+1]))
    return np.asarray(list_of_derivatives)

# Fonction permettant de mettre sous "plot" une liste de points
def print_lagrange(ix,iy,eps):
    polynomials = lagrange_interpolation(ix,iy)
    X_liste = np.arange(ix[0], ix[ix.size-1], eps)
    Y_liste = []
    for i in range(0, X_liste.size):
        j = 0;
        while(ix[j+1]<X_liste[i]):
            j += 1;
        Y_liste.append(polynomials[j](X_liste[i]));
    #mp.plot(ix, iy, 'ro');
    mp.plot(X_liste, Y_liste, linewidth=1.0)
    return True

# Fonction affichant l'ensemble de la courbe (intrados et extrados compris)
def print_all_lagrange(ix,iy,ex,ey,eps):
    mp.plot(ix, iy, '+')
    mp.plot(ex,ey,'+')
    print_lagrange(ix,iy,eps)
    print_lagrange(ex,ey,eps)
    # mp.savefig("Lagrangeapprox.png")
    mp.show()

# print_all_lagrange(ix,iy,ex,ey,eps)
