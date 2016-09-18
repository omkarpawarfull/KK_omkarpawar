package com.kismatkonnect.kismatkonnect;

import android.app.Application;
import android.content.res.ObbInfo;
import android.gesture.GestureOverlayView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mou on 9/3/16.
 */
public class GlobalData {

    private static GlobalData myInstance;
    private JSONObject userInfo;
    private JSONObject searchPreference;
    private boolean newUser;
    private String cover_photo_url;

    private GlobalData(){
        userInfo = new JSONObject();
        searchPreference = new JSONObject();
        newUser = false;
        cover_photo_url = null;
    }

    public static synchronized GlobalData getInstance(){
        if( myInstance == null ){
            myInstance = new GlobalData();
        }
        return  myInstance;
    }


    public JSONObject getUserInfo(){
        return userInfo;
    }

    public void updateUserInfo(JSONObject profile){
        JSONArray keys = profile.names();
        int keyNum = keys.length();
        try {
            for(int i = 0; i< keyNum; ++i){
                String cur_key = keys.get(i).toString();
                switch (cur_key) {
                    case "id":
                    case "facebook_id":
                        userInfo.put(cur_key, profile.getLong(cur_key));
                        break;
                    case "last_name":
                    case "first_name":
                    case "email":
                    case "gender":
                    case "birthday":
                    case "interested_in":
                    case "career":
                    case "education":
                    case "location":
                    case "community":
                    case "religion":
                        if (profile.getString(cur_key).equalsIgnoreCase("null")) {
                            userInfo.put(cur_key, JSONObject.NULL);
                        } else {
                            userInfo.put(cur_key, profile.getString(cur_key));
                        }
                        break;
                    case "pics":
                        JSONArray pics = profile.getJSONArray(cur_key);
                        JSONArray urls = new JSONArray();
                        for(int k =0; k<3; ++k){
                            urls.put(k, "https://s3.amazonaws.com/com.kismatkonnect.user-images/user-images/" + pics.getJSONObject(k).getString("url"));
                        }
                        userInfo.put(cur_key, urls);
                        break;
                    default: break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public JSONObject getSearchPreference() {
        return searchPreference;
    }

    public void updateSearchPreference(JSONObject profile){
        JSONArray keys = profile.names();
        int keyNum = keys.length();
        try {
            for(int i = 0; i< keyNum; ++i){
                String cur_key = keys.get(i).toString();
                switch (cur_key){
                    case "max_age":
                    case "min_age":
                    case "search_radius":
                        userInfo.put(cur_key, profile.getInt(cur_key));
                        break;
                    case "friendship":
                    case "casual":
                    case "soulmates":
                        userInfo.put(cur_key, profile.getBoolean(cur_key));
                        break;
                    case "gender":
                    case "education_level":
                    case "community":
                    case "religion":
                        userInfo.put(cur_key, profile.getString(cur_key));
                        break;
                    default: break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public boolean needRegister() {
        return newUser;
    }

    public void setNeedRegister(boolean b) {
        newUser = b;
    }

    public void setCoverPhotoUrl(String _url){
        cover_photo_url = _url;
    }

    public String getCoverPhotoUrl() {
        return cover_photo_url;
    }

//    public class UserInfo {
//        public String kk_id;
//        public String fb_id;
//        public String last_name;
//        public String first_name;
//        public String email;
//        public String gender;
//        public String birthday;
//        public String interested_in;
//        public String career;
//        public String education;
//        public String location;
//        public String community;
//        public String religion;
//        public String height;
//        public String[] pics;
//
//        public UserInfo(){
//            pics = new String[3];
//        }
//    }


}
