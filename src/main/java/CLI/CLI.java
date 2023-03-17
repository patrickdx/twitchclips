package CLI;

import java.time.LocalDate;
import java.util.Scanner;

import Exceptions.DownloadException;
import api.ClipHelper;
import api.clipAPI;
import rest.ClipQuery;

public class CLI {

    private static final String CLIENT_ID = null;
    private static final String CLIENT_SECRET = null;
	static ClipQuery query;
	public static void startup(String[] args) {
		System.out.println("TwitchClips Started.");
		Scanner scanner = new Scanner(System.in);
		String streamer;
		int clips;
		LocalDate start, end;
		while (true) {
			args = scanner.nextLine().split(" ");
			try {
				streamer = args[0];
				clips = Integer.valueOf(args[1]);
				start = LocalDate.parse(args[2]);
				end = LocalDate.parse(args[3]);
				// forsen 10 2019-02-23 2023-02-02
				query = new ClipQuery(streamer, "22484632", clips, start, end);
				run();
				break;
			} catch (IllegalArgumentException e) {            // a more customized exception message
				System.out.println(e.getMessage());
			}
//			catch (Exception e) {
//				System.out.println("Expected format for clips:  <twitch_channel_name> <# of clips to download> <YYYY-MM-DD> <YYYY-MM-DD>");
//			}
		}
	}

	private static void run(){
		clipAPI api = new clipAPI(CLIENT_ID, CLIENT_SECRET);
		try {
			ClipHelper.downloadClip(api.getClip(query));
		} catch (DownloadException e) {
			throw new RuntimeException(e);
		}
	}


}
