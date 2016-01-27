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
          <p class="navbar-brand"><strong style="color:green">E</strong>stupina<strong style="color:green">R</strong>affin<strong style="color:green">P</strong>atry &amp; Co</p>
                

	</div>
	<div class="collapse navbar-collapse">
	<ul class="nav navbar-nav">
	<li ><a href="home.php">Accueil</a></li>
	<li class="active"><a href="">Mon panier</a></li>
	</ul>  
	</div>
		
      </div>
    </div>
	
        <div class="jumbotron">
        <center><h1>Bonjour <strong><?php echo $_GET['mail']?></strong></h1></center>
        </div></br>  
	
	<div class="col-sm-8">
	<div class="form-inline">
	<p><strong><center>Contenu de votre panier</strong> (*Dans la limite des stocks disponibles)</center></p>
	<form role="form" action="" method="post" >
	
	<!--on recupere toute l'url-->
	<?php $adresse = "http://".$_SERVER['SERVER_NAME'].$_SERVER["REQUEST_URI"];
	$_SESSION['adresse'] = $adresse;
	$longueur=strlen($adresse);
	$index = strpos($adresse, "ref=");
	$nombre = 0;
	while($index<$longueur){
		$reference= substr($adresse, $index+4, 12);
		$index +=17;
		$nombre += 1;
		//echo '</br>'.$reference;
		$mysqli = new mysqli("localhost","root","","e-commerce2");
		$requete="SELECT DESIGNATION, PRIX, QUANTITE_EN_STOCK FROM PRODUIT WHERE REFERENCE='$reference'";
		$resultat = mysqli_query($mysqli, $requete);
		$nb_lignetot = mysqli_num_rows($resultat);
		if($nb_lignetot == 0){
			echo '<center><h1>'.'Problème'.'</h1></center>';
		}
		else{
		$ligne = 0;
		for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
			$column = $resultat->fetch_row();
						?>
						
	
		<div class="form-group">
		<label>Produit <?php echo $nombre; ?>: </label>
		<input name="hidden[]" type="HIDDEN" value="<?php echo $reference ?>"> 
		<input name="Designation[]" type="text" disabled="disabled" class="form-control" placeholder="Nom produit" value="<?php echo $column["0"];?>" required >
		<label>Prix unitaire: </label>
		<input name="Prix" type="text"  disabled="disabled" class="form-control" placeholder="Prix" value="<?php echo $column["1"];?> €" required >
		<label>Choisir une quantité<strong> *</strong>: </label>
		<input name="Quantite[]" class="form-control" type="number"  min="1" placeholder="Quantité" max="<?php echo $column["2"];?>" >
		
		
		</div></br>
		
		<?php }
		}
		}
		?>

	<!--boucle pour retourner les produits sélectionnés-->
	
				</br><input name="submit2" class="btn btn-success" type="submit"  value="Valider" placeholder="Quantité">&nbsp;<strong>puis</strong>&nbsp;<input type="submit" name="submit3"class="btn btn-warning" value="Passer la commande">
				</br></br><strong><p style="font-size:small">En cliquant sur <i>Passer la commande</i> vous obtiendrez le détail de votre commande</p></strong>
				</div>
				</div>
				
				
				
				<div class="col-md-4">	 
				<center><label>Pour valider veuiller remplir les champs suivant</label></center>
				
				<label>Date de livraison</label>
				<!--code php pour retourner toutes les références et savoir si elles appartiennent a plusieurs catalogues-->
				<input name="date_commande" type="date"  class="form-control" value='<?php echo $_POST['date_commande']; ?>' placeholder="Date de Livraison" >	
				<label>Adresse de livraison</label>
				<input name="lieu_commande" type="text"  class="form-control" value='<?php echo $_POST['lieu_commande'];?>' placeholder="Adresse de Livraison" >	
				</form></br>
				</div>
			<!--la personne qui valide doit remplir le formulaire à droite sinon alerte-->
			<?php
			if(isset($_POST["submit2"]) and isset($_POST["date_commande"]) and isset($_POST["lieu_commande"]) and isset($_POST["Quantite"])){
				if(!empty($_POST["date_commande"]) and !empty($_POST["lieu_commande"]) and !empty($_POST["Quantite"])){
				$securite=0;
			//I-insert dans COMMANDE
				//1-but récupérer le numéro du client
				$mail=$_GET["mail"];
				$mdp=$_GET["mdp"];
				$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
				$chercher="SELECT NUMERO_CLIENT AS NUM FROM client WHERE MAIL='$mail' and Mot_De_Passe='$mdp'";
				$resultat = mysqli_query($connexion_choix, $chercher);
				while ($ligne = $resultat->fetch_assoc()) {
					$num_client = $ligne["NUM"];	
				}
				mysqli_close($connexion_choix);
				//echo $num_client;
				
				//2-on génére un numéro de commande 
				$numero_reference = reference_nombre2(19);
				$num_commande = $numero_reference;
				
				//cree url
				
				
				
				
				//3-recuperation de la date de livraison
				$date_livraison=$_POST["date_commande"];
				
				//4-recuperation du lieu de livraison
				$lieu_livraison=$_POST["lieu_commande"];
				
				//5-Montant valeur par défaut = 0
				//6-valider valeur par défaut
				
				$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
				$insert="INSERT INTO commande(NUMERO_COMMANDE, NUMERO_CLIENT, DATE_LIVRAISON, ADRESSE_LIVRAISON, VALIDE, MONTANT) VALUES ('$num_commande','$num_client','$date_livraison','$lieu_livraison','','')";
				$resultat = mysqli_query($connexion_choix, $insert);
				mysqli_close($connexion_choix);
				
			//II-insert dans est_contenu
				
				//on récupérer les références des produits
				//pour chaque reference faire un insert avec le numéro de commande (ci-dessus) et avec la quantité
				
				$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
				foreach ($_POST['hidden'] as $index => $hidden) {
					$data1 = $hidden;
					$data2 = $_POST['Quantite'][$index];
					$requete="INSERT INTO est_contenu (REFERENCE, NUMERO_COMMANDE, QUANTITE) VALUES ('$data1','$num_commande','$data2')";
					mysqli_query($connexion_choix, $requete );
				}
				mysqli_close($connexion_choix);
				$securite=1;
				
				
			}
			echo 'remplir le formulaire pour valider et chosiir une quantité positive';
			$securite=1;
			}
			?>
			
			<?php
			if(isset($_POST["submit3"]) and isset($_POST["date_commande"]) and isset($_POST["lieu_commande"]) and isset($_POST["Quantite"])){
			$lieu_commande=$_POST["lieu_commande"];
			$date_commande=$_POST["date_commande"];
			
			
				
				
			
			
			}
			
			
			?>
		
	</body>
</html>  
		  