<?php
include("functions.php");

$qDb = "CREATE DATABASE IF NOT EXISTS `askaround_g18`;";

$qTbRps = "CREATE TABLE IF NOT EXISTS `reponses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` int(11) NOT NULL,
  `auteur` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contenu` longtext NOT NULL,
  `nbrvotes` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`question`) REFERENCES `questions`(`id`),
  FOREIGN KEY (`auteur`) REFERENCES `utilisateurs`(`id`)
) ENGINE=InnoDB;";

$qTbQst = "CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auteur` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `contenu` longtext NOT NULL,
  PRIMARY KEY(`id`),
  FOREIGN KEY (`auteur`) REFERENCES `utilisateurs`(`id`)
) ENGINE=InnoDB;";

$qTbUsr = "CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) NOT NULL,
  `pseudo` varchar(255) NOT NULL,
  `motdepass` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;";

$qTbVot = "CREATE TABLE IF NOT EXISTS `votes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auteur` int(11) NOT NULL,
  `question` int(11) NOT NULL,
  `reponse` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`reponse`) REFERENCES `reponses`(`id`),
  FOREIGN KEY (`auteur`) REFERENCES `utilisateurs`(`id`),
  FOREIGN KEY (`question`) REFERENCES `questions`(`id`)
) ENGINE=InnoDB;";

$qInitTbMax = "INSERT INTO utilisateurs (mail, pseudo, motdepass) VALUES ('max@max.max', 'Max', 'max');";
$qInitTbBob = "INSERT INTO utilisateurs (mail, pseudo, motdepass) VALUES ('bob@bob.bob', 'Bob', 'bob');";
$qInitTbHello1 = "INSERT INTO questions (auteur, contenu) VALUES (1, 'Hello?');";
$qInitTbHello2 = "INSERT INTO reponses (question, auteur, contenu) VALUES (1, 2, 'Hello!');";
$qInitTbVote = "INSERT INTO votes (auteur, question, reponse) VALUES (1, 1, 1);";
$qInitTbVote1 = "UPDATE reponses SET nbrvotes=1 WHERE id=1;";

echo "Connexion au serveur MySQL.";

$con = con1();

echo "création de la base de données askaround_g18";
mysqli_query($con,$qDb);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Connexion à la base.";

$con = con();

echo "Création de la table utilisateurs.";
mysqli_query($con, $qTbUsr);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de Max.";
mysqli_query($con, $qInitTbMax);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de Bob.";
mysqli_query($con, $qInitTbBob);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de la table questions.";
mysqli_query($con, $qTbQst);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de la table reponses.";
mysqli_query($con, $qTbRps);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de la table votes.";
mysqli_query($con, $qTbVot);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de Hello1.";
mysqli_query($con, $qInitTbHello1);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création de Hello2.";
mysqli_query($con, $qInitTbHello2);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Création du Vote.";
mysqli_query($con, $qInitTbVote);
echo mysqli_info($con);
echo mysqli_error($con);

echo "Mise à jour réponse.";
mysqli_query($con, $qInitTbVote1);
echo mysqli_info($con);
echo mysqli_error($con);

mysqli_close($con);
?>
