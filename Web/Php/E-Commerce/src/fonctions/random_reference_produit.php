<?php 
//renvoie une valeur (numero ou caractere)
function attribuer_rand_valeur($numero) {

    // de 1 à 36
    switch($numero) {
        case "1"  : 
		$rand_valeur = "A"; 
		break;
        case "2"  : 
		$rand_valeur = "B"; 
		break;
        case "3"  : 
		$rand_valeur = "C"; 
		break;
        case "4"  : 
		$rand_valeur = "D"; 
		break;
        case "5"  : 
		$rand_valeur = "E"; 
		break;
        case "6"  : 
		$rand_valeur = "F"; 
		break;
        case "7"  : 
		$rand_valeur = "G"; 
		break;
        case "8"  : 
		$rand_valeur = "H"; 
		break;
        case "9"  : 
		$rand_valeur = "I"; 
		break;
        case "10" : 
		$rand_valeur = "J"; 
		break;
        case "11" : 
		$rand_valeur = "K"; 
		break;
        case "12" : 
		$rand_valeur = "L"; 
		break;
        case "13" : 
		$rand_valeur = "M"; 
		break;
        case "14" : 
		$rand_valeur = "N"; 
		break;
        case "15" : 
		$rand_valeur = "O"; 
		break;
        case "16" : 
		$rand_valeur = "P"; 
		break;
        case "17" : 
		$rand_valeur = "Q"; 
		break;
        case "18" : 
		$rand_valeur = "R"; 
		break;
        case "19" : 
		$rand_valeur = "S"; 
		break;
        case "20" : 
		$rand_valeur = "T"; 
		break;
        case "21" : 
		$rand_valeur = "U"; 
		break;
        case "22" : 
		$rand_valeur = "V"; 
		break;
        case "23" : 
		$rand_valeur = "W"; 
		break;
        case "24" : 
		$rand_valeur = "X"; 
		break;
        case "25" : 
		$rand_valeur = "Y"; 
		break;
        case "26" : 
		$rand_valeur = "Z"; 
		break;
        case "27" : 
		$rand_valeur = "0"; 
		break;
        case "28" : 
		$rand_valeur = "1"; 
		break;
        case "29" : 
		$rand_valeur = "2"; 
		break;
        case "30" : 
		$rand_valeur = "3"; 
		break;
        case "31" : 
		$rand_valeur = "4"; 
		break;
        case "32" : 
		$rand_valeur = "5"; 
		break;
        case "33" : 
		$rand_valeur = "6"; 
		break;
        case "34" : 
		$rand_valeur = "7"; 
		break;
        case "35" : 
		$rand_valeur = "8"; 
		break;
        case "36" : 
		$rand_valeur = "9"; 
		break;
    }
    return $rand_valeur;
}


//renvoie une valeur (numero ou caractere)
function attribuer_rand_valeur2($numero) {

    // de 1 à 36
    switch($numero) {
        case "1"  : 
		$rand_valeur = "1"; 
		break;
        case "2"  : 
		$rand_valeur = "2"; 
		break;
        case "3"  : 
		$rand_valeur = "3"; 
		break;
        case "4"  : 
		$rand_valeur = "4"; 
		break;
        case "5"  : 
		$rand_valeur = "5"; 
		break;
        case "6"  : 
		$rand_valeur = "6"; 
		break;
        case "7"  : 
		$rand_valeur = "7"; 
		break;
        case "8"  : 
		$rand_valeur = "8"; 
		break;
        case "9"  : 
		$rand_valeur = "9"; 
		break;
        case "10" : 
		$rand_valeur = "0"; 
		break;
     
    }
    return $rand_valeur;
}

//retourne une chaine de caractere GCYGIEE64HVGC par exemple

function reference_nombre($longueur) {
    if ($longueur>0) {
	//declare une chaine de caractere ref
        $ref="";
        for ($i=1; $i<=$longueur; $i++) {
		$choix=rand(1, 36);
		$caractere=attribuer_rand_valeur($choix);
		$ref .= $caractere;
        }
    }
    return $ref;
}

function reference_nombre2($longueur) {
    if ($longueur>0) {
	//declare une chaine de caractere ref
        $ref="";
        for ($i=1; $i<=$longueur; $i++) {
		$choix=rand(1, 10);
		$caractere=attribuer_rand_valeur2($choix);
		$ref .= $caractere;
        }
    }
    return $ref;
}



?>