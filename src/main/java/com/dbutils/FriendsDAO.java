package com.dbutils;

import com.beans.JsonConverter;
import com.beans.User;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.response.beans.AcceptFriendRequestResponse;
import com.response.beans.ListFriendRequestResponse;
import com.response.beans.FriendRequestSentResponse;
import com.response.beans.LoadFriendsResponse;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO {
    public static List<User> getListOfPeopleYouMayKnow(User user){
        Connection con=null;
        Statement st=null;
        ResultSet rd=null;
        List<User> peopleYouMayKnow=null;

        try {
            con=DBUtils.getDbConnection();
            st= con.createStatement();
            String query="select users.userId,users.name,users.gender,users.dob,users.email,dp.imageUrl from users\n" +
                    "left join dp_table dp on dp.userId=users.userId\n" +
                    "where users.userId not in (select friend_user_id from friends where userId="+user.getUserId()
                    +" union select userId from friend_request where friend_userId="+user.getUserId()
                    +" union select "+user.getUserId()+");";
            rd=st.executeQuery(query);
            if(rd!=null){
                peopleYouMayKnow=new ArrayList<>();
                while(rd.next()){
                    User u=new User();
                    u.populateBasicUserInfo(rd.getInt(1),
                            rd.getString(2),
                            rd.getString(3),
                            rd.getDate(4).toLocalDate(),
                            rd.getString(5),
                            AppContants.USER_DP_BASE_ADDR+rd.getString(6));
                    peopleYouMayKnow.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                con.close();
                st.close();
                rd.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return peopleYouMayKnow;
    }

    public static boolean doesFriendRequestExist(int userId, int FriendUserId){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "select * from friend_request where userId = " + userId + " and friend_userId = " + FriendUserId;

        con = DBUtils.getDbConnection();
        try{
            st = con.createStatement();
            rs = st.executeQuery(QUERY);
            if(rs.next()){
                return true;
            }

        }catch(SQLException se){
            se.printStackTrace();
        }
        return false;
    }

    public static void RevokeFriendRequest(int userId, int FriendUserId, FriendRequestSentResponse response){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "delete from friend_request where userId = " + userId + " and friend_userId = " + FriendUserId;
        con = DBUtils.getDbConnection();

        try{
            st = con.createStatement();
            int row = st.executeUpdate(QUERY);
            if(row>0){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.FRIEND_REQUEST_REVOKED);
                response.setData(AppContants.ADD_FRIEND);
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FRIEND_REQUEST_NOT_REVOKED);
                response.setData(AppContants.REQUEST_SENT);
            }
        }catch(SQLException se){se.printStackTrace();}


    }


    public static void sendFriendRequest(int userId,int friendUserId,FriendRequestSentResponse response){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "insert into friend_request values('req"+UserDAO.getCorrespondigId("friend_request")+"',"+userId+","+friendUserId+",'"+LocalDate.now()+"',0);";

        con = DBUtils.getDbConnection();

        try{
            st= con.createStatement();
            int row = st.executeUpdate(QUERY);
            if(row>0){
              response.setStatus(AppContants.SUCCESS_CODE);
              response.setMessage(AppContants.FRIEND_REQUEST_SENT);
              response.setData(AppContants.REQUEST_SENT);
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FRIEND_REQUEST_NOT_SENT);
                response.setData(AppContants.ADD_FRIEND);
            }
        }catch(SQLException se){
            se.printStackTrace();
        }

    }

    public static void getFriendRequests(int userId, ListFriendRequestResponse response){
        Connection con=null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = " select u.userId,u.name,dp.imageUrl from users u left join dp_table dp on u.userId = dp.userId where u.userId in(select friend_userId from friend_request where userId ="+userId+" and status =0);";


        List<User> requests  = null;
        con = DBUtils.getDbConnection();
        try{
            st = con.createStatement();
            rs = st.executeQuery(QUERY);
            requests=new ArrayList<>();
            while(rs.next()){
                User user = new User();
               user.setUserId(rs.getInt(1));
               user.setUserName(rs.getString(2));
              // user.setEmail(rs.getString(3));
               //user.setGender(rs.getString(4));
               //user.setDob(rs.getDate(5).toLocalDate());
               //user.setStart_date(rs.getDate(6).toLocalDate());
                user.setDp(AppContants.USER_DP_BASE_ADDR+rs.getString(3));
               requests.add(user);
            }

            if(requests.size()>0 && requests !=null){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.OK);
                response.setData(JsonConverter.toJson(requests));
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }



        }catch(SQLException se){se.printStackTrace();}

    }

    public static void confirmFriendRequest(int userId, int friendUserId, AcceptFriendRequestResponse response){
        Connection con=null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        ResultSet rs = null;
        String QUERY1 = "insert into friends values('con"+UserDAO.getCorrespondigId("friends")+"',"+userId+","+friendUserId+",1);";
        String QUERY2 = "update friend_request set status= 1 where userId= " + userId + " and  friend_userId = " + friendUserId+";";
        String QUERY3 = "insert into friends values('con"+(UserDAO.getCorrespondigId("friends")+1)+"',"+friendUserId+","+userId+",1);";
        con = DBUtils.getDbConnection();

        try{
            con.setAutoCommit(false);
           pst1 = con.prepareStatement(QUERY1);
           pst2 = con.prepareStatement(QUERY2);
           pst3 = con.prepareStatement(QUERY3);
           pst1.executeUpdate();
           pst2.executeUpdate();
           pst3.executeUpdate();
           con.commit();

           response.setStatus(AppContants.SUCCESS_CODE);
           response.setMessage(AppContants.FRIEND_REQUEST_ACCEPTED);
           response.setData(AppContants.FRIENDS);


        }catch (SQLException se){
            try {
                con.rollback();
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FRIEND_REQUEST_ACCEPTING_FAILED);
                response.setData(null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void loadAllFriends(int userId, LoadFriendsResponse response){
        Connection con=null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT DISTINCT \n" +
                "    u.userId AS friend_id,\n" +
                "    u.name AS friend_name,\n" +
                "    d.imageUrl AS friend_dp\n" +
                "FROM \n" +
                "    friends f\n" +
                "JOIN \n" +
                "    users u ON f.friend_user_id = u.userId\n" +
                "JOIN \n" +
                "    dp_table d ON u.userId = d.userId\n" +
                "WHERE \n" +
                "    f.userId ="+userId+";";

        List<User> friends = null;
        con = DBUtils.getDbConnection();
        try{
            st = con.createStatement();
            rs = st.executeQuery(QUERY);
            friends =new ArrayList<>();
            while(rs.next()){
                User frnds = new User();
                frnds.setUserId(rs.getInt(1));
                frnds.setUserName(rs.getString(2));
                frnds.setDp(AppContants.USER_DP_BASE_ADDR+rs.getString(3));
                friends.add(frnds);
            }

            if(friends.size()>0 && friends !=null){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.OK);
                response.setData(JsonConverter.toJson(friends));
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }



        }catch(SQLException se){se.printStackTrace();}

    }










    public static void main(String[] args) {
        User u=new User();
        u.setUserId(1);
        List<User> users=getListOfPeopleYouMayKnow(u);
        for(User user:users){
            System.out.println(user.getUserId()+" "+
                    user.getUserName()+" "+ user.getDob()+" "+user.getDp());
        }
    }

}
