<!DOCTYPE html>

<html lang='fr'>
	
  <head>
    <meta charset='utf8'>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/offcanvas.css" rel="stylesheet">
	<link href="css/grid.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title></title>
  </head>
  
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
          <a href="admin.php" class="navbar-brand"><strong style="color:green">E</strong>stupina<strong style="color:green">R</strong>affin<strong style="color:green">P</strong>atry &amp; Co</a>
                

			</div>
			<div class="collapse navbar-collapse">
			  <ul class="nav navbar-nav">
				<li class="active"><a href="home.php">Accueil</a></li>
				<li ><a href="index.php">Se connecter</a></li>
				<!--<li><a href="">Mon Panier</a></li>-->
			  </ul>  
		  
		 
		  
			 <form action="" method="post" class="navbar-form navbar-right" role="search">
			  <!--<a style='color:white'>Catégorie : </a>-->
			  <div class="form-group">
			  
				<select class="form-control" id="sel1" name="categorie">
					<option value="Par_Defaut" >Tout les produits</option>
					<option value="Voiture" >Véhicules</option>
					<option value="Livre">Livres</option>
					<option value="Vetements">Vêtements</option>
					<option value="Informatique">Informatique</option>
					<option value="VinetGastronomie">Vin  &amp; Gastronomie</option>
					<option value="Telephonie">Téléphonie</option>
					<option value="Jouets">Jouets</option>
				</select></div>
				
			  <!--<a style='color:white'>Trier par : </a>-->
			  <div class="form-group">
				<select class="form-control" id="sel1" name="trierpar">
					<option value="RAND()" >Trier par</option>
					<option value="PRIX DESC" >Prix + à -</option>
					<option value="PRIX ASC">Prix - à +</option>
					<option value="DESIGNATION ASC">De A à Z</option>
					<option value="DESIGNATION DESC">De Z à A</option>
					<option value="QUANTITE_EN_STOCK DESC">Quantité + à -</option>
					<option value="QUANTITE_EN_STOCK ASC">Quantité - à +</option>
				</select></div>
				  <input type="submit" name="submit" class="btn btn-default" value="ok">
				<div class="form-group">
					<input type="text" class="form-control" name="rechercher" placeholder="Rechercher un article ">
				</div>
					<input type="submit" name="submit2" class="btn btn-default" value="ok">
				 </form>
			 
        </div>
		
      </div>
    </div>
	
          <div class="jumbotron">
            <center><h1>Bienvenue sur le site</h1><span text-align="right">*voir le panier</span></center>
          </div></br>     
	 
		  			<div class="container">
					<div class="row">
					  <div class="col-md-2"><strong>Référence</strong></div>
					  <div class="col-md-2"><strong>Désignation</strong></div>
					  <div class="col-md-4"><strong>Description</strong></div>
					  <div class="col-md-1"><strong>Quantité</strong></div>
					  <div class="col-md-1"><strong>Prix</strong></div> 
					  <div class="col-md-1"><strong>+/-</strong></div>
					  <div class="col-md-1"><strong>Panier</strong></div>
					 
					</div>
					</div>
					
					 <?php
					//enumeration des modes possibles dans la nav
					//Remarque: si on clique sur trierpar et ok ça non accepté
					
					//securité
					$rechercher=NULL;
					$categorie=NULL;
					$trierpar=NULL;
					$securite=0;
					//on remplis que l'onglet recherche
					if (isset($_POST['rechercher']) and isset($_POST['submit2'])){
						$securite = 1;
						$rechercher=$_POST['rechercher'];
						$requete = "SELECT * from PRODUIT P where QUANTITE_EN_STOCK > 0 and DESIGNATION LIKE '%$rechercher%' ";
					}
					if(isset($_POST['categorie']) and isset($_POST['submit'])){
						//si on remplis que la catégorie
						$categorie=$_POST['categorie'];
						$securite = 1;
						$requete = "SELECT * from PRODUIT P, APPARTIENT A, CATALOGUE C where P.reference = A.reference and A.numero_catalogue = C.numero_catalogue and P.QUANTITE_EN_STOCK > 0 and nom_catalogue = '$categorie'";	
					}
					if (isset($_POST['categorie']) and isset($_POST['trierpar']) and isset($_POST['submit'])){
						//si on remplis la catégorie ET le trier par
						$securite = 1;
						$categorie=$_POST['categorie'];
						$trierpar=$_POST['trierpar'];
						//par défaut on a la liste de tous les produit qui apparaissent dans un ordre aléatoire sinon on choisis 
						//de paufiner l'analyse avec 'trierpar'
						if($categorie == 'Par_Defaut'){
							$securite = 1;
							$requete = "SELECT * FROM produit ORDER BY $trierpar";
						}
						else{
						$requete = "SELECT P.REFERENCE, DESIGNATION, DESCRIPTIF, QUANTITE_EN_STOCK, PRIX, NOM_CATALOGUE  from PRODUIT P, APPARTIENT A, CATALOGUE C where C.NUMERO_CATALOGUE = A.NUMERO_CATALOGUE and A.REFERENCE = P.REFERENCE and P.QUANTITE_EN_STOCK > 0 and NOM_CATALOGUE='$categorie' group by NOM_CATALOGUE, P.REFERENCE ORDER BY $trierpar";
						}
					}
					if($securite == 1) {
						$mysqli = new mysqli("localhost","root","","e-commerce2");
						$query=$requete;
						$resultat = $mysqli->query($query);
						$nb_lignetot = $resultat->num_rows;
							//gestion du tableau vide
							if($nb_lignetot == 0){
								echo '<center><h1>'.'Désolé aucun produit ne correspond à votre recherche'.'</h1></center>';
							}
						else{
						$ligne = 0;
						for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
							$column = $resultat->fetch_row();
						?>
		
						<?php include('visu.php'); ?>
						
					
						<?php
							}
							echo '<input id="voir_panier" type="submit" name="submit3" class="btn btn-info" style="width:100%" value="Voir les produits choisis"></form>';
							
							
							
							$securite = 0;
							
						   }
						         }						
							else {
								echo '<center><h1>'.'Veuillez sélectionner une catégorie'.'</h1></center>';
							}
					?>	
										
					
					
					

					<?php
					if(isset($_POST['submit3']) and isset($_GET['mail']) and isset($_GET['mdp'])){
						$mail=$_GET['mail'];
						$mdp=$_GET['mdp'];
						
						
						if(!empty($_POST['check_list'])) {
							//nombre de checkbox selectionnées.
							$compter = count($_POST['check_list']);
							$refs = "";
							$i = 0;
							echo "<div style='background-color:#eee' class='jumbotron'><p><strong>Ma sélection:</strong></p>";
							foreach($_POST['check_list'] as $selected) {
								$i++;
								$mysqli = new mysqli("localhost","root","","e-commerce2");
								$requete="SELECT DESIGNATION, PRIX FROM PRODUIT WHERE REFERENCE='$selected'";
								$refs .= "ref=";
								$refs .= $selected;
								if($i < $compter)
									$refs .= '&';
								/*$longueur_chaine=strlen($refs);
								for ($i = 0 ; $i < $longueur_chaine - 1 ; $i++){
									$refs_url[$i]=$refs[$i];
								}*/
								//for ($i=0;$i<count($refs_url);$i++) {
								//echo $refs_url[$i];
								//}
								
								//echo $refs;
								$resultat = mysqli_query($mysqli, $requete);
								$nb_lignetot = mysqli_num_rows($resultat);
								$ligne = 0;
								
								for($ligne=0;$ligne<$nb_lignetot;$ligne++) {	
									$column = $resultat->fetch_row(); 
									//echo $categorie_choix.'.php?'.'voiture='.$column['1'];
									
								?>
						
								
								<p style="border:2px">-- <?php echo $column["0"]?> -- <?php echo $column["1"].' €'?></p>
							<?php	
							}
						}?>	
						<a href='<?php echo 'Commande.php?mail='.$mail.'&mdp='.$mdp.'&'.$refs ?>' style="color:black">Gérer le contenu du panier</a>

						<button class="btn btn-warning"><a href="home.php" style="color:white">Annuler</a></button>

						<?php
						}
						else{
							echo "<b>Aucun produit choisis.</b>";
							echo 'Veuillez vous connecter ou créer un compte pour voir votre panier';
						}
					}
					?>					
				 </br></br>	

</body>
</html>