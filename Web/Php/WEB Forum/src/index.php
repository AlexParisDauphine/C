<?php 
  include("config.php");
  include("functions.php");
  session_start(); 
  $connecte = false;
  if (isset($_POST['mail']) && isset($_POST['pass'])) {
    if (auth($_POST['mail'],$_POST['pass'])==true) {
      $_SESSION['connecte'] = true;
      $user=user($_POST['mail']);
      $id=$user['id'];
      $mail=$_POST['mail'];
      $_SESSION['userid']=$id;
      $connecte = true;
    } else {
       $msg = "Adresse mail ou mot de passe incorrecte";
    }
  } elseif (isset($_GET['deconnecte'])) {
     session_destroy();
    }
    elseif (isset($_SESSION['connecte'])) {
     $connecte = true;
    }
?>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="./bootstrap.css" rel="stylesheet">
    <link href="./signin.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title><?php echo blogTitle(); ?></title>
  </head>
  <body>
    <?php if (isset($msg)) echo "<h3>$msg</h3>"; ?>
    <?php if ($connecte == false){ ?>
      <form name='log' method='post' class="form-signin" role="form" action='index.php'>
        <h2 class="form-signin-heading">Connectez-vous</h2>
        <input type='email' name='mail' class="form-control" placeholder="Adresse mail" required autofocus>
        <input type='password' name='pass' class="form-control" placeholder="Mot de passe" required>
        <input type="submit" class="btn btn-lg btn-primary btn-block" value="Connexion" />
        <?php echo "<h4>
                     <a href='back_inscription_form.php'>s'inscrire</a>
                   </h4>";?>
      </form> 
      <?php } else { header("Location: home.php");} ?>
  </body>
</html>
