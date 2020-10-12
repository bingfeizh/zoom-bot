package bots;

import org.ini4j.Wini;

import java.io.File;

public class INIParser {

    static String iniFilePath = "src/main/java/bots/bot.ini";

    static String client_id;
    static String client_secret;
    static String port;
    static String browser_path;
    static String redirect_url;

    public static boolean parse_ini_file(String iniPath){
        try{
            Wini ini = new Wini(new File(iniFilePath));
            //Wini ini = new Wini(new File(iniPath));
            client_id = ini.get("OAuth", "client_id");
            client_secret = ini.get("OAuth", "client_secret");
            port = ini.get("OAuth", "port");
            browser_path = ini.get("OAuth", "browser_path");
            redirect_url = ini.get("OAuth", "redirect_url");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return true;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getPort() {
        return port;
    }

    public String getBrowser_path() {
        return browser_path;
    }

    public String getRedirectURL(){return redirect_url;}

}