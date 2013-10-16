mongodb-javaweb
===============
Aplicação Java web demonstra como trabalhar com Java e o MongoDB, uma alternativa de Banco de dados NoSQL. O MongoDB é um banco de dados orientado a Documentos, nessa aplicação seria a entidade Mercadoria.

Saiba mais sobre o MongoDB nessa url: http://www.mongodb.org/
É possível executar esse projeto de forma simples, da linha de comando. Utilizo o micro-container Spark, veja mais detalhes sobre essa tecnologia: http://www.sparkjava.com/ 

Esse projeto é complemento da palestra: http://www.slideshare.net/edermag/mongodb-altpersistencia

Na pasta comandos-mongo, você encontra exemplos de comandos para manipular dados no MongoDB via shell:
* Insert, Update, Delete, Query e Aggregation.

Detalhes da implementação
-------
Tecnologias utilizadas na implementação:

* Driver MongoDB para Java: a camada de persistência (DAO) utiliza a API disponível no driver do MongoDB para Java;
* Freemaker: tecnologia para construção de front-end (HTML) baseado em templates.
* Bootstrap: framework css;
* JQuery: framework JavaScript;
* Spark micro-container;

Pré-requisitos
-------
* JDK - versão 1.7 do Java, ou mais recente;
* Qualquer IDE Java com suporte ao Maven;
* Maven - para build e dependências.
* MongoDB versão 2.4.5;

