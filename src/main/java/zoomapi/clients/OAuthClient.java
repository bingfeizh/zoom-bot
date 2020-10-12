package zoomapi.clients;

import zoomapi.api.ChatComponent;
import zoomapi.baseUnit.Token;
import zoomapi.components.*;
import zoomapi.tablehandler.CredentialTableHandler;
import zoomapi.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

public class OAuthClient extends ZoomClient {
    ChatChannelsComponent chatChannelsComponent;
    ChatMessagesComponent chatMessagesComponent;
    ChatComponent chatComponent;
    UserComponent userComponent;
    MeetingComponent meetingComponent;
    ReportComponent reportComponent;
    WebinarComponent webinarComponent;
    RecordingComponent recordingComponent;


    public void init(String client_id, String client_secret, String port, String redirect_url,
                     String browser_path, String data_type, int timeout) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, SQLException, InvocationTargetException {
        super.init(client_id,client_secret,timeout);

        config.put("client_id", client_id);
        config.put("client_secret", client_secret);
        config.put("port", port);
        config.put("redirect_url", redirect_url);
        config.put("browser_path", browser_path);

        CredentialTableHandler credentialTableHandler = new CredentialTableHandler(Token.class);
        Token token = credentialTableHandler.getToken(client_id);

        if (token == null || new Date().getTime() - Long.parseLong(token.getTimestamp()) > 900000) {
            System.out.println("Update token");
            Token t = new Token(client_id,
                    util.getOAuthToken(client_id, client_secret, port, redirect_url, browser_path),
                    Long.toString(new Date().getTime()));
            credentialTableHandler.updateToken(t);
        }

        token = credentialTableHandler.getToken(client_id);
        config.put("token", token.getToken());
        //config.put("token", util.getOAuthToken(client_id, client_secret, port, redirect_url, browser_path));
        //config.put("token", "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiJhNzY3YTE1Ni03ZDYwLTQyODMtYmMzYS1hMTgwYTk5NjYxYzcifQ.eyJ2ZXIiOiI2IiwiY2xpZW50SWQiOiIyaVNoMWRuZ1FES2k2M1d2eXFxMjJRIiwiY29kZSI6ImZTVzA3OWszTWpfTkFyRlVrd3dUdWlyOGZLQ0xib3FDQSIsImlzcyI6InVybjp6b29tOmNvbm5lY3Q6Y2xpZW50aWQ6MmlTaDFkbmdRREtpNjNXdnlxcTIyUSIsImF1dGhlbnRpY2F0aW9uSWQiOiIzNTg5YWEwMDRiMjg0OGRiMGFmNjkwMzQzNDc3MTQzNyIsInVzZXJJZCI6Ik5BckZVa3d3VHVpcjhmS0NMYm9xQ0EiLCJncm91cE51bWJlciI6MCwiYXVkIjoiaHR0cHM6Ly9vYXV0aC56b29tLnVzIiwiYWNjb3VudElkIjoiOE9RZG02YXlSa21lTVhxclZTQmhfdyIsIm5iZiI6MTU4NzYxMzgwNywiZXhwIjoxNTg3NjE3NDA3LCJ0b2tlblR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE1ODc2MTM4MDcsImp0aSI6ImQyM2M4NGI5LTg1OWYtNDRmYi1hZGQ1LWE3YzMyN2I2MzAxNiIsInRvbGVyYW5jZUlkIjowfQ.zPUW7Zcf3qx7j7-YCpDdRzed2q93kt-6VCg_lTrdF-IYTHKX_BruAfCkX4pVICx_YkbQiVDMgyBmPNLixnSIwA");

        COMPONENT_CLASSES.put("chat_channels", ChatMessagesComponent.class);
        COMPONENT_CLASSES.put("chat_massages", ChatChannelsComponent.class);
        COMPONENT_CLASSES.put("chat", ChatComponent.class);

        chatChannelsComponent = new ChatChannelsComponent();
        chatChannelsComponent.init(this.base_uri, this.config);

        chatMessagesComponent = new ChatMessagesComponent();
        chatMessagesComponent.init(this.base_uri, this.config);

        chatComponent = new ChatComponent();
        chatComponent.init(this.base_uri, this.config);

        userComponent = new UserComponent();
        userComponent.init(API_BASE_URIS, config);

        meetingComponent = new MeetingComponent();
        meetingComponent.init(API_BASE_URIS, config);

        recordingComponent = new RecordingComponent();
        recordingComponent.init(API_BASE_URIS, config);

        webinarComponent = new WebinarComponent();
        webinarComponent.init(API_BASE_URIS, config);

        reportComponent = new ReportComponent();
        reportComponent.init(API_BASE_URIS, config);
    }

    public void init(String client_id, String client_secret, String port, String redirect_url,
                     String browser_path) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, SQLException, InvocationTargetException {
        init(client_id,client_secret,port,redirect_url,browser_path,"json", 15);
        super.init(client_id, client_secret, timeout);
    }

    public void refreshToken() throws IOException {
        util.getOAuthToken(config.get("client_id"), config.get("client_secret")
                ,config.get("port"), config.get("redirect_uri"), config.get("browser_path"));
    }

    public String getRedirectUrl() {
        return config.get("redirect_uri");
    }

    public void setRedirectUrl(String url) throws IOException {
        config.put("redirect_uri", url);
        refreshToken();
    }

    public ChatMessagesComponent chatMessages() {
        return chatMessagesComponent;
    }

    public ChatChannelsComponent chatChannels() {
        return chatChannelsComponent;
    }

    public ChatComponent chat() {
        return chatComponent;
    }

    public MeetingComponent meeting() {
        return meetingComponent;
    }

    public ReportComponent report() {
        return reportComponent;
    }

    public UserComponent user() {
        return userComponent;
    }

    public WebinarComponent webinar() {
        return webinarComponent;
    }

    public RecordingComponent recording() {
        return recordingComponent;
    }



}
