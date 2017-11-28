package org.chatlib.chatlib.model.operator;

import com.google.gson.annotations.SerializedName;

public class Greeting {
    @SerializedName("greating")
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
