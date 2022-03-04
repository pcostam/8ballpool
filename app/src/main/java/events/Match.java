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
 * Match event
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "event-type",
        "time",
        "user-a",
        "user-b",
        "user-a-postmatch-info",
        "user-b-postmatch-info",
        "winner",
        "game-tier",
        "duration"
})
@Generated("jsonschema2pojo")
public class Match extends Event{

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    private Match.EventType eventType;
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
    @JsonProperty("user-a")
    private String userA;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-b")
    private String userB;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-a-postmatch-info")
    private UserAPostmatchInfo userAPostmatchInfo;
    @JsonProperty("user-b-postmatch-info")
    private UserBPostmatchInfo userBPostmatchInfo;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("winner")
    private String winner;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("game-tier")
    private Integer gameTier;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("duration")
    private Integer duration;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public Match.EventType getEventType() {
        return eventType;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("event-type")
    public void setEventType(Match.EventType eventType) {
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
    @JsonProperty("user-a")
    public String getUserA() {
        return userA;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-a")
    public void setUserA(String userA) {
        this.userA = userA;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-b")
    public String getUserB() {
        return userB;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-b")
    public void setUserB(String userB) {
        this.userB = userB;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-a-postmatch-info")
    public UserAPostmatchInfo getUserAPostmatchInfo() {
        return userAPostmatchInfo;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("user-a-postmatch-info")
    public void setUserAPostmatchInfo(UserAPostmatchInfo userAPostmatchInfo) {
        this.userAPostmatchInfo = userAPostmatchInfo;
    }

    @JsonProperty("user-b-postmatch-info")
    public UserBPostmatchInfo getUserBPostmatchInfo() {
        return userBPostmatchInfo;
    }

    @JsonProperty("user-b-postmatch-info")
    public void setUserBPostmatchInfo(UserBPostmatchInfo userBPostmatchInfo) {
        this.userBPostmatchInfo = userBPostmatchInfo;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("winner")
    public String getWinner() {
        return winner;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("winner")
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("game-tier")
    public Integer getGameTier() {
        return gameTier;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("game-tier")
    public void setGameTier(Integer gameTier) {
        this.gameTier = gameTier;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Generated("jsonschema2pojo")
    public enum EventType {

        MATCH("match");
        private final String value;
        private final static Map<String, Match.EventType> CONSTANTS = new HashMap<String, Match.EventType>();

        static {
            for (Match.EventType c: values()) {
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
        public static Match.EventType fromValue(String value) {
            Match.EventType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}

