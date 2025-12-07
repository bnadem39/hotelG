# Guide de Configuration IntelliJ IDEA

## Configuration Rapide

### 1. Ouvrir le Projet
1. Lancez IntelliJ IDEA
2. File → Open
3. Sélectionnez le dossier `hotelG`
4. Cliquez sur OK

### 2. Configurer le SDK
1. File → Project Structure (Ctrl+Alt+Shift+S)
2. Project Settings → Project
3. SDK : Sélectionnez JDK 21 (ou cliquez sur "Add SDK" → "Download JDK" → Temurin 21)
4. Language level : 21 - Record patterns, pattern matching for switch
5. Cliquez sur Apply puis OK

### 3. Recharger Maven
1. Ouvrez la fenêtre Maven (View → Tool Windows → Maven)
2. Cliquez sur l'icône de rechargement (↻) "Reload All Maven Projects"
3. Attendez que toutes les dépendances soient téléchargées

### 4. Créer une Configuration d'Exécution

#### Option A : Exécution Automatique (Recommandé)
1. Ouvrez le fichier `MainApp.java`
2. Faites un clic droit sur la classe `MainApp`
3. Sélectionnez "Run 'MainApp.main()'"
4. IntelliJ créera automatiquement la configuration

#### Option B : Configuration Manuelle
1. Run → Edit Configurations
2. Cliquez sur + → Application
3. Remplissez les champs :
   - Name : `Hotel Management App`
   - Module : `hotelG`
   - Main class : `com.hotel.main.MainApp`
   - VM options : (laisser vide)
   - Working directory : `$MODULE_WORKING_DIR$`
4. Cliquez sur Apply puis OK

### 5. Exécuter l'Application
1. Sélectionnez la configuration "MainApp" dans la barre d'outils
2. Cliquez sur le bouton ▶️ Run (Shift+F10)
3. Ou cliquez sur le bouton 🐞 Debug (Shift+F9) pour déboguer

## Résolution de Problèmes dans IntelliJ

### Problème : "Cannot resolve symbol 'javafx'"
**Solution :**
1. Maven → Reload All Maven Projects
2. File → Invalidate Caches / Restart → Invalidate and Restart

### Problème : "Module not found: javafx.controls"
**Solution :**
1. Vérifiez que le `module-info.java` existe à la racine de `src/main/java`
2. Assurez-vous que les dépendances JavaFX sont dans le `pom.xml`
3. Rechargez Maven

### Problème : "Error: JavaFX runtime components are missing"
**Solution :**
1. Vérifiez que JavaFX est dans les dépendances Maven
2. Le plugin `javafx-maven-plugin` devrait gérer cela automatiquement
3. Si le problème persiste, utilisez : `mvn javafx:run` en ligne de commande

### Problème : Fichiers FXML non trouvés
**Solution :**
1. Vérifiez que les fichiers FXML sont dans : `src/main/java/com/hotel/view/fxml/`
2. Build → Rebuild Project
3. Les fichiers FXML doivent être copiés dans le dossier `target/classes`

### Problème : CSS non appliqué
**Solution :**
1. Vérifiez que `style.css` est dans : `src/main/java/com/hotel/view/css/`
2. Vérifiez les chemins dans les fichiers FXML : `stylesheets="@../css/style.css"`
3. Rebuild le projet

## Raccourcis Clavier Utiles

| Action                  | Windows/Linux    | macOS           |
|-------------------------|------------------|-----------------|
| Run                     | Shift+F10        | Ctrl+R          |
| Debug                   | Shift+F9         | Ctrl+D          |
| Stop                    | Ctrl+F2          | Cmd+F2          |
| Build Project           | Ctrl+F9          | Cmd+F9          |
| Project Structure       | Ctrl+Alt+Shift+S | Cmd+;           |
| Search Everywhere       | Double Shift     | Double Shift    |

## Structure des Packages dans IntelliJ

Votre projet devrait apparaître ainsi dans la vue Project :

```
hotelG
├── .idea (fichiers IntelliJ - auto-générés)
├── src
│   └── main
│       └── java
│           ├── module-info.java
│           └── com.hotel
│               ├── controller
│               ├── data
│               ├── main
│               ├── model
│               ├── service
│               └── view
│                   ├── css
│                   └── fxml
├── target (fichiers compilés - auto-généré)
├── pom.xml
└── README.md
```

## Astuces pour le Développement

### 1. Auto-complétion
- Utilisez Ctrl+Space pour l'auto-complétion
- Ctrl+Shift+Space pour l'auto-complétion contextuelle

### 2. Navigation Rapide
- Ctrl+N : Rechercher une classe
- Ctrl+Shift+N : Rechercher un fichier
- Ctrl+B : Aller à la déclaration

### 3. Refactoring
- Shift+F6 : Renommer
- Ctrl+Alt+M : Extraire une méthode
- Ctrl+Alt+V : Extraire une variable

### 4. Déboguer
- Placez des breakpoints en cliquant dans la marge gauche
- Utilisez Shift+F9 pour lancer en mode debug
- F8 : Step over
- F7 : Step into
- F9 : Resume program

## Tests de l'Application

### Test 1 : Connexion Admin
1. Login : `admin` / Password : `123`
2. Vérifiez que vous voyez le tableau de bord administrateur

### Test 2 : Gestion des Chambres
1. Connectez-vous en tant qu'admin
2. Allez dans "Gestion des Chambres"
3. Sélectionnez une chambre
4. Changez son statut

### Test 3 : Réponse aux Avis
1. Connectez-vous en tant qu'admin
2. Allez dans "Avis et Réclamations"
3. Sélectionnez un avis sans réponse
4. Tapez une réponse et cliquez sur "Répondre"

### Test 4 : Check-In/Check-Out
1. Connectez-vous en tant que `reception`
2. Allez dans "Réservations Actives"
3. Testez les boutons Check-In et Check-Out

### Test 5 : Avis Client
1. Connectez-vous en tant que `ali`
2. Allez dans "Mes Avis et Réclamations"
3. Soumettez un nouvel avis

## Support

Si vous rencontrez des problèmes :
1. Vérifiez que JDK 21 est installé : `java -version` dans le terminal
2. Vérifiez que Maven fonctionne : `mvn -version`
3. Nettoyez et recompilez : Build → Rebuild Project
4. Invalidez les caches : File → Invalidate Caches / Restart

Bonne utilisation ! 🎉

