# twitchclps

command line program that downloads twitch clips from any twitch streamer. can download x clips from y timerange using Java. 

## usage

format:
```
<client_id> <client_secret> <twitch_channel_name> <#_of_clips_to_download> <starting_date> <ending_date>
```
example of downloading clips through command line:

```
java clipUI c3cr5ws4pukjraklk3zmxhoau6bk0i icjtrl292o0jjdc9jm6ojcmu1blj51 forsen 3 2021-06-01 2021-07-29
```


- downloaded clips will be located at the root directory. 
- a new directory will be created for every new streamer.
```
.\saved_clips\<streamer_name>
 ```


## prerequisites

need twitch client ID / client secret in order to access twitch api. 

go to: https://dev.twitch.tv/console/apps/create

![image](https://user-images.githubusercontent.com/59300081/127726447-7c57b05d-3afa-48e1-93e9-05d72f8b7c07.png)


from there, fill out the information, and set "OAuth Redirect URLs" as "https://localhost". 

![image](https://user-images.githubusercontent.com/59300081/127726345-f34cf265-deda-4fb1-9f31-8a08acb304ca.png)

click "manage"

![image](https://user-images.githubusercontent.com/59300081/127726403-66ceb95c-78a1-456e-b71b-2221ee277553.png)

click "new secret" to generate cilent secret. 

copy down "client ID" and the client secret





