package app.deyal.deyal_app.repository;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Register;
import app.deyal.deyal_app.data.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Auth {

    private static final String serverUrl = "http://localhost:3030/v1";

    public static boolean login(String email, String password, boolean remember) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //Creating a HttpPost object
            HttpPost httppost = new HttpPost(serverUrl.concat("/auth/login"));

            //adding email, password and remember
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("remember", remember ? "true" : "false"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Executing the Post request
            HttpResponse httpresponse = httpclient.execute(httppost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpresponse.getEntity().getContent());
                String json = scanner.nextLine();
//            System.out.println(json);
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                String token = jsonObject.getAsJsonObject().get("payload").getAsString();

                //add preference api to save token
                StageManager.getInstance().token = token;
                PreferenceSave.getInstance().setToken(token);

                System.out.println(token);
            }

            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            Scanner sc = new Scanner(httpresponse.getEntity().getContent());
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }

            if (statusCode == 200)
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register(Register register) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //Creating a HttpPost object
            HttpPost httppost = new HttpPost(serverUrl.concat("/auth/register"));

            //adding register data
            Gson gson = new Gson();
            String json = gson.toJson(register);
            httppost.setEntity(new StringEntity(json));
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            //Executing the Post request
            HttpResponse httpresponse = httpclient.execute(httppost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();

            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            Scanner sc = new Scanner(httpresponse.getEntity().getContent());
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }

            if (statusCode == 200)
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getUserData(String token) {
        if (token == null) return false;
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //adding token
            URIBuilder uriBuilder = new URIBuilder(serverUrl.concat("/auth/user"));
            uriBuilder.setParameter("token", token);

            //Creating a HttpGet object
            HttpGet httpGet = new HttpGet(uriBuilder.build());

//            System.out.println(httpGet.getURI());

            //Executing the Get request
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
//            System.out.println(json);
                JsonObject userdata = JsonParser
                        .parseString(json)
                        .getAsJsonObject()
                        .get("payload")
                        .getAsJsonObject();
                System.out.println(userdata);

                Gson gson = new Gson();
                StageManager.getInstance().user = gson.fromJson(userdata, User.class);
//                user.print();

                return true;
            } else {
                //Printing the status line
                System.out.println(httpResponse.getStatusLine());
                Scanner sc = new Scanner(httpResponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }

                return false;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateProfile(String token, User user) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //adding token
            URIBuilder uriBuilder = new URIBuilder(serverUrl.concat("/auth/update"));
            uriBuilder.setParameter("token", token);

            //Creating a HttpPut object
            HttpPut httpPut = new HttpPut(uriBuilder.build());

            //adding user data
            Gson gson = new Gson();
            String json = gson.toJson(user);
            System.out.println(json);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setEntity(new StringEntity(json));

            //Executing the Put request
            HttpResponse httpresponse = httpclient.execute(httpPut);

            int statusCode = httpresponse.getStatusLine().getStatusCode();

            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            Scanner sc = new Scanner(httpresponse.getEntity().getContent());
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }

            if (statusCode == 200)
                return true;
            else
                return false;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(String token, String newPassword, String oldPassword) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //Creating a HttpPost object
            HttpPost httppost = new HttpPost(serverUrl.concat("/auth/changepassword"));

            //adding email, password and remember
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("new password", newPassword));
            nameValuePairs.add(new BasicNameValuePair("old password", oldPassword));
            nameValuePairs.add(new BasicNameValuePair("token", token));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Executing the Post request
            HttpResponse httpresponse = httpclient.execute(httppost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();

            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            Scanner sc = new Scanner(httpresponse.getEntity().getContent());
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }

            if (statusCode == 200)
                return true;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
