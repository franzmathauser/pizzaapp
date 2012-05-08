Projektwerkstatt - Pizza-Service App
====================================

Dieses Repository beinhaltet alle Projekte der Pizza-Service App.

Voraussetzungen: 
----------------
* JDK 1.6
* Eclipse Indigo SR2
* Checkstyle Plugin
* JBoss Tools Plugin
* EGit Plugin
* Android Development Tools Plugin
* JBoss Application Server 6.1
* MySQL 5.5
* Android SDK (V2.2 mit Google API)

Datasource:
-----------
Die JBoss Datasource f�r die Anbindung der MySQL Datenbank liegt im Projekt "BuildAndDeployEnvironment". Der zugeh�rige JNDI-Name lautet:

    java:jdbc/PizzaAppDS

Der ben�tigte JDBC-Treiber liegt im Projekt "BasicLibraries". Verwendet wird:

	mysql-connector-java-5.1.18-bin.jar

Build Properties:
-----------------	
F�r die ant Skripten existieren global-build.properties im Projekt "BuildAndDeployEnvironment". Diese m�ssen individuell auf jedem Rechner, je nach Konfiguration angepasst werden. Speziell der Installationspfad des JBoss Application Server muss richtig gesetzt werden, da sonst der Build-Prozess fehlschl�gt.

Build:
------
Die Pizza-Service App l�sst sich per ant bauen. Das zugeh�rige Skript liegt im Projekt "PizzaApp".

    ant -buildfile PizzaApp/build.xml complete-build

Deployment:
-----------
F�r das automatische Deployment ist ebenfalls ein ant Skript vorhanden.

    ant -buildfile PizzaApp/dev-build.xml deploy
