# MetroEase

application utilisant une api REST permettant de suivre en temps réel les horaires des métros parisiens

Nation QR Code             |  Chatelet QR Code        |   Gare d'Austerlitz  | Saint Lazare  |
:-------------------------:|:------------------------:|:--------------------:|:--------------:
![nation](https://chart.googleapis.com/chart?cht=qr&chl=M%C3%A9tro%201%2FNation%2FM%C3%A9tro%201%2CM%C3%A9tro%202%2CM%C3%A9tro%206%2CM%C3%A9tro%209%2F%23f9ca24&chs=180x180&choe=UTF-8&chld=L\|2)  |  ![chatelet](https://chart.googleapis.com/chart?cht=qr&chl=M%C3%A9tro%201%2FChatelet%2FM%C3%A9tro%201%2CM%C3%A9tro%204%2CM%C3%A9tro%207%2CM%C3%A9tro%2011%2CM%C3%A9tro%2014%2F%23f9ca24&chs=180x180&choe=UTF-8&chld=L\|2) |![Gare d'Austerlitz](https://chart.googleapis.com/chart?cht=qr&chl=M%C3%A9tro%2010%2FGare%20d'Austerlitz%2FM%C3%A9tro%205%2CM%C3%A9tro%2010%2F%23e67e22&chs=180x180&choe=UTF-8&chld=L\|2) | ![saint lazare](https://chart.googleapis.com/chart?cht=qr&chl=M%C3%A9tro%2014%2FSaint-Lazare%2FM%C3%A9tro%203%2CM%C3%A9tro%2012%2CM%C3%A9tro%2013%2CM%C3%A9tro%2014%2F%23c56cf0&chs=180x180&choe=UTF-8&chld=L\|2)

**Il est obligatoire d'avoir internet (wifi ou data) d'activé afin d'utiliser l'application.**

Une fois l'application lancé, une page d'accueil apparait avec les différentes ligne de Métro ainsi qu'un message informant l'utilisateur des éventuelles perturbations sur celles-ci.

Un boutton flottant permet d'ouvrir le lecteur de QR code (il faut donc donner à l'application la permission de prendre des photos & d'enregistrer des vidéos). Refuser ceci laisse l'application fonctionnelle, mais on ne pourra pas utiliser les QR codes.
Si l'utilisateur accepte, il suffit ensuite de pointer l'appareil sur les QR codes ci dessus pour se faire rediriger vers la station correspondante.

Le menu en haut à gauche dévoile 4 onglets:
 * **Home** : l'onglet d'accueil
 * **Plan** : cet onglet fait apparaitre une carte zoomable des métros de paris
 * **Favoris** : Permet d'accéder aux stations qui ont été ajoutées aux favoris. On peu également supprimer les stations dans cet onglet.
 * **Ma position** : demande d'accéder aux service de géolocalisation. Une fois que la GPS est capté par l'appareil, google map zoom automatiquement sur notre position actuelle

Cliquer sur une ligne dans l'onglet home permet de faire apparaitre une liste des stations de celle-ci avec les métros qui arrivent imminement dans les deux directions.
Des icones de ligne peuvent apparaitre a coté du nom d'une station, signifiant que celle ci est une station de correspondance entre plusieurs lignes.
Un swipe vers le haut permet d'actualiser les horaires.

Cliquer ensuite sur une station dévoile les horaires des prochains métros dans les deux sens. Si la station est une station de correspondance on voit également les horaires pour chaque ligne. Un coeur grisé est également présent et le click sur ce dernier permet d'ajouter la station au favoris. Ainsi, cette station sera directement accessible depuis l'onglet favoris.
Un swipe vers le haut permet aussi d'actualiser les horaires.

## fonctionnalités

* Rajout d'une carte des métros parisiens
* swipe vers le bas une fois qu'on a la liste des stations d'un métro pour actualiser les horaires
* swipe vers le bas quand on est dans l'activité des horaires des stations pour actualiser les horaires
* ajout QR code pour accéder directement à la station
* ajout utilisation de google map & posibilité d'obtenir la localisation de la personne
