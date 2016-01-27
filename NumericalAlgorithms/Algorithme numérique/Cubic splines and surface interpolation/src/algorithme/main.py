import test as ts

def main():
    print "Les fonctions de tests sur la partie d'integration ont ete desactives en raison de leur longueur au niveau temporel. Si vous souhaitez vraiment les faire, vous pouvez les activer manuellement en decommentant la premiere ligne de main() dans main.py"
    ts.test_p1();
    # ts.test_p2(); 
    # ts.test_p3();
    ts.test_lagrange();
    return True;

main();
