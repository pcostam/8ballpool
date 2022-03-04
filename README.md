Inside kafka directory. Program tested on windows 10.
1. Start zookeeper server
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
2. Start kafka server 
bin\windows\kafka-server-start.bat config\server.properties
3. Build app
gradle build
4. Run app
./gradlew run
Additional notes:
To test only the producer use:
bin\windows\kafka-console-consumer.bat --topic 8sample-topic --from-beginning --bootstrap-server localhost:9092