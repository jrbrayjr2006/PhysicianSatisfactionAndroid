package com.saterfieldmedical.physiciansatisfaction.model;

/**
 * Created by jamesbray on 7/11/16.
 */
public class Survey {

    private String rating;
    private String whyFeeling;
    private String response;
    private String siteCode;
    private static Survey survey;

    public static Survey getInstance() {
        if(survey == null) {
            survey = new Survey();
        }
        return survey;
    }

    private Survey() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getWhyFeeling() {
        return whyFeeling;
    }

    public void setWhyFeeling(String whyFeeling) {
        this.whyFeeling = whyFeeling;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
