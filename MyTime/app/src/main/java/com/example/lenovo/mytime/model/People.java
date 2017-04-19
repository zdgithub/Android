package com.example.lenovo.mytime.model;

public class People {
    private String Name ;
    private String Phome;


    public String getName() {
        return Name;
    }



    public void setName(String name) {
        Name = name;
    }



    public String getPhome() {
        return Phome;
    }



    public void setPhome(String phome) {
        Phome = phome;
    }




    public People(String name, String phone){
        super();
        this.Name = name;
        this.Phome = phone;

    }
}