# Projet de Gestion des Mariages - JPA/Hibernate

Ce projet implémente un système de gestion des mariages entre hommes et femmes en utilisant JPA/Hibernate avec MySQL.

## Structure du Projet

```
exercice-3/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/
│   │   │       └── projet/
│   │   │           ├── beans/           # Entités JPA
│   │   │           │   ├── Homme.java
│   │   │           │   ├── Femme.java
│   │   │           │   ├── Mariage.java
│   │   │           │   └── MariagePK.java
│   │   │           ├── dao/             # Interface DAO
│   │   │           │   └── IDao.java
│   │   │           ├── service/         # Services métier
│   │   │           │   ├── HommeService.java
│   │   │           │   ├── FemmeService.java
│   │   │           │   └── MariageService.java
│   │   │           ├── util/            # Utilitaires
│   │   │           │   └── HibernateUtil.java
│   │   │           └── Main.java        # Classe principale
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml      # Configuration JPA
│   │       └── application.properties   # Propriétés de l'application
│   └── test/
└── pom.xml
```

## 🌟 Avantages de Spring Boot

### ✅ Par rapport à JPA pur:
- **Injection de dépendances automatique** (@Autowired)
- **Gestion des transactions déclaratives** (@Transactional)
- **Repositories Spring Data JPA** (CRUD automatique)
- **Configuration simplifiée** (application.properties au lieu de persistence.xml)
- **Auto-configuration** (plus besoin de HibernateUtil)
- **Requêtes personnalisées** avec @Query
- **CommandLineRunner** pour l'exécution au démarrage

## 1. Couche Persistance

### Entités JPA

#### Homme
- **Attributs** : id, nom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage

#### Femme
- **Attributs** : id, nom, prénom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage
- **Requêtes nommées** :
  - `Femme.findMarriedTwiceOrMore` : Trouve les femmes mariées au moins 2 fois
  - Requête native pour compter les enfants entre deux dates

#### Mariage
- **Clé composite** : MariagePK (homme_id, femme_id, date_debut)
- **Attributs** : date de fin, nombre d'enfants
- **Relations** : ManyToOne avec Homme et Femme

#### MariagePK
- **Classe Embeddable** pour la clé composite du mariage
- **Attributs** : hommeId, femmeId, dateDebut

### Configuration

#### persistence.xml
```xml
<persistence-unit name="mariagePU">
  - Driver: MySQL
  - URL: jdbc:mysql://localhost:3306/mariage_db
  - Auto-DDL: update
  - Show SQL: true
</persistence-unit>
```

#### HibernateUtil
Classe utilitaire pour gérer l'EntityManagerFactory et créer des EntityManager.

## 2. Couche Service

### Interface IDao<T>
Interface générique définissant les opérations CRUD de base :
- `create(T o)` : Créer une entité
- `delete(T o)` : Supprimer une entité
- `update(T o)` : Mettre à jour une entité
- `findById(int id)` : Trouver par ID
- `findAll()` : Récupérer toutes les entités

### HommeService

Implémente `IDao<Homme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getEpousesEntreDeuxDates(Homme homme, Date dateDebut, Date dateFin)**
   - Retourne la liste des épouses d'un homme marié entre deux dates
   - Utilise JPQL

2. **afficherMariagesHomme(Homme homme)**
   - Affiche tous les mariages d'un homme avec détails :
     - Nom de l'épouse
     - Dates de début et fin
     - Nombre d'enfants

### FemmeService

Implémente `IDao<Femme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreEnfantsEntreDeuxDates(Femme femme, Date dateDebut, Date dateFin)**
   - Exécute une requête native nommée
   - Retourne le nombre total d'enfants d'une femme entre deux dates

2. **getFemmesMarieesPlusieuresFois()**
   - Exécute une requête nommée
   - Retourne les femmes mariées au moins 2 fois

### MariageService

