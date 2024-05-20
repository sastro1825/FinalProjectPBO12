/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author USER
 */
import java.net.Socket;
import java.sql.*;

public class db_con {
    Connection connection;
    Statement statement;
    String SQL;
    String url;
    String username;
    String password;
    Socket client;
    int Port;
    String Host;
    
    public db_con(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.Host = Host;
        this.Port = Port;
    }
    
    public Connection connectDatabase() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            } 
        catch(Exception e) {
             System.out.println(e.toString());
            }
        
        return connection;
    }
    
    public Connection disconnectDatabase() {
        try {
            connection.close();
        }
        catch(Exception e) {
        }
        
        return connection;
    }
    
    public ResultSet databaseExecute(String sql) {
        connectDatabase();
        ResultSet resultSet = null;
        
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(sql);
        }
        catch(SQLException ex) {
        }
        
        return resultSet;
    }
    
    public String databaseUpdate(String sql) {
        connectDatabase();
        String result = "";
        
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println(sql);
        }
        catch(SQLException ex) {
        }
        
        return result;
    }
    
    public ResultSet databaseSelectAll(String namaTabel) {
        SQL = "SELECT * FROM" + namaTabel;
        
        return this.databaseExecute(SQL);
    }
    
    public ResultSet databaseSelectAll(String namaTabel, String condition) {
        SQL = "SELECT * FROM" + namaTabel + "WHERE" + condition;
        
        return this.databaseExecute(SQL);
    }
    
    public ResultSet databaseSelect(String[] namaKolom, String namaTabel) {
        SQL = "SELECT ";
        
        for(int i = 0; i <= namaKolom.length - 1; i++) {
            SQL += namaKolom[i];
            if(i < namaKolom.length - 1) {
                SQL += ",";
            }
        }
        
        SQL += " FROM " + namaTabel;
        
        return this.databaseExecute(SQL);
    }
    
    public ResultSet databaseSelect(String[] namaKolom, String namaTabel, String condition) {
        SQL = "SELECT ";
        
        for(int i = 0; i <= namaKolom.length - 1; i++) {
            SQL += namaKolom[i];
            if(i < namaKolom.length - 1) {
                SQL += ",";
            }
        }
        
        SQL += " FROM " + namaTabel + " WHERE " + condition;
        
        return this.databaseExecute(SQL);
    }
    
    public String databaseInsert(String namaTabel, String[] tableValues) {
        SQL = "INSERT INTO " + namaTabel + "VALUES(";
        
        for(int i = 0; i <= tableValues.length - 1; i++) {
            SQL += "'" + tableValues[i] + "'";
            if(i < tableValues.length - 1){
                SQL += ",";
            }
        }
        
        SQL += ")";
        
        return this.databaseUpdate(SQL);
    }
    
    /*public String databaseInsert(String namaTabel, String[] namaKolom, String[] tableValues) {
        int i;
        SQL = "INSERT INTO " + namaTabel + "(";
        
        for(i = 0; i <= namaKolom.length - 1; i++) {
            SQL += namaKolom[i];
            if (i < namaKolom.length - 1) {
                SQL += ",";
            }
        }
        
        SQL += ") VALUES(";
        
        for(i = 0; i <= tableValues.length - 1; i++) {
            SQL += "'" + tableValues[i] + "'";
            if(i < tableValues.length - 1) {
                SQL += ",";
            }
        }
        
        SQL += ")";
        
        return this.databaseUpdate(SQL);
    }*/
    
    public String databaseInsert(String namaTabel, String[] namaKolom, String[] tableValues) {
    int i;
    String SQL = "INSERT INTO " + namaTabel + "(";
    
    for(i = 0; i < namaKolom.length; i++) {
        SQL += namaKolom[i];
        if (i < namaKolom.length - 1) {
            SQL += ",";
        }
    }
    
    SQL += ") VALUES(";
    
    for(i = 0; i < tableValues.length; i++) {
        SQL += "?"; // gunakan parameterized query
        if(i < tableValues.length - 1) {
            SQL += ",";
        }
    }
    
    SQL += ")";
    
    return this.databaseUpdate(SQL); // Ubah kembali jika method databaseUpdate dibutuhkan
    //return "SUCCESS"; // Ganti dengan string "SUCCESS" untuk sementara
}
    
    public String mySQLUpdate(String namaTabel, String[] namaKolom, String[] tableValues, String condition) {
        SQL = "UPDATE " + namaTabel + " SET ";
        
        for(int i = 0; i <= namaKolom.length - 1; i++) {
            SQL += namaKolom[i] + "='" + tableValues[i] + "'";
            if(i < namaKolom.length - 1) {
                SQL += ",";
            }
        }
        
        SQL += " WHERE " + condition;
        
        return this.databaseUpdate(SQL);
    }
    
    public String databaseDelete(String namaTabel) {
        SQL = "DELETE FROM " + namaTabel;
        
        return this.databaseUpdate(SQL);
    }
    
    public String databaseDelete(String namaTabel, String condition) {
        SQL = "DELETE FROM " + namaTabel + " WHERE " + condition;
        
        return this.databaseUpdate(SQL);
    }
}
