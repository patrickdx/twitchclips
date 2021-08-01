import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.JSONException;

public class clipUI {

    public static void main (String[] args){

//        // "clean" mp4s in existing dirs
//        File saved_clips = new File (".\\src\\main\\resources\\saved_clips\\");
//        String[] paths = saved_clips.list();
//
//        try{
//            for (String pathFile : paths){
//                FileUtils.cleanDirectory(new File (".\\src\\main\\resources\\saved_clips\\" + pathFile));
//
//            }
//        }
//        catch (IOException e){
//
//        }

        System.out.println("sup");

        //get top clips from twitch api: <twitch_channel_name> <# of clips to download> <start date> <end date>

        String CLIENT_ID;
        String CLIENT_SECRET;
        String streamer;
        int clipAmount;
        String startingDate;
        String endingDate;

        String alphanumeric = "[A-Za-z0-9]+";
        String validUsername = "^[a-zA-Z0-9_]{4,25}+$";  //match username alphanumeric and underscore 4-25 length

        CLIENT_ID = args[0];
        CLIENT_SECRET = args[1];
        streamer = args[2];


        if (!streamer.matches(validUsername) || !CLIENT_ID.matches(alphanumeric) || !CLIENT_SECRET.matches(alphanumeric)){           // credientals / broadcaster names are only made up of alphanumeric characetrs
            throw new InputMismatchException("enter valid credentials");
        }

        try{
             clipAmount = Integer.valueOf(args[3]);
        }

        catch (InputMismatchException e){
            throw new InputMismatchException("input is: <client_id> <client_secret> <twitch_channel_name> <# of clips to download> <starting date> <ending date>");
        }

        if (clipAmount < 1 || clipAmount > 1000){     // api can only return max 1k clips
            throw new IllegalArgumentException("enter amount of clips to download < 1000");
        }

        startingDate = args[4];
        endingDate = args[5];

        // regex pro tip: anchor characters

        //If a caret (^) is at the beginning of the entire regular expression, it matches the beginning of a line.
        //
        //If a dollar sign ($) is at the end of the entire regular expression, it matches the end of a line. (only match if match is at end of line)
        //
        //If an entire regular expression is enclosed by a caret and dollar sign (^like this$), it matches an entire line.

        // example: [0-9]{2}-[0-9]{2} for format "00-00", looks okay, but matches "7-00-00-10", because of no anchor characters.

        String date = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";   // matches XXXX-XX-XX,
        if (!startingDate.matches(date) || !endingDate.matches(date)){
            throw new IllegalArgumentException("enter date in corect format: YYYY-MM-DD");

        }

        twitchAPI api = new twitchAPI(CLIENT_ID, CLIENT_SECRET);
        try {
			api.getAuthToken();
		    String channelID = api.getChannelID(streamer);
	        ArrayList<Clip> clips =  getClips.getBroadcasterClips(streamer, channelID, clipAmount, api,startingDate, endingDate);

	        if (clips.isEmpty()){
	            System.out.println("broadcaster has clips disabled/no clips found in specified timeframe");
	            System.exit(0);
	        }

	            for (Clip j : clips){
	                getClips.downloadClip(j);
	            }
	            
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    



    }

}