Implémente `IDao<Mariage>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Utilise l'**API Criteria** (CriteriaBuilder, CriteriaQuery)
   - Compte les hommes mariés à exactement 4 femmes entre deux dates
   - Démontre l'utilisation avancée de l'API Criteria avec :
     - Jointures
     - Groupement (GROUP BY)
     - Filtres (HAVING)

2. **getHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Alternative avec JPQL
   - Retourne la liste des hommes concernés

## Configuration de la Base de Données

### Prérequis
1. Installer MySQL Server
2. Créer un utilisateur avec les permissions appropriées

### Configuration
Modifier le fichier `persistence.xml` ou `application.properties` :

```properties
hibernate.connection.url=jdbc:mysql://localhost:3306/mariage_db?createDatabaseIfNotExist=true
hibernate.connection.username=root
hibernate.connection.password=YOUR_PASSWORD
```

### Génération automatique
La base de données et les tables sont **créées automatiquement** au premier lancement grâce à :
- `createDatabaseIfNotExist=true` dans l'URL
- `hibernate.hbm2ddl.auto=update` dans la configuration

## Utilisation

### Compilation
```bash
mvn clean install
```

### Exécution
```bash
mvn exec:java -Dexec.mainClass="ma.projet.Main"
```

Ou depuis votre IDE, exécutez la classe `Main.java`.

### Exemple d'utilisation dans Main.java

```java
// Créer les services
HommeService hommeService = new HommeService();
FemmeService femmeService = new FemmeService();
MariageService mariageService = new MariageService();

// Créer des entités
Homme h1 = new Homme("Alami", "0612345678", "Casablanca", new Date());
hommeService.create(h1);

Femme f1 = new Femme("Amrani", "Fatima", "0645678901", "Casablanca", new Date());
femmeService.create(f1);

// Créer un mariage
Mariage m1 = new Mariage(h1, f1, dateDebut, dateFin, 2);
mariageService.create(m1);

// Requêtes spécifiques
List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(h1, date1, date2);
long nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(f1, date1, date2);
List<Femme> femmesMultiMariages = femmeService.getFemmesMarieesPlusieuresFois();
long nbHommes = mariageService.getNombreHommesMariesQuatreFemmes(date1, date2);
```

## Fonctionnalités Implémentées

### ✅ Couche Persistance
- [x] Entités avec annotations JPA (Homme, Femme, Mariage)
- [x] Configuration persistence.xml
- [x] Classe HibernateUtil
- [x] Génération automatique de la base MySQL

### ✅ Couche Service
- [x] Interface IDao générique
- [x] HommeService avec méthode épouses entre dates
- [x] HommeService avec affichage détaillé des mariages
- [x] FemmeService avec requête native nommée (nombre d'enfants)
- [x] FemmeService avec requête nommée (femmes mariées 2+ fois)
- [x] MariageService avec API Criteria (hommes mariés 4 femmes)

## Modèle de Données

### Schéma des tables générées

```sql
Table: homme
- id (PK, AUTO_INCREMENT)
- nom
- telephone
- adresse
- date_naissance

Table: femme
- id (PK, AUTO_INCREMENT)
- nom
- prenom
- telephone
- adresse
- date_naissance

