package com.dhimahi.models;


public class Brand {
    private String name;
    private String slug;




    public Brand(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }





    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}