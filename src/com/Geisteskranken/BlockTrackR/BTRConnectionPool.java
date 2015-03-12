package com.Geisteskranken.BlockTrackR;

import com.zaxxer.hikari.HikariConfig;  
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;  
import java.sql.SQLException;  

public class BTRConnectionPool {

    private static BTRConnectionPool instance = null;  
    private static HikariDataSource ds = null;  
  
    static  
    {  
        try  
        {  
            instance = new BTRConnectionPool();  
        }  
        catch (Exception e)  
        {  
            throw new RuntimeException(e.getMessage(), e);  
        }  
  
    }  
  
    private BTRConnectionPool()  
    {  
        HikariConfig config = new HikariConfig();  
        config.setMaximumPoolSize(10);
        config.setDataSourceClassName("com.mysql.jdbc.Driver");  
        config.setJdbcUrl("jdbc:mysql://" + BlockTrackR.host + ":" + BlockTrackR.port);
        //config.setJdbcUrl("jdbc:mysql://" + BlockTrackR.host + ":" + BlockTrackR.port + "/" + BlockTrackR.database);
        config.setUsername(BlockTrackR.dbuser);
        config.setPassword(BlockTrackR.dbpass);
  
        ds = new HikariDataSource(config);  
    }  
  
    public static BTRConnectionPool getInstance ()  
    {  
        return instance;  
    }  
  
    public static Connection getConnection()  throws SQLException  
    {  
        return ds.getConnection();  
    }  

}
