import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getClips {


    // returns Clip array. x top Clips that are y hrs old using helix API endpoint.
    //https://dev.twitch.tv/docs/api/reference#get-clips
    public static ArrayList<Clip> getBroadcasterClips(String broadcaster, String channelID, int amount, twitchAPI api, String startingDate, String endingDate) throws JSONException {


        //	Ending date/time for returned clips, in RFC3339 format.
        startingDate += "T01:00:00-00:00";
        endingDate += "T23:59:59-00:00";            // last possible moment

//      Clip clips[] = new Clip[amount];      sometimes # of clips in timeframe < amount
        ArrayList<Clip> clips = new ArrayList<>();

        String URL = String.format("https://api.twitch.tv/helix/clips?broadcaster_id=%s&first=%d&started_at=%s&ended_at=%s", channelID, amount, startingDate, endingDate);
        String responseJSON = api.getClips(URL);

        //parse data

        String mp4_url="";
        String title = "";
        String url = "";

        JSONObject obj =  new JSONObject(responseJSON);
        JSONArray arr = obj.getJSONArray("data");

        for (int i=0; i<arr.length(); i++){

            JSONObject object = arr.getJSONObject(i);

            title = object.getString("title");
            url = object.getString("url");

            String thumbnail_url = (object.getString("thumbnail_url"));
            Pattern pattern = Pattern.compile("^(.*?)-preview-");   // some random regex
            Matcher matcher = pattern.matcher(thumbnail_url);
            if (matcher.find())
            {
                mp4_url = matcher.group(1) + ".mp4";

            }
            clips.add(new Clip (title, mp4_url, broadcaster, url));

        }
        return clips;

    }


    //hehe
    public static void downloadClip(Clip clip){

        try {
            String title = clip.getTitle();
            Files.createDirectories(Paths.get(".\\saved_clips\\" + clip.getBroadcaster()));      // for new startup
            title = clip.getTitle().replaceAll("[\\\\\\/:*?\"<>|]|[ \\f\\t\\v]$", "");  //A filename cannot contain any of the following characters: \ / : * ? " < > |

            System.out.println("downloaded: " + "\"" + title +"\"" +  " from: " + clip.getUrl());

            FileUtils.copyURLToFile(new URL(clip.getMp4_url()), new File(".\\saved_clips\\" +clip.getBroadcaster() + "\\" +  title+".mp4"));
        } catch (Exception e) {
            System.out.println("download error occured.. try again");
        }

    }




}
