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
	<li class="active"><a href="home.php">Mise à jour</a></li>
	<li><a href="home.php">Acceuil</a></li>
	</ul>  
	</div>
		
      </div>
    </div>
	
        <div class="jumbotron">
        <center><h1>Mise à jour de <strong><?php echo $_GET['categorie_choix']?></strong></h1></center>
        </div></br>  

	<!--code pour remplir les champs automatiquement-->
	<!--penser a pouvoir modifier le catalogue plus tard-->
	<?php
	$reference_produit=$_GET['reference_produit'];
	//$table=$_GET['categorie_choix'];
	$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
	$requete_choix="SELECT DESIGNATION, DESCRIPTIF, QUANTITE_EN_STOCK, PRIX  FROM PRODUIT WHERE REFERENCE='$reference_produit' ";
	$resultat = mysqli_query($connexion_choix, $requete_choix);
	$nb_lignetot = mysqli_num_rows($resultat);
	//gestion du tableau vide
	if($nb_lignetot == 0){
		echo '<center><h1>'.'Désolé aucun produit ne correspond à votre recherche'.'</h1></center>';
	}
	else{	
		$ligne = 0;
		for($ligne=0;$ligne<$nb_lignetot;$ligne++) {
			$column = $resultat->fetch_row(); 
			?>
			<div class="col-sm-4">
			<p><strong><center>Mettre à jour</center></strong></p>
				<form role="form" action="" method="post" >
				<div class="form-group">
				<label>Nom produit</label>
				<input name="Designation" type="text"  class="form-control" placeholder="Nom produit" value='<?php echo $column["0"] ?>' required >
				</div>
				<div class="form-group">
				<label>Descriptif</label>
				<textarea name="Descriptif" class="form-control" rows="5" placeholder="Description du produit" ><?php echo $column["1"] ?></textarea>
				</div>
				<div class="form-group">
				<label>Quantité</label>
				<input name="Quantite" class="form-control" type="number"  min="0" placeholder="Quantité" value='<?php echo $column["2"] ?>' required >
				</div>
				<div class="form-group">
				
				<div class="form-group">
				<label>Prix en €</label>
				<input name="Prix" type="number" step="0.01" class="form-control" min="0" placeholder="Prix" value='<?php echo $column["3"]?>' required >
				</div>
				<input name="submit" type="submit" class="btn btn-info" value="Valider la mise à jour">&nbsp;&nbsp;<input type="submit" name="supprimer"class="btn btn-warning" value="Supprimer l'article">
				</form>
				</div>
			</div>
		
			<?php
			}
	}
	mysqli_close($connexion_choix);
	?>
	
	<!--on récupere les nouvelles valeur du formulaire pour updater-->
	<?php
	$Designation=NULL;
	$Description=NULL;
	$Reference=NULL;
	$Quantite=NULL;
	$Prix=NULL;
	//recuperation de la variable de l'url
	$numero_reference = $_GET['reference_produit'];
	$Reference = $numero_reference;
	//echo $Reference;
	if(isset($_POST['Designation']) && isset($_POST['Descriptif']) && isset($_POST['Quantite']) && isset($_POST['Prix']) && isset($_POST['submit'])){
		if(!empty($_POST['Designation']) && !empty($_POST['Descriptif']) && !empty($_POST['submit']))
		{
		$connexion = new mysqli("localhost","root","", "e-commerce2");
		if (!$connexion) {
			echo 'Problème serveur';
		}	
		else {
			$Designation=$_POST['Designation'];
			//$table=$_GET['categorie_choix'];
			$Descriptif=$_POST['Descriptif'];
			$Quantite=$_POST['Quantite'];
			$Prix=$_POST['Prix'];
			$requete = "UPDATE PRODUIT SET DESIGNATION='$Designation', DESCRIPTIF='$Descriptif', QUANTITE_EN_STOCK='$Quantite', PRIX='$Prix' WHERE REFERENCE='$Reference'";
			$resultat = mysqli_query($connexion, $requete);
			echo 'mise a jour effectuée';
			mysqli_close($connexion);
		     }
		}
	}
	?>	
	<?php
	if(isset($_POST['supprimer'])){
	$table=$_GET['categorie_choix'];
	$supprimer=$_POST['supprimer'];
	//$numero_reference = $_GET['reference_produit'];
	$connexion = new mysqli("localhost","root","", "e-commerce2");
	//faut commencer par supprimer dans la table appartient puis dans la table produit
	$requete1="DELETE FROM APPARTIENT WHERE REFERENCE='$numero_reference'";
	$resultat = mysqli_query($connexion, $requete1);
	if($resultat==1){
		$requete2="DELETE FROM PRODUIT WHERE REFERENCE='$numero_reference'";
		$resultat = mysqli_query($connexion, $requete2);
		echo 'suppression effectuée';
	}
	mysqli_close($connexion);
	
	}
	?>
	</body>
</html>  
		  