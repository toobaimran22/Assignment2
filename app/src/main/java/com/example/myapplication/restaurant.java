package com.example.myapplication;

public class restaurant {
    private String name,location,phone,rating;

    public restaurant()
    {}
    public restaurant(String name,String location,String phone,String rating)
    {
        this.name=name;
        this.location=location;
        this.phone=phone;
        this.rating=rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
