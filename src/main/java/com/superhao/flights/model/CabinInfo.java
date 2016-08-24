
package com.superhao.flights.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "cabinCode",
    "cabinRank",
    "cabinRankDetail",
    "restSeatNumber",
    "facePrice",
    "discount"
})
public class CabinInfo {

    @JsonProperty("cabinCode")
    private String cabinCode;
    @JsonProperty("cabinRank")
    private String cabinRank;
    @JsonProperty("cabinRankDetail")
    private String cabinRankDetail;
    @JsonProperty("restSeatNumber")
    private Integer restSeatNumber;
    @JsonProperty("facePrice")
    private Integer facePrice;
    @JsonProperty("discount")
    private Integer discount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The cabinCode
     */
    @JsonProperty("cabinCode")
    public String getCabinCode() {
        return cabinCode;
    }

    /**
     * 
     * @param cabinCode
     *     The cabinCode
     */
    @JsonProperty("cabinCode")
    public void setCabinCode(String cabinCode) {
        this.cabinCode = cabinCode;
    }

    /**
     * 
     * @return
     *     The cabinRank
     */
    @JsonProperty("cabinRank")
    public String getCabinRank() {
        return cabinRank;
    }

    /**
     * 
     * @param cabinRank
     *     The cabinRank
     */
    @JsonProperty("cabinRank")
    public void setCabinRank(String cabinRank) {
        this.cabinRank = cabinRank;
    }

    /**
     * 
     * @return
     *     The cabinRankDetail
     */
    @JsonProperty("cabinRankDetail")
    public String getCabinRankDetail() {
        return cabinRankDetail;
    }

    /**
     * 
     * @param cabinRankDetail
     *     The cabinRankDetail
     */
    @JsonProperty("cabinRankDetail")
    public void setCabinRankDetail(String cabinRankDetail) {
        this.cabinRankDetail = cabinRankDetail;
    }

    /**
     * 
     * @return
     *     The restSeatNumber
     */
    @JsonProperty("restSeatNumber")
    public Integer getRestSeatNumber() {
        return restSeatNumber;
    }

    /**
     * 
     * @param restSeatNumber
     *     The restSeatNumber
     */
    @JsonProperty("restSeatNumber")
    public void setRestSeatNumber(Integer restSeatNumber) {
        this.restSeatNumber = restSeatNumber;
    }

    /**
     * 
     * @return
     *     The facePrice
     */
    @JsonProperty("facePrice")
    public Integer getFacePrice() {
        return facePrice;
    }

    /**
     * 
     * @param facePrice
     *     The facePrice
     */
    @JsonProperty("facePrice")
    public void setFacePrice(Integer facePrice) {
        this.facePrice = facePrice;
    }

    /**
     * 
     * @return
     *     The discount
     */
    @JsonProperty("discount")
    public Integer getDiscount() {
        return discount;
    }

    /**
     * 
     * @param discount
     *     The discount
     */
    @JsonProperty("discount")
    public void setDiscount(Integer discount) {
        this.discount = discount;
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
