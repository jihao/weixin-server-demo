
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
    "yPrice",
    "fPrice",
    "cPrice"
})
public class FullPriceCabin {

    @JsonProperty("yPrice")
    private Integer yPrice;
    @JsonProperty("fPrice")
    private Integer fPrice;
    @JsonProperty("cPrice")
    private Integer cPrice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The yPrice
     */
    @JsonProperty("yPrice")
    public Integer getYPrice() {
        return yPrice;
    }

    /**
     * 
     * @param yPrice
     *     The yPrice
     */
    @JsonProperty("yPrice")
    public void setYPrice(Integer yPrice) {
        this.yPrice = yPrice;
    }

    /**
     * 
     * @return
     *     The fPrice
     */
    @JsonProperty("fPrice")
    public Integer getFPrice() {
        return fPrice;
    }

    /**
     * 
     * @param fPrice
     *     The fPrice
     */
    @JsonProperty("fPrice")
    public void setFPrice(Integer fPrice) {
        this.fPrice = fPrice;
    }

    /**
     * 
     * @return
     *     The cPrice
     */
    @JsonProperty("cPrice")
    public Integer getCPrice() {
        return cPrice;
    }

    /**
     * 
     * @param cPrice
     *     The cPrice
     */
    @JsonProperty("cPrice")
    public void setCPrice(Integer cPrice) {
        this.cPrice = cPrice;
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
