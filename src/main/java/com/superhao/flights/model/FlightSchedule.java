
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
    "flightNo",
    "airlineCompany",
    "shareFlightNo",
    "planeModelCN",
    "fromAirport",
    "toAirport",
    "fromAirportCN",
    "toAirportCN",
    "LowestPriceRestSeat",
    "fromDate",
    "toDate",
    "flightDuration",
    "fromTower",
    "toTower",
    "crossDay",
    "stopCount",
    "planeModel",
    "lowestPrice",
    "fullPriceCabin",
    "airportFee",
    "fuelTax",
    "cabinInfo"
})
public class FlightSchedule {

    @JsonProperty("flightNo")
    private String flightNo;
    @JsonProperty("airlineCompany")
    private String airlineCompany;
    @JsonProperty("shareFlightNo")
    private Boolean shareFlightNo;
    @JsonProperty("planeModelCN")
    private String planeModelCN;
    @JsonProperty("fromAirport")
    private String fromAirport;
    @JsonProperty("toAirport")
    private String toAirport;
    @JsonProperty("fromAirportCN")
    private String fromAirportCN;
    @JsonProperty("toAirportCN")
    private String toAirportCN;
    @JsonProperty("LowestPriceRestSeat")
    private Integer lowestPriceRestSeat;
    @JsonProperty("fromDate")
    private String fromDate;
    @JsonProperty("toDate")
    private String toDate;
    @JsonProperty("flightDuration")
    private String flightDuration;
    @JsonProperty("fromTower")
    private String fromTower;
    @JsonProperty("toTower")
    private String toTower;
    @JsonProperty("crossDay")
    private Boolean crossDay;
    @JsonProperty("stopCount")
    private Integer stopCount;
    @JsonProperty("planeModel")
    private String planeModel;
    @JsonProperty("lowestPrice")
    private Integer lowestPrice;
    @JsonProperty("fullPriceCabin")
    @Valid
    private FullPriceCabin fullPriceCabin;
    @JsonProperty("airportFee")
    private Integer airportFee;
    @JsonProperty("fuelTax")
    private Integer fuelTax;
    @JsonProperty("cabinInfo")
    @Valid
    private List<CabinInfo> cabinInfo = new ArrayList<CabinInfo>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The flightNo
     */
    @JsonProperty("flightNo")
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 
     * @param flightNo
     *     The flightNo
     */
    @JsonProperty("flightNo")
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 
     * @return
     *     The airlineCompany
     */
    @JsonProperty("airlineCompany")
    public String getAirlineCompany() {
        return airlineCompany;
    }

