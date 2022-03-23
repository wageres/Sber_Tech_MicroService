package org.wageres.dao;

import org.wageres.model.User;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDao
{
    private static UserDao instance;

    private HashMap<Integer,User> users = new HashMap<>();

    public static synchronized UserDao getInstance()
    {
        if(instance == null)
        {
            instance = new UserDao();
        }
        return instance;
    }

    private UserDao() {}

    public List<User> getAll()
    {
        return  new ArrayList<User>(users.values());
    }

    public User getUser(int key)
    {
        if(users.containsKey(key))
        {
            return users.get(key);
        }
        return null;
    }

    public boolean updateUser(int key,User recUser)
    {
        if(!users.containsKey(key))
        {
            return false;
        }

        users.remove(key);

        users.put(recUser.getIdentifier(),recUser);

        return true;
    }

    public User deleteUser(int key)
    {
        if(!users.containsKey(key))
        {
            return null;
        }

        User user = users.get(key);
        users.remove(key);
        return user;
    }

    public boolean isOccupiedKey(int key,User recUser)
    {
        if(recUser.getIdentifier() != key && users.containsKey(recUser.getIdentifier()))
        {
            return true;
        }
        return false;
    }


    public  boolean addUser(User user)
    {
        if(users.containsKey(user.getIdentifier()))
        {
            return false;
        }

        users.put(user.getIdentifier(),user);
        return true;
    }

}
