package model;

import org.json.JSONObject;
import persistance.Writable;

// Represents a Champion in LoL and their optimal role
public class Champion implements Writable {
    private Role role;
    private String name;        // the Champions name

    // REQUIRES: champName has a non-zero length
    // EFFECTS: Constructs a champion with a name and role
    public Champion(String champName, Role role) {
        name = champName;
        this.role = role;

    }

    // EFFECTS: returns a string representing the champions role
    public String statString() {
        return name + " (Role = " + role + ")";
    }


    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("role", role);
        return json;
    }

}