Table: mariage
- homme_id (PK, FK)
- femme_id (PK, FK)
- date_debut (PK)
- date_fin
- nombre_enfants
```

## Points Techniques Importants

1. **Clé Composite** : Utilisation de `@Embeddable` et `@EmbeddedId` pour MariagePK
2. **Requêtes Nommées** : `@NamedQuery` et `@NamedNativeQuery` dans Femme
3. **API Criteria** : Démonstration complète dans MariageService
4. **Relations Bidirectionnelles** : OneToMany/ManyToOne entre entités
5. **Gestion des Transactions** : EntityTransaction pour CRUD
6. **Lazy Loading** : Configuration FetchType.LAZY pour les collections

## Auteur

Projet d'exercice JPA/Hibernate - Gestion des Mariages

## License

Ce projet est à des fins éducatives.
# Projet de Gestion des Mariages - JPA/Hibernate

Ce projet implémente un système de gestion des mariages entre hommes et femmes en utilisant JPA/Hibernate avec MySQL.

## Structure du Projet

```
exercice-3/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/
│   │   │       └── projet/
│   │   │           ├── beans/           # Entités JPA
│   │   │           │   ├── Homme.java
│   │   │           │   ├── Femme.java
│   │   │           │   ├── Mariage.java
│   │   │           │   └── MariagePK.java
│   │   │           ├── dao/             # Interface DAO
│   │   │           │   └── IDao.java
│   │   │           ├── repository/      # Repositories Spring Data JPA
│   │   │           │   ├── HommeRepository.java
│   │   │           │   ├── FemmeRepository.java
│   │   │           │   └── MariageRepository.java
│   │   │           ├── service/         # Services métier (@Service)
│   │   │           │   ├── HommeService.java
│   │   │           │   ├── FemmeService.java
│   │   │           │   └── MariageService.java
│   │   │           ├── MariageApplication.java  # Application Spring Boot
│   │   │           └── Main.java        # CommandLineRunner
│   │   └── resources/
│   │       └── application.properties   # Configuration Spring Boot
│   └── test/
└── pom.xml
```

## Technologies Utilisées

- **Java 21**
- **Hibernate 6.2.7** - ORM Framework
- **MySQL 8.0.33** - Base de données
- **JPA 3.1.0** - Spécification de persistance
- **Maven** - Gestion des dépendances

## 1. Couche Persistance

### Entités JPA

#### Homme
- **Attributs** : id, nom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage

#### Femme
- **Attributs** : id, nom, prénom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage
- **Requêtes nommées** :
  - `Femme.findMarriedTwiceOrMore` : Trouve les femmes mariées au moins 2 fois
  - Requête native pour compter les enfants entre deux dates

#### Mariage
- **Clé composite** : MariagePK (homme_id, femme_id, date_debut)
- **Attributs** : date de fin, nombre d'enfants
- **Relations** : ManyToOne avec Homme et Femme

#### MariagePK
- **Classe Embeddable** pour la clé composite du mariage
- **Attributs** : hommeId, femmeId, dateDebut

### Configuration

#### persistence.xml
```xml
<persistence-unit name="mariagePU">
  - Driver: MySQL
  - URL: jdbc:mysql://localhost:3306/mariage_db
  - Auto-DDL: update
  - Show SQL: true
</persistence-unit>
```

#### HibernateUtil
Classe utilitaire pour gérer l'EntityManagerFactory et créer des EntityManager.

## 2. Couche Service

### Interface IDao<T>
Interface générique définissant les opérations CRUD de base :
- `create(T o)` : Créer une entité
- `delete(T o)` : Supprimer une entité
- `update(T o)` : Mettre à jour une entité
- `findById(int id)` : Trouver par ID
- `findAll()` : Récupérer toutes les entités

### HommeService

Implémente `IDao<Homme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getEpousesEntreDeuxDates(Homme homme, Date dateDebut, Date dateFin)**
   - Retourne la liste des épouses d'un homme marié entre deux dates
   - Utilise JPQL

2. **afficherMariagesHomme(Homme homme)**
   - Affiche tous les mariages d'un homme avec détails :
     - Nom de l'épouse
     - Dates de début et fin
     - Nombre d'enfants

### FemmeService

Implémente `IDao<Femme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreEnfantsEntreDeuxDates(Femme femme, Date dateDebut, Date dateFin)**
   - Exécute une requête native nommée
   - Retourne le nombre total d'enfants d'une femme entre deux dates

2. **getFemmesMarieesPlusieuresFois()**
   - Exécute une requête nommée
   - Retourne les femmes mariées au moins 2 fois

### MariageService

Implémente `IDao<Mariage>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Utilise l'**API Criteria** (CriteriaBuilder, CriteriaQuery)
   - Compte les hommes mariés à exactement 4 femmes entre deux dates
   - Démontre l'utilisation avancée de l'API Criteria avec :
     - Jointures
     - Groupement (GROUP BY)
     - Filtres (HAVING)

