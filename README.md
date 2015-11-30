Projet de gestion des biens par Hayat Bourgi et Guitta Akoury.

Mon email: hayat.bourgi@gmail.com

On a créé  quatre applications:

L'application web “GestionDesBiens" qui gere les biens des sociétés et de les organisations. Dans notre application on a fait la gestion de biens de I-SAE CNAM. On a utiliser le serveur glassfish et pour la sécurité on a utilise les Realms de type form.
	L’utilisateur a des droits d’acces limites:
	Il peut transférer les biens d’une salle a une autre (transaction interne).
	Il peut faire scan pour le qrcode de chaque biens pour voir les détails de ce bien
	L’admin peut accéder a tous les services:
	Il peut creer, éditer, afficher , annuler les centres, les groupes, les biens location, les personnels, les salles, les types, les utilisateurs, les transactions (interne et externe),les transports.
	Il peut savoir la location sur la map  des biens transférés d’un centre a un autre avant leur arrive.
Il peut avoir des rapports sur les biens, les transactions et les transports.
On a utilise le plugin jspdf pour générer les rapports en PDF.
A la creation de chaque bien un qr code sera genere dans un un dossier image sous glassfish-4.1/glassfish/domains/domain1/applications
Pour savoir la location des biens transférés a un autre centre on prend la longitude et la latitude enregistre dans une application sous parse.com et on les utilisent pour afficher le marqueur sur google map.
Pour accéder comme admin le mot de login est admin et mot de pass est admin aussi

-Application android SetLocation pout envoyer les coordonnées (latitude et longitude) a une application cree sous  parse.com 
-Application  android My Map  pour savoir la position sur le map avec les coordonnes déjà enregistres sous parse.com
-Application android GestionDes biens qui a la meme service et fonctionnalité que l’application web GestionsDesBiens talque celle ci est android


