package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 15-05-2016.
 */
public class Ads {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_logo_url")
    @Expose
    private String companyLogoUrl;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image_url")
    @Expose
    private String productImageUrl;
    @SerializedName("product_discretion")
    @Expose
    private String productDescription;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId The company_id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * @return The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return The companyLogoUrl
     */
    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    /**
     * @param companyLogoUrl The company_logo_url
     */
    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    /**
     * @return The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return The productImageUrl
     */
    public String getProductImageUrl() {
        return productImageUrl;
    }

    /**
     * @param productImageUrl The product_image_url
     */
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    /**
     * @return The productDiscretion
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDiscretion The product_discretion
     */
    public void setProductDescription(String productDiscretion) {
        this.productDescription = productDiscretion;
    }


    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", productName='" + productName + '\'' +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}
