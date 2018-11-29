=====
= RMI
=====

RMI (Remote Method Invocation) est un mécanisme permettant à plusieurs JVMs /
processus Java de communiquer selon le paradigme RPC (Remote Procedure Call).
Plus précisément, avec RMI, il devient possible d'invoquer depuis une JVM une
méthode d'un objet distant, hébergé par une seconde JVM, potentiellement
exécutée sur une machine géographiquement éloignée de la première. 

=========
= Un exemple simple
=========

Jetons un coup d'oeil à l'exemple fourni. Prenez votre temps pour étudier
chaque fichier et de comprendre chaque partie des programmes proposés.  La
classe 'AdderImpl' fournit la méthode 'int add(int x,int y)' qui retourne la
somme des entiers passés en paramètre. Cette méthode est rendue accessible
depuis l'extérieur du programme Java la contenant: elle peut être invoquée
depuis un autre programme Java lancé dans une autre JVM.

Décrivons rapidement le contenu du projet 'Adder'.


  - Adder.java est l'interface du service

  - AdderImpl.java est l'implémentation du service. Au-delà de la fonction
'add(...)' elle-même, AdderImpl fournit la machinerie du serveur: elle crée un
registre pour contenir une description du service 'adder'. Plus précisément, ce
registre va contenir le 'stub', c'est-à-dire une petite portion de code Java
simulant le présence du service localement et qui va rendre transparent la
communication avec le serveur. Le 'stub' est créé par le serveur et mis à
disposition des clients dans ce registre.
    
  - Client.java est un programme simple qui va invoquer la méthode distante
'add(...)'

  - adder.policy spécifie les droits à donner aux processus cherchant à accéder
au registre.

============
= Déploiement
============

Pour tester en ligne de commande (Eclipse n'est pas nécessaire), compilons
d'abord les classes Java dans le répertoire dans lequel vous aurez copié les
sources: 

  $ javac -cp . *.java

Démarrons le serveur avec la ligne suivante (assurez-vous d'avoir
'adder.policy' dans ce même répertoire):
 
  $ java -Djava.rmi.server.hostname=localhost -Djava.security.policy=adder.policy AdderImpl

Le serveur attend de potentiels invocations depuis une autre JVM. Démarrons le
client:

  $ java -Djava.rmi.server.hostname=localhost -Djava.security.policy=adder.policy Client


=================================================
= Un nouveau programme: Achat de billets en ligne 
=================================================

C'est votre tour de développer une application à base de RMI pour gérer un
ensemble de billets de train en ligne.

    - Ecrivez l'interface du service: 'TicketMachine.java'. Ce service doit
fournir deux méthodes: 1) 'bookTickets(int nb)' pour acheter des billets et 2)
'addNewTickets(int nb)' pour rajouter des billets dans le système lorsqu'il
viendrait à en manquer. Dans cette première version, 'bookTickets' retourne
'true' si le nombre de billets demandé est inférieur ou égal à ce qu'il reste
dans le système à ce moment là, 'false' sinon.

    - Ecrivez l'implémentation de TicketMachine. Rajoutez la machinerie pour
que cette classe puisse lancer le serveur contenant une 'TicketMachine'.

    - Ecrivez le client, démarrez le serveur et lancer un ou plusieurs clients.
Vérifiez le comportement de votre programme.

    - Développez et lancez un ou plusieurs processus Managers rajoutant des
billets si nécessaire (par exemple qui vérifient que le nombre de billets
n'atteint pas zéro.)

    - Résolvez les problèmes de synchronisation éventuels.

======================
= Pour aller plus loin 
======================

Rendons les clients un peu plus patients: modifiez la fonction de réservation
pour qu'un client qui commande plus de billets qu'il n'y en a de disponible
attende qu'un manager remettent suffisamment de billets pour qu'il puisse être
satisfait. Démarrez plusieurs clients et plusieurs managers. Vérifiez que tout
se passe bien.

