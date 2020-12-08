package demobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TreeNode {
    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "children")
    private List<TreeNode> children;

    @JsonProperty(value = "isVrt")
    private Boolean isVrt;

    @JsonProperty(value = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVrt() {
        return isVrt;
    }

    public void setVrt(Boolean vrt) {
        isVrt = vrt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
