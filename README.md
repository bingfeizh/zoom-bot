This is a Gradle project. Simply build with Gradle in Intellij IDEA. All ms4 implementations are located under JavaImpl. 

Folder structure is basically identical to the that of the python implementations.

ini file is located in directory [bots/]. You need to specify the bot parameters in it.
Set ```client_id``` as your client id;
Set ```client_secret``` as your client secret;
Set ```browser_path``` as your browser path;
Set ```redirect_url``` as the url exposed by ngrok.
Also, you need to add ```redirect_url``` to the white list of your app.

You can find the ChatBot in JavaImpl/src/main/java/bots/ChatBot.java. 

To test notification functions, you need to run NotificationBot.java in directory [bots/].

In NotificationBot.java, from line 53, you can add all channels you want to subscribe for messages.

Sometimes the code will throw exception ```Exception in thread "main" kong.unirest.json.JSONException: JSONObject["access_token"] not found.```, please run the bot again.
