# SR03 - Spring Chat App

## Partie Admin

## Partie Client Chat

### Description

Il s'agit d'une application de chat simple qui permet aux utilisateurs de discuter en temps réel à travers WebSocket.
La partie client de cette application est construite avec :

- React 18 : Une bibliothèque JavaScript pour construire des interfaces utilisateur.
- Node.js : Un runtime JavaScript construit sur le moteur JavaScript V8 de Chrome.
- Shadcn/ui et tailwindcss : Des bibliothèques de composants React et des outils de conception CSS.
- Vite : Un outil de build alternatif à Webpack qui vise à fournir une expérience de développement plus rapide et plus simple.
- TanStack React Query v5 : Une bibliothèque pour gérer, mettre en cache, synchroniser et mettre à jour l'état du serveur dans les applications React.
- STOMP over WebSocket : Un simple protocole de messagerie orienté texte sur WebSocket.

### Lancement de l'application

#### Prérequis

Le projet est testé sur Node.js version 20.13.1. Vous devez avoir Node.js installé sur votre machine pour exécuter le projet.

#### Installation

1. Clonez le dépôt :

    ```bash
    git clone https://gitlab.utc.fr/amoudoug/sr03-spring-chat-app.git
    ```

1. Accédez au répertoire du projet :

    ```bash
    cd sr03-spring-chat-app
    ```

1. Accédez au répertoire de la partie client :

    ```bash
    cd client
    ```

1. Installez les dépendances :

    ```bash
    npm install
    ```

1. Lancez l'application :

    ```bash
    npm run dev
    ```

1. Ouvrez votre navigateur et accédez à l'URL suivante : [http://localhost:5173](http://localhost:5173)

1. Vous pouvez également faire un build de l'application ( étape 7, 8, 9 optionnel) :

    ```bash
    npm run build
    ```

1. et lancer l'application en mode production :

    ```bash
    npm run serve
    ```

1. Ouvrez votre navigateur et accédez à l'URL suivante (attention au numéro de port 4173 au lieu de 5173) : [http://localhost:4173](http://localhost:4173)

### Fonctionnalités implémentées

#### Fonctionnalités demandées

Les fonctionalités demandées sont les suivantes :

- Login : page de connexion pour l'utilisateur implémentée
- Logout : bouton de déconnexion dans le Sidebar (cliquer sur le bouton Profil de l'utilisateur, puis "Logout")
- Liste de chatrooms : page My Chatrooms dans le Sidebar, liste des chatrooms auxquels l'utilisateur appartient
- Liste d'inviations : page My Invitations dans le Sidebar, liste des invitations reçues par l'utilisateur (ne sont pas créees par l'utilisateur lui-même)
- Connexion à un salon de chat dont l'utilisateur fait partie : page My Chatrooms et My Invitations dans le Sidebar
- Envoi de messages : cliquer sur le bouton "Chat" sur la page My Chatrooms ou My Invitations pour ouvrir le salon de chat et envoyer des messages
- Réception de messages : la réception est automatique, il y a également un système de notification mis en place pour informer l'utilisateur de l'arrivée de nouveaux messages
- Déconnexion du salon de chat : cliquer sur le bouton "Quit" sur un chatroom de la page My Chatrooms ou My Invitations pour quitter le salon de chat
- Créer un nouveau salon de chat : cliquer sur l'onglet "Plan a Discussion" dans le Sidebar pour créer un nouveau salon de chat
- Ajouter un utilisateur à un salon de chat : sur l'interface de chat, cliquer sur le bouton "Add User" (à côté du bouton "Users in Chatroom") pour ajouter un utilisateur à un salon de chat
- Test de validité du salon de chat : si un chatroom est expiré ou n'a pas encore commencé, l'utilisateur ne peut pas y accéder.
- Pagination : les listes de chatrooms, d'utilisateurs... sont paginés pour une meilleure expérience utilisateur

#### Fonctionnalités supplémentaires

En plus des fonctionnalités demandées, nous avons également implémenté les fonctionnalités suivantes :

- Notification de chat : une notification est affichée pour informer l'utilisateur de l'arrivée de nouveaux messages, et ceci dans quel chatroom le message a été envoyé, et par qui.
- La capacité de renouveler les données fetchées (liste de chatrooms, d'utilisateurs...) manuellement et automatiquement : cliquer sur le bouton "Refresh" dans le Sidebar. Les données sont également rafraîchies automatiquement quand l'utilisateur crée un nouveau salon de chat, quitte un salon de chat... Cela est fait grâce à la bibliothèque TanStack React Query.
- Dark Mode : l'implémentation partielle (n'est pas stable)
- Sidebar dynamique et application "responsive" : le Sidebar est dynamique et peut devenir un "Sheet" si la taille de l'écran est limitée. L'application est également "responsive" et s'adapte à la taille de l'écran de l'utilisateur.
