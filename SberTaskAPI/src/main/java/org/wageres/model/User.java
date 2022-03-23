package org.wageres.model;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("id")
    private int identifier;
    private String name;

    public User()
    {

    }

    public User(int identifier,String name)
    {
        this.identifier = identifier;
        this.name = name;
    }

    public int getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(int identifier)
    {
        this.identifier = identifier;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
