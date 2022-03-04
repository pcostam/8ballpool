package kafka;

import events.Event;
import events.InApp;
import events.Init;
import events.Match;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerThreadHandler implements Runnable {
    private ConsumerRecord<String, ? extends Event> consumerRecord;

    public ConsumerThreadHandler(ConsumerRecord<String, ? extends Event > consumerRecord) {
        this.consumerRecord = consumerRecord;
    }

    @Override
    public void run() {
        if(consumerRecord.value() instanceof Init) {
            ConsumerRecord<String, Init>  subtypeConsumerRecord = (ConsumerRecord<String, Init>) consumerRecord;
            System.out.println("Process: " + subtypeConsumerRecord.value().getEventType() + " "
                    +  subtypeConsumerRecord.value().getCountry()
                    + " " + subtypeConsumerRecord.value().getPlatform()
                    + " " + subtypeConsumerRecord.value().getUserId()
                    + " " + subtypeConsumerRecord.value().getTime() + ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

        else if(consumerRecord.value() instanceof InApp) {
            ConsumerRecord<String, InApp>  subtypeConsumerRecord = (ConsumerRecord<String, InApp>) consumerRecord;
            System.out.println("Process: "
                    + subtypeConsumerRecord.value().getEventType()
                    + " " + subtypeConsumerRecord.value().getUserId()
                    + " " + subtypeConsumerRecord.value().getProductId()
                    + " " + subtypeConsumerRecord.value().getTime() +
                   ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

        else if(consumerRecord.value() instanceof Match) {
            ConsumerRecord<String, Match>  subtypeConsumerRecord = (ConsumerRecord<String, Match>) consumerRecord;
            System.out.println("Process: "
                    + subtypeConsumerRecord.value().getEventType()
                    + " " + subtypeConsumerRecord.value().getUserA()
                    + " " + subtypeConsumerRecord.value().getUserB() + 
                    ", Offset: " + consumerRecord.offset()
                    + ", By ThreadID: " + Thread.currentThread().getId());
        }

    }
}
