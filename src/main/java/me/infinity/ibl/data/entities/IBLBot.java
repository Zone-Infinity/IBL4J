package me.infinity.ibl.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IBLBot {
    @JsonIgnore
    private boolean exists = true;

    private String name;
    private String tags;
    private String prefix;
    private String owner;
    private List<String> additionalOwners;
    private String library;
    @JsonProperty("short")
    private String shortDescription;
    @JsonProperty("long")
    private String longDescription;

    private boolean staffBot;
    private boolean nsfw;

    private Programs programs;
    private Analytics analytics;
    private Links links;

    private IBLBot() {
    }

    @JsonIgnore
    public void doesNotExist() {
        exists = false;
    }

    @JsonIgnore
    public boolean exists() {
        return exists;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getOwner() {
        return owner;
    }

    @SuppressWarnings("unchecked")
    public void setAdditionalOwners(Object additionalOwners) {
        if (additionalOwners instanceof List)
            if (((List<?>) additionalOwners).size() == 0)
                this.additionalOwners = new ArrayList<>();
            else
                this.additionalOwners = (List<String>) additionalOwners;
        else
            this.additionalOwners = new ArrayList<>();
    }

    public List<String> getAdditionalOwners() {
        return additionalOwners;
    }

    public String getLibrary() {
        return library;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public boolean isStaffBot() {
        return staffBot;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public Programs getPrograms() {
        return programs;
    }

    public Analytics getAnalytics() {
        return analytics;
    }

    public Links getLinks() {
        return links;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Programs {
        private boolean premium;
        private boolean certified;

        public boolean isPremium() {
            return premium;
        }

        public boolean isCertified() {
            return certified;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Analytics {
        private int servers;
        private int shards;
        private int votes;
        private int views;
        private int invites;

        public int getServers() {
            return servers;
        }

        public int getShards() {
            return shards;
        }

        public int getVotes() {
            return votes;
        }

        public int getViews() {
            return views;
        }

        public int getInvites() {
            return invites;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private String website;
        private String donate;
        private String support;
        private String github;
        private String banner;
        private String invite;

        public String getWebsite() {
            return website;
        }

        public String getDonate() {
            return donate;
        }

        public String getSupport() {
            return support;
        }

        public String getGithub() {
            return github;
        }

        public String getBanner() {
            return banner;
        }

        public String getInvite() {
            return invite;
        }
    }
}
