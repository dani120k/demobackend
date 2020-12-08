package demobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XmlResponse {
    @JsonProperty(value = "text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
