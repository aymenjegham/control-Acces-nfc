package com.support.android.designlibdemo;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class getData extends AsyncTask<String,Void,String>{
    private TextView roleField;
    private Context context;
    private int byGetOrPost = 0;
    String code;

    //flag 0 means get and 1 means post.(By default it is get.)
    public getData(Context context, int flag) {
        this.context = context;
        Log.e("result flag: ", "  flag " + flag);

        byGetOrPost = flag;
    }

    protected void onPreExecute(){

    }



    public String getName() {
        return code;
    }




    @Override
    protected String doInBackground(String... arg0) {



        String username = (String) arg0[0];
        String dataUrl = "http://192.168.1.179:120655s/nfc/getuser.php?id=s2d4d";
        String dataUrlParameters = "id="+"s2d45sd";

        Log.e("Search Respon: ", "Search sb.ssss():  "+postData(dataUrl, dataUrlParameters));
        String link = "http://192.168.1.179:12065/nfc/getuser.php?id=" + username;


        return username;
    }


    @Override
    protected void onPostExecute(String result){
        code=  result;
        Log.e("result result: ", "  result " + result);

    }
    public String postData(String sURL,String sData)
    {
        try
        {
            String data = URLEncoder.encode("jsonString", "UTF-8")+ "=" + URLEncoder.encode(sData, "UTF-8");
            URL url = new URL(sURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null)
            {

                sb.append(line + "\n");
            }


            return sb.toString();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static void getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        String link = "http://192.168.1.179:12065/nfc/getuser.php?id=" + "test";
        String dataUrl = "http://192.168.1.179:12065/nfc/getuser.php";
        String dataUrlParameters = "id="+"priyabrat";
        URL url;
        HttpURLConnection connection = null;
        try {
// Create connection
            url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length","" + Integer.toString(dataUrlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
// Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(dataUrlParameters);
            wr.flush();
            wr.close();
// Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String responseStr = response.toString();
            Log.d("Server response",responseStr);
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
        HttpURLConnection urlConnection = null;


    }
}
