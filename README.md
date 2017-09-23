# CS 140: Computer Programming Fundamentals I

This code is product of my efforts in CS 140, Computer Programming Fundamentals I class in the Fall of 2017.

### How do I get set up?

* Java 8 container running on an Ubuntu 16.04 docker image.

    * [cogniteev/oracle-java](https://store.docker.com/community/images/cogniteev/oracle-java)

* Install instructions

    Pull and build image. 

        $ docker pull cogniteev/oracle-java

* Deployment instructions

        $ docker run -it --rm \
        $        --name java8 \
        $        -v /C/Users/mrtea/OneDrive/edu/wcc/2017_fall/cs140/labs:/data \
        $        cogniteev/oracle-java
    
    Or
    
        $ docker run -it --rm --name java8 cogniteev/oracle-java

    Run java

        $ docker run -it --rm --name java8 cogniteev/oracle-java java

    Run javac

        $ docker run -it --rm --name java8 cogniteev/oracle-java javac

    Bash into a running container

        $ docker exec -t -i java8 /bin/bash
