
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
    <title></title>
  </head>
  <body>
   
      <form name='log' method='post' class="form-signin" role="form" action=''>
        <center><h2 class="form-signin-heading">Créer un compte </br><a href="index_admin.php" style="font-size:small">Déjà inscrit</a></h2></center>
		
		<input type='text' name='nom' class='form-control' placeholder='Nom' required>
		<input type='text' name='adresse' class='form-control' placeholder='Adresse postale' required>
        <input type='email' name='mail' class="form-control" placeholder="Adresse mail" required autofocus>
		 <input type='text' name='tel' class="form-control" placeholder="Téléphone" required>
        <input type='password' name='mdp' class="form-control" placeholder="Mot de passe" required>
        <input type="submit" name="submit" class="btn btn-lg btn-primary btn-block" value="S'inscrire" />
        
      </form> 
	  <?php
		//on rajoute un mdp !
				
		if(isset($_POST['nom']) && isset($_POST['adresse']) && isset($_POST['mail']) && isset($_POST['tel']) && isset($_POST['mdp']) && isset($_POST['submit'])){
			if(!empty($_POST['nom']) && !empty($_POST['adresse']) && !empty($_POST['mail']) && !empty($_POST['tel']) && !empty($_POST['mdp']) && !empty($_POST['submit'])){
			//si tous les champs sont remplis et si mdp n'existe pas déjà, connexion
			//utiliser un trigger serait mieux
			$connexion = new mysqli("localhost","root","", "e-commerce2");
			
			if (!$connexion) {
				echo "Problème serveur";
			}
			
			//récupérer le numéro client le plus élevé de la table
			else {
				$resultat = mysqli_query($connexion, "SELECT MAX(NUMERO_CLIENT) AS dernier_numero_client FROM client");
				while ($ligne = $resultat->fetch_assoc()) {
					$dernier_numero_client = $ligne["dernier_numero_client"];
					
				}
				$dernier_numero_client = $dernier_numero_client + 1;
						
				$nom=$_POST['nom'];
				$adresse=$_POST['adresse'];
				$mail=$_POST['mail'];
				$tel=$_POST['tel'];
				$mdp=$_POST['mdp'];
				//insere la nouvelle personne
				$requete ="INSERT INTO client (NUMERO_CLIENT, NOM_CLIENT, ADRESSE_POSTALE, MAIL, TELEPHONE, Mot_De_Passe) VALUES ('$dernier_numero_client', '$nom','$adresse','$mail','$tel','$mdp')";
				$resultat = mysqli_query($connexion, $requete);
					echo "Votre compte à bien été crée";
					mysqli_close($connexion);
					header('Location:index_admin.php');
					
				}
			
				
			} 
			else {
			echo "Remplissez tous les champs !";
			}
			
		}
			?>
     
  </body>
</html>