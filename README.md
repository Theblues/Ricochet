Ricochet Robot
==============

Installation de l'application Android
---------------------

- Télécharger le fichier __RicochetRobots-android.apk__ (cliquer sur le lien, puis télécharger grâce au bouton __Raw__)
- Copier sur votre téléphone Android (dossier Download)
- Sur votre téléphone, aller dans __Paramètre__ => __Sécurité__ => Cocher __Sources inconnues__
- Aller sur l'application [Astro](https://play.google.com/store/apps/details?id=com.metago.astro&hl=fr) 
- Installer l'application qui ce trouve dans le dossier Download
    
Lien d'un [tutoriel](http://www.commentcamarche.net/faq/38861-android-installer-un-apk) si besoin.

Règle du jeu :
--------------

###Structure du jeu :###


- 4 sections de plateaux
- 5 robots (rouge, vert, jaune, bleu, noir)
- 17 tuiles objectif distribuées en quatre groupes de quatre tuiles de couleur identique à celle d'un robot, et une tuile multicolore
- 1 sablier (1min)
    
###But du jeu###

A chaque tour, vous devez récupérer le pion objectif placé au milieu de plateau. Il existe, sur le plateau, une case objectif identique en couleur et en symbole au pion à récupérer. Votre but est d'imaginer la manière qui permettra au robot de cette couleur de terminer sur cette case en effectuant le moins de mouvement possibles. Le joueur qui effectue le moins de mouvement, récupère le pion, et celui qui possède le plus de pions, gagne la partie.

###Planifier le mouvement des robots###

En début du tour, les robots ne se déplacent que dans l'esprit des joueurs, c'est-à-dire que les joueurs visualisent le parcours le plus court pour parvenir à la case objectif, __sans__ déplacer les robots.
Les robots se déplacent horizontalement ou verticalement, selon le choix du joueur, mais ils ne possèdent pas de frein. Ils s'arrêtent seulement lorsqu'ils rencontrent un robot ou un mur.
Chaque mouvement du robot vers un nouvel obstacle compte pour un déplacement. Quand un robot s'arrête, il peut effectuer son dernier déplacement seulement si un autre robot s'est déplacé.

###Déroulement d'un tour###

* Le tour commence lorsqu'un pion objectif est placé au centre du plateau.
* Chaque joueur essaie alors d'imager de quelle façon le robot actif (de même couleur que le pion) pourra se rendre sur la case objectif (même couleur, même symbole) en effectuant le moins de déplacement possibles.
* Si le pion central est le le pion multi-couleurs alors tous les robots peuvent se rendre sur la case.
* Afin de se déplacer vers l'objectif, le robot actif doit touchet et rebondir au moins une dois sur un obstacle.  S'il a la possibilité  de se rendre directement sur la case objectif, sans rebondir , il doit choisir un autre chemin.
* Dès qu'un joueur a trouvé une solution, il parie le nombre de mouvements trouvés et déclenche le sablier. les autres joueurs ont maintenant une minute pour faire leur paris. Il n'y a pas d'ordre à suivre pour les formuler et les joueurs peuvent en proposer plusieurs seulement pour une valeur plus petit.
* Lorsque le temps est écoulé, le plus petit pari joue le tour. Il déplace les robots tel qu'il l'a planifié, et compte le nombre de ses mouvements. Si ce nombre est inférieur ou exact à son pari, il remporte le tour. S'il échoue, il replace les robots à leur place initial et le joueur ayant le pari immédiatement supérieur joue à sa place. En cas d'égalité, le joueur le moins bien placé dans la partie, à la priorité. Cela continue tant qu'un joueur n'a pas réussi son pari. Si aucun joueur ne réussi, personne ne récupère le pion, que l'on remmélange avec les autres pions restant.
* Dès qu'un tour est terminé, un autre commence. Piochez un nouveau pion objectif que vous placerez au centre du plateau.

###Fin du jeu###

A 2 joueurs, la partie s'arrête lorsqu'un joueur récupère 8 pions objectif.


###Remarque :###

Si au bout de 5 minutes, aucune solution n'est trouvée, nous vous conseillons de retourner le sablier et d'attendre une minute. Si la situation n'évolue pas, remettez le pion avec les autres et mélangez l'ensemble afin de piocher un nouveau pion.
