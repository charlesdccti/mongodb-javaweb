#!/bin/bash

if [ -z "$1" ]
  then
    echo "Build via maven ..."
    mvn compile exec:java -Dexec.mainClass=br.com.yaw.mongo.MercadoriaController
else
    if [ $1 = "gradle" ]
      then
        echo "Build via gradle ..."
        gradle run
    else
        echo "Build via maven ..."
        mvn compile exec:java -Dexec.mainClass=br.com.yaw.mongo.MercadoriaController
    fi
fi


