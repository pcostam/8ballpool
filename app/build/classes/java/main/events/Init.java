
package events;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.processing.Generated;


/**
 * Init event
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "event-type",
        "time",
        "user-id",
        "country",
        "platform"
})
@Generated("jsonschema2pojo")
public class Init {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    private Init.EventType eventType;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("time")
    private Integer time;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-id")
    private String userId;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("country")
    private String country;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("platform")
    private String platform;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public Init.EventType getEventType() {
        return eventType;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public void setEventType(Init.EventType eventType) {
        this.eventType = eventType;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("time")
    public Integer getTime() {
        return time;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("time")
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-id")
    public String getUserId() {
        return userId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Generated("jsonschema2pojo")
    public enum EventType {

        INIT("init");
        private final String value;
        private final static Map<String, Init.EventType> CONSTANTS = new HashMap<String, Init.EventType>();

        static {
            for (Init.EventType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        EventType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Init.EventType fromValue(String value) {
            Init.EventType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}