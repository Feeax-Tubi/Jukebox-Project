/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayerproject;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Feeax
 */
public class DatabaseService {
    String url = "jdbc:mysql://localhost:3306/musicplayerproject?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    String user = "root";
    String password = "18070006042n";
    private ResultSet rs;
    
    private Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
        }
        return connection;
    }
    
     public void getInfo (JTable table, String username)
    {        
        Connection connection = connect();
        try (PreparedStatement find = connection.prepareStatement("SELECT * FROM musicplayerproject." + username)) 
        {
            rs = find.executeQuery();  
            while(rs.next())
            {
                int id = rs.getInt(1);
                String songname = rs.getString(2);
                String artistname = rs.getString(3);
                String genrename = rs.getString(4);
                String songpath = rs.getString(5);
                
                Object[] content = {id,songname,artistname,genrename,songpath};
                DefaultTableModel modelTable = (DefaultTableModel) table.getModel(); 
                modelTable.addRow(content);  
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    }
    
     public void getInfo (JTable table)
    {        
        Connection connection = connect();
        try (PreparedStatement find = connection.prepareStatement("SELECT * FROM musicplayerproject.musicplayerlist")) 
        {
            rs = find.executeQuery();  
            while(rs.next())
            {
                int id = rs.getInt(1);
                String songname = rs.getString(2);
                String artistname = rs.getString(3);
                String genrename = rs.getString(4);
                String songpath = rs.getString(5);
                
                Object[] content = {id,songname,artistname,genrename,songpath};
                DefaultTableModel modelTable = (DefaultTableModel) table.getModel(); 
                modelTable.addRow(content);  
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    }
     
     public String getPath(int id)
    {
        String path = null;
        
        Connection connection = connect();
        try (PreparedStatement find = connection.prepareStatement("SELECT songpath FROM musicplayerproject.musicplayerlist where id=" + id)) 
        {
            rs = find.executeQuery();
            while(rs.next())
            {
                path = rs.getString(1);
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return path;
    } 
     
     public String getPathUser(int id, String username)
    {
        String path = null;
        
        Connection connection = connect();
        try (PreparedStatement find = connection.prepareStatement("SELECT songpath FROM musicplayerproject." + username + " where id=" + id)) 
        {
            rs = find.executeQuery();
            while(rs.next())
            {
                path = rs.getString(1);
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return path;
    } 
     
     public void deleteSong(int id)
    {
        Connection connection = connect();
        try 
        {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM musicplayerproject.musicplayerlist where id=" + id);
            int i;
            i = pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    } 
     
     public void deleteSongFromUser(int id,String username)
    {
        Connection connection = connect();
        try 
        {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM musicplayerproject." + username + " where id=" + id);
            int i;
            i = pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    } 
     
      public static String getFilePath(String path) {
        path = path.replace("\\", "/");
        return path;
    }
     
      public void createAccount(String username, String password, String email)
    {
        Connection connection = connect();
        
        try
        {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO musicplayerproject.accounts (username,password,email) values(?,?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    }
      
      public void addSongToUser(String songname,String artistname, String genrename, String songpath, String username)
    {
        Connection connection = connect();
        songpath = getFilePath(songpath);
                
        try
        {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO musicplayerproject." + username + " (songname,artistname,genrename,songpath) values(?,?,?,?)");
            pstmt.setString(1, songname);
            pstmt.setString(2, artistname);
            pstmt.setString(3, genrename);
            pstmt.setString(4, songpath);
            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        
    }
      
      public boolean checkLogin(String username, String password){
        Connection connection = connect();
        
        try              
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM musicplayerproject.accounts where username='" + username +"'" + " AND password='" + password + "'";
            rs = statement.executeQuery(SQL);
            if(rs.next())
            {
                return true;
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return false;
    }
      
      public boolean checkLogin(String username){
        Connection connection = connect();
        
        try              
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM musicplayerproject.accounts where username='" + username +"'";
            rs = statement.executeQuery(SQL);
            if(rs.next())
            {
                return true;
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return false;
    }
      
      public boolean checkSongUser(String songname, String artistname, String username){
        Connection connection = connect();
        
        try              
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM musicplayerproject." + username + " where songname='" + songname +"'" + " AND artistname='" + artistname + "'";
            rs = statement.executeQuery(SQL);
            if(rs.next())
            {
                return true;
            }
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return false;
    }
      
       public int getUserId(String username)
    {
        int userid = 0;
        
        Connection connection = connect();
        try (PreparedStatement find = connection.prepareStatement("SELECT id FROM musicplayerproject.accounts where username='" + username + "'")) 
        {
            rs = find.executeQuery();
            while(rs.next())
            {
                userid = rs.getInt(1);
            }     
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return userid;
    }
       
       public void createUserSongTable(String username)
    {
        Connection connection = connect();
        try
        {
            String SQL = "CREATE TABLE IF NOT EXISTS musicplayerproject." + username + " ("+ 
                    "id INT NOT NULL AUTO_INCREMENT,"+ 
                    "songname VARCHAR(30) NOT NULL,"+ 
                    "artistname VARCHAR(30) NOT NULL," + 
                    "genrename VARCHAR(15) NOT NULL,"+ 
                    "songpath VARCHAR(250) NOT NULL,"+ 
                    "PRIMARY KEY (id))";     
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            int i;
            i = pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
    }
       
       
       
}
