package com.demo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataDownloadTask extends AsyncTask<String, Integer, ArrayList<Celebrity>>   {
    private final StringBuilder pageCode = new StringBuilder();
    private HttpURLConnection urlConnection = null;
    private final StringBuilder https = new StringBuilder();
    private final ArrayList<Celebrity> celebrityArrayList = new ArrayList<Celebrity>();
    private InputStream inputStream;
    private int progress = 0;
    private final Context mContext;
    private final View rootView;
    ProgressBar progressBar;

    public DataDownloadTask(Context context, View rootView) {
        this.mContext = context;
        this.rootView = rootView;
    }


    @Override
    protected void onPreExecute() {
       progressBar = rootView.findViewById(R.id.progressBar);
       progressBar.setMax(100);
       progressBar.setProgress(0);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      progressBar.setProgress(values[0]);
    }

    /*@Override
    protected void onPostExecute(ArrayList<Celebrity> celebrities) {
        super.onPostExecute(celebrities);
        ImageView imageView  = (ImageView)((Activity) mContext).findViewById(R.id.imageViewCelebrity);;
        imageView.setImageResource(R.drawable.ic_launcher_background);}
    Передача контекста */

    @Override
    protected ArrayList<Celebrity> doInBackground(String... strings) {

        try {

            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            if (inputStream != null) {
                pageCode.append(bufferedReader.readLine());
            }
            String page = pageCode.toString();
            Pattern patternName = Pattern.compile("\"name\":\"(.*?)\"");
            Pattern patternPictureURL = Pattern.compile("\"squareImage\":\"//(.*?)[?]");
            Matcher matcherName = patternName.matcher(page);
            Matcher matcherPictureURL = patternPictureURL.matcher(page);
            while (matcherPictureURL.find() && matcherName.find()) {
                https.setLength(0);
                https.append("https://");
                https.append(matcherPictureURL.group(1));
                celebrityArrayList.add(new Celebrity(matcherName.group(1), https.toString()));
            }

            for(int i = 0; i < celebrityArrayList.size(); i++){
                if(inputStream != null){
                    inputStream.close();
                }
                 urlConnection.disconnect();
                 url = new URL(celebrityArrayList.get(i).getBitmapURL());
                 urlConnection = (HttpURLConnection) url.openConnection();
                 inputStream = urlConnection.getInputStream();
                 Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                 Celebrity celebrity =   new Celebrity(celebrityArrayList.get(i).getName(), bitmap);
                 celebrityArrayList.set(i, celebrity);
                 progress++;

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            if (urlConnection != null) {
                urlConnection.disconnect();
                try {
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return celebrityArrayList;


    }

}

