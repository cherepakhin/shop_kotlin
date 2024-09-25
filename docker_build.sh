#./gradlew build
# size=52914

# build fat jar
./gradlew bootBuildImage
# size=56783818
# BUILD SUCCESSFUL in 3m 8s

# build docker image
docker build --build-arg JAR_FILE=build/libs/shop_kotlin-0.24.0105.jar -t shop_kotlin/app .

# run docker image (8980 - main port, 8988 - actuator)
docker run -p 8980:8980 -p 8988:8988  docker.io/library/shop_kotlin:0.24.0105

# echo test
http :8980/shop_kotlin/api/echo/aaaa