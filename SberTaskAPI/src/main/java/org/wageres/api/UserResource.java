package org.wageres.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.wageres.dao.UserDao;
import org.wageres.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;


@Path("/users")
public class UserResource
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll()
            throws IOException
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> users = UserDao.getInstance().getAll();
        final String json = gson.toJson(users);

        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id)
    {
        int keyId;
        try
        {
            keyId = Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            return  Response.status(Response.Status.BAD_REQUEST).build();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = UserDao.getInstance().getUser(keyId);
        if(user == null)
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final String json = gson.toJson(user);

        return Response.status(Response.Status.OK).entity(json).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") String id,String json)
    {
        try
        {
            int keyId;
            try
            {
                keyId = Integer.parseInt(id);
            }
            catch (NumberFormatException e)
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            User recUser = gson.fromJson(json, User.class);

            if(UserDao.getInstance().isOccupiedKey(keyId,recUser))
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if(!UserDao.getInstance().updateUser(keyId,recUser))
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return  Response.status(Response.Status.OK).build();

        }
        catch (Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String id)
    {
        try
        {
            int keyId;
            try
            {
                keyId = Integer.parseInt(id);
            }
            catch (NumberFormatException e)
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = UserDao.getInstance().deleteUser(keyId);
            if(user == null)
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(user);

            return Response.status(Response.Status.OK).entity(json).build();

        }
        catch (Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String json)
    {
        try
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            User recUser = gson.fromJson(json, User.class);

            if(recUser.getIdentifier() <= 0 || recUser.getName() == "")
            {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if (UserDao.getInstance().addUser(recUser)) {
                return Response.status(Response.Status.CREATED).entity("{\"message\":\"User added successfully!\"}").build();
            } else {
                return Response.status(Response.Status.CONFLICT).entity("{\"message\":\"User with given id already exists!\"}").build();
            }
        }
        catch (Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
