package com.mushroomrobot.redex;

import android.util.Log;

import com.mushroomrobot.redex.utils.RemoteData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick.
 */

// This class creates Posts objects out of the Reddit API, and maintains a list of these posts for
// other classes to use.

public class PostsHolder {

    private final String URL_TEMPLATE =
            "http://www.reddit.com/r/SUBREDDIT_NAME/"
            + ".json"
            + "?after=AFTER";

    String subreddit;
    String url;
    String after;

    PostsHolder(String sr){
        subreddit = sr;
        after = "";
        generateURL();
    }

    //This method generates the actual URL from the template based on the subreddit name and the
    //'after' property

    private void generateURL(){
        url = URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url = url.replace("AFTER", after);
    }

    //Returns a list of Post objects after fetching data from Reddit using the JSON API
    List<Post> fetchPosts(){
        String raw = RemoteData.readContents(url);
        List<Post> list = new ArrayList<Post>();
        try{
            JSONObject data = new JSONObject(raw).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");

            //Using this property we can fetch the next set of posts from the same subreddit
            after = data.getString("after");

            for (int i=0; i<children.length(); i++){
                JSONObject cur = children.getJSONObject(i).getJSONObject("data");

                Post p = new Post();
                p.title = cur.optString("title");
                p.url = cur.optString("url");
                p.numComments = cur.optInt("num_comments");
                p.points = cur.optInt("score");
                p.author = cur.optString("author");
                p.subreddit = cur.optString("subreddit");
                p.permalink = cur.optString("permalink");
                p.domain = cur.optString("domain");
                p.id = cur.optString("id");
                if (p.title!=null) list.add(p);
            }
        } catch (JSONException e){
            Log.e("fetchPosts()", e.toString());
        }
        return list;
    }

    //This is to fetch the next set of posts using the 'after' property
    List<Post> fetchMorePosts(){
        generateURL();
        return fetchPosts();
    }
}