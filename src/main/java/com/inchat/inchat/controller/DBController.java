package com.inchat.inchat.controller;

import com.inchat.inchat.domain.UserVO;

import java.sql.*;

public class DBController {
    private final String url = "jdbc:mysql://database-1.ccpa8uvbjkgx.us-west-1.rds.amazonaws.com/InChat";
    private final String id = "admin";
    private final String pw = "zahard1050!";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /*          Public Methods          */

    /*          User Methods            */
    public UserVO getUser(String id){
        String sql = String.format("select * from user where id = '%s'",id);
        rs = executeSelect("User",sql);
        try{
            if(rs.next()){
                String pw = rs.getString(2);
                String nick = rs.getString(3);
                UserVO user = new UserVO(id,pw,nick);
                return user;
            }
            else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Getting User Error");
            return null;
        }
    }

    public UserVO loginUser(String id, String pw){
        String sql = String.format("select * from user where id = '%s' and pw = '%s'",id,pw);
        rs = executeSelect("User",sql);
        try{
            if(rs.next()){
                String nick = rs.getString(3);
                UserVO user = new UserVO(id,pw,nick);
                return user;
            }else
                return null;
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Login User Error");
            return null;
        }
    }

    public boolean addUser(UserVO user){
        String sql = String.format("insert into user values('%s','%s','%s')",user.getId(),user.getPw(),user.getNick());
        return executeAddition("User",sql);
    }

    /*          Private Methods         */

    /*          Connection Methods      */

    private void closeConnection(){
        try {
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            if(pstmt != null && !pstmt.isClosed()){
                pstmt.close();
            }
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void getConnection(){
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url,id,pw);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("DB Connection Error");
        }
    }

    /*          SQL Execute Methods     */

    private ResultSet executeSelect(String type, String sql){
        getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            return rs;
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(sql);
            System.err.println("SQL Select Query of "+type+" Error");
            return null;
        }
    }

    private boolean executeUpdate(String type, String sql){
        getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(sql);
            System.err.println("SQL Update Query of "+type+" Error");
            return false;
        }
    }

    private boolean executeDelete(String type, String sql){
        getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(sql);
            System.err.println("SQL Delete Query of "+type+" Error");
            return false;
        }
    }

    private boolean executeAddition(String type, String sql){
        getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(sql);
            System.err.println("SQL Insert Query of "+type+" Error");
            return false;
        }
    }
}
