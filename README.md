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
Die JBoss Datasource für die Anbindung der MySQL Datenbank liegt im Projekt "BuildAndDeployEnvironment". Der zugehörige JNDI-Name lautet:

    java:jdbc/PizzaAppDS

Der benötigte JDBC-Treiber liegt im Projekt "BasicLibraries". Verwendet wird:

	mysql-connector-java-5.1.18-bin.jar

Build Properties:
-----------------	
Für die ant Skripten existieren global-build.properties im Projekt "BuildAndDeployEnvironment". Diese müssen individuell auf jedem Rechner, je nach Konfiguration angepasst werden. Speziell der Installationspfad des JBoss Application Server muss richtig gesetzt werden, da sonst der Build-Prozess fehlschlägt.

Build:
------
Die Pizza-Service App lässt sich per ant bauen. Das zugehörige Skript liegt im Projekt "PizzaApp".

    ant -buildfile PizzaApp/build.xml complete-build

Deployment:
-----------
Für das automatische Deployment ist ebenfalls ein ant Skript vorhanden.

    ant -buildfile PizzaApp/dev-build.xml deploy
