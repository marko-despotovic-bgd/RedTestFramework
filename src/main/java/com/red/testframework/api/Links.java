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
        "self",
        "user",
        "events",
        "heroes",
        "roles"
})
public class Links {

    @JsonProperty("self")
    private Self self;
    @JsonProperty("user")
    private User_ user;
    @JsonProperty("events")
    private Events events;
    @JsonProperty("heroes")
    private Heroes heroes;
    @JsonProperty("roles")
    private Roles roles;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("self")
    public Self getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Self self) {
        this.self = self;
    }

    @JsonProperty("user")
    public User_ getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User_ user) {
        this.user = user;
    }

    @JsonProperty("events")
    public Events getEvents() {
        return events;
    }

    @JsonProperty("events")
    public void setEvents(Events events) {
        this.events = events;
    }

    @JsonProperty("heroes")
    public Heroes getHeroes() {
        return heroes;
    }

    @JsonProperty("heroes")
    public void setHeroes(Heroes heroes) {
        this.heroes = heroes;
    }

    @JsonProperty("roles")
    public Roles getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(Roles roles) {
        this.roles = roles;
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
