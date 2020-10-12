package zoomapi.clients;

import zoomapi.components.*;
import zoomapi.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JWTZoomClient extends ZoomClient {

    UserComponent userComponent;
    MeetingComponent meetingComponent;
    ReportComponent reportComponent;
    WebinarComponent webinarComponent;
    RecordingComponent recordingComponent;

    public void init(String client_id, String client_secret, String data_type, int timeout) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        super.init(client_id,client_secret,timeout);

        data_type = data_type == null ? "json" : data_type;

        config.put("client_id", client_id);
        config.put("client_secret", client_secret);
        config.put("data_type", data_type);
        config.put("token", util.generateJWT(client_id, client_secret));

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

    public void refreshToken() throws IOException {
        config.put("token", util.generateJWT(config.get("client_id"), config.get("client_secret")));
    }

    public void setApiKey(String api_key) {
        this.config.put("api_key", api_key);
    }

    public void setApiSecret(String api_secret) {
        this.config.put("api_secret", api_secret);
    }

    public String getApiKey() {
        return this.config.get("api_key");
    }

    public String getApiSecret() {
        return this.config.get("api_secret");
    }

    public String getRedirectUrl() {
        return config.get("redirect_uri");
    }

    public void setRedirectUrl(String url) throws IOException {
        config.put("redirect_uri", url);
        refreshToken();
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
