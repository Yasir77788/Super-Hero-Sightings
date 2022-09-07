package com.sg.superheroSightings.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Organization {


    private int orgId;

    @NotBlank(message = "Organization name must not be empty.")
    @Size(max = 50, message="Organization name must be less than 50 characters.")
    private String orgName;

    @NotBlank(message = "Organization description must not be empty.")
    @Size(max = 200, message="Organization description must be less than 200 characters.")
    private String orgDescription;

    @NotBlank(message = "Organization super-status must not be empty.")
    @Size(max = 50, message="Organization super-status must be less than 50 characters.")
    private String heroOrVillainOrg;

    @NotBlank(message = "Organization phone must not be empty.")
    @Size(max = 50, message="Organization phone must be less than 50 characters.")
    private String orgPhone;

    @NotBlank(message = "Organization email must not be empty.")
    @Size(max = 50, message="Organization email must be less than 50 characters.")
    private String orgEmail;

    @NotBlank(message = "Organization location must not be empty.")
    @Size(max = 50, message="Organization location must be less than 50 characters.")
    private Location location;

    @NotBlank(message = "Organization members must not be empty.")
    @Size(max = 50, message="Organization members must be less than 50 characters.")
    private List<Super> members;


    public Organization(String orgName, String orgDescription, String heroOrVillainOrg,
                        String orgPhone, String orgEmail, Location location, List<Super> members) {
        this.orgName = orgName;
        this.orgDescription = orgDescription;
        this.heroOrVillainOrg = heroOrVillainOrg;
        this.orgPhone = orgPhone;
        this.orgEmail = orgEmail;
        this.location = location;
        this.members = members;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getHeroOrVillainOrg() {
        return heroOrVillainOrg;
    }

    public void setHeroOrVillainOrg(String heroOrVillainOrg) {
        this.heroOrVillainOrg = heroOrVillainOrg;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Super> getMembers() {
        return members;
    }

    public void setMembers(List<Super> members) {
        this.members = members;
    }

    public Organization() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return orgId == that.orgId && Objects.equals(orgName, that.orgName) && Objects.equals(orgDescription, that.orgDescription) && Objects.equals(heroOrVillainOrg, that.heroOrVillainOrg) && Objects.equals(orgPhone, that.orgPhone) && Objects.equals(orgEmail, that.orgEmail) && Objects.equals(location, that.location) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, orgName, orgDescription, heroOrVillainOrg, orgPhone, orgEmail, location, members);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", orgDescription='" + orgDescription + '\'' +
                ", HeroOrVillainOrg='" + heroOrVillainOrg + '\'' +
                ", orgPhone='" + orgPhone + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", location=" + location +
                ", members=" + members +
                '}';
    }
}
