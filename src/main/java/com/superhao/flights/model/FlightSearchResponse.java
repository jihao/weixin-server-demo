
package com.superhao.flights.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "fromCityCN",
    "toCityCN",
    "fromDate",
    "flightSchedules",
    "rescode"
})
public class FlightSearchResponse {

    @JsonProperty("fromCityCN")
    private String fromCityCN;
    @JsonProperty("toCityCN")
    private String toCityCN;
    @JsonProperty("fromDate")
    private String fromDate;
    @JsonProperty("flightSchedules")
    @Valid
    private List<FlightSchedule> flightSchedules = new ArrayList<FlightSchedule>();
    @JsonProperty("rescode")
    private String rescode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The fromCityCN
     */
    @JsonProperty("fromCityCN")
    public String getFromCityCN() {
        return fromCityCN;
    }

    /**
     * 
     * @param fromCityCN
     *     The fromCityCN
     */
    @JsonProperty("fromCityCN")
    public void setFromCityCN(String fromCityCN) {
        this.fromCityCN = fromCityCN;
    }

    /**
     * 
     * @return
     *     The toCityCN
     */
    @JsonProperty("toCityCN")
    public String getToCityCN() {
        return toCityCN;
    }

    /**
     * 
     * @param toCityCN
     *     The toCityCN
     */
    @JsonProperty("toCityCN")
    public void setToCityCN(String toCityCN) {
        this.toCityCN = toCityCN;
    }

    /**
     * 
     * @return
     *     The fromDate
     */
    @JsonProperty("fromDate")
    public String getFromDate() {
        return fromDate;
    }

    /**
     * 
     * @param fromDate
     *     The fromDate
     */
    @JsonProperty("fromDate")
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * 
     * @return
     *     The flightSchedules
     */
    @JsonProperty("flightSchedules")
    public List<FlightSchedule> getFlightSchedules() {
        return flightSchedules;
    }

    /**
     * 
     * @param flightSchedules
     *     The flightSchedules
     */
    @JsonProperty("flightSchedules")
    public void setFlightSchedules(List<FlightSchedule> flightSchedules) {
        this.flightSchedules = flightSchedules;
    }

    /**
     * 
     * @return
     *     The rescode
     */
    @JsonProperty("rescode")
    public String getRescode() {
        return rescode;
    }

    /**
     * 
     * @param rescode
     *     The rescode
     */
    @JsonProperty("rescode")
    public void setRescode(String rescode) {
        this.rescode = rescode;
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
