package zoomapi.clients;

import kong.unirest.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiClient {
        int timeout;
        String base_uri;
        public HashMap<String, String> config;


        public void init(String base_uri, int timeout) {
            this.config = new HashMap<>();
            this.base_uri = base_uri;
            this.timeout = timeout;
        }
        public void init(String base_uri) {
            init(base_uri, 15);
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public void setBase_uri(String base_uri) {
            this.base_uri = base_uri.endsWith("/") ? base_uri.substring(0,base_uri.length()-1) : base_uri;
        }

        public int getTimeout() {
            return timeout;
        }

        public String getBase_uri() {
            return base_uri;
        }

        /**
         *         """Get the URL for the given endpoint
         *
         *         :param endpoint: The endpoint
         *         :return: The full URL for the endpoint
         *         """
         * @param endpoint
         * @return
         */
        public String urlFor(String endpoint){
            if(!endpoint.startsWith("/")){
                endpoint = "/" + endpoint;
            }
            if(endpoint.endsWith("/")){
                endpoint = endpoint.substring(0, endpoint.length()-1);
            }
            return this.base_uri + endpoint;
        }

        public HttpResponse<JsonNode> get_request(String endpoint,
                                                  HashMap<String, String> params,
                                                  HashMap<String, String> headers
        ) throws InterruptedException {
            if (headers == null) {
                headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + config.get("token"));
            }

            //Set req url
            GetRequest connect = Unirest.get(urlFor(endpoint));

            //Set req headers
            for (Map.Entry<String, String> h : headers.entrySet()) {
                connect.header(h.getKey(), h.getValue());
            }

            //Set req params
            for (Map.Entry<String, String> p : params.entrySet()) {
                connect.queryString(p.getKey(), p.getValue());
            }

            //Set req timeout
            connect.connectTimeout(timeout * 1000);

            //Get resp
            HttpResponse<JsonNode> response = connect.asJson();


            TimeUnit.SECONDS.sleep(1);

            return response;
        }

        public HttpResponse<JsonNode> post_request(String endpoint,
                                                   HashMap<String, String> params,
                                                   HashMap<String, String> data,
                                                   HashMap<String, String> headers,
                                                   Cookie cookies) throws InterruptedException {
            if (headers == null) {
                headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + config.get("token"));
            }
            headers.put("Content-type", "application/json");

            //Set req url
            HttpRequestWithBody connect = Unirest.post(urlFor(endpoint));

            //Set req headers
            for (Map.Entry<String, String> h : headers.entrySet()) {
                connect.header(h.getKey(), h.getValue());
            }

            //Set req params
            for (Map.Entry<String, String> p : params.entrySet()) {
                connect.queryString(p.getKey(), p.getValue());
            }

            String body = "";
            //Set body
            if (data != null && data.size() != 0) {
                body = "{";

                for (Map.Entry<String, String> d : data.entrySet()) {
                    if (d.getKey() == "members") {
                        body = "{\"members\":[{\"email\":\"" + d.getValue() + "\"}]}";
                        break;
                    }
                    body = body + "\"" + d.getKey() + "\":"
                            + "\"" + d.getValue() + "\",";
                }
                body = body.substring(0, body.length() - 1);
                body += "}";
                //connect.body(body);
            }

            //Set cookies
            if (cookies != null) {
                connect.cookie(cookies);
            }


            //Set req timeout
            connect.connectTimeout(timeout * 1000);

            //Get resp
            HttpResponse<JsonNode> response = connect.body(body).asJson();

            TimeUnit.SECONDS.sleep(1);

            return response;
        }

        public HttpResponse<JsonNode> patch_request(String endpoint,
                                                    HashMap<String, String> params,
                                                    HashMap<String, String> data,
                                                    HashMap<String, String> headers,
                                                    Cookie cookies) throws InterruptedException {
            if (headers == null) {
                headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + config.get("token"));

            }
            headers.put("Content-type", "application/json");

            //Set req url
            HttpRequestWithBody connect = Unirest.patch(urlFor(endpoint));

            //Set req headers
            for (Map.Entry<String, String> h : headers.entrySet()) {
                connect.header(h.getKey(), h.getValue());
            }

            //Set req params
            for (Map.Entry<String, String> p : params.entrySet()) {
                connect.queryString(p.getKey(), p.getValue());
            }

            String body = "";
            //Set body
            if (data != null && data.size() != 0) {
                body = "{";
                for (Map.Entry<String, String> d : data.entrySet()) {
                    body = body + "\"" + d.getKey() + "\":"
                            + "\"" + d.getValue() + "\",";
                }
                body = body.substring(0, body.length() - 1);
                body += "}";
                //connect.body(body);
            }

            if (cookies != null) {
                connect.cookie(cookies);
            }

            //Set req timeout
            connect.connectTimeout(timeout * 1000);

            //Get resp
            HttpResponse<JsonNode> response = connect.body(body).asJson();

            TimeUnit.SECONDS.sleep(1);

            return response;
        }

        public HttpResponse<JsonNode> delete_request(String endpoint,
                                                     HashMap<String, String> params,
                                                     HashMap<String, String> data,
                                                     HashMap<String, String> headers,
                                                     Cookie cookies) throws InterruptedException {
            if (headers == null) {
                headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + config.get("token"));
            }

            //Set req url
            HttpRequestWithBody connect = Unirest.delete(urlFor(endpoint));

            //Set req headers
            for (Map.Entry<String, String> h : headers.entrySet()) {
                connect.header(h.getKey(), h.getValue());
            }

            //Set req params
            if (params != null) {
                for (Map.Entry<String, String> p : params.entrySet()) {
                    connect.queryString(p.getKey(), p.getValue());
                }
            }

            String body = "";
            //Set body
            if (data != null && data.size() != 0) {
                body = "{";
                for (Map.Entry<String, String> d : data.entrySet()) {
                    body = body + "\"" + d.getKey() + "\":"
                            + "\"" + d.getValue() + "\",";
                }
                body = body.substring(0, body.length() - 1);
                body += "}";
                //connect.body(body);
            }

            if (cookies != null) {
                connect.cookie(cookies);
            }

            //Set req timeout
            connect.connectTimeout(timeout * 1000);

            //Get resp
            HttpResponse<JsonNode> response = connect.body(body).asJson();

            TimeUnit.SECONDS.sleep(1);

            return response;
        }

        public HttpResponse<JsonNode> put_request(String endpoint,
                                                  HashMap<String, String> params,
                                                  HashMap<String, String> data,
                                                  HashMap<String, String> headers,
                                                  Cookie cookies) throws InterruptedException {
            if (headers == null) {
                headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + config.get("token"));

            }
            headers.put("Content-type", "application/json");

            //Set req url
            HttpRequestWithBody connect = Unirest.put(urlFor(endpoint));

            //Set req headers
            for (Map.Entry<String, String> h : headers.entrySet()) {
                connect.header(h.getKey(), h.getValue());
            }

            //Set req params
            for (Map.Entry<String, String> p : params.entrySet()) {
                connect.queryString(p.getKey(), p.getValue());
            }

            String body = "";
            //Set body
            if (data != null && data.size() != 0) {
                body = "{";
                for (Map.Entry<String, String> d : data.entrySet()) {
                    body = body + "\"" + d.getKey() + "\":"
                            + "\"" + d.getValue() + "\",";
                }
                body = body.substring(0, body.length() - 1);
                body += "}";
                //connect.body(body);
            }

            if (cookies != null) {
                connect.cookie(cookies);
            }

            //Set req timeout
            connect.connectTimeout(timeout * 1000);

            //Get resp
            HttpResponse<JsonNode> response = connect.body(body).asJson();

            TimeUnit.SECONDS.sleep(1);

            return response;
        }
}
