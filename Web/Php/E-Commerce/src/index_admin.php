

<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
    <title></title>
  </head>
  <body>
   
      <form name='log' method='post' class="form-signin" role="form" action=''>
        <center><h2 class="form-signin-heading">Se connecter</h2></center>	
        <input type='email' name='mail' class="form-control" placeholder="Adresse mail" required autofocus>
	<input type='password' name='mdp' class="form-control" placeholder="Mot de passe" required>
        <input type="submit" name="submit" class="btn btn-lg btn-primary btn-block" value="Se connecter" />
      </form> 
      <?php
      if(isset($_POST["mail"]) and isset($_POST["mdp"])){
	//vérifier que ça existe dans la base
	$mail=$_POST["mail"];
	$mdp=$_POST["mdp"];
	$connexion_choix = new mysqli("localhost","root","", "e-commerce2");
	$requete="SELECT MAIL, Mot_De_Passe from client WHERE MAIL='$mail' and Mot_De_Passe='$mdp' ";
	$resultat = mysqli_query($connexion_choix, $requete);
	if($resultat==1){
		header('Location:home.php?mail='.$mail.'&mdp='.$mdp.'');
	}
	else{
	echo 'mail et mot de passe innexistant';
	}
	}
	
      
      ?>
  
      
</body>
</html>