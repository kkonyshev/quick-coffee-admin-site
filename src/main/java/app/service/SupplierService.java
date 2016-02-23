package app.service;

import app.model.*;
import app.model.api.SupplierAddRes;
import app.model.api.SupplierGetRes;
import app.model.api.SupplierListRes;
import app.model.api.SupplierUpdateRes;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Service
public class SupplierService {

    public List<Supplier> getSuppliers() {
        return getList().getSuppliers();
    }

    public SupplierGetRes get(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders("admin","1");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity requestEntity = new HttpEntity(headers);

        String uri = "http://quick-cofee-dev.odin.pa.infobox.ru/Quick/QuickAdmin.svc/supplier/"+id;
        ResponseEntity<SupplierGetRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierGetRes.class);
        return quote.getBody();
    }

    public SupplierAddRes add(Supplier supplier) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders("admin","1");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Supplier> requestEntity = new HttpEntity(supplier, headers);

        String uri = "http://quick-cofee-dev.odin.pa.infobox.ru/Quick/QuickAdmin.svc/supplier";
        ResponseEntity<SupplierAddRes> quote = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, SupplierAddRes.class);
        return quote.getBody();
    }

    public SupplierUpdateRes update(Supplier supplier) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders("admin","1");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Supplier> requestEntity = new HttpEntity(supplier, headers);

        String uri = "http://quick-cofee-dev.odin.pa.infobox.ru/Quick/QuickAdmin.svc/supplier/"+supplier.getId();
        ResponseEntity<SupplierUpdateRes> quote = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, SupplierUpdateRes.class);
        return quote.getBody();
    }

    public SupplierListRes getList() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders("admin","1");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity requestEntity = new HttpEntity(headers);

        String uri = "http://quick-cofee-dev.odin.pa.infobox.ru/Quick/QuickAdmin.svc/supplier";
        ResponseEntity<SupplierListRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierListRes.class);
        return quote.getBody();
    }

    public HttpHeaders createHeaders(String username, String password ){
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
