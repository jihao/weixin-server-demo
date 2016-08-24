
package com.superhao.semantic.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "startDate",
    "endLoc",
    "startLoc"
})
public class Slots {

    @JsonProperty("startDate")
    @Valid
    private StartDate startDate;
    @JsonProperty("endLoc")
    @Valid
    private EndLoc endLoc;
    @JsonProperty("startLoc")
    @Valid
    private StartLoc startLoc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The startDate
     */
    @JsonProperty("startDate")
    public StartDate getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The startDate
     */
    @JsonProperty("startDate")
    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The endLoc
     */
    @JsonProperty("endLoc")
    public EndLoc getEndLoc() {
        return endLoc;
    }

    /**
     * 
     * @param endLoc
     *     The endLoc
     */
    @JsonProperty("endLoc")
    public void setEndLoc(EndLoc endLoc) {
        this.endLoc = endLoc;
    }

    /**
     * 
     * @return
     *     The startLoc
     */
    @JsonProperty("startLoc")
    public StartLoc getStartLoc() {
        return startLoc;
    }

    /**
     * 
     * @param startLoc
     *     The startLoc
     */
    @JsonProperty("startLoc")
    public void setStartLoc(StartLoc startLoc) {
        this.startLoc = startLoc;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
