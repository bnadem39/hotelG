# 🏨 Système de Gestion d'Hôtel - Version Statique

## 📋 Description
Application Java complète de gestion d'hôtel 100% statique (données en mémoire) utilisant :
- **Java 21** (Eclipse Temurin)
- **JavaFX 21** pour l'interface graphique
- **Maven** pour la gestion des dépendances
- **JPMS** (Java Platform Module System)

## 🏗️ Structure du Projet
```
HotelManagementStatic/
├── pom.xml
└── src/
    └── main/
        └── java/
            ├── module-info.java
            ├── com.hotel.main/
            │   └── MainApp.java
            ├── com.hotel.data/
            │   └── StaticData.java
            ├── com.hotel.model/
            │   ├── User.java
            │   ├── UserRole.java
            │   ├── Room.java
            │   ├── RoomStatus.java
            │   ├── Reservation.java
            │   └── Review.java
            ├── com.hotel.service/
            │   ├── AuthService.java
            │   ├── RoomService.java
            │   ├── ReservationService.java
            │   └── ReviewService.java
            ├── com.hotel.controller/
            │   ├── LoginController.java
            │   ├── AdminController.java
            │   ├── ReceptionController.java
            │   └── ClientController.java
            └── com.hotel.view/
                ├── fxml/
                │   ├── login.fxml
                │   ├── admin-dashboard.fxml
                │   ├── reception-dashboard.fxml
                │   └── client-dashboard.fxml
                └── css/
                    └── style.css
```

## 👥 Comptes de Test

| Login      | Mot de passe | Rôle          | Description                    |
|------------|--------------|---------------|--------------------------------|
| admin      | 123          | ADMIN         | Administrateur principal       |
| reception  | 123          | RECEPTION     | Réceptionniste chef           |
| ali        | 123          | CLIENT        | Client Ali Ben Salah          |
| sarra      | 123          | CLIENT        | Client Sarra Mansouri         |

