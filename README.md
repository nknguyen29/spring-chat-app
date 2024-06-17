# SR03 - Application de Chat

Dit que ce README développement d’une application de salon de discussion (chat). Dans
cette application, chaque utilisateur peut programmer un canal de discussion (un chat) à une date avec une durée de validité et inviter au moins un autre utilisateur.

Dit qu'on a une partie admin en Spring Boot qui permet de gérer les utilisateurs et forunit une API REST pour la partie client en React.


Dit que le code source du proet se trouve ici : [https://gitlab.utc.fr/amoudoug/sr03-spring-chat-app](https://gitlab.utc.fr/amoudoug/sr03-spring-chat-app)


## Installation

Dis que le projet a été développé dans un systeme d'exploitation Windows 11 et Ubuntu 24.04 LTS avec les outils suivants avec l'editeur Visual Studio Code.

Dit que les dépendances suivantes sont nécessaires pour lancer le projet :
- Java 21
- Maven 3.2.0
- Node.js 20.13.1
- npm 10.8.1

Dit que pour lancer le projet, il faut d'abord cloner le dépôt :

```bash
git clone https://gitlab.utc.fr/amoudoug/sr03-spring-chat-app.git
```


Dit que tres important il faut modifier ou créer le fichier `.env` dans le répertoire `chatapp` pour y mettre des identifiants valides pour le serveur de base de données. Dit qu'en .env.example est fourni pour vous aider à créer le fichier `.env` :

```bash
MYSQL_HOST=localhost
MYSQL_USER=root
MYSQL_PASSWORD=yourpassword
MYSQL_DATABASE=chatapp
MYSQL_PORT=3306

JWT_SECRET=thisisasamplesecret
JWT_ACCESS_EXPIRATION_MS=900000
JWT_REFRESH_EXPIRATION_MS=2592000000
JWT_RESET_PASSWORD_EXPIRATION_MS=600000
JWT_VERIFY_EMAIL_EXPIRATION_MS=600000

SMTP_PROTOCOL=smtps
SMTP_HOST=smtps.utc.fr
SMTP_PORT=465
SMTP_USERNAME=login@AD
SMTP_PASSWORD=password
EMAIL_FROM=support@yourapp.com
```

Dit que les identifiant de la base deonnees utilisée pour le developpement de l'application sont les suivants :

```bash
MYSQL_HOST=tuxa.sme.utc
MYSQL_USER=sr03p006
MYSQL_PASSWORD=PyP9u50hFvlU
MYSQL_DATABASE=sr03p006
MYSQL_PORT=3306
```

Pour la configuration de l'envoie de mail, veuillez vous referer aux indications de 5000 de l'université de Technologie de Compiègne. https://5000.utc.fr/front/helpdesk.faq.php?id=59

Dit que le dotenv est utilisé pour charger les variables d'environnement dans le projet Spring. Dit qu'il est utilisé dans le application.properties.


## Utilisation

### Serveur

Très important, pensez à créez le env selon les indications ci-dessus.

Dit que pour lancer le backend Spring, il faut se rendre dans le répertoire `chatapp`, puis lancer le backend Spring avec la commande suivante :

```bash
cd chatapp
./mvnw spring-boot:run
```

Dit que le projet a été configuré avec un environement de developpement egalement. Dit que nous l'aborderons plus tard mais l'interface admin utilise TailwindCSS qui ets bundlé avec Webpack. De sorte si vous voulez que l'interface se mette à jour automatiquement lors de la modification des templates HTML, il faut lancer le serveur de développement avec la commande suivante :

```bash
# dans le répertoire chatapp
npm run start
```

Dit que ça ouvrira un serveur de développement sur le port 9000 qui va proxyfier les requêtes vers le backend Spring sur le port 8080. Dit que vous pouvez accéder à l'interface admin à l'URL suivante : [http://localhost:9000](http://localhost:9000)

ATTENTION :
Dit que ce n'est que dans le cas où vous voulez beneficier d'un environnement de développement pour l'interface admin. Sinon, vous pouvez accéder à l'interface admin à l'URL suivante : [http://localhost:8080](http://localhost:8080) apres avoir lancé le backend Spring avec la commande `./mvnw spring-boot:run`.

### Client





## Table des matières



## Partie Serveur

Elle consiste au développement de la gestion des utilisateurs, la création d'un utilisateur, la modification d'un utilisateur, la activation/désactivation d'un utilisateur, la suppression d'un utilisateur, la récupération de la liste des utilisateurs dans une pagination, etc. Comme exigé dans le cahier des charges on ne propose pas d'interface cote administaretur pour gerer les salons de chats.


### Strcuture du projet

Dit que le projet Spring est structuré de la manière suivante :

- `src/main/java` : contient les fichiers Java du projet
- `src/main/resources` : contient les fichiers de ressources du projet
- `src/main/resources/templates` : contient les templates HTML de l'interface admin
- `src/main/resources/static` : contient les fichiers statiques de l'interface admin (CSS, JS, images...)
- `src/main/resources/application.properties` : contient les configurations de l'application Spring

Dit que le répertoire src/main/java contient les packages les fichires Java du projets et est structuré de la manière suivante :

- `config` : contient les fichiers de configuration de l'application Spring
- `controller` : contient les contrôleurs qui gèrent les requêtes HTTP de l'interfacec admin
- `domain` : contient les classes métiers `@Entity` de l'application Spring
- `mapper` : contient les mappers qui convertissent les classes métiers en DTO et vice versa
- `model` : contient les *Data Transfer Object* (DTO) utilisés par les contrôleurs
- `repos` : contient les classes de couche d'accès aux données de l'application Spring
- `rest` : contient les `@RestController` qui gèrent les requêtes REST de l'API REST
- `service` : contient les services `@Service`qui gèrent la logique métier de l'application Spring  
- `util` : contient les classes utilitaires,, souvent `@Component`


### Architecture

L’interface d’administration de l’application selon l’architecture MVC. Plus aprticuliemeent, on renforce cette structure MVC par des services et des DTOs. Les services sont des classes qui contiennent la logique métier de l'application. Les DTOs sont des classes qui contiennent les données transférées entre les contrôleurs et les services. Les contrôleurs sont des classes qui gèrent les requêtes HTTP de l'interface admin.

Par exemple lors d'une requete de création d'un utilisateur, le contrôleur `UserController` va recevoir la requête HTTP, puis il va appeler le service `UserService` pour créer l'utilisateur. Le service `UserService` va appeler le repository `UserRepository` pour enregistrer l'utilisateur dans la base de données. Le repository `UserRepository` va utiliser JPA et Hibernate pour enregistrer l'utilisateur dans la base de données. Eventuelememnt, le controlleur pouraait aussi fournir un DTO au service qui utilisera un mapper pour convertir le DTO en classe métier. Cela permet de séparer les responsabilités et de rendre le code plus lisible et maintenable, et sécurisé. Dit que pour toutes les opérations CRUD, on suit cette architecture car elle est plus robuste et sécurisée.

#### Couche de données

##### Persistance

Dit qu'on utilise une abse de donénes MySQL fournie par l'Université de Technologie de Compiègne. Dit qu'on a donc la dependance suivante dans le fichier `pom.xml` :

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

Dit qu'on a trtois tables dans notre base de données : `users`, `chatrooms` et `chatroom_users`. Dit que la table `users` contient les informations des utilisateurs, la table `chatrooms` contient les informations des salons de chat, et la table `chatroom_users` contient les relations entre les utilisateurs et les salons de chat. C'est la table d'association.


Dit que ces tables sont automatiquement créées lors du lancement de l'application Spring. Dit que les classes métiers `@Entity` sont les suivantes :
- `User` : contient les informations des utilisateurs
- `Chatroom` : contient les informations des salons de chat
- `ChatroomUser` : contient les relations entre les utilisateurs et les salons de chat
Ces classes sont toutes defineis dans le doddiser `domain`.

Dit qu'on a une relation `@ManyToOn` entre `ChatroomUser` et `User`, et une relation `@ManyToOne` entre `ChatroomUser` et `Chatroom`. Dit qu'on a pas préféré utilisé une relation `@ManyToMany` car dans l'avenir on pourrait ajouter des informations supplémentaires à la relation entre un utilisateur et un salon de chat. Par exemple, on pourrait ajouter une date d'invitation, une date d'acceptation, une date de refus, etc.

##### Accès aux données

Dit que les classes métiers (JAVA BEANS)  sont mappées avec les tables de la base de données avec Spring Data JPA et. Pour rappelle Hibernate est ORMest une implémentation JPA, tandis que Spring Data JPA est une abstraction d'accès aux données JPA. Spring Data JPA ne peut pas fonctionner sans fournisseur JPA. Dit que les classes de couche d'accès aux données `@Repository` sont les suivantes :
- `UserRepository` : contient les méthodes pour accéder aux données de la table `users`
- `ChatroomRepository` : contient les méthodes pour accéder aux données de la table `chatrooms`
- `ChatroomUserRepository` : contient les méthodes pour accéder aux données de la table `chatroom_users`
Ces classes sont toutes defineis dans le doddiser `repos`.

##### Data Transfer Object (DTO)

Explique rapidement a quoi sert un DTO
Dit qu'on a plein de dto (une vingtaine) definie dans le dossier `model`. Dit que chaque DTO est utilisé pour une opération CRUD spécifique. Par exemple, on a un DTO `UserPostDTO` pour créer un utilisateur, un DTO `UserPublicDTO` pour récupérer le sinformations publiques (non sensibles, e.g. pas de mot de passe) d'un utilisateur, etc.

##### Mappers

Dit que les mappers sont utilisés pour convertir les classes métiers en DTO et vice versa. Dit que les mappers sont utilisés pour séparer les responsabilités et rendre le code plus lisible et maintenable. Dit que les mappers sont les suivants Dans le dossier `mapper`.


#### Sécurité

Dit que la sécurité est très importante dans une application Spring. Dit que pour sécuriser l'application, on utilise Spring Security. Spring Security est un framework qui fournit une authentification et une autorisation robustes pour les applications Spring. Dit que Spring Security est très flexible et peut être configuré pour répondre à des besoins de sécurité très spécifiques.

Dit qu'on a deux mecanismes de sécurité dans l'application. L'interafce admin qui tourne sur le port 8080 utilise un système d'authentification basé sur les cookies. Dit qu'un simple formulaire de login est utilisé pour se connecter à l'interface admin. bien évideeemnt seul les utilisateurs enregistrés dans la base de données comme administrateurs peuvent se connecter à l'interface admin. 

Dit que l'API REST dont tous les enpoints sont préfixés par `/api` utilise un système d'authentification basé sur les tokens JWT. Dit que les tokens JWT sont des tokens auto-suffisants qui contiennent toutes les informations nécessaires pour vérifier l'identité d'un utilisateur. Dit que les tokens JWT sont signés avec une clé secrète, et peuvent être vérifiés sans accéder à la base de données. Dit que les tokens JWT sont utilisés pour authentifier les utilisateurs de l'API REST.

Dit que les configurations de sécurité sont dans le fichier `config/SecurityConfig.java`.


```
    @Bean
    @Order(value = 1)
    public SecurityFilterChain apiFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(withDefaults())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .failureHandler(loginFailureHandler)
                        .successHandler(loginSuccessHandler))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout_success")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login?login_required")))
                .build();
    }
```


Les mots de passe sont tous encodés avec BCrypt. Dit que les champs mots de passe dan la base de données sont préfixés par `{bcrypt}` pour indiquer que le mot de passe est encodé avec BCrypt. À des fins de tests, certaies champs sont prefixés par `{noop}` pour indiquer que le mot de passe n'est pas encodé. Dit que c'est juste pour les tests, et que dans un environnement de production, tous les mots de passe sont être encodés avec BCrypt.

##### Authentification


Dit que pour se connecter à l'inteface admin, on utilise un simple formulaire de login. On a alors un cookie de session qui est créé pour l'utilisateur. Dit que le cookie de session est utilisé pour authentifier l'utilisateur. Dit que le cookie de session est stocké dans le navigateur de l'utilisateur, et est envoyé avec chaque requête HTTP. Dit que le cookie de session est utilisé pour authentifier l'utilisateur.


Dit que le nomrbe de tentatives de connexion est limité au nombre de 3. Au dela de ces tentatives le compte de l'utilisateur sera bloqué etil devra faire appele à un adminsitrateur pour qu'il reactive son compte.


Dit qqu'une route en POST est fournie pour recupere un token JWT. Dit que le token JWT est utilisé pour authentifier les utilisateurs de l'API REST. Dit que le token JWT a été configuré avec un payload pour ajouté des informations supplémentaires comme le nom de l'utilisateur, le rôle de l'utilisateur, etc. Dit que le token JWT a une date d'expiration définie dans le fichier `.env`. Dit que la gestion du token est gérée par la partie client. Le serveur se contente seulement de générer le token et de vérifier sa validité lors de chaque requête.


##### Autorisation

Dit qu'on a deux roles dans l'application : `ROLE_USER` et `ROLE_ADMIN`. Dit que les utilisateurs enregistrés dans la base de données ont false dans le champs isadmin et les utilisateurs qui sont des administrateurs ont true dans le champs isadmin. Dit que ce soit l'interface admin ou les endpoints rest, des autorisations sont mises en place pour les utilisateurs. Par exemple, seuls les utilisateurs avec le role `ROLE_ADMIN` peuvent accéder à l'interface admin. Seuls les utilisateurs connectés (`@PreAuthorize("isAuthenticated()")`) peuvent accéder aux endpoints de l'API REST.


### Gestions des utilisateurs

#### Création d'un utilisateur

Dit que pour créer un utilisateur, on se rend /users/add (http://localhost:8080/users/add). Il suffit de remplir le formulaire. Des vérifications sont faites côté interface (vérification de la longueur du mot de passe par exemple), mais aussi cote serveur (vérification de l'unicité de l'email par exemple). Dit que si une erreur est détectée, un message d'erreur est affiché à l'utilisateur. Dit que si l'utilisateur est créé avec succès, un toast de succès est affiché à l'utilisateur.

#### Modification d'un utilisateur

Dit que pour modifier un utilisateur, on se rend /users/{id}/edit (http://localhost:8080/users/{id}/edit). Il suffit de remplir le formulaire. Des vérifications sont faites côté interface (vérification de la longueur du mot de passe par exemple), mais aussi cote serveur (vérification de l'unicité de l'email par exemple). Dit que si une erreur est détectée, un message d'erreur est affiché à l'utilisateur. Dit que si l'utilisateur est modifié avec succès, un toast de succès est affiché à l'utilisateur.

#### Liste des utilisateurs

Dit que la liste des utilisateurs est paginée pour une meilleure expérience utilisateur. Dit que la liste des utilisateurs est affichée sur la page /users (http://localhost:8080/users). Dit que la liste des utilisateurs est paginée pour une meilleure expérience utilisateur, dit aussi qu'on peut filtrer les utilisateurs selon certains critères (par exemple, par nom, par email, par rôle...). Dit qu'on peut aussi les trier par ordre croissant ou décroissant. Dit que la taille des pages est modifiable.

#### Détails d'un utilisateur

Dit que pour voir les détails d'un utilisateur, on se rend sur la page /users/{id} (http://localhost:8080/users/{id}). Dit que les détails de l'utilisateur sont affichés sur cette page. Dit que les détails de l'utilisateur sont affichés sur cette page. Dit que l'utilisateur peut voir les informations de l'utilisateur, et peut aussi modifier ou supprimer l'utilisateur.

#### Suppression d'un utilisateur

Dit que pour supprimer un utilisateur, on se rend sur la page de liste des utilisateurs ou page details utilisateurs, puis on clique sur le bouton delete. Dit que si l'utilisateur est supprimé avec succès, un toast de succès est affiché à l'utilisateur.

#### Activation/Désactivation d'un utilisateur

Dit qu'on peut activer/désactiver un utilisateur en cliquant sur le bouton lock ou unlock sur la page de liste des utilisateur ou page details utilisateurs. Dit que si l'utilisateur est activé/désactivé avec succès, un toast de succès est affiché à l'utilisateur.


### Vues et Templates


Dit qu'on utilise Thymeleaf (une usine a gaz)
Dit qu"on reutilise des templates selon un mdoele d'heitage avec des layoute. para exemple le fichier `base.html` est le layout de base de l'application avec tous les éléments communs à toutes les pages Par exemple, les meta tags, les liens vers les fichiers CSS, les liens vers les fichiers JS, etc. Dit que les autres templates héritent de ce layout de base. 


Dit qu'on utilsie pour le style tailwindcss. Dit que tailwindcss est une bibliothèque de conception CSS qui permet de styliser rapidement et facilement les éléments HTML. Dit que tailwindcss est utilisé pour styliser l'interface admin. Dit que tailwindcss est utilisé pour styliser les boutons, les formulaires, les tables, les alertes, les toasts, etc. Dit que tailwindcss est utilisé pour styliser les éléments HTML de l'interface admin. Dit qu'on a aussi un plugin Flowbite pour agrémenter le style de base de tailwindcss. 

Dit que le site full responsive, developpe. Un gros travail a été fait sur le design developpe.

Il n'y aps de page d'accueil dans l'application. C'est une feature a implementer et faudrait voir avec le client pour savoir ce qu'il veut étant donné qu'il ne l'a pas demandé dans le cahier des charges.


### API REST

#### Endpoints

Dit que les endpoints de l'API REST sont préfixés par `/api`. Dit que les endpoints de l'API REST sont toutes fournies dans le package `rest`. Dit que les endpoints de l'API REST sont les suivants :
- - - - - - 


Dit qu'à l'instar des Controleurs de l'interface admin, les @RestController de l'API REST procedent de la meme manière mais retourne juste des objets JSON converti en interne grace a Jackson et les annotations de Spring. Jakson est une bibliothèque Java qui peut être utilisée pour convertir des objets Java en JSON et vice versa en utilisant les annotations de Jackson. Les restcontrolleur utilisent donc des service qui eux memes manipulent des dtos, des classes metiers et des repositories.

#### Swagger UI

Dit que Swagger UI est une interface utilisateur qui permet de visualiser et de tester les endpoints de l'API REST. Dit que Swagger UI est très utile pour tester les endpoints de l'API REST. Dit que Swagger UI est très utile pour tester les endpoints de l'API REST. Dit que le Swagger UI est accessible à l'URL suivante : [/swagger-ui.html](http://localhost:8080/swagger-ui.html)



## Partie Client Chat

◼ L’interface utilisateur : planification, édition et affichage de la liste des salons de discussion
(chats) et de la page de chat. La structure de l’application est en architecture single page
(Composants React + APIs REST). Cette interface exploite le même modèle et donc la même
couche accès aux données 

### Description de l'architecture

Développement du serveur de chat avec le protocole WebSocket en utilisant l'API java websocket.
L’interface d’utilisateur de la partie 1 utilise le serveur de chat via la fenêtre de chat. Ce serveur reçoit
des messages depuis la fenêtre de chat et il les diffuse sur le canal approprié au niveau des clients
connectés

Il s'agit d'une application de chat simple qui permet aux utilisateurs de discuter en temps réel à travers WebSocket.
La partie client de cette application est construite avec :

- React 18 : Une bibliothèque JavaScript pour construire des interfaces utilisateur.
- Node.js : Un runtime JavaScript construit sur le moteur JavaScript V8 de Chrome.
- Shadcn/ui et tailwindcss : Une bibliothèque de composants React et des outils de conception CSS.
- Vite.js : Un outil de build alternatif à Webpack qui vise à fournir une expérience de développement plus rapide et plus simple.
- TanStack React Query v5 : Une bibliothèque pour gérer, mettre en cache, synchroniser et mettre à jour l'état du serveur dans les applications React.
- STOMP over WebSocket : Un simple protocole de messagerie orienté texte sur WebSocket.

### Conception et interactions entre les technologies

#### React

React est utilisé pour construire l'interface utilisateur de l'application. React permet de créer des composants réutilisables et de les combiner pour créer des interfaces utilisateur complexes, tels que `Chatroom`, `CreateChatroom`, `InviteUser`, `Login`, `MyChatrooms`, `MyInvitations`, `PlanDiscussion`, `Profile`, `Sidebar`, `StompListener`...

Les composants React sont utilisés pour afficher l'interface utilisateur de l'application (la partie Chat).

#### Spring

Le backend Spring contient les endpoints pour l'API REST ainsi que les endpoints pour les messages WebSocket. Les messages WebSocket sont envoyés et reçus par le backend Spring. Les messages WebSocket sont écoutés dans le composant `StompListener.jsx` dans la partie client. L'API REST est utilisée pour fetcher les données de l'application (liste de chatrooms, d'utilisateurs...).

#### WebSocket

Pour la partie Client, Les messages WebSocket sont écoutés dans le composant `StompListener.jsx`. Les messages sont écoutés en permanence, et les messages reçus sont stockés dans un useState prop `messages` dans `App.jsx`.  Les messages sont ensuite affichés dans le chatroom correspondant. Le composant `Chatroom.jsx` utilise le prop `messages` dans `App.jsx` et affiche les messages à l'utilisateur.

### Particularités techniques

#### Chatroom

Le composant `Chatroom.jsx` est utilisé pour afficher l'interface de Chat. Les messages sont affichés en sucession, et les nouveaux messages sont ajoutés à la fin. Le composant `Chatroom.jsx` utilise également un champ Input pour envoyer des messages. Les nouveaux messages arrivées sont notifiés à l'utilisateur, et ceci n'est pas dans le composant `Chatroom.jsx` mais dans le composant `StompListener.jsx`.

#### React Query

React Query permet de rafraîchir les données. Sans un mécanisme de rafraîchissement, quand un utilisateur rejoint un salon de chat, crée un salon de chat... par exemple, `StompListener.jsx` n'écouterait pas les nouveaux salons car les données ne seraient pas mises à jour.

React Query permet de gérer ce problème en mettant en cache les données et en les rafraîchissant quand on fait invalidateQueries. Par exemple dans `CreateChatrooms.jsx`, on peut voir comment les données sont rafraîchies après la création d'un nouveau salon de chat :

```jsx

  const queryClient = useQueryClient(); // used to invalidate the query

  const mutation = useMutation({
    // used to send the data to the server
    mutationFn: (payload) => {
      return axios.post(`/api/chatrooms`, payload, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("token")}`,
        },
      });
    },
    onError: (error) => {
      console.log("[CreateChatrooms] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[CreateChatrooms] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
        { queryKey: ["users"] },
      ]);
    },
  });

