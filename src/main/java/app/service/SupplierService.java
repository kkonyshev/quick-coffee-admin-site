package app.service;

import app.config.Config;
import app.model.Supplier;
import app.model.api.SupplierAddRes;
import app.model.api.SupplierGetRes;
import app.model.api.SupplierListRes;
import app.model.api.SupplierUpdateRes;
import app.util.EntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@SuppressWarnings("unused")
public class SupplierService {

    private final String path = "/supplier";

    @Autowired
    private Config config;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EntityBuilder entityBuilder;

    public SupplierGetRes read(Long id) {
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        String uri = config.getUrl() + path + "/" + id;
        ResponseEntity<SupplierGetRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierGetRes.class);
        return quote.getBody();
    }

    public SupplierGetRes delete(Long id) {
        String uri = config.getUrl() + path + "/" + id;
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        ResponseEntity<SupplierGetRes> quote = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, SupplierGetRes.class);
        return quote.getBody();
    }
    
    public SupplierAddRes create(Supplier supplier) {
        String uri = config.getUrl() + path;
        HttpEntity<Supplier> requestEntity = entityBuilder.bodyEntity(supplier);
        ResponseEntity<SupplierAddRes> quote = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, SupplierAddRes.class);
        return quote.getBody();
    }

    public SupplierUpdateRes update(Supplier supplier) {
        String uri = config.getUrl() + path + "/" + supplier.getId();
        HttpEntity<Supplier> requestEntity = entityBuilder.bodyEntity(supplier);
        ResponseEntity<SupplierUpdateRes> quote = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, SupplierUpdateRes.class);
        return quote.getBody();
    }

    public SupplierListRes getList() {
        String uri = config.getUrl() + path;
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        ResponseEntity<SupplierListRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierListRes.class);
        return quote.getBody();
    }

    public List<Supplier> getSuppliers() {
        return getList().getSuppliers();
    }
}
