package kafka;

import events.Event;
import events.InApp;
import events.Init;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerThreadHandler implements Runnable {
    private ConsumerRecord<String, ? extends Event> consumerRecord;

    public ConsumerThreadHandler(ConsumerRecord<String, ? extends Event > consumerRecord) {
        this.consumerRecord = consumerRecord;
    }

    @Override
    public void run() {
        if(consumerRecord.value().getEventType().toString().equals("init")) {
            ConsumerRecord<String, Init>  subtypeConsumerRecord = (ConsumerRecord<String, Init>) consumerRecord;
            System.out.println("Process: " + subtypeConsumerRecord.value().getCountry() + ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

        else if(consumerRecord.value().getEventType().toString().equals("in-app-purchase")) {
            ConsumerRecord<String, InApp>  subtypeConsumerRecord = (ConsumerRecord<String, InApp>) consumerRecord;
            System.out.println("Process: " + subtypeConsumerRecord.value().getUserId() + ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

    }
}
