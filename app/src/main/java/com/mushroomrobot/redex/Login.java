package com.mushroomrobot.redex;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nick.
 */
public class Login {



    //This method creates a connection that allows you to POST data
    private HttpURLConnection getConnection(String url){
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e){
            Log.d("Invalid URL", url);
            return null;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) u.openConnection();
        } catch (IOException e){
            Log.d("Unable to connection", url);
            return null;
        }
        //Timeout after 30 seconds
        connection.setReadTimeout(30000);
        //Allows POST data
        connection.setDoInput(true);
        return connection;
    }

    //This method lets you POST data to the URL
    private boolean writeToConnection(HttpURLConnection con, String data){
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(con.getOutputStream()));
            pw.write(data);
            pw.close();
            return true;
        } catch (IOException e){
            Log.d("Unable to write", e.toString());
            return false;
        }
    }

    // https://www.reddit.com/api/v1/authorize?client_id=aDcKr4ajR9PXgQ&response_type=code&state=ran1234567popcorn&redirect_uri=http://www.mushroomrobot.com&duration=permanent&scope=identity,edit,flair,history,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,wikiedit,wikiread


}
