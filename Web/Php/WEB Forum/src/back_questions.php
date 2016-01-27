<?php 
  include("functions.php");
  session_start();
  testAcces();
?>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="./bootstrap.css" rel="stylesheet">
    <link href="./offcanvas.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title><?php echo blogTitle(); ?></title>
    <style type='text/css'>
      #li{margin-left:500px;}
      #c{color: blue;font-size: 20px;}
      #r{font-size: 20px;}      
    </style>
  </head>
  <body>
    <div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <p class="navbar-brand">Mathssupport</p>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="home.php">Accueil</a></li>
            <li><a href="back_questions_form.php">Poser une question</a></li>
            <li class="active"><a href="back_questions.php">Toutes les questions</a></li>
            <li id='li'><a href="index.php?deconnecte=">Déconnexion</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="container">
      <h2 >Toutes les questions:</h2>
      <?php
        $questions = questions();
        foreach ($questions as $question) {
          $contenu=$question['contenu'];
          $questionid = $question['id'];
          $user=userfromid($question['auteur']);
          $pseudo=$user['pseudo'];
          echo "<div class='list-group'>
                  <a id='r' class='list-group-item' href='back_reponses.php?questionid=$questionid'>
                    <strong id='c' >$pseudo:&nbsp;&nbsp;&nbsp;</strong>
                    $contenu
                  </a>
                </div>";
        }
      ?>
    </div>
  </body>
</html>
