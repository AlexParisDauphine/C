<?php 
  include("functions.php");
  session_start();
  testAcces();
?>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href='./bootstrap.css' rel='stylesheet'>
    <link href='./offcanvas.css' rel='stylesheet'>
    <title><?php echo blogTitle(); ?></title>
    <style type='text/css'>
      .c{font-size: 20px;} 
      .v{text-align: center;}
      #retour{margin-left:650px;}
      #oui{margin-left:600px;}
      #non{margin-left:100px;}
    </style>
  </head>
  <body>
    <?php 
      if(isset($_GET['vote'])) {
        $reponse=reponse($_GET['vote']);
        if($_SESSION['userid']==$reponse['auteur']){
        die("Accès interdit.");
        }
        $nbrvotes=$reponse['nbrvotes'];
        $question=$reponse['question'];
        $vote=$_GET['vote'];
        if(dejavote($question,$_SESSION['userid'])){ 
      	  if(dejavote1($_GET['vote'],$_SESSION['userid'])){
      	    echo "<h3 class='v'>Vous avez déjà voté pour cette réponse </h3>
                  <a class='c' id='retour' href='back_reponses.php?questionid=$question'>retour</a>";
          }else{
            echo "<h3 class='v'>Vous avez déjà voté pour une autre réponse, est ce que vous voulez modifier votre vote?</h3>
                  <a class='c' id='oui' href='back_votes_edit.php?vote=$vote'>oui</a>
                  <a class='c' id='non' href='back_reponses.php?questionid=$question'>non</a>";
          }
        }else{
          ajoutVote($_SESSION['userid'], $question, $_GET['vote']);
          ajoutVote1($_GET['vote'],$nbrvotes);
          header("Location: back_reponses.php?questionid=$question");
        }
      }
    ?>
  </body>
</html>  