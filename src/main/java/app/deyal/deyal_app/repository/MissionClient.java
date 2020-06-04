package app.deyal.deyal_app.repository;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Mission;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class MissionClient {

    private static final String serverUrl = "http://localhost:3030/v1";

    public static boolean getMissionList(String token) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //adding token
//            URIBuilder uriBuilder = new URIBuilder(serverUrl.concat("/mission/list"));
//            uriBuilder.setParameter("token", token);

            //Creating a HttpGet object
//            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpGet httpGet = new HttpGet("http://localhost:3030/v2/mission/list");

//            System.out.println(httpGet.getURI());

            //Executing the Get request
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
//            System.out.println(json);
                JsonArray jsonList = JsonParser
                        .parseString(json)
                        .getAsJsonObject()
                        .get("payload")
                        .getAsJsonArray();
                System.out.println(jsonList);

                Gson gson = new Gson();
                Type missionListType = new TypeToken<ArrayList<Mission>>() {
                }.getType();

                ArrayList<Mission> missionArray = gson.fromJson(jsonList, missionListType);

                StageManager.getInstance().missionList = missionArray;

                //printing list
//                for(Mission mission : missionArray) {
//                    mission.print();
//                }

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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createMission(String token, Mission mission) {
        try {
            //Creating a HttpClient object
            CloseableHttpClient httpclient = HttpClients.createDefault();

            //adding token
            URIBuilder uriBuilder = new URIBuilder(serverUrl.concat("/mission/create"));
            uriBuilder.setParameter("token", token);

            //Creating a HttpPut object
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            //adding user data
            Gson gson = new Gson();
            String json = gson.toJson(mission);
            System.out.println(json);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(json));

            //Executing the Put request
            HttpResponse httpresponse = httpclient.execute(httpPost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();

            //Printing the status line
            System.out.println(httpresponse.getStatusLine());
            Scanner sc = new Scanner(httpresponse.getEntity().getContent());
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }

            return statusCode == 200;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

}
