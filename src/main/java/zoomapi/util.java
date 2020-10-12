package zoomapi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import zoomapi.baseUnit.Channel;
import zoomapi.baseUnit.Member;
import zoomapi.baseUnit.Message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CancellationException;

public class util {

    /**
     * enforces keys to be list of string
     * @param keys
     * @param keys
     * @param allow_null
     */
    public static boolean require_keys(Map<String, String> keys, List<String> req_keys, boolean allow_null){

        for(int i = 0; i < req_keys.size(); i++){
            if(!keys.containsKey((req_keys.get(i)))){
                throw new IllegalArgumentException(req_keys.get(i) + " must be set");
            }
            if(!allow_null && keys.get(req_keys.get(i)) == null){
                throw new IllegalArgumentException(req_keys.get(i) + " cannot be null");
            }
        }
        return true;
    }

    public static String date_to_str(String date){
        Date toFormat = new Date(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return simpleDateFormat.format((toFormat));
    }

    public static String generateJWT(String client_id, String client_secret) throws UnsupportedEncodingException {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        HashMap<String, String> payload = new HashMap<>();
        Date exp = new Date(Instant.now().getEpochSecond() + 3600);
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withHeader(header)
                    .withIssuer(client_id)
                    .withExpiresAt(exp)
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return new String(token.getBytes("UTF-8"),"UTF-8");
    }

    public static String getOAuthToken(String client_id, String client_secret, String port,
                                String redirect_uri, String browser_path) throws IOException {

        //Request User Authorization
        URL auth = new URL("https://zoom.us/oauth/authorize?response_type=code&client_id=" +
                client_id +
                "&redirect_uri=" +
                redirect_uri);
        HttpURLConnection auth_conn = (HttpURLConnection) auth.openConnection();
        auth_conn.setRequestMethod("GET");
        auth_conn.setInstanceFollowRedirects(true);
        //System.out.println(auth_conn.getURL().toString() + " ----------------------------url");
        auth_conn.connect();
        ProcessBuilder proc = new ProcessBuilder(browser_path,
                auth_conn.getURL().toString());
        proc.start();
        //System.out.println(auth_conn.getURL().toString() + " ----------------------------url");

        //Get code
        String code = "";

        while (code.length() == 0) {
            HttpResponse<JsonNode> get_code = Unirest.get("http://localhost:4040/api/requests/http").asJson();
            JSONArray jArray = get_code.getBody().getObject().getJSONArray("requests");
            for (int i = 0; i < jArray.length(); i++) {
                String status_code = jArray.getJSONObject(i)
                        .getJSONObject("response")
                        .get("status_code").toString();
                if (status_code.equals("502")||status_code.equals("302")) {
                    code = jArray.getJSONObject(i)
                            .getJSONObject("request")
                            .get("uri")
                            .toString();
                    if (!code.equals("/favicon.ico")) {
                        code = code.substring(7);
                        break;
                    }
                }
            }
        }

        //Request Access Token
        Unirest.config().cookieSpec("ignoreCookies");
        String token_url = "https://zoom.us/oauth/token";
        String authorization = "Basic " +
                Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
        HttpRequestWithBody token_conn = Unirest.post(token_url);
        token_conn.header("Authorization", authorization);
        token_conn.queryString("grant_type", "authorization_code");
        token_conn.queryString("code", code);
        token_conn.queryString("redirect_uri", redirect_uri);
        token_conn.connectTimeout(36000);

        HttpResponse<JsonNode> get_token = token_conn.asJson();

        JSONObject token = new JSONObject();
        //while (token.toString().indexOf("access_token") == -1)
        token = get_token.getBody().getObject();

        Unirest.config().cookieSpec("default");
        return token.get("access_token").toString();
    }

    public static Message messageParser(JSONObject msg){
        Message m = new Message();
        m.setId(msg.getString("id"));
        //m.setSender(msg.getString("sender"));
        m.setDate_time(msg.getString("date_time"));
        m.setMessage(msg.getString("message"));
        m.setTimestamp(msg.getString("timestamp"));
        return m;
    }

    public static Member memberParser(JSONObject member){
        Member m = new Member();
        m.setId(member.getString("id"));
        m.setEmail(member.getString("email"));
        m.setFirstName(member.getString("first_name"));
        m.setLastName(member.getString("last_name"));
        m.setRole(member.getString("role"));
        return m;
    }

    public static Channel channelParser(JSONObject channel){
        Channel c = new Channel();
        c.setId(channel.getString("id"));
        c.setName(channel.getString("name"));
        c.setType(Integer.toString(channel.getInt("type")));
        return c;
    }
}
