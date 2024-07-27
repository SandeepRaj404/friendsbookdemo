package com.dbutils;

import com.beans.JsonConverter;
import com.beans.User;
import com.beans.UserPost;
import com.constants.AppContants;
import com.constants.CommonErros;
import com.response.beans.PostLikeResponse;
import com.response.beans.PostUploadResponse;
import com.response.beans.ShowPostResponse;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public static int getLikes(String postId){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "select count(*) from likePost where postId = '"+postId+"', and status = 1;";
        con = DBUtils.getDbConnection();

        try {
            st = con.createStatement();
            rs = st.executeQuery(QUERY);
            if(rs.next()){
                return rs.getInt(1)+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public static List<User> getPosts(User user, int limit,int offest,ShowPostResponse response) {
        Connection con=null;
        Statement st=null;
        ResultSet rd=null;
        List<User> posts=null;
        User usersOfPost = null;
        UserPost post=null;

        try{
            con=DBUtils.getDbConnection();
            st= con.createStatement();
            String query="select distinct u.name,u.userId,p.caption,p.imageAddr,p.likes,p.comments,p.start_date,dp.imageUrl,p.postId" +
                    " from user_post p join users u on p.userId = u.userId join dp_table dp on dp.userId = p.userId where p.userId = "+user.getUserId()+"" +
                    " or p.userId in (select friend_user_id from friends where userId = "+user.getUserId()+"" +
                    " union select userId from friends where friend_user_id = "+user.getUserId()+")" +
                    "order by p.postId desc limit  "+limit+" offset  "+offest+";";
            rd=st.executeQuery(query);
            if(rd!=null){
                posts=new ArrayList<>();

                while(rd.next()){
                    usersOfPost=new User();
                    post=new UserPost();
                    usersOfPost.setUserName(rd.getString(1));
                    usersOfPost.setUserId(rd.getInt(2));
                    post.setCaption(rd.getString(3));
                    post.setImage(AppContants.USER_DPOST_BASE_ADDR+rd.getString(4));
                    post.setLikes(rd.getInt(5));
                    post.setComments(rd.getInt(6));
                    post.setDateOfPost(rd.getDate(7).toLocalDate());
                    usersOfPost.setDp(AppContants.USER_DP_BASE_ADDR+rd.getString(8));
                    post.setPostId(rd.getString(9));

                    if(isPostLiked(user.getUserId(),post.getPostId())){
                        post.setLike_status(AppContants.LIKE_COLOR);
                    }else{
                        post.setLike_status(AppContants.UNLIKE_COLOR);
                    }

                    //post.setLikes(getLikes(post.getPostId()));
                    usersOfPost.setUserPost(post);
                    posts.add(usersOfPost);

                }

            }
            if(posts!=null && posts.size()>0){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.OK);
                response.setData(JsonConverter.toJson(posts));
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                con.close();
                st.close();rd.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return posts;
    }

    public static void makePost(User user, PostUploadResponse response){
        Connection con = null;
        PreparedStatement st = null;
        //PreparedStatement st2 = null;
        ResultSet rs = null;
        boolean result=false;
        String QUERY = "insert into user_post values('"+"post"+UserDAO.getCorrespondigId("user_post")+"',"+user.getUserId()+",'"+user.getUserPost().getCaption()+"','"+user.getUserPost().getImage()+"','"+user.getUserPost().getLikes()+"','"+user.getUserPost().getComments()+"','"+user.getUserPost().getShares()+"','"+user.getUserPost().getDateOfPost()+"',null);";
        //String QUERY2 = "insert into likepost values("+UserDAO.getCorrespondigId("likepost")+","+user.getUserId()+",'post"+UserDAO.getCorrespondigId("user_post")+"',0);";

        con = DBUtils.getDbConnection();
        try {
            st = con.prepareStatement(QUERY);
            //st2 = con.prepareStatement(QUERY2);
            int row =  st.executeUpdate();
            //st2.executeUpdate();
            if(row>0){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.POST_UPLOADED_SUCCESSFULLY);

            }else{
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(CommonErros.POST_UPLOAD_FAILED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static boolean isPostLiked(int userId,String postId){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "select * from likepost where userId = " + userId + " and postId = '" + postId +"';";

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
    public static void likePost(int userId, String postId, PostLikeResponse response){
        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement st1 = null;
        ResultSet rs = null;
        String QUERY = "insert into likepost values("+UserDAO.getCorrespondigId("likepost")+","+userId+",'"+postId+"');";
        String QUERY1 = "update user_post set likes = likes+1 where postId = '" + postId +"';";
        con = DBUtils.getDbConnection();
        try{
            con.setAutoCommit(false);
            st = con.prepareStatement(QUERY);
            st1 = con.prepareStatement(QUERY1);
            int row = st.executeUpdate();
            int row1 = st1.executeUpdate();
            con.commit();
            if(row>0 && row1>0){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.POST_LIKED);
                response.setData(AppContants.LIKE_COLOR);
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }
        }catch(SQLException se){
            try {
                con.rollback();
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED_TO_UPDATE_LIKE_DUE_TO_EXCEPTION);
                response.setData(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        }



    }

    public static void unlikePost(int userId,String postId,PostLikeResponse response){

        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement st1 = null;
        ResultSet rs = null;
        String QUERY = "delete from likepost where userId = " + userId + " and postId = '" + postId +"';";
        String QUERY1 = "update user_post set likes = likes-1 where postId = '" + postId +"';";

        con = DBUtils.getDbConnection();
        try{
            con.setAutoCommit(false);
            st = con.prepareStatement(QUERY);
            st1 = con.prepareStatement(QUERY1);

            int row = st.executeUpdate();
            int row1 = st1.executeUpdate();
            con.commit();
            if(row>0 && row1>0){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.POST_UNLIKED);
                response.setData(AppContants.UNLIKE_COLOR);
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }
        }catch(SQLException se){
            try {
                con.rollback();
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED_TO_UPDATE_LIKE_DUE_TO_EXCEPTION);
                response.setData(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        }



    }
    public static List<User> getPosts(int userId,int limit,int offset,ShowPostResponse response){
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        String QUERY = "select distinct p.postId,p.imageAddr,p.caption,p.start_date,u.name,u.userId,dp.imageurl,p.likes,p.comments,p.start_date from user_post p join users u on p.userId = u.userId join dp_table dp on p.userId = dp.userId  where p.userId ="+userId+" limit "+limit+" offset "+offset;
        con = DBUtils.getDbConnection();
        List<User> users = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery(QUERY);
            UserPost post = null;
            User user = null;
            users = new ArrayList<>();
            while(rs.next()){
                user = new User();
                 post = new UserPost();
                    post.setPostId(rs.getString(1));
                    post.setImage(AppContants.USER_DPOST_BASE_ADDR+rs.getString(2));
                    post.setCaption(rs.getString(3));
                    post.setDateOfPost(rs.getDate(4).toLocalDate());
                    user.setUserName(rs.getString(5));
                    user.setUserId(rs.getInt(6));
                    user.setDp(AppContants.USER_DP_BASE_ADDR+rs.getString(7));
                    post.setLikes(rs.getInt(8));
                    post.setComments(rs.getInt(9));
                    post.setDateOfPost(rs.getDate(10).toLocalDate());


                    if(isPostLiked(user.getUserId(),post.getPostId())){
                        post.setLike_status(AppContants.LIKE_COLOR);
                    }else{
                        post.setLike_status(AppContants.UNLIKE_COLOR);
                    }

                user.setUserPost(post);
                 users.add(user);
            }
            if(users.size()>0 && users!=null){
                response.setStatus(AppContants.SUCCESS_CODE);
                response.setMessage(AppContants.OK);
                response.setData(JsonConverter.toJson(users));
            }else{
                response.setStatus(CommonErros.BAD_REQUEST);
                response.setMessage(CommonErros.FAILED);
                response.setData(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
