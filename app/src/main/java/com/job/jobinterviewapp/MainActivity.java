package com.job.jobinterviewapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Declarations

    Toolbar toolbar;
    TextView descriptionText,dateText,categoryText;
    ImageView previewImage,ogImage;
    LinearLayout linearContainer;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Defination

        previewImage = (ImageView) findViewById(R.id.jsonImage);         //DEFAULT VISIBILITY - GONE
        ogImage = (ImageView) findViewById(R.id.ogImage);

        //Progress Dialog
        pd = new ProgressDialog(MainActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(true);
        pd.setTitle("Loading....");
        pd.setMessage("Please Wait");
        pd.show();

        //LinearContainer contains all the views except ImageView, DEFAULT VISIBILITY - GONE
        linearContainer = (LinearLayout) findViewById(R.id.linearContainer);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        dateText = (TextView) findViewById(R.id.dateText);
        categoryText = (TextView) findViewById(R.id.categoryText);
        setUpToolBar();

        getData();

    }

    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



    void getData()
    {
        //Simple Retrofit Stuffs

        String url = API.url;
        Call<ApiInterview> apiInterviewCall = API.getService().getPostList(url);
        apiInterviewCall.enqueue(new Callback<ApiInterview>() {
            @Override
            public void onResponse(Call<ApiInterview> call, Response<ApiInterview> response) {
                ApiInterview api = response.body();
                Data data = api.getData();
                dateText.setText(data.getPublishDate().toString());
                descriptionText.setText(data.getDescription().toString());
                categoryText.setText(data.getVideoCategory().getName().toString());
                try {
                    Picasso.with(MainActivity.this).load(data.getCoverImage().toString()).into(previewImage);
                    Glide.with(MainActivity.this).load(data.getOgImage().toString()).into(ogImage);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }

                //Dismissing ProgressDialog and Making Views Visible

                pd.dismiss();

                previewImage.setVisibility(View.VISIBLE);
                linearContainer.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<ApiInterview> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}
