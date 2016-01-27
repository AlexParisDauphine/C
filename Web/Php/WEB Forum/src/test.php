    <?php
    $tableau = array();
    $handle = @fopen("motsinterdits.txt", "r" );
    if ($handle)
    {
       while (!feof($handle))
       {
         $buffer = fgets($handle, 4096);
         $tableau[] = $buffer;
       }
       fclose($handle);
    }
    if ($tableau)
    {
       echo $tableau;
    }
    else
    {
       echo "Le remplissage du tableau a échoué";
    }
    ?>
