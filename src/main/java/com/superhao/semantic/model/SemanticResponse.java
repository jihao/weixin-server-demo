
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
    "semantic",
    "rc",
    "operation",
    "service",
    "text"
})
public class SemanticResponse {

    @JsonProperty("semantic")
    @Valid
    private Semantic semantic;
    @JsonProperty("rc")
    private Integer rc;
    @JsonProperty("operation")
    private String operation;
    @JsonProperty("service")
    private String service;
    @JsonProperty("text")
    private String text;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The semantic
     */
    @JsonProperty("semantic")
    public Semantic getSemantic() {
        return semantic;
    }

    /**
     * 
     * @param semantic
     *     The semantic
     */
    @JsonProperty("semantic")
    public void setSemantic(Semantic semantic) {
        this.semantic = semantic;
    }

    /**
     * 
     * @return
     *     The rc
     */
    @JsonProperty("rc")
    public Integer getRc() {
        return rc;
    }

    /**
     * 
     * @param rc
     *     The rc
     */
    @JsonProperty("rc")
    public void setRc(Integer rc) {
        this.rc = rc;
    }

    /**
     * 
     * @return
     *     The operation
     */
    @JsonProperty("operation")
    public String getOperation() {
        return operation;
    }

    /**
     * 
     * @param operation
     *     The operation
     */
    @JsonProperty("operation")
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * 
     * @return
     *     The service
     */
    @JsonProperty("service")
    public String getService() {
        return service;
    }

    /**
     * 
     * @param service
     *     The service
     */
    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    /**
     * 
     * @return
     *     The text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
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
