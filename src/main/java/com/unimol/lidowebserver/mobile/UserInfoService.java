package com.unimol.lidowebserver.mobile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/userservice")
public class UserInfoService {

    @GET
    @Path("/userinfo")
    @Produces("application/json")
    public String usersInfo(@QueryParam("mail") String mail) throws ClassNotFoundException, SQLException {
        String userInfo = "";
        ArrayList<UserInfo> users = new ArrayList<UserInfo>();

        users = new UserInfoManager().getUsers(mail);

        if (!users.isEmpty()) {
            Gson gson = new Gson();
            userInfo = gson.toJson(users);
        } else {
            userInfo = Utility.constructJSONMessage("USER INFO", false, "MAIL SBAGLIATA");
        }
        return userInfo;
    }
}

