package app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by ka on 28/02/16.
 */
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        //do logging
        System.out.print(">>["+request.getMethod()+"]"+request.getURI());
        if (body!=null && body.length>0) {
            System.out.println(new String(body));
        } else {
            System.out.println();
        }

        ClientHttpResponse response = execution.execute(request, body);

        String outBody = new BufferedReader(new InputStreamReader(response.getBody())).lines().collect(Collectors.joining("\n"));
        System.out.println("<<[");
        System.out.println(outBody);
        return response;
    }
}
