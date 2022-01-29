FROM openjdk:latest
COPY ./target/seMethods-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "seMethods-0.1.0.2-jar-with-dependencies.jar"]

#  at this stage we will build a image (Build the Docker Image)
# (We can do this by clicking the green triangles in the  Dockerfile and selecting Build Image for Dockerfile. )
#  after when the image is build, should be on the buttom sha**** ()
#  we need to right clique on  the image and create a container and provide him with
# the same netwokr bridge se-methods than after keep default the containerID and run
# Then the container will be creater and talk to the mongoDB server