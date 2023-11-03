./gradlew build
docker build --build-arg JAR_FILE=build/libs/shop_kotlin-0.1.11.jar -t shop_kotlin/app .