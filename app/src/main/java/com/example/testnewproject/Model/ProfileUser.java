package com.example.testnewproject.Model;

public class ProfileUser {

    String id, name, age, dob, favgenre;

    public ProfileUser(String id, String name, String age, String dob, String favgenre) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.favgenre = favgenre;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFavgenre() {
        return favgenre;
    }

    public void setFavgenre(String favgenre) {
        this.favgenre = favgenre;
    }
}
