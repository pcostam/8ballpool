package events;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*
Parent class for events InApp and Init. Useful to interact with Kafka Api when type of the
event is not yet known
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event<E extends Enum<E>> {
    private E eventType;

    protected Event(E eventType) {
        this.eventType = eventType;
    }

    protected Event() {
    }


    public E getEventType() {
        return this.eventType;
    }

    public void setEventType(E eventType) {
        this.eventType = eventType;
    }


}
