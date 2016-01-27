<?php
include("config.php");

// Retourne le titre du blog
function blogTitle() {
	return $GLOBALS['blogTitle'];
}

// Permet de vérifier qu'un utilisateur est authentifié. 
function testAcces() {
  if (!isset($_SESSION['connecte'])) die("Accès interdit.");
}

// Établi une connexion au serveur
function con1() {
  return mysqli_connect($GLOBALS['dbServ'], $GLOBALS['dbUser'], $GLOBALS['dbPass']);
}

// Établi une connexion à la base de données
function con() {
  return mysqli_connect($GLOBALS['dbServ'], $GLOBALS['dbUser'], $GLOBALS['dbPass'], $GLOBALS['dbName']);
}

// Permet de vérifier le mail et le mot de passe
function auth($mail,$pass) {
  $con = con();
  $res = mysqli_query($con , "SELECT * FROM utilisateurs WHERE mail='$mail' AND motdepass='$pass'");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
}

// Retourne la liste de toutes les réponses d'une question triés par ordre de votes décroissantes
function reponses($question) {
  $con = con();
  $res = mysqli_query($con, "SELECT * FROM reponses WHERE question = '$question' ORDER BY nbrvotes DESC;");
  $assoc = mysqli_fetch_all($res, MYSQLI_ASSOC);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne une réponse en fonction de son id
function reponse($id) {
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT * FROM reponses WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'i', $id);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Ajoute une réponse
function ajoutReponse($question, $auteur, $contenu) {
  $con = con();
  $stmt = mysqli_prepare($con, "INSERT INTO reponses (question,auteur,contenu, nbrvotes) VALUES (?,?,?,0)");
  mysqli_stmt_bind_param($stmt, 'iis', $question, $auteur, $contenu);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Supprime une réponse
function supprReponse($id) {
  $con = con();
  $stmt = mysqli_prepare($con, "DELETE FROM reponses WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'i', $id);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Édite une réponse 
function editeReponse($id,$contenu) {
  $con = con();
  $stmt = mysqli_prepare($con, "UPDATE reponses SET  date = CURRENT_TIMESTAMP, contenu = '$contenu', nbrvotes = 0 WHERE id = '$id'");
  mysqli_stmt_bind_param($stmt, 'is',$id, $contenu);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Retourne la liste des questions
function questions() {
  $con = con();
  $res = mysqli_query($con, "SELECT * FROM questions ORDER BY date DESC;");
  $assoc = mysqli_fetch_all($res, MYSQLI_ASSOC);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne une question en fonction de son id
function question($id) {
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT * FROM questions WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'i', $id);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne la liste des questions sans réponse
function questions_sans_reponses() {
  $con = con();
  $res = mysqli_query($con, "SELECT * FROM questions WHERE questions.id NOT IN (SELECT question FROM reponses)  ORDER BY date DESC;");
  $assoc = mysqli_fetch_all($res, MYSQLI_ASSOC);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne la liste des questions d'un auteur
function me_questions($id) {
  $con = con();
  $res = mysqli_query($con, "SELECT * FROM questions WHERE auteur='$id'  ORDER BY date DESC;");
  $assoc = mysqli_fetch_all($res, MYSQLI_ASSOC);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Ajoute une question
function ajoutQuestion($auteur,$contenu) {
  $con = con();
  $stmt = mysqli_prepare($con, "INSERT INTO questions (auteur,contenu) VALUES (?,?)");
  mysqli_stmt_bind_param($stmt, 'is', $auteur, $contenu);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Édite une question
function editeQuestion($id,$contenu) {
  $con = con();
  $stmt = mysqli_prepare($con, "UPDATE questions SET  date = CURRENT_TIMESTAMP, contenu = '$contenu' WHERE id = '$id'");
  mysqli_stmt_bind_param($stmt, 'is',$id, $contenu);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Retourne un utilisateur en fonction de son id
function userfromid($id) {
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT * FROM utilisateurs WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'i', $id);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne un utilisateur en fonction de son mail
function user($mail) {
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT * FROM utilisateurs WHERE mail = ?");
  mysqli_stmt_bind_param($stmt, 's', $mail);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Retourne l'utilisateur qui a posé une question
function utilisateurfromquestion($questionid){
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT auteur FROM questions WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'i', $id);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Vérifie si un mail existe déjà
function utilisateurexiste($mail){
  $con = con();
  $res = mysqli_query($con , "SELECT * FROM utilisateurs WHERE mail='$mail'");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
}

// Vérifie si un pseudo existe déjà
function utilisateurexiste1($pseudo){
  $con = con();
  $res = mysqli_query($con , "SELECT * FROM utilisateurs WHERE pseudo='$pseudo'");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
}

// Ajoute un utilisateur
function ajoutUtilisateur($mail, $pass, $pseudo) {
  $con = con();
  $stmt = mysqli_prepare($con, "INSERT INTO utilisateurs (mail, motdepass, pseudo) VALUES (?,?,?)");
  mysqli_stmt_bind_param($stmt, 'sss', $mail, $pass, $pseudo);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Permet de savoir si un utilisateur a déjà voté pour l'une des réponses d'une question
function dejavote($question,$auteur){
  $con = con();
  $res = mysqli_query($con , "SELECT * FROM votes WHERE question='$question' AND auteur='$auteur'");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
}

// Permet de savoir si un utilisateur a déjà voté pour une certaine réponse
function dejavote1($reponse,$auteur){
  $con = con();
  $res = mysqli_query($con , "SELECT * FROM votes WHERE reponse='$reponse' AND auteur='$auteur'");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
}

// retourne le vote d'un utilisateur pour une question
function dejavote2($question,$id) {
  $con = con();
  $stmt = mysqli_prepare($con, "SELECT * FROM votes WHERE question = ? AND auteur = ?");
  mysqli_stmt_bind_param($stmt, 'ii', $question, $id);
  mysqli_stmt_execute($stmt);
  $res = mysqli_stmt_get_result($stmt);
  $assoc = mysqli_fetch_assoc($res);
  mysqli_free_result($res);
  mysqli_close($con);
  return $assoc;
}

// Permet d'ajouter un vote 
function ajoutVote($auteur, $question, $reponse) {
  $con = con();
  $stmt = mysqli_prepare($con, "INSERT INTO votes (auteur, question, reponse) VALUES (?,?,?)");
  mysqli_stmt_bind_param($stmt, 'iii', $auteur, $question, $reponse);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Permet de mettre à jour le nombre de votes après ajout
function ajoutVote1($reponse, $nbrvotes) {
  $con = con();
  $stmt = mysqli_prepare($con, "UPDATE reponses SET nbrvotes = ?+1 WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'ii', $nbrvotes, $reponse);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Supprime un vote d'un auteur
function supprVote($auteur,$reponse) {
  $con = con();
  $stmt = mysqli_prepare($con, "DELETE FROM votes WHERE auteur = ? AND reponse = ? ");
  mysqli_stmt_bind_param($stmt, 'ii', $auteur, $reponse);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// Met à jour le nombre de votes après supression
function supprVote1($reponse, $nbrvotes) {
  $con = con();
  $stmt = mysqli_prepare($con, "UPDATE reponses SET nbrvotes = ?-1 WHERE id = ?");
  mysqli_stmt_bind_param($stmt, 'ii', $nbrvotes, $reponse);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}

// supprime tous les votes d'une réponse en cas d'edition ou de supression de cette dérnière
function supprVote2($reponse) {
  $con = con();
  $stmt = mysqli_prepare($con, "DELETE FROM votes WHERE reponse = ? ");
  mysqli_stmt_bind_param($stmt, 'i', $reponse);
  mysqli_stmt_execute($stmt);
  mysqli_close($con);
}
//compte le nombre de points de chaque utilisateurs
function pointsGagne($question,$auteur){
  $con = con();
  $res = mysqli_query($con , "select count(id) from votes where auteur=''");
  $row = mysqli_fetch_array($res);
  if($row==NULL){
   return FALSE;}
  return TRUE;
  mysqli_close($con);
  }