package com.mobiledi.mgk.cinemawiki;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DetailViewActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView releaseYear;
    TextView rating;
    ImageView poster;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        movieTitle      = (TextView) findViewById(R.id.movietitle);
        releaseYear     = (TextView) findViewById(R.id.releaseYear);
        rating          = (TextView) findViewById(R.id.movieRating);
        description     = (TextView) findViewById(R.id.description);
        poster          = (ImageView)findViewById(R.id.posterPath);
        Bundle movie=getIntent().getExtras();
        String title=movie.getString("title");
        String year=movie.getString("releaseYear");
        String popularity=movie.getString("rating");
        String imagePath=movie.getString("posterPath");
        String decs=movie.getString("description");
        GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(new String[] { imagePath });
        movieTitle.setText("Title:"+title);
        releaseYear.setText("Release Date: "+year);
        rating.setText("Rating: "+popularity);
        description.setText("Overview: "+decs);

    }
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            poster.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
