package app.service;

import app.config.Config;
import app.model.Place;
import app.model.Supplier;
import app.model.api.*;
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

    public static final String SUPPLIER_PATH = "/supplier";
    public static final String PLACE_PATH = "/place";

    @Autowired
    private Config config;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EntityBuilder entityBuilder;

    /*
    Supplier
     */

    public List<Supplier> getSuppliers() {
        return getListSuppliers().getSuppliers();
    }

    public SupplierListRes getListSuppliers() {
        String uri = config.getUrl() + SUPPLIER_PATH;
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        ResponseEntity<SupplierListRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierListRes.class);
        return quote.getBody();
    }

    public SupplierUpdateRes update(Supplier supplier) {
        String uri = config.getUrl() + SUPPLIER_PATH + "/" + supplier.getId();
        HttpEntity<Supplier> requestEntity = entityBuilder.bodyEntity(supplier);
        ResponseEntity<SupplierUpdateRes> quote = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, SupplierUpdateRes.class);
        return quote.getBody();
    }

    public SupplierAddRes create(Supplier supplier) {
        String uri = config.getUrl() + SUPPLIER_PATH;
        HttpEntity<Supplier> requestEntity = entityBuilder.bodyEntity(supplier);
        ResponseEntity<SupplierAddRes> quote = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, SupplierAddRes.class);
        return quote.getBody();
    }

    public SupplierGetRes read(Long id) {
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        String uri = config.getUrl() + SUPPLIER_PATH + "/" + id;
        ResponseEntity<SupplierGetRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SupplierGetRes.class);
        return quote.getBody();
    }

    public SupplierGetRes delete(Long id) {
        String uri = config.getUrl() + SUPPLIER_PATH + "/" + id;
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        ResponseEntity<SupplierGetRes> quote = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, SupplierGetRes.class);
        return quote.getBody();
    }

    /*
    Places
     */

    public List<Place> getPlaces(Long supplierId) {
        return getListPlaces(supplierId).getPlaces();
    }

    public PlaceListRes getListPlaces(Long supplierId) {
        String uri = config.getUrl() + SUPPLIER_PATH + "/" + supplierId + PLACE_PATH;
        HttpEntity requestEntity = entityBuilder.simpleEntity();
        ResponseEntity<PlaceListRes> quote = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, PlaceListRes.class);
        return quote.getBody();
    }
}
