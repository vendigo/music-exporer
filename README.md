docker build -t dmmarchenko/music-explorer .

./gradlew bootBuildImage --imageName=dmmarchenko/music-explorer

docker run -e "SPRING_PROFILES_ACTIVE=local" -p 8080:8080 --name MusicExplorer -t dmmarchenko/music-explorer 