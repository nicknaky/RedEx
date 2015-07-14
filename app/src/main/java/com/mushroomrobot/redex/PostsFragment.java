package com.mushroomrobot.redex;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick.
 */

//This class simply just loads the posts into the listView by using PostsHolder

public class PostsFragment extends Fragment {

    ListView postsList;
    ArrayAdapter<Post> adapter;
    Handler handler;

    String subreddit;
    List<Post> posts;
    PostsHolder postsHolder;



    public static Fragment newInstance(String subreddit){
        PostsFragment pf = new PostsFragment();
        pf.subreddit = subreddit;
        pf.postsHolder = new PostsHolder(pf.subreddit);
        return pf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_posts, container, false);
        postsList = (ListView) v.findViewById(R.id.posts_listView);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        handler = new Handler();
        posts = new ArrayList<Post>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize(){
        //This should run only once as the setRetainInstance(true) method has been called on this fragment

        if(posts.size()==0){
            //Must execute network tasks outside the UI thread.  So create a new thread.

            new Thread(){
                public void run(){
                    posts.addAll(postsHolder.fetchPosts());

                    // UI elements should only be accessed in the primary thread, so we must use a handler here
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        } else {
            createAdapter();
        }
    }


    //This method creates an adapter and assigns it to the list
    private void createAdapter(){
        //Make sure this fragment is still part of the activity
        if(getActivity()==null) return;

        adapter = new ArrayAdapter<Post>(getActivity(), R.layout.li_post, posts){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView==null){
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.li_post, null);
                }

                TextView postTitle = (TextView) convertView.findViewById(R.id.post_title);
                TextView postDetails = (TextView) convertView.findViewById(R.id.post_details);
                TextView postScore = (TextView) convertView.findViewById(R.id.post_score);

                postTitle.setText(posts.get(position).title);
                postDetails.setText(posts.get(position).getDetails());
                postScore.setText(posts.get(position).getScore());
                return convertView;
            }
        };
        postsList.setAdapter(adapter);
    }
}
