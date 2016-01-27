<?php
 include("functions.php");
 session_start(); 
 testAcces();
 $id=$_SESSION['userid'];
 $user=userfromid($id);
 $mail=$user['mail'];
?>
<!--nombre de points-->

<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="./bootstrap.css" rel="stylesheet">
    <link href="./offcanvas.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title><?php echo blogTitle(); ?></title>
    <style type='text/css'>
      #li{margin-left:500px;}  
      #c{font-size: 18px;}     
      #m{color: blue; font-size:20px;}
      #a{margin-left:600px;}   
      #b{font-size: 18px;}  
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
            <li class="active"><a href="home.php">Accueil</a></li>
            <li><a href="back_questions_form.php">Poser une question</a></li>
            <li><a href="back_questions.php">Toutes les questions</a></li>
            <li id='li'><a href="index.php?deconnecte=">Déconnexion</a></li>
          </ul>
        </div>
      </div>
    </div>
    
    <div class="jumbotron">
            <center><h1>Bienvenue sur ce forum</h1></center>
          </div>
	  
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          
          <div class="row">
            <div class="col-6 col-sm-6 col-lg-">
              <h2>Questions sans réponses:</h2>
              <?php
               $questions = questions_sans_reponses();
               foreach ($questions as $question) {
                 $contenu = $question['contenu'];
                 $questionid = $question['id'];
                 $user=userfromid($question['auteur']);
                 $pseudo=$user['pseudo'];
                 echo "<div class='list-group'>
                         <a id='c' class='list-group-item' href='back_reponses.php?questionid=$questionid'>
                           <strong id='m' >$pseudo:&nbsp;&nbsp;&nbsp;</strong>
                           $contenu
                          </a>
                       </div>";
                }
              ?>
            </div>
            <div class="col-14 col-sm-14 col-lg-14" id='a'>
              <h2>Mes questions:</h2>
              <?php                    
                $questions = me_questions($id);
                foreach ($questions as $question) {
                  $contenu = $question['contenu'];
                  $questionid = $question['id'];
                  echo "<div class='list-group'>
                          <a id='b' class='list-group-item' href='back_reponses.php?questionid=$questionid'>
                           $contenu
                          </a>
                       </div>";
                }
              ?>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
