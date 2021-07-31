
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class twitchAPI  {


    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String AUTH_TOKEN;


    public twitchAPI(String CLIENT_ID, String CLIENT_SECRET){

        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
        this.AUTH_TOKEN = null;

    }

    // one instance, reuse
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();



    //https://dev.twitch.tv/docs/authentication#validating-requests
    //"App access tokens expire after about 60 days, so you should check that your app access token is valid by submitting
    // a request to the validation endpoint (see Validating Requests). If your token has expired, generate a new one."


    public void getAuthToken() throws JSONException{

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&grant_type=client_credentials"))
                .uri(URI.create("https://id.twitch.tv/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response1 = null;

        try {
            response1 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("input wrong, enter correct client credentials");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //parse JSON
        JSONObject obj = new JSONObject(response1.body());

        if (response1.statusCode() == 403 || response1.statusCode() == 400 ){
            throw new IllegalArgumentException("client_id/client_secret is incorrect");
        }
        else if (response1.statusCode() == 500){
            throw new IllegalArgumentException("date may be too old, twitch api cannot retrieve the clips...");
        }
        String token = obj.getString("access_token");

        AUTH_TOKEN = token;

    }

    public String getChannelID(String broadcasterName) throws JSONException{


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("https://api.twitch.tv/helix/users?login=%s", broadcasterName)))
                .setHeader("Client-Id", CLIENT_ID)
                .setHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(response.body());

        JSONArray data = json.getJSONArray("data");

        if (data.length() ==0 ){
            throw new IllegalArgumentException("Check if broadcaster name exists");
        }

        JSONObject xd = data.getJSONObject(0);


        return xd.getString("id");

    }


//    public static void generateToken(){         // will use same if not expired yet...
//
//
//        //validate token
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
//                .uri(URI.create("https://id.twitch.tv/oauth2/validate"))
//                .setHeader("Authorization", "OAuth " + twitchAPI.AUTH_TOKEN)
//                .build();
//
//        HttpResponse<String> response = null;
//        try {
//            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        //parse JSON
//        JSONObject obj1 = new JSONObject(response.body());
//
//
//        System.out.println(response.body());
//
//
//        int timeLeft= obj1.getInt("expires_in");
//        System.out.println("time left:" + timeLeft);
//
//        if (timeLeft < 24 * 2 * 60 * 60 ) {     // renew token if less than 2 days to expiry.
//
//            getAuthToken();
//            System.out.println("time expired.. renewed!" + AUTH_TOKEN);
//        }
//        else{
//            System.out.println("token already good :3 " + AUTH_TOKEN);
//        }
//    }


    public String getClips(String URL){     // all endpoints at twitch API require clientid/authorization


        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL))
                .setHeader("Client-Id", CLIENT_ID)
                .setHeader("Authorization", "Bearer " + AUTH_TOKEN)
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return response.body();
    }


}
