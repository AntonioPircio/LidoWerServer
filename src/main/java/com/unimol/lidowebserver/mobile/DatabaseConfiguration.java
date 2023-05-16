package com.unimol.lidowebserver.mobile;

import java.sql.*;

public class DatabaseConfiguration {
    private Connection connection;

    public DatabaseConfiguration() throws ClassNotFoundException, SQLException {
        this.createConnection();
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createConnection() {
        try {
            Class.forName(Constants.getDbClass());
            this.connection = DriverManager.getConnection(Constants.getDbUrl(), Constants.getDbUser(), Constants.getDbPassword());
            System.out.println("La connessione al database è andata a buon fine");
        } catch (ClassNotFoundException e) {
            System.err.println("Classe non trovata com.mysql.jdbc.Driver" + " " +  e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connessione al database fallita " +  " " + e.getMessage());
        }
    }

    public void stopConnection() {
        try {
            this.connection.close();
            System.out.println("Connessione al database chiusa con successo");
        } catch (SQLException e) {
            System.err.println("La connessione al database non si può chiudere" + " " + e.getMessage());
        }
    }

    public boolean checkLogin(String mail, String password) {
        boolean isUserAvaiable = false;

        if (this.getConnection() != null) {

            try {
                Statement statement = getConnection().createStatement();
                String passwordToVerify = password.trim();
                String query = "SELECT Password FROM User WHERE Mail = '" + mail
                        + "'";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String passwordRetrieval = AES.decrypt(resultSet.getString("Password"), Constants.secretKey);

                    if (passwordToVerify.equals(passwordRetrieval.trim())) {
                        isUserAvaiable = true;
                    }
                }
            } catch (SQLException sqlException) {
                isUserAvaiable = false;
                System.err.println(sqlException.getMessage());
            } finally {
                if (getConnection() != null) {
                    stopConnection();
                }
            }
        }
        return isUserAvaiable;
    }


    public boolean insertUser(String name, String surname, String mail, String password) throws SQLException {
        boolean insertStatus = false;
        if (this.getConnection() != null) {
            try {
                Statement statement = getConnection().createStatement();
                String query = "INSERT into User (Name, Surname, Mail, Password) values('" + name + "'," + "'"
                        + surname + "','" + mail + "','" + password + "')";

                int records = statement.executeUpdate(query);
                if (records > 0) {
                    insertStatus = true;
                }
            } catch (SQLException sqlException) {
                throw sqlException;

            } finally {
                if (getConnection() != null) {
                    stopConnection();
                }
            }
        }
        return insertStatus;
    }


    public boolean updatePassword(String mail, String newPassword) {
        boolean updateStatus = false;

        if (this.getConnection() != null) {
            try {
                Statement statement = getConnection().createStatement();
                String updateQuery = "UPDATE User SET Password =";
                String parameterPassword = "'" + newPassword + "'";
                String conditionQuery = "WHERE Mail =";
                String parameterMail = "'" + mail + "'";
                String query = updateQuery.concat(parameterPassword).concat(conditionQuery).concat(parameterMail.replaceAll("\\s", ""));

                int records = statement.executeUpdate(query);

                if (records > 0) {
                    updateStatus = true;
                }
            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            } finally {
                if (getConnection() != null) {
                    stopConnection();
                }
            }
        }
        return updateStatus;
    }


    public boolean checkMail(String mail) {
        boolean isMailAvaiable = false;

        if (this.getConnection() != null) {
            try {
                Statement statement = getConnection().createStatement();
                String selectQuery = "SELECT * FROM User WHERE Mail = ";
                String mailQuery = "'" + mail + "'";
                String query = selectQuery.concat(mailQuery.replaceAll("\\s", ""));

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    isMailAvaiable = true;
                }
            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            } finally {
                if (getConnection() != null) {
                    stopConnection();
                }
            }
        }
        return isMailAvaiable;
    }

    public boolean makeReservation(String mail, String ID_Umbrella) {
        boolean insertStatus = false;
        boolean isReserved = false;

        try {
            Statement firsrtStatement = getConnection().createStatement();
            String isReservedQuery = "SELECT * FROM Prenotazioni WHERE ID_Affittato = " + "'" + ID_Umbrella + "'";
            ResultSet resultSetPrenotazioni = firsrtStatement.executeQuery(isReservedQuery);

            while (resultSetPrenotazioni.next()) {
                isReserved = true;
            }
                if (!isReserved) {
                    Statement statement = getConnection().createStatement();
                    String queryInsert = "INSERT into Prenotazioni (Mail_User, ID_Affittato)";
                    String quesryValues = "values('" + mail + "'," + "'" + ID_Umbrella + "')";
                    String query = queryInsert.concat(quesryValues.replaceAll("\\s", ""));

                    int records = statement.executeUpdate(query);
                    if (records > 0) {
                        insertStatus = true;
                    }
                }
                } catch (SQLException sqlException) {
                    System.err.println("ERRORE NELLA PRENOTAZIONE CONTROLLA LA MAIL O L' ID OMBRELLONE" + " " + sqlException.getMessage());
                } finally {
                    if (getConnection() != null) {
                        stopConnection();
                    }
                }
        return insertStatus;
    }

    public boolean deleteUser(String mail) {
        boolean result = false;

            if (this.getConnection() != null) {
                    try {
                        Statement statement = getConnection().createStatement();
                        String firstQuery = "DELETE FROM User WHERE Mail = '" + mail
                                + "'";
                        int resultQuery = statement.executeUpdate(firstQuery);

                        Statement secondStatement = getConnection().createStatement();
                        String secondQuery = "DELETE FROM Prenotazioni WHERE Mail_User = '" + mail
                                + "'";
                        secondStatement.executeUpdate(secondQuery);

                        if (resultQuery > 0) {
                            result = true;
                        }
                    } catch (SQLException sqlException) {
                        System.err.println(sqlException.getMessage());
                    } finally {
                        if (getConnection() != null) {
                            stopConnection();
                        }
                    }
                }
        return result;
    }

    public boolean deleteReservation(String mail) {
        boolean result = false;

        if (getConnection() != null) {
            try {
                Statement statement = getConnection().createStatement();
                String firstQuery = "DELETE FROM Prenotazioni WHERE Mail_User = '" + mail
                        + "'";
                int resultQuery = statement.executeUpdate(firstQuery);

                if (resultQuery > 0) {
                    result = true;
                }
            } catch (SQLException sqlException) {
                System.err.println(sqlException.getMessage());
            } finally {
                if (getConnection() != null) {
                    stopConnection();
                }

            }
        }
        return result;
    }

}
