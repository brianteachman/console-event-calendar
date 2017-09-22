# CS 140: Computer Programming Fundamentals I #

This is code is product of my efforts in CS 140, Computer Programming Fundamentals I class in the Fall of 2017.

### How do I get set up? ###

* Summary of set up
    
    Java 8 is run through a docker image.

    * [Official](https://github.com/dockerfile/java/tree/master/oracle-java8)
    * [cogniteev/oracle-java](https://store.docker.com/community/images/cogniteev/oracle-java)

* Install instructions

    Official image:

        $ docker pull store/oracle/serverjre:8

    cogniteev/oracle-java:

        $ docker pull cogniteev/oracle-java

* Deployment instructions

        $ docker run -it --rm cogniteev/oracle-java

    Run java

        $ docker run -it --rm cogniteev/oracle-java java

    Run javac

        $ docker run -it --rm cogniteev/oracle-java javac

