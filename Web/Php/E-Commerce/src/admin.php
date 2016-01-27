<?php include_once('fonctions/random_reference_produit.php'); ?>
<!DOCTYPE html>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/offcanvas.css" rel="stylesheet">
	<link href="css/grid.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title></title>
	<script type="text/javascript" src="boostrap.min.js"></script>
    <script type="text/javascript" src="bootstrap.js"></script>
	
  </head>
  
				<?php
				$alert_rouge=NULL;
				$alert_verte=NULL;	
				//on génére la référence d'un produit de façon automatique (voir le fichier random_reference_produit.php)
				$Designation=NULL;
				$Description=NULL;
				$Reference=NULL;
				$Quantite=NULL;
				$Prix=NULL;
				
				$numero_reference = reference_nombre(12);
				$Reference = $numero_reference;
				//echo $Reference;
				if(isset($_POST['Designation']) && isset($_POST['Descriptif']) && isset($_POST['Quantite']) && isset($_POST['Numero_Catalogue']) && isset($_POST['Prix']) && isset($_POST['submit'])){
					if(!empty($_POST['Designation']) && !empty($_POST['Descriptif']) && !empty($_POST['Quantite']) && !empty($_POST['Prix']) && !empty($_POST['submit']))
					{
					$connexion = new mysqli("localhost","root","", "e-commerce2");
					if (!$connexion) {
						$alert_rouge = 3;
					}	
					//récupèrer le numéro client le plus élevé de la table
					else {
					
					//génération de la référence d'une longueur de 12 par défaut
					
					
					
					//affectation des variables du formulaire
					$Designation=$_POST['Designation'];
					$Descriptif=$_POST['Descriptif'];
					$Quantite=$_POST['Quantite'];
					$Prix=$_POST['Prix'];
					
					$requete = "INSERT INTO PRODUIT (REFERENCE, DESIGNATION, DESCRIPTIF, QUANTITE_EN_STOCK, PRIX) values ('$Reference', '$Designation', '$Descriptif', '$Quantite', '$Prix')";
					$resultat = mysqli_query($connexion, $requete);
					
					//**Mise en place d'un TRIGGER**
					//**A chaque fois qu'on insert un produit, on doit lui affecter l'appartenance à un catalogue
					//$requete="CREATE TRIGGER ajout_produit AFTER INSERT ON  PRODUIT FOR EACH ROW BEGIN INSERT INTO APPARTIENT (REFERENCE, NUMERO_CATALOGUE) values ('2222222222', '$Numero_Catalogue') end;";
					//**pk ça marche pas ce foutu trigger**
					
					$Numero_Catalogue=$_POST['Numero_Catalogue'];
					
					$requete="INSERT INTO APPARTIENT (REFERENCE, NUMERO_CATALOGUE) values ('$Reference','$Numero_Catalogue')";
					$resultat = mysqli_query($connexion, $requete);
					mysqli_close($connexion);
					$alert_verte = 1;
					}
					
					
				}else {
				 $alert_rouge = 2;
				}
				}
				?>
				
  
  
  
  <body>
    <div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
		  
		<!--header-->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <p class="navbar-brand"><strong style="color:green">E</strong>stupina<strong style="color:green">R</strong>affin<strong style="color:green">P</strong>atry &amp; Co</p>
                

		</div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="">Administration</a></li>
			 <li><a href="home.php">Revenir à l'acceuil</a></li>
	  </ul>	
        </div>
		
      </div>
    </div>
	
	<!--affichage des alertes-->
	<?php
	//par défaut mettre une banière grise avec 'administrateur' marqué
	if($alert_verte!=1 && $alert_rouge!=2){
		echo  '<div class="jumbotron">
		   <center><h1>'.'Administrateurs'.'</h1></center>
          </div></br>';
	}
	//si on fait une erreur en remplissant le formulaire de 'produit'
	if($alert_verte==1){
		echo  '<div class="jumbotron2">
		   <center><h1>'.'Produit rajouté. Merci.'.'</h1></center>
          </div></br>';
	}
	if($alert_rouge==2){
		echo  '<div class="jumbotron3">
		   <center><h1>'.'Veuillez remplir tous les champs.'.'</h1></center>
          </div></br>';
	}
	if($alert_rouge==3){
		echo  '<div class="jumbotron3">
		   <center><h1>'.'Problème serveur.'.'</h1></center>
          </div></br>';
	}
	?>
          
	 
		
			<div class="col-sm-4">
			<p><strong><center>Ajouter</center></strong></p>
				
				
					 <form role="form" action="" method="post" >
					  <div class="form-group">
					    <label>Nom produit</label>
					    <input name="Designation" type="text"  class="form-control" placeholder="Nom produit" required >
					  </div>
					  <div class="form-group">
					    <label>Descriptif</label>
					    <textarea name="Descriptif" class="form-control" rows="5" placeholder="Description du produit"></textarea>
					  </div>
					  <div class="form-group">
					    <label>Quantité</label>
					    <input name="Quantite" class="form-control" type="number"  min="0" placeholder="Quantité" required >
					  </div>
					   <div class="form-group">
					    <label>Choisir le catalogue</label>
					    <!--<input name="Numero_Catalogue" class="form-control" type="number"  min="0" max="6" placeholder="Catalogue dans lequel le produit se retrouvera" required >-->
					    <select class="form-control" id="sel1" name="Numero_Catalogue">
						<option value="0">Voiture</option>
						<option value="1">Livres</option>
						<option value="2">Informatique</option>
						<option value="3">Vetements</option>
						<option value="4">Vin et Gastronomie</option>
						<option value="5">Telephonie</option>
						<option value="6">Jouets</option>
					    </select>
					  </div>
					    <div class="form-group">
					    <label>Prix en €</label>
					   <input name="Prix" type="number" step="0.01" class="form-control" min="0" placeholder="Prix" required >
					  </div>
					  <input name="submit" type="submit" class="btn btn-info" value="Enregistrer le produit">
					</form>
				
				
				</div>
				
				
				<div class="col-md-4">	
				
				<p><strong><center>Statistique</center></strong></p>
					 <form role="form" action="" method="post" >
					 <label>Catalogue(s) ou le produit apparait</label>
					 <!--code php pour retourner toutes les références et savoir si elles appartiennent a plusieurs catalogues-->
					  <input name="demande_reference" type="text"  class="form-control" placeholder="Indiquer la référence du produit" >
					  <input name="submit_produit_catalogue" type="submit" class="btn btn-info" style="width:100%" value="Visualiser">
					  
					  <?php
					  $ref=NULL;
					  if(isset($_POST['demande_reference']) && isset($_POST['submit_produit_catalogue'])){
						$ref=$_POST['demande_reference'];
						$connexion_choix = new mysqli("localhost","root","", "e-commerce2");						
						$requete="select NOM_CATALOGUE from CATALOGUE C, APPARTIENT A, PRODUIT P where C.NUMERO_CATALOGUE = A.NUMERO_CATALOGUE and A.REFERENCE = P.REFERENCE and A.REFERENCE='$ref' GROUP BY NOM_CATALOGUE";
						$resultat = mysqli_query($connexion_choix, $requete);
						$nb_lignetot = mysqli_num_rows($resultat);
						//gestion du tableau vide
							if($nb_lignetot == 0){
								echo '<center><p>'.'Veuillez mettre une référence correcte'.'</p></center></br>';
							}
							else{	echo '<strong>'.$ref.' appartient à(aux)catalogue(s) suivant(s): &#8681'.'</strong></br>' ;
							$ligne = 0;
							$afficher_nombre=$ligne;
							for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
								$column = $resultat->fetch_row();
								$afficher_nombre=$afficher_nombre+1;
								//echo $categorie_choix.'.php?'.'voiture='.$column['1'];
								?>
								<div class="trait" style="height: 1px; background-color:grey"></div>
								<!--on passe dans l'url la référence du produit et pas la désignation pour éviter les espaces et la sécurité-->
								<p><?php echo 'Catalogue '.$afficher_nombre.': <strong style="color:purple">'.$column["0"].'</strong>'?></p>
									
							<?php
							}
								
						}
					}		
					?>
					  
					 <label>Produit(s) indisponible(s)</label>
					   <input name="submit_non_disponible" type="submit" class="btn btn-info" style="width:100%" value="Liste des produits non disponibles">
					</form></br>
				<!--retourne les produit de quantité égale à 0 (alerte pour l'administrateur)-->
				<?php
				$categorie_choix=NULL;
				if(isset($_POST['submit_non_disponible'])){
					$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
					$requete="SELECT P.REFERENCE, DESIGNATION, QUANTITE_EN_STOCK, NOM_CATALOGUE from PRODUIT P, APPARTIENT A, CATALOGUE C where C.NUMERO_CATALOGUE = A.NUMERO_CATALOGUE and A.REFERENCE = P.REFERENCE group by P.REFERENCE HAVING QUANTITE_EN_STOCK = 0";
					$resultat = mysqli_query($connexion_choix, $requete);
					$nb_lignetot = mysqli_num_rows($resultat);
					//gestion du tableau vide
					if($nb_lignetot == 0){
						echo '<center><h1>'.'Désolé aucun produit ne correspond à votre recherche'.'</h1></center>';
							}
					else{	echo '<strong>'."Les produits ci dessous ne sont plus disponible &#8681".'</strong></br>' ;
					$ligne = 0;
					for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
						$column = $resultat->fetch_row(); 
						//echo $categorie_choix.'.php?'.'voiture='.$column['1'];
						?>
						<div class="trait" style="height: 1px; background-color:grey"></div>
						<!--on passe dans l'url la référence du produit et pas la désignation pour éviter les espaces et la sécurité-->
						<a href='<?php echo 'Update.php?reference_produit='.$column['0'].'&categorie_choix='.$column['3'] ?>' style="color:red"><?php echo $column["0"]; ?> - <i><?php echo $column["1"]; ?> - <?php echo 'Quantité restante:'.$column["2"]?></i></a>
									
						<?php
						}
								
					}
				}
														
							
				?>
				</div>
				
				
				
				
				
				
				<div class="col-md-4">	
				<p><strong><center>Mettre à jour les produits</center></strong></p>
				
					 <form role="form" action="" method="post" >
					   <div class="form-group">
					   <label></br>Choisir un catalogue</label>
					   <select class="form-control" id="sel1" name="categorie_choix">
						<option disabled="disabled" >Choisir un catalogue</option>
						<option value="Voiture" >Voiture</option>
						<option value="Livre">Livres</option>
						<option value="Vetements">Vêtements</option>
						<option value="Informatique">Informatique</option>
						<option value="VinetGastronomie">Vin  &amp; Gastronomie</option>
						<option value="Telephonie">Téléphonie</option>
						<option value="Jouets">Jouets</option>
					  </select>
					  </div>
					  
					  <input name="submit_choix" type="submit" class="btn btn-info" value="Valider">
					</form></br>
					
					<?php
					$categorie_choix=NULL;
					if(isset($_POST['categorie_choix']) && isset($_POST['submit_choix'])){
						$categorie_choix=$_POST['categorie_choix'];
						$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
						$requete_choix="SELECT DESIGNATION, P.REFERENCE FROM PRODUIT P, APPARTIENT A, CATALOGUE C WHERE C.NUMERO_CATALOGUE = A.NUMERO_CATALOGUE and A.REFERENCE = P.REFERENCE and C.NOM_CATALOGUE = '$categorie_choix' ";
						$resultat = mysqli_query($connexion_choix, $requete_choix);
						$nb_lignetot = $resultat->num_rows;
							//gestion du tableau vide
							if($nb_lignetot == 0){
								echo '<center><h1>'.'Désolé aucun produit ne correspond à votre recherche'.'</h1></center>';
							}
							else{	echo '<strong>'."Veuiller sélectionner le produit à mettre a jour &#8681".'</strong></br>' ;
								
								$ligne = 0;
								for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
								$column = $resultat->fetch_row(); 
								//echo $categorie_choix.'.php?'.'voiture='.$column['1'];
								?>
								<div class="trait" style="height: 1px; background-color:grey"></div>
								<!--on passe dans l'url la référence du produit et pas la désignation pour éviter les espaces et la sécurité-->
								<a href='<?php echo 'Update.php?reference_produit='.$column['1'].'&categorie_choix='.$categorie_choix ?>'><?php echo $column["0"]; ?> - <i><?php echo $column["1"]; ?></i></a>
								
								<?php
								}
							
							     }
						         }						
							
						?>
					
					
					
					
					
					
				
				</div>
				
					

</body>
</html>