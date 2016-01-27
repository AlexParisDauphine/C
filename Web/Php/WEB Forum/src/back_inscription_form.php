<?php 
  include("functions.php");
  if(isset ($_POST['mail']) && isset ($_POST['pseudo']) && isset ($_POST['pass']) && isset ($_POST['pass1'])){
   if(utilisateurexiste($_POST['mail']) || utilisateurexiste1($_POST['pseudo'])){
     $msg = "Mail ou pseudo déjà existant";
    }else{
      if($_POST['pass1']!=$_POST['pass']){
        $msg = "Mots de passes non identiques";
      }else{
        ajoutUtilisateur($_POST['mail'], $_POST['pass'], $_POST['pseudo']);
        header("Location: index.php");
       }
    }
  }   
?>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="./bootstrap.css" rel="stylesheet">
    <link href="./signin.css" rel="stylesheet">
    <title><?php echo blogTitle(); ?></title>
  </head>
  <body>       
      <?php if (isset($msg)) echo "<h3>$msg</h3>"; ?>
      <form name='log' method='post' action='back_inscription_form.php' class="form-signin" role="form">
        <h2 class="form-signin-heading">Inscription</h2>
        <input type='email' name='mail' class="form-control" placeholder="Adresse mail" required autofocus>
        <br>
        <input type='text' name='pseudo' class="form-control" placeholder="Pseudo" required >
        <br>
        <input type='password' name='pass' class="form-control" placeholder="Mot de passe" required >
        <br>
        <input type='password' name='pass1' class="form-control" placeholder="Retapez mot de passe" required>
        <input type="submit" class="btn btn-lg btn-primary btn-block" value="S'inscrire" />
	 <?php echo "<h4>
                     <a href='index.php'>Déjà inscris</a>
                   </h4>";?>
      </form>
  </body>
</html>