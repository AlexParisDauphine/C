  
<?php 

	
   
	
  include("functions.php");
  session_start();
  testAcces();

  if (isset($_POST['contenu'])) {
   if (isset($_POST['edite'])) {
      $questionid=$_POST['edite'];
      $question=question($_POST['edite']);
      if($_SESSION['userid']!=$question['auteur']){
        die("Accès interdit.");
      }
      editeQuestion($_POST['edite'], $_POST['contenu']);
      $reponses = reponses($questionid);
      foreach ($reponses as $reponse) {
        supprVote2($reponse['id']);
        supprReponse($reponse['id']);
      }
      header("Location: back_reponses.php?questionid=$questionid");     
      die();
   }else{
    if ($_POST['contenu']=="" ) {
      $msg="Veuillez remplir le champ ci-dessous";
    }
    if ($_POST['contenu']!="") {
	$Cdc = $_POST['contenu'];
	
	
   
	//$MauvaisMots = array("con","Pute","connard","pass","stack","name","html");
	$MauvaisMots = array("connard","conard","connart","musulman","madoff","pédale","pédales","con","cons","pute","putes","chier","chié","chiers","batard","batart","batards","batardes","batarde","biatch","salaud","salauds","merde","merdiques","caca","crote","putain","fuck","bite","cul","tg","groslard","enculé","encule","salopart","salopard","bâfre","islamiste","terroriste","islamistes","islam","coran","bible","chrétiens","chrétien","sexe","sexes","religions","religion","blancbec","bouffonade","Juif","juifs","hébreux","sadique","bique","politique","politiques","Hollande","sarkozy","Sarkozy","hollande","République","république","Répuliques","républiques","Boudhiste","boudhistes","Israel","israel","allumé","allumeuse","fric","mmerde","mmerde","rasta","rastaman","secte","sectaire","beuglant","bouffon","richard","ISF","homo","pd","pédé","pédérastes","pédéraste","homophobe","homosexusel","homosexuelle","eiil","fn","ump","ps","modem","merde","hitler","nazis","nazi","faciste","fascistes","mecreant","mecreants","mécréant","mécréants","voyous","voyou","antisémite","antisemites","nègre","negres","negre","amerloque","boche","espingouin","meteque","djihadiste","djihadistes","djihad","négrito","métèque","youpin","youtre","rital","negro","négro","chleuh","bicot","femmelette","flic","flic","couilles","couille","Branleur","crétin","crétine","Emmerdeur","Emmerdeuse","Hydrocéphale","Lopette","Couillon","couillonne","Pisseux","Plouc","Pouffiasse","Raclure","foutre","gland","Samovar","Abruti","anticastriste","colonialisme","anachorète","bénédictin","cénobite","chanoine","dévot","dominicain","ecclésiastique","jésuite","prieur","Ayatollah","Allah","fdp","FDP","TG","tg","bougnoule","bougnoules","pekinois","ss","pisseuse","hamas","fnlc","mlp","jmlp","dsk","isis","bullshit","@2m1","a2m1","@l1di","al1di","b1sur","koi29","kwa","pk","vrMen","stp","tomB","dak","komencava","Bolosse","feuj","tepu","teupu","pute","shoa","faurisson","dieudonne","dieudo","shoah","alya","fniste","umpiste","socialiste","fnistes","socialistes","umpistes","centristes","centriste","chintok","pékinois","saloperie","merdique","merdasse","pute","fuck","claramorgane","wesh","tepu","bicrave","bolos","bedave","gogol","gogole","atouchement","attouchement","baise","bande","bisexualit","bite","blocage","blondes","bondage","bouche","bourrin","branle","branlette","caca","calins","capote","caresses","chastet","chate","chatte","clitori","cochon","cockring","coit","cokin","cokine","consentement","contraception","copulation","coquin","coquine","couille","couple","culbut","culote","culotte","cunil","cunnil","cybersexe","dance","desir","désir","domination","downblouse","drague","echangism","erect","érect","erogene","érogène","fantasm","felat","fellat","feminites","féminités","fessee","fessée","fetichi","fétichi","fist","flagellation","foufoune","frigid","fucking","gay","gerontoph","gérontoph","godemich","harcelement","harcèlement","hard","heterosexuel","hétérosexuel","homosexuel","hysteri","hystéri","impuissance","incest","initiatives","injures","intimite","intimité","ivg","jouir","langue","latex","lecher","lécher","lesbien","levres","lèvres","libertin","life-show","machisme","masochisme","massage","masturb","masturbateur","mineure","mst","mytho","nympho","obscen","obscén","onanisme","orgasm","pd","pédé","pederaste","pédéraste","pedophilie","pédophilie","peep- show","penetration","pénétratio","penis","pénis","perversions","phallus","pillule","pilule","porno","pornographie","portabl","précoce","prepuce","prépuce","prostit","pubien","pubis","pucelage","pudeur","pulsion","pute","sadomasochisme","salop","saphisme","sauter,scatophile","seins,sensualité","sex","sexe","sex-shop","sexualit","sexuel","sida","simulation","sodomi","soumission","sperm","strip","sucer","tease","testicul","vagin","verge","viol","virginit","virilit","voyeurisme","vulgarit","vulve","bamboula");
	//print_r ($MauvaisMots);
	$Tableau = array();
	$Trouver = preg_match_all(
                "/\b(" . implode($MauvaisMots,"|") . ")\b/i", 
                $Cdc, 
                $Tableau
              );
	if ($Trouver) {
	echo "Votre message à corriger: <strong>\"$Cdc\"</strong></br></br>";
	$msg="Veuillez ôter ce lexique de ce forum!";
	$mots = array_unique($Tableau[0]);
	foreach($mots as $mots) {
    echo "<li>" . $mots . "</li>";
	}
	echo "</ul></br>";
	
	}
	
	//} 
    else{
      $auteur=$_SESSION['userid'];
      ajoutQuestion($auteur,$_POST['contenu']);
      header("Location: back_questions.php");
     }
   }

}
  }elseif(isset($_GET['edite'])) { 
    $question = question($_GET['edite']);
    if ($question == null) die("question invalide.");
    $id = $question['id'];
    $contenu = $question['contenu'];
  }
?>
<html lang='fr'>
  <head>
    <meta charset='utf8'>
    <link href="./bootstrap.css" rel="stylesheet">
    <link href="./offcanvas.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/Sanstitre.jpg">
    <title><?php echo blogTitle(); ?></title>
    <style type='text/css'>
      #li{margin-left:800px;}      
      #c{color: black; font-size: 20px;}   
      #f{text-align: center;margin-top:80px;}   
      #v{width: 30%; height: 20%;}
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
            <li id='li'><a href="home.php">Accueil</a></li>
            <li><a href="index.php?deconnecte=">Déconnexion</a></li>
          </ul>
        </div>
      </div>
    </div>
    <?php if (isset($msg)) echo "<p>$msg</p>"; ?>
    <form name='question' method='post' id='f'>
      <label id='c' for='contenu'>
        <strong  >Contenu</strong>: 
      </label>
          <?php
             echo "<div>
                     <textarea id='v' name='contenu'>";
            if (isset($contenu)) echo $contenu; 
            echo "</textarea>
            </div>";
            if (isset($id)) {
              echo "<input type='hidden' name='edite' value='$id'>";
            }
            
          ?>
       <input type='submit' name='envoyer'>
    </form>
  </body>
</html>
