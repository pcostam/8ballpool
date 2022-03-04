Component that writes real-time events. This project uses MongoBD, Kafka and Kafka Streams. 
One model consumer was used similarly to the repository: https://github.com/howtoprogram/Kafka-MultiThread-Java-Example/blob/master/src/main/java/com/howtoprogram/kafka/singleconsumer/SingleConsumerMain.java![image](https://user-images.githubusercontent.com/36275331/137787319-9762e6da-ad41-42df-95c8-d8c4789c83aa.png)


How to run on windows 10:
Inside kafka directory:
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
