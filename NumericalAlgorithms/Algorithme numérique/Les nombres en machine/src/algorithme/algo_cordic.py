import numpy as np;


L = [np.log(2),np.log(1.1),np.log(1.01),np.log(1.001),np.log(1.0001),np.log(1.00001),np.log(1.000001)]
A = [np.arctan(1),np.arctan(0.1),np.arctan(0.01),np.arctan(0.001),np.arctan(0.0001)]
def ln (x):
    n = 0
    while(x>=10 and x<1):
        if(x<1):
            x = x * 10
            n = n + 1
        else:
            x = x / 10.0
            n = n + 1
    return ln_step_2(x*(pow(10,-n))) + (n*np.log(10))
def ln_step_2 (x):
    """Deuxieme etape du calcul de ln x entre 1 et 10"""
    k = 0
    y = 0
    p = 1
    while(k <= 6):
        while(x >= p + p*(pow(10,-k))):
            y = y + L[k]
            p = p + p*(pow(10,-k))
        k = k + 1
    return y + (x/p-1)

def exp (x):
    n = 0
    while(x>=np.log(10) and x<0):
        if(x<0):
            x = x + np.log(10)
            n = n + 1
        else :
            x = x - np.log(10)
            n = n + 1
    return exp_step_2(x-n*np.log(10))* (pow(10,n))
def exp_step_2(x):
    """"Deuxieme etape du calcul de exp x entre 0 et log(10)"""
    k = 0
    y = 1
    while(k <= 6):
        while(x >= L[k]):
            x = x - L[k]
            y = y + y*(pow(10,-k))
        k = k + 1
    return y + y * x

def tan(x):
    y = x - np.pi*(np.floor(x/(np.pi)))
    if(np.abs(y)<((np.pi)/4)):
        z = tan_step_2(np.abs(y))
    if(np.abs(y)<((np.pi)/2)):
        z = -tan_step_2(np.pi - np.abs(y))
    else:
        z = (1/(tan_step_2((np.pi)/2 - x)))
    if(y<0):
        z = -z
    return z
    
def tan_step_2(x):
    """Deuxieme etape du calcul de tan x entre o et pi/4"""
    k = 0
    n = 0
    d = 1
    while(k <= 4):
        while(x >= A[k]):
            x = x - A[k]
            mp = n + d*(pow(10,-k))
            d = d - n*(pow(10,-k))
            n = mp
        k = k + 1
    return (n + x*d)/(d-x*n)

def arctan(x):
    if (x < 0 and x >-1):
        return -arctan_step_2(-x)
    if (x >= 1):
        return ((np.pi)/2.0 -arctan_step_2(1/(1.0*x)))
    if (x <= -1):
        return -((np.pi)/2.0 -arctan_step_2((-1)/(1.0*x)))
                 
def arctan_step_2(x):
    """Deuxieme etape du calcul de arctan x entre 0 et 1"""
    k = 0
    y = 1
    r = 0
    while(k <= 4):
        while(x < y*(pow(10,-k))):
            k = k + 1
        if k > 4 :
            break
        xp  = x - y*(pow(10,-k))
        y = y + x*(pow(10,-k))
        x = xp
        r = r + A[k]
    return r + x/y


print "%f" % ln(15.56)