## 📊 Données Pré-chargées
- **4 utilisateurs** (1 admin, 1 réception, 2 clients)
- **10 chambres** (101-110) avec statuts variés (Libre, Occupée, Ménage)
- **5 réservations** existantes
- **6 avis/réclamations** (certains avec réponse admin, d'autres en attente)

## ✨ Fonctionnalités

### 🔐 Écran de Connexion
- Interface moderne et élégante
- Redirection automatique selon le rôle de l'utilisateur
- Affichage des comptes de test

### 👔 Interface Administrateur
- **Tableau de bord** avec statistiques en temps réel
- **Gestion des chambres** : modifier le statut (Libre/Occupée/Ménage)
- **Visualisation** de toutes les réservations
- **Gestion des avis/réclamations** : répondre aux clients

### 🎯 Interface Réception
- **Statistiques** des chambres en temps réel
- **Check-in rapide** pour les réservations
- **Check-out** avec mise à jour automatique du statut
- **Gestion du ménage** : marquer les chambres comme nettoyées
- **Actualisation** en temps réel

### 👤 Interface Client
- **Mes réservations** : visualisation de toutes les réservations
- **Mes avis** : historique des avis et réclamations
- **Soumettre un avis** ou une réclamation
- **Voir les réponses** de l'administration

## 🚀 Installation et Exécution

### Prérequis
1. **JDK 21** (Eclipse Temurin recommandé)
   - Télécharger : https://adoptium.net/
2. **Maven** (version 3.6+)
   - Télécharger : https://maven.apache.org/download.cgi
3. **IntelliJ IDEA** (Community ou Ultimate)

### Méthode 1 : Exécution avec IntelliJ IDEA (Recommandée)

1. **Ouvrir le projet** dans IntelliJ IDEA :
   - File → Open → Sélectionner le dossier `hotelG`

2. **Configurer le JDK** :
   - File → Project Structure → Project
   - SDK : Sélectionner JDK 21 (Temurin)
   - Language level : 21

3. **Recharger Maven** :
   - Clic droit sur `pom.xml` → Maven → Reload Project

4. **Exécuter l'application** :
   - Ouvrir `MainApp.java`
   - Cliquer sur le bouton ▶️ vert à côté de la classe `MainApp`
   - OU : Clic droit → Run 'MainApp.main()'

### Méthode 2 : Ligne de commande avec Maven

```bash
# Compiler le projet
mvn clean compile

# Exécuter l'application
mvn javafx:run
```

### Méthode 3 : Créer un JAR exécutable

```bash
# Compiler et packager
mvn clean package

# Exécuter le JAR (si plugin configuré)
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml -jar target/HotelManagementStatic-1.0.0.jar
```

## 🎨 Design et Thème

L'application utilise un thème moderne avec :
- **Couleurs principales** : Bleu nuit (#1e3a5f) et nuances de gris
- **Boutons arrondis** avec effets hover
- **Cartes de statistiques** avec ombres portées
- **Tableaux** avec lignes alternées et sélection colorée
- **Police** : Segoe UI (fallback : Arial)

## 🔧 Configuration Technique

### Maven (pom.xml)
- Java 21 avec `<maven.compiler.release>21</maven.compiler.release>`
- JavaFX 21.0.1
- Plugin JavaFX Maven pour l'exécution

### Module JPMS (module-info.java)
```java
module com.hotel.main {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.hotel.main;
    exports com.hotel.model;
    exports com.hotel.controller;
    opens com.hotel.controller to javafx.fxml;
    opens com.hotel.view.fxml to javafx.fxml;
    opens com.hotel.model to javafx.base;
}
```

## 📝 Guide d'Utilisation

### Scénario 1 : Administrateur
1. Se connecter avec `admin` / `123`
2. Voir les statistiques globales
3. Onglet "Gestion des Chambres" : changer le statut d'une chambre
4. Onglet "Avis et Réclamations" : sélectionner un avis et répondre

### Scénario 2 : Réceptionniste
1. Se connecter avec `reception` / `123`
2. Voir l'état des chambres en temps réel
3. Onglet "Réservations Actives" : sélectionner une réservation
4. Effectuer un Check-In ou Check-Out
5. Marquer les chambres en ménage comme nettoyées

### Scénario 3 : Client
1. Se connecter avec `ali` ou `sarra` / `123`
2. Onglet "Mes Réservations" : voir toutes vos réservations
3. Onglet "Mes Avis" : soumettre un nouvel avis ou réclamation
4. Voir les réponses de l'administration

## 🐛 Résolution de Problèmes

### Erreur : "URI is not registered" dans les fichiers FXML
- **Cause** : Schémas XML JavaFX non configurés dans IntelliJ
- **Solution** : 
  1. File → Settings → Languages & Frameworks → Schemas and DTDs
  2. Ajouter les schémas :
     - URI: `http://javafx.com/javafx/21`
     - URI: `http://javafx.com/fxml/1`
  3. Redémarrer IntelliJ
- **Alternative** : Le fichier `.idea/externalResources.xml` est déjà configuré automatiquement

### Erreur : "Module not found"
- Vérifier que JavaFX est bien dans les dépendances Maven
- Recharger le projet Maven dans IntelliJ

### Erreur : "Cannot find FXML file"
- Les fichiers FXML doivent être dans `src/main/java/com/hotel/view/fxml/`
- Vérifier les chemins dans les contrôleurs : `/com/hotel/view/fxml/...`

### Erreur : "Unsupported class file version"
- Vérifier que le JDK 21 est bien configuré
- File → Project Structure → Project → SDK : JDK 21

### L'application ne démarre pas
- Vérifier les logs dans la console IntelliJ
- S'assurer que `MainApp` est la classe principale
- Vérifier que le `module-info.java` est correct

## 📦 Technologies Utilisées

| Technologie      | Version | Utilisation                           |
|------------------|---------|---------------------------------------|
| Java             | 21      | Langage principal                     |
| JavaFX           | 21.0.1  | Interface graphique                   |
| Maven            | 3.x     | Gestion des dépendances               |
| JPMS             | -       | Système de modules Java               |
| CSS              | 3       | Styles de l'interface                 |

## 🎯 Points Forts du Projet

✅ **Architecture MVC** claire et bien structurée  
✅ **JPMS** : projet modulaire moderne  
✅ **100% statique** : aucune base de données requise  
✅ **JavaFX Properties** : binding réactif pour les tableaux  
✅ **Design moderne** : interface élégante et professionnelle  
✅ **Prêt à l'emploi** : données de test pré-chargées  
✅ **Multi-rôles** : 3 interfaces différentes selon l'utilisateur  
✅ **Code propre** : respect des conventions Java  

## 📄 Licence

Ce projet est un exemple éducatif pour démontrer :
- L'utilisation de Java 21 avec JavaFX
- La mise en place d'un projet Maven modulaire
- Les bonnes pratiques en architecture MVC

## 👨‍💻 Auteur

Projet de démonstration - Système de Gestion d'Hôtel  
Version 1.0.0 - Décembre 2025

---

**Note** : Ce projet utilise des données en mémoire uniquement. Toutes les modifications sont perdues au redémarrage de l'application.
