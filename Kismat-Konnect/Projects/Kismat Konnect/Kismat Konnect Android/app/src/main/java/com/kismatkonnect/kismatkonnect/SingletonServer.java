package com.kismatkonnect.kismatkonnect;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mou on 9/2/16.
 */
public class SingletonServer {

    private static SingletonServer myInstance;
    private static Context mCtx;

    private SingletonServer(Context context){
        mCtx = context;
    }

    public static synchronized SingletonServer getInstance(Context context) {
        if (myInstance == null) {
            myInstance = new SingletonServer(context);
        }
        return myInstance;
    }



    public interface VolleyRequestCallback{
        void onSuccess(JSONObject response);
    }

    private void newUserRegister(String token) {
        String url = "https://kismatkonnect.com/api/v1/user/register.json";
        JSONObject params = new JSONObject();
        try {
            params.put("access_token", token);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server responds to the new registration.");
                        try {
                            String result = response.getString("result");
                            if(result.equalsIgnoreCase("success")){
                                Log.v("SingletonServer", response.getString("message"));
                                GlobalData.getInstance().updateUserInfo(response.getJSONObject("user"));
                            } else {
                                Log.v("SingletonServer", "New registration fails.");
                            }
                        } catch (JSONException e) {
                            Log.v("SingletonServer", "New registration errs.");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "Registration Fails");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }


    private void fetchProfile(String id, final VolleyRequestCallback callback){
        String url = "https://kismatkonnect.com/api/v1/user/"+id+"/profile.json";
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server responds to fetching detail profile.");
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "Fetching detail profile errs.");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }


    private void fetchPicUrl(String id){
        String url = "https://kismatkonnect.com/api/v1/user/"+id+"/pics.json";
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server responds to fetching picture urls.");
                        GlobalData.getInstance().updateUserInfo(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "Fetching detail profile errs.");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }

    private void fetchCoverPhoto(String id){
        String url = "http://kismatkonnect.com/api/v1/user/"+id+"/cover-photo.json";
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server responds to fetching cover photo.");
                        try {
                            String pic_full_url = "https://s3.amazonaws.com/com.kismatkonnect.user-images/user-images/" + response.getString("cover_photo");
                            GlobalData.getInstance().setCoverPhotoUrl(pic_full_url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "Fetching cover photo errs.");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }

    public void registerIfNotAndFetchProfile(final AccessToken accessToken,
                                             final VolleyRequestCallback regCallback,
                                             final VolleyRequestCallback noRegCallback){
        String url = "https://kismatkonnect.com/api/v1/user/" + accessToken.getUserId();
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server determines if the user exists.");
                        try {
                            String result = response.getString("result");
                            if(result.equalsIgnoreCase("error")){
                                Log.v("SingletonServer", response.getString("message"));
                                newUserRegister(accessToken.getToken());
                                fetchPicUrl(accessToken.getUserId());
                                fetchProfile(accessToken.getUserId(), regCallback);
                                fetchCoverPhoto(accessToken.getUserId());
                            } else {
                                Log.v("SingletonServer", response.toString());
                                GlobalData.getInstance().updateUserInfo(response.getJSONObject("user"));
                                fetchPicUrl(accessToken.getUserId());
                                fetchProfile(accessToken.getUserId(), noRegCallback);
                                fetchCoverPhoto(accessToken.getUserId());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "registerIfNotAndFetchProfile errs.");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }


    public void confirmProfile(String id){
        String url = "https://kismatkonnect.com/api/v1/user/"+id+"/finalize-registration.json";
        JSONObject params = new JSONObject();
        try {
            params.put("facebook_id", GlobalData.getInstance().getUserInfo().getLong("facebook_id"));
            params.put("height", GlobalData.getInstance().getUserInfo().getString("height"));
            params.put("community", GlobalData.getInstance().getUserInfo().getString("community"));
            params.put("religion", GlobalData.getInstance().getUserInfo().getString("religion"));
            params.put("interested_in", GlobalData.getInstance().getUserInfo().getString("interested_in"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("friends", GlobalData.getInstance().getSearchPreference().get("friendship"));
            jsonObject.put("casual", GlobalData.getInstance().getSearchPreference().get("casual"));
            jsonObject.put("soulmate", GlobalData.getInstance().getSearchPreference().get("soulmates"));
            params.put("searching_for", jsonObject);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.v("SingletonServer", "Server responds to confirming profile.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.v("SingletonServer", "Confirm Profile errs.");
                    }
                }
        ) {
        };
        SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
    }



}