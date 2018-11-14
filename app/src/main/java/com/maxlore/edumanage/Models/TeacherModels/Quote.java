package com.maxlore.edumanage.Models.TeacherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil on 05-06-2016.
 */
public class Quote {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quote")
    @Expose
    private String quote;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * @param quote The quote
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

}