```

React Query est utilisé pour gérer les données de l'application. Les données sont fetchées avec des hooks personnalisés dans `src/hooks`. Les données sont fetchées avec Axios, et les données sont mises en cache et synchronisées automatiquement. Les données sont rafraîchies manuellement avec un bouton "Refresh" dans le Sidebar, et automatiquement quand l'utilisateur crée un nouveau salon de chat, quitte un salon de chat... Les mutations (useMutation) sont utilisées directement dans les composants.

### Structure du projet

Le projet React est divisé en plusieurs dossiers :

- `src/hooks` : contient les hooks personnalisés pour fetcher des données, utilisés dans l'application. Ces hooks sont customisés à partir de React Query's useQuery. Les mutations (useMutation) sont utilisées directement dans les composants.
- `src/api` : contient les api utilisées dans `src/hooks`. Ici se trouve les appels aux endpoints de l'API Spring. Les appels sont faits avec Axios (axios.get).
- `src/components` : contient les composants React de l'application
- `index.jsx` : le point d'entrée de l'application. Ici se trouve le composant principal de l'application, `App`. React Strict Mode est activé en permanence.
- `App.jsx` : le composant principal de l'application. Le composant `StompListener` est utilisé pour écouter les messages WebSocket. Les `PrivateWrapper` permet de masquer les liens de navigation si l'utilisateur n'est pas connecté.
- `vite.config.js` : le projet est buildé avec Vite (et pas create-react-app). Ce fichier contient la configuration de Vite.
- `tailwind.config.js` : le fichier de configuration de TailwindCSS, qui est utilisé pour la conception CSS de l'application. Shadcn/ui est également utilisé pour les composants React.

### Lancement de l'application

#### Prérequis

- Le projet est testé sur Node.js version 20.13.1. Vous devez avoir Node.js installé sur votre machine pour exécuter le projet.
- La version de React utilisée est la version 18.3.1, React 17 ne va pas fonctionner à cause de la dépendance de TanStack React Query v5.
- Vite.js est utilisé pour le build du projet et pas Webpack, il faut aller sur [http://localhost:5173](http://localhost:5173) ou [http://localhost:4173](http://localhost:4173) et pas [http://localhost:3000](http://localhost:3000) comme les projets Webpack.

#### Installation

1. Clonez le dépôt :

    ```bash
    git clone https://gitlab.utc.fr/amoudoug/sr03-spring-chat-app.git
    ```

1. Accédez au répertoire du projet :

    ```bash
    cd sr03-spring-chat-app
    ```

1. Accédez au répertoire de la partie serveur :

    ```bash
    cd chatapp
    ```

1. Modifiez / Adaptez le .env (voir .env.example et l'adapter pour y mettre des identifiants valides pour le serveur de base de données) :

    ```bash
    cp .env.example .env
    sudo nano .env
    ```

1. Lancer le backend Spring :

    ```bash
    ./mvnw spring-boot:run
    ```

1. Le backend est lancé, maintenant au frontend. Accédez au répertoire de la partie client :

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

### Fonctionnalités et Utilisations

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


On ne souhaite pas sauvegarder les messages (contenu + heure) des canaux de discutions mais ils seront seulement diffusés sur les sessions (canaux connectés) de tous les utilisateurs connectés à ce chat. Une personne qui se connecte tardivement à un canal ne verra pas les messages publiés antérieurement.
MAIS ON LA FAIT EN QUELQUE SORTE!!!



En plus des fonctionnalités demandées, nous avons également implémenté les fonctionnalités suivantes :

- Notification de chat : une notification est affichée pour informer l'utilisateur de l'arrivée de nouveaux messages, et ceci dans quel chatroom le message a été envoyé, et par qui.
- La capacité de renouveler les données fetchées (liste de chatrooms, d'utilisateurs...) manuellement et automatiquement : cliquer sur le bouton "Refresh" dans le Sidebar. Les données sont également rafraîchies automatiquement quand l'utilisateur crée un nouveau salon de chat, quitte un salon de chat... Cela est fait grâce à la bibliothèque TanStack React Query.
- Dark Mode : l'implémentation partielle (n'est pas stable)
- Sidebar dynamique et application "responsive" : le Sidebar est dynamique et peut devenir un "Sheet" si la taille de l'écran est limitée. L'application est également "responsive" et s'adapte à la taille de l'écran de l'utilisateur.
- Les `PrivateWrapper` permet de masquer les liens de navigation si l'utilisateur n'est pas connecté (voir `App.tsx`).

### Eco Index

![EcoIndex de Chat](ecoindex-chat.png)

L'application obtien un score A. Les problèmes sont les suivants :

- Add expires or cache-control headers : 0 / 1 resources cached .....
- Compress ressources (>= 95%) : 0% resources compressed .
- Externalize css and js : 5 inline stylesheet(s) and inline javascript(s) found
- Validate js : 201 javascript error(s) found .....
- Minified cs and js : 92/95 not minified css or js .....
- Provide print stylesheet : No print stylesheet found

#### Piste d'amélioration

Pour améliorer le score, il faudrait :

- Ajouter des headers expires ou cache-control pour les ressources
- Compresser les ressources
- Externaliser les css et js
- Valider le js
- Minifier les css et js
- Ajouter un print stylesheet
