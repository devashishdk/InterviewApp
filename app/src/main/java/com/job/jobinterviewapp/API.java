package com.job.jobinterviewapp;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class API {

    //Key that we will use to retrive post list
    public static final String url = "https://www.addatimes.com/api/video-details/664/";


    public interface PostService {
        //This will be used to retrive post list
        //<PostList> type because we have created our main POJO class as PostList which has getPostList method which returns post list
        @GET
        Call<ApiInterview> getPostList(@Url String url);
    }

    public static PostService postService = null;

    public static PostService getService()
    {
        if(postService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }
}
