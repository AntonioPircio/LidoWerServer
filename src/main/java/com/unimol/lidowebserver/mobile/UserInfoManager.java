package com.unimol.lidowebserver.mobile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserInfoManager {

    public ArrayList<UserInfo> getUsers(String mail) throws ClassNotFoundException, SQLException {
        ArrayList<UserInfo> users = new ArrayList<UserInfo>();
        String query = "";

        if (mail.equals(Constants.emailManager)) {

            query = "select Name,Surname,P.ID_Affittato,Mail,Numero_Sedie,Numero_Sdraio,Prezzo from " +
                    "Prenotazioni as P " +
                    "join Materiale_Affittato as M " +
                    "on P.ID_Affittato=M.ID_Affittato " +
                    "join User as U " +
                    "on P.Mail_User=U.Mail";
        } else {
            query = "select Name,Surname,P.ID_Affittato,Mail,Numero_Sedie,Numero_Sdraio,Prezzo from " +
                    "Prenotazioni as P " +
                    "join Materiale_Affittato as M " +
                    "on P.ID_Affittato=M.ID_Affittato " +
                    "join User as U " +
                    "on P.Mail_User=U.Mail " +
                    "where U.Mail = " + "'" + mail + "'";

        }
            DatabaseConfiguration database = new DatabaseConfiguration();
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            try {

                while (resultSet.next()) {
                    UserInfo user = new UserInfo();
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setMail(resultSet.getString("mail"));
                    user.setID_Affittato(resultSet.getString("ID_Affittato"));
                    user.setNumero_Sedie(resultSet.getInt("Numero_Sedie"));
                    user.setNumero_Sdraio(resultSet.getInt("Numero_Sdraio"));
                    user.setPrezzo(resultSet.getInt("Prezzo"));

                    users.add(user);
                }
            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            }
            finally {
                if (database.getConnection() != null) {
                database.stopConnection();
                }
            }

        return users;
    }

}
