#!/bin/bash

set -e

rm -rf ~/.m2/repository/fr/inria/gforge/spoon/spoon-core/8.4.0-SNAPSHOT/ 

mvn clean

mvn deploy -DskipTests --offline

mvn install:install-file -Dfile=target/spoon-core-8.4.0-SNAPSHOT.jar -DgroupId=fr.inria.gforge.spoon -DartifactId=spoon-core -Dpackaging=jar -Dversion=8.4.0-SNAPSHOT -DpomFile=pom.xml

mvn source:jar install:install-file -Dfile=target/spoon-core-8.4.0-SNAPSHOT.jar -DgroupId=fr.inria.gforge.spoon -DartifactId=spoon-pom -Dpackaging=jar -Dversion=8.4.0-SNAPSHOT -DpomFile=pom.xml

mvn install:install-file -Dfile=target/spoon-core-8.4.0-SNAPSHOT-sources.jar -DgroupId=fr.inria.gforge.spoon -DartifactId=spoon-core -Dpackaging=jar -Dversion=8.4.0-SNAPSHOT -DpomFile=pom.xml -Dclassifier=sources