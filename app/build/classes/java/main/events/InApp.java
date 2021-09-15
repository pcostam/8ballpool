
package events;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * In-app purchase event
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "event-type",
        "time",
        "purchase_value",
        "user-id",
        "product-id"
})
@Generated("jsonschema2pojo")
public class InApp {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    private InApp.EventType eventType;
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
    @JsonProperty("purchase_value")
    private Double purchaseValue;
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
    @JsonProperty("product-id")
    private String productId;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public InApp.EventType getEventType() {
        return eventType;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public void setEventType(InApp.EventType eventType) {
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
    @JsonProperty("purchase_value")
    public Double getPurchaseValue() {
        return purchaseValue;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("purchase_value")
    public void setPurchaseValue(Double purchaseValue) {
        this.purchaseValue = purchaseValue;
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
    @JsonProperty("product-id")
    public String getProductId() {
        return productId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("product-id")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Generated("jsonschema2pojo")
    public enum EventType {

        IN_APP_PURCHASE("in-app-purchase");
        private final String value;
        private final static Map<String, InApp.EventType> CONSTANTS = new HashMap<String, InApp.EventType>();

        static {
            for (InApp.EventType c: values()) {
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
        public static InApp.EventType fromValue(String value) {
            InApp.EventType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}