2. **getHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Alternative avec JPQL
   - Retourne la liste des hommes concernés

## Configuration de la Base de Données

### Prérequis
1. Installer MySQL Server
2. Créer un utilisateur avec les permissions appropriées

### Configuration
Modifier le fichier `persistence.xml` ou `application.properties` :

```properties
hibernate.connection.url=jdbc:mysql://localhost:3306/mariage_db?createDatabaseIfNotExist=true
hibernate.connection.username=root
hibernate.connection.password=YOUR_PASSWORD
```

### Génération automatique
La base de données et les tables sont **créées automatiquement** au premier lancement grâce à :
- `createDatabaseIfNotExist=true` dans l'URL
- `hibernate.hbm2ddl.auto=update` dans la configuration

## Utilisation

### Compilation
```bash
mvn clean install
```

### Exécution
```bash
mvn exec:java -Dexec.mainClass="ma.projet.Main"
```

Ou depuis votre IDE, exécutez la classe `Main.java`.

### Exemple d'utilisation dans Main.java

```java
// Créer les services
HommeService hommeService = new HommeService();
FemmeService femmeService = new FemmeService();
MariageService mariageService = new MariageService();

// Créer des entités
Homme h1 = new Homme("Alami", "0612345678", "Casablanca", new Date());
hommeService.create(h1);

Femme f1 = new Femme("Amrani", "Fatima", "0645678901", "Casablanca", new Date());
femmeService.create(f1);

// Créer un mariage
Mariage m1 = new Mariage(h1, f1, dateDebut, dateFin, 2);
mariageService.create(m1);

// Requêtes spécifiques
List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(h1, date1, date2);
long nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(f1, date1, date2);
List<Femme> femmesMultiMariages = femmeService.getFemmesMarieesPlusieuresFois();
long nbHommes = mariageService.getNombreHommesMariesQuatreFemmes(date1, date2);
```

## Fonctionnalités Implémentées

### ✅ Couche Persistance
- [x] Entités avec annotations JPA (Homme, Femme, Mariage)
- [x] Configuration persistence.xml
- [x] Classe HibernateUtil
- [x] Génération automatique de la base MySQL

### ✅ Couche Service
- [x] Interface IDao générique
- [x] HommeService avec méthode épouses entre dates
- [x] HommeService avec affichage détaillé des mariages
- [x] FemmeService avec requête native nommée (nombre d'enfants)
- [x] FemmeService avec requête nommée (femmes mariées 2+ fois)
- [x] MariageService avec API Criteria (hommes mariés 4 femmes)

## Modèle de Données

### Schéma des tables générées

```sql
Table: homme
- id (PK, AUTO_INCREMENT)
- nom
- telephone
- adresse
- date_naissance

Table: femme
- id (PK, AUTO_INCREMENT)
- nom
- prenom
- telephone
- adresse
- date_naissance

Table: mariage
- homme_id (PK, FK)
- femme_id (PK, FK)
- date_debut (PK)
- date_fin
- nombre_enfants
```

## Points Techniques Importants

1. **Clé Composite** : Utilisation de `@Embeddable` et `@EmbeddedId` pour MariagePK
2. **Requêtes Nommées** : `@NamedQuery` et `@NamedNativeQuery` dans Femme
3. **API Criteria** : Démonstration complète dans MariageService
4. **Relations Bidirectionnelles** : OneToMany/ManyToOne entre entités
5. **Gestion des Transactions** : EntityTransaction pour CRUD
6. **Lazy Loading** : Configuration FetchType.LAZY pour les collections

## Auteur

Projet d'exercice JPA/Hibernate - Gestion des Mariages

## License

Ce projet est à des fins éducatives.
# Projet de Gestion des Mariages - Spring Boot + Hibernate

Ce projet implémente un système de gestion des mariages entre hommes et femmes en utilisant **Spring Boot**, **Spring Data JPA** et **Hibernate** avec MySQL.

## 🎯 Technologies

- **Spring Boot 3.1.5** - Framework principal
- **Spring Data JPA** - Repositories et gestion des données
- **Hibernate 6.x** - ORM (inclus avec Spring Data JPA)
- **MySQL 8.0** - Base de données
- **Java 21** - Langage de programmation
- **Maven** - Gestion des dépendances

## Structure du Projet

```
exercice-3/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/
│   │   │       └── projet/
│   │   │           ├── beans/           # Entités JPA
│   │   │           │   ├── Homme.java
│   │   │           │   ├── Femme.java
│   │   │           │   ├── Mariage.java
│   │   │           │   └── MariagePK.java
│   │   │           ├── dao/             # Interface DAO
│   │   │           │   └── IDao.java
│   │   │           ├── service/         # Services métier
│   │   │           │   ├── HommeService.java
│   │   │           │   ├── FemmeService.java
│   │   │           │   └── MariageService.java
│   │   │           ├── util/            # Utilitaires
│   │   │           │   └── HibernateUtil.java
│   │   │           └── Main.java        # Classe principale
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml      # Configuration JPA
│   │       └── application.properties   # Propriétés de l'application
│   └── test/
└── pom.xml
```

## Technologies Utilisées

- **Java 21**
- **Hibernate 6.2.7** - ORM Framework
- **MySQL 8.0.33** - Base de données
- **JPA 3.1.0** - Spécification de persistance
- **Maven** - Gestion des dépendances

## 1. Couche Persistance

### Entités JPA

#### Homme
- **Attributs** : id, nom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage

#### Femme
- **Attributs** : id, nom, prénom, téléphone, adresse, date de naissance
- **Relations** : OneToMany avec Mariage
- **Requêtes nommées** :
  - `Femme.findMarriedTwiceOrMore` : Trouve les femmes mariées au moins 2 fois
  - Requête native pour compter les enfants entre deux dates

#### Mariage
- **Clé composite** : MariagePK (homme_id, femme_id, date_debut)
- **Attributs** : date de fin, nombre d'enfants
- **Relations** : ManyToOne avec Homme et Femme

#### MariagePK
- **Classe Embeddable** pour la clé composite du mariage
- **Attributs** : hommeId, femmeId, dateDebut

### Configuration

#### persistence.xml
```xml
<persistence-unit name="mariagePU">
  - Driver: MySQL
  - URL: jdbc:mysql://localhost:3306/mariage_db
  - Auto-DDL: update
  - Show SQL: true
</persistence-unit>
```

#### HibernateUtil
Classe utilitaire pour gérer l'EntityManagerFactory et créer des EntityManager.

## 2. Couche Service

### Interface IDao<T>
Interface générique définissant les opérations CRUD de base :
- `create(T o)` : Créer une entité
- `delete(T o)` : Supprimer une entité
- `update(T o)` : Mettre à jour une entité
- `findById(int id)` : Trouver par ID
- `findAll()` : Récupérer toutes les entités

### HommeService

Implémente `IDao<Homme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getEpousesEntreDeuxDates(Homme homme, Date dateDebut, Date dateFin)**
   - Retourne la liste des épouses d'un homme marié entre deux dates
   - Utilise JPQL

2. **afficherMariagesHomme(Homme homme)**
   - Affiche tous les mariages d'un homme avec détails :
     - Nom de l'épouse
     - Dates de début et fin
     - Nombre d'enfants

### FemmeService

Implémente `IDao<Femme>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreEnfantsEntreDeuxDates(Femme femme, Date dateDebut, Date dateFin)**
   - Exécute une requête native nommée
   - Retourne le nombre total d'enfants d'une femme entre deux dates

2. **getFemmesMarieesPlusieuresFois()**
   - Exécute une requête nommée
   - Retourne les femmes mariées au moins 2 fois

### MariageService

Implémente `IDao<Mariage>` avec les méthodes spécifiques :

#### Méthodes principales :
1. **getNombreHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Utilise l'**API Criteria** (CriteriaBuilder, CriteriaQuery)
   - Compte les hommes mariés à exactement 4 femmes entre deux dates
   - Démontre l'utilisation avancée de l'API Criteria avec :
     - Jointures
     - Groupement (GROUP BY)
     - Filtres (HAVING)

2. **getHommesMariesQuatreFemmes(Date dateDebut, Date dateFin)**
   - Alternative avec JPQL
   - Retourne la liste des hommes concernés

## Configuration de la Base de Données

### Prérequis
1. Installer MySQL Server
2. Créer un utilisateur avec les permissions appropriées

### Configuration
Modifier le fichier `persistence.xml` ou `application.properties` :

```properties
hibernate.connection.url=jdbc:mysql://localhost:3306/mariage_db?createDatabaseIfNotExist=true
hibernate.connection.username=root
hibernate.connection.password=YOUR_PASSWORD
```

### Génération automatique
La base de données et les tables sont **créées automatiquement** au premier lancement grâce à :
- `createDatabaseIfNotExist=true` dans l'URL
- `hibernate.hbm2ddl.auto=update` dans la configuration

## Utilisation

### Compilation
```bash
mvn clean install
```

### Exécution
```bash
mvn exec:java -Dexec.mainClass="ma.projet.Main"
```

Ou depuis votre IDE, exécutez la classe `Main.java`.

### Exemple d'utilisation dans Main.java

```java
// Créer les services
HommeService hommeService = new HommeService();
FemmeService femmeService = new FemmeService();
MariageService mariageService = new MariageService();

// Créer des entités
Homme h1 = new Homme("Alami", "0612345678", "Casablanca", new Date());
hommeService.create(h1);

Femme f1 = new Femme("Amrani", "Fatima", "0645678901", "Casablanca", new Date());
femmeService.create(f1);

// Créer un mariage
Mariage m1 = new Mariage(h1, f1, dateDebut, dateFin, 2);
mariageService.create(m1);

// Requêtes spécifiques
List<Femme> epouses = hommeService.getEpousesEntreDeuxDates(h1, date1, date2);
long nbEnfants = femmeService.getNombreEnfantsEntreDeuxDates(f1, date1, date2);
List<Femme> femmesMultiMariages = femmeService.getFemmesMarieesPlusieuresFois();
long nbHommes = mariageService.getNombreHommesMariesQuatreFemmes(date1, date2);
```

## Fonctionnalités Implémentées

### ✅ Couche Persistance
- [x] Entités avec annotations JPA (Homme, Femme, Mariage)
- [x] Configuration persistence.xml
- [x] Classe HibernateUtil
- [x] Génération automatique de la base MySQL

### ✅ Couche Service
- [x] Interface IDao générique
- [x] HommeService avec méthode épouses entre dates
- [x] HommeService avec affichage détaillé des mariages
- [x] FemmeService avec requête native nommée (nombre d'enfants)
- [x] FemmeService avec requête nommée (femmes mariées 2+ fois)
- [x] MariageService avec API Criteria (hommes mariés 4 femmes)

## Modèle de Données

### Schéma des tables générées

```sql
Table: homme
- id (PK, AUTO_INCREMENT)
- nom
- telephone
- adresse
- date_naissance

Table: femme
- id (PK, AUTO_INCREMENT)
- nom
- prenom
- telephone
- adresse
- date_naissance

Table: mariage
- homme_id (PK, FK)
- femme_id (PK, FK)
- date_debut (PK)
- date_fin
- nombre_enfants
```

## Points Techniques Importants

1. **Clé Composite** : Utilisation de `@Embeddable` et `@EmbeddedId` pour MariagePK
2. **Requêtes Nommées** : `@NamedQuery` et `@NamedNativeQuery` dans Femme
3. **API Criteria** : Démonstration complète dans MariageService
4. **Relations Bidirectionnelles** : OneToMany/ManyToOne entre entités
5. **Gestion des Transactions** : EntityTransaction pour CRUD
6. **Lazy Loading** : Configuration FetchType.LAZY pour les collections

## Auteur

Projet d'exercice JPA/Hibernate - Gestion des Mariages

## License

Ce projet est à des fins éducatives.

# Exercice_3