    /**
     * 
     * @param airlineCompany
     *     The airlineCompany
     */
    @JsonProperty("airlineCompany")
    public void setAirlineCompany(String airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    /**
     * 
     * @return
     *     The shareFlightNo
     */
    @JsonProperty("shareFlightNo")
    public Boolean getShareFlightNo() {
        return shareFlightNo;
    }

    /**
     * 
     * @param shareFlightNo
     *     The shareFlightNo
     */
    @JsonProperty("shareFlightNo")
    public void setShareFlightNo(Boolean shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    /**
     * 
     * @return
     *     The planeModelCN
     */
    @JsonProperty("planeModelCN")
    public String getPlaneModelCN() {
        return planeModelCN;
    }

    /**
     * 
     * @param planeModelCN
     *     The planeModelCN
     */
    @JsonProperty("planeModelCN")
    public void setPlaneModelCN(String planeModelCN) {
        this.planeModelCN = planeModelCN;
    }

    /**
     * 
     * @return
     *     The fromAirport
     */
    @JsonProperty("fromAirport")
    public String getFromAirport() {
        return fromAirport;
    }

    /**
     * 
     * @param fromAirport
     *     The fromAirport
     */
    @JsonProperty("fromAirport")
    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    /**
     * 
     * @return
     *     The toAirport
     */
    @JsonProperty("toAirport")
    public String getToAirport() {
        return toAirport;
    }

    /**
     * 
     * @param toAirport
     *     The toAirport
     */
    @JsonProperty("toAirport")
    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    /**
     * 
     * @return
     *     The fromAirportCN
     */
    @JsonProperty("fromAirportCN")
    public String getFromAirportCN() {
        return fromAirportCN;
    }

    /**
     * 
     * @param fromAirportCN
     *     The fromAirportCN
     */
    @JsonProperty("fromAirportCN")
    public void setFromAirportCN(String fromAirportCN) {
        this.fromAirportCN = fromAirportCN;
    }

    /**
     * 
     * @return
     *     The toAirportCN
     */
    @JsonProperty("toAirportCN")
    public String getToAirportCN() {
        return toAirportCN;
    }

    /**
     * 
     * @param toAirportCN
     *     The toAirportCN
     */
    @JsonProperty("toAirportCN")
    public void setToAirportCN(String toAirportCN) {
        this.toAirportCN = toAirportCN;
    }

    /**
     * 
     * @return
     *     The lowestPriceRestSeat
     */
    @JsonProperty("LowestPriceRestSeat")
    public Integer getLowestPriceRestSeat() {
        return lowestPriceRestSeat;
    }

    /**
     * 
     * @param lowestPriceRestSeat
     *     The LowestPriceRestSeat
     */
    @JsonProperty("LowestPriceRestSeat")
    public void setLowestPriceRestSeat(Integer lowestPriceRestSeat) {
        this.lowestPriceRestSeat = lowestPriceRestSeat;
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
     *     The toDate
     */
    @JsonProperty("toDate")
    public String getToDate() {
        return toDate;
    }

    /**
     * 
     * @param toDate
     *     The toDate
     */
    @JsonProperty("toDate")
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * 
     * @return
     *     The flightDuration
     */
    @JsonProperty("flightDuration")
    public String getFlightDuration() {
        return flightDuration;
    }

    /**
     * 
     * @param flightDuration
     *     The flightDuration
     */
    @JsonProperty("flightDuration")
    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    /**
     * 
     * @return
     *     The fromTower
     */
    @JsonProperty("fromTower")
    public String getFromTower() {
        return fromTower;
    }

    /**
     * 
     * @param fromTower
     *     The fromTower
     */
    @JsonProperty("fromTower")
    public void setFromTower(String fromTower) {
        this.fromTower = fromTower;
    }

    /**
     * 
     * @return
     *     The toTower
     */
    @JsonProperty("toTower")
    public String getToTower() {
        return toTower;
    }

    /**
     * 
     * @param toTower
     *     The toTower
     */
    @JsonProperty("toTower")
    public void setToTower(String toTower) {
        this.toTower = toTower;
    }

    /**
     * 
     * @return
     *     The crossDay
     */
    @JsonProperty("crossDay")
    public Boolean getCrossDay() {
        return crossDay;
    }

    /**
     * 
     * @param crossDay
     *     The crossDay
     */
    @JsonProperty("crossDay")
    public void setCrossDay(Boolean crossDay) {
        this.crossDay = crossDay;
    }

    /**
     * 
     * @return
     *     The stopCount
     */
    @JsonProperty("stopCount")
    public Integer getStopCount() {
        return stopCount;
    }

    /**
     * 
     * @param stopCount
     *     The stopCount
     */
    @JsonProperty("stopCount")
    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }

    /**
     * 
     * @return
     *     The planeModel
     */
    @JsonProperty("planeModel")
    public String getPlaneModel() {
        return planeModel;
    }

    /**
     * 
     * @param planeModel
     *     The planeModel
     */
    @JsonProperty("planeModel")
    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    /**
     * 
     * @return
     *     The lowestPrice
     */
    @JsonProperty("lowestPrice")
    public Integer getLowestPrice() {
        return lowestPrice;
    }

    /**
     * 
     * @param lowestPrice
     *     The lowestPrice
     */
    @JsonProperty("lowestPrice")
    public void setLowestPrice(Integer lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * 
     * @return
     *     The fullPriceCabin
     */
    @JsonProperty("fullPriceCabin")
    public FullPriceCabin getFullPriceCabin() {
        return fullPriceCabin;
    }

    /**
     * 
     * @param fullPriceCabin
     *     The fullPriceCabin
     */
    @JsonProperty("fullPriceCabin")
    public void setFullPriceCabin(FullPriceCabin fullPriceCabin) {
        this.fullPriceCabin = fullPriceCabin;
    }

    /**
     * 
     * @return
     *     The airportFee
     */
    @JsonProperty("airportFee")
    public Integer getAirportFee() {
        return airportFee;
    }

    /**
     * 
     * @param airportFee
     *     The airportFee
     */
    @JsonProperty("airportFee")
    public void setAirportFee(Integer airportFee) {
        this.airportFee = airportFee;
    }

    /**
     * 
     * @return
     *     The fuelTax
     */
    @JsonProperty("fuelTax")
    public Integer getFuelTax() {
        return fuelTax;
    }

    /**
     * 
     * @param fuelTax
     *     The fuelTax
     */
    @JsonProperty("fuelTax")
    public void setFuelTax(Integer fuelTax) {
        this.fuelTax = fuelTax;
    }

    /**
     * 
     * @return
     *     The cabinInfo
     */
    @JsonProperty("cabinInfo")
    public List<CabinInfo> getCabinInfo() {
        return cabinInfo;
    }

    /**
     * 
     * @param cabinInfo
     *     The cabinInfo
     */
    @JsonProperty("cabinInfo")
    public void setCabinInfo(List<CabinInfo> cabinInfo) {
        this.cabinInfo = cabinInfo;
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
