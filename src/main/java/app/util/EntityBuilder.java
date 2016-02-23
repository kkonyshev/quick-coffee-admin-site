package app.util;

import app.config.Config;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Arrays;

@Component
public class EntityBuilder {

    @Autowired
    private Config config;

    public HttpEntity simpleEntity() {
        HttpHeaders headers = createHeaders(config.getLogin(), config.getPassword());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity(headers);
    }

    public <T> HttpEntity<T> bodyEntity(T body) {
        HttpHeaders headers = createHeaders(config.getLogin(), config.getPassword());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity(body, headers);
    }

    protected HttpHeaders buildHeaders() {
        HttpHeaders headers = createHeaders(config.getLogin(), config.getPassword());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    protected HttpHeaders createHeaders(String username, String password ){
        return new HttpHeaders(){
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
    }
}
