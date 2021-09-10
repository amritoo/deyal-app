package app.deyal.deyal_app.repository;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.managers.DataManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

    public static boolean getMissionList(String token) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_MISSION_LIST_ALL);
            uriBuilder.setParameter("token", token);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            // Executing the Get request
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
                JsonArray jsonList = JsonParser.parseString(json).getAsJsonObject().get("payload").getAsJsonArray();

                Gson gson = new Gson();
                Type missionListType = new TypeToken<ArrayList<Mission>>() {
                }.getType();
                ArrayList<Mission> missionArray = gson.fromJson(jsonList, missionListType);

                DataManager.getInstance().allMissionsList = missionArray;

                return true;
            } else {
                // Printing the status line
                System.out.println("Inside get mission list\n" + httpResponse.getStatusLine());
                Scanner sc = new Scanner(httpResponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside get mission list");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getMyMissionList(String token) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_MISSION_LIST_MY);
            uriBuilder.setParameter("token", token);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            // Executing the Get request
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
                JsonArray jsonList = JsonParser.parseString(json).getAsJsonObject().get("payload").getAsJsonArray();

                Gson gson = new Gson();
                Type missionListType = new TypeToken<ArrayList<Mission>>() {
                }.getType();
                ArrayList<Mission> missionArray = gson.fromJson(jsonList, missionListType);

                DataManager.getInstance().myMissionsList = missionArray;

                return true;
            } else {
                // Printing the status line
                System.out.println("Inside get my mission list\n" + httpResponse.getStatusLine());
                Scanner sc = new Scanner(httpResponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside get my mission list");
                return false;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean searchMission(String token, String title) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_MISSION_SEARCH);
            uriBuilder.setParameter("token", token);
            uriBuilder.setParameter("title", title);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpResponse httpResponse = httpclient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
                String json = scanner.nextLine();
                JsonArray jsonList = JsonParser.parseString(json).getAsJsonObject().get("payload").getAsJsonArray();

                Gson gson = new Gson();
                Type missionListType = new TypeToken<ArrayList<Mission>>() {
                }.getType();
                ArrayList<Mission> missionArray = gson.fromJson(jsonList, missionListType);

                DataManager.getInstance().tempMissionList = missionArray;

                return true;
            } else {
                // Printing the status line
                System.out.println("Inside search mission\n" + httpResponse.getStatusLine());
                Scanner sc = new Scanner(httpResponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside search mission");
                return false;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createMission(String token, Mission mission) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_MISSION_CREATE);
            uriBuilder.setParameter("token", token);
            HttpPost httpPost = new HttpPost(uriBuilder.build());

            // Adding user data
            Gson gson = new Gson();
            String json = gson.toJson(mission);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(json));

            // Executing the Put request
            HttpResponse httpresponse = httpclient.execute(httpPost);

            int statusCode = httpresponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            } else {
                // Printing the status line
                System.out.println("Inside create mission\n" + httpresponse.getStatusLine());
                Scanner sc = new Scanner(httpresponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside create mission");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateMission(String token, Mission mission) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(Constants.URL_MISSION_UPDATE);
            uriBuilder.setParameter("token", token);
            HttpPut httpPut = new HttpPut(uriBuilder.build());

            // Adding mission data
            Gson gson = new Gson();
            String json = gson.toJson(mission);
            System.out.println(json);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setEntity(new StringEntity(json));

            // Executing the Put request
            HttpResponse httpresponse = httpclient.execute(httpPut);

            int statusCode = httpresponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            } else {
                // Printing the status line
                System.out.println("Inside update mission\n" + httpresponse.getStatusLine());
                Scanner sc = new Scanner(httpresponse.getEntity().getContent());
                while (sc.hasNext()) {
                    System.out.println(sc.nextLine());
                }
                System.out.println("Outside update mission");
                return false;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

}
