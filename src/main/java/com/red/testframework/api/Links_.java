package com.red.testframework.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "first",
        "self",
        "next",
        "last",
        "profile",
        "search"
})
public class Links_ {

    @JsonProperty("first")
    private First first;
    @JsonProperty("self")
    private Self_ self;
    @JsonProperty("next")
    private Next next;
    @JsonProperty("last")
    private Last last;
    @JsonProperty("profile")
    private Profile profile;
    @JsonProperty("search")
    private Search search;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("first")
    public First getFirst() {
        return first;
    }

    @JsonProperty("first")
    public void setFirst(First first) {
        this.first = first;
    }

    @JsonProperty("self")
    public Self_ getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Self_ self) {
        this.self = self;
    }

    @JsonProperty("next")
    public Next getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(Next next) {
        this.next = next;
    }

    @JsonProperty("last")
    public Last getLast() {
        return last;
    }

    @JsonProperty("last")
    public void setLast(Last last) {
        this.last = last;
    }

    @JsonProperty("profile")
    public Profile getProfile() {
        return profile;
    }

    @JsonProperty("profile")
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @JsonProperty("search")
    public Search getSearch() {
        return search;
    }

    @JsonProperty("search")
    public void setSearch(Search search) {
        this.search = search;
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
