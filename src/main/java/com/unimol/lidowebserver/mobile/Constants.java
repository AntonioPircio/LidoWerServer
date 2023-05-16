package com.unimol.lidowebserver.mobile;

public class Constants {
    private static String dbHost ="localhost";
    private static String dbPort ="3306";
    private static String dbName ="Lido";
    private static String dbUrl ="jdbc:mysql://" + dbHost + ":" + dbPort + "/"+dbName ;
    private static String dbUser ="root";
    private static String dbPassword = "antonio";
    private static String dbClass = "com.mysql.cj.jdbc.Driver";
    public static String SMTP_HOST = "out.virgilio.it";
    public static String SMTP_SFP = "465";
    public static String SMTP_SFC = "javax.net.ssl.SSLSocketFactory";
    public static String SMTP_AUTH = "true";
    public static String SMTP_PORT = "465";
    public static String emailManager = "EmailUsedByManager";
    public static String emailPassword = "setAPassword";
    public static String secretKey = "setAKeytoEncryption";
    public static int primaryKeyCodeError = 1062;


    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getDbClass() {
        return dbClass;
    }
}
