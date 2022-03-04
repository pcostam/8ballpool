package events;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coin-balance-after-match",
        "level-after-match",
        "device",
        "platform"
})
@Generated("jsonschema2pojo")
public class UserBPostmatchInfo {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("coin-balance-after-match")
    private Integer coinBalanceAfterMatch;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("level-after-match")
    private Integer levelAfterMatch;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("device")
    private String device;
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
    @JsonProperty("coin-balance-after-match")
    public Integer getCoinBalanceAfterMatch() {
        return coinBalanceAfterMatch;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("coin-balance-after-match")
    public void setCoinBalanceAfterMatch(Integer coinBalanceAfterMatch) {
        this.coinBalanceAfterMatch = coinBalanceAfterMatch;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("level-after-match")
    public Integer getLevelAfterMatch() {
        return levelAfterMatch;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("level-after-match")
    public void setLevelAfterMatch(Integer levelAfterMatch) {
        this.levelAfterMatch = levelAfterMatch;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("device")
    public String getDevice() {
        return device;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("device")
    public void setDevice(String device) {
        this.device = device;
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

}