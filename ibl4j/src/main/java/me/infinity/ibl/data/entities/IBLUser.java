package me.infinity.ibl.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IBLUser {
    @JsonIgnore
    private boolean exists = true;

    private String nickname;
    private String about;
    @JsonProperty("certified_dev")
    private boolean certifiedDev;
    private boolean developer;
    private boolean staff;
    private Links links;

    private IBLUser() {
    }

    @JsonIgnore
    public void doesNotExist() {
        exists = false;
    }

    @JsonIgnore
    public boolean exists() {
        return exists;
    }

    public boolean isExists() {
        return exists;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAbout() {
        return about;
    }

    public boolean isCertifiedDev() {
        return certifiedDev;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public boolean isStaff() {
        return staff;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private String website;
        private String github;

        public String getWebsite() {
            return website;
        }

        public String getGithub() {
            return github;
        }
    }
}
