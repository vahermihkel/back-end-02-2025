package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.model.Supplier1;
import ee.mihkel.veebipood.model.Supplier2;
import ee.mihkel.veebipood.model.Supplier3;
import ee.mihkel.veebipood.model.Supplier3Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    RestTemplate restTemplate;

    public List<Supplier1> getSupplier1Products() {

//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://fakestoreapi.com/products";
        ResponseEntity<Supplier1[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1[].class);
        return Arrays.stream(response.getBody())
                .filter(e -> e.getRating().getRate() > 2.5)
                .peek(e -> e.setRetailPrice(e.getPrice() * 1.2))
                .toList();

//        List<Supplier1> finalList = new ArrayList<>();
//        for (Supplier1 s: response.getBody()) {
//            if (s.getRating().getRate() > 2.5) {
//                s.setRetailPrice(s.getPrice() * 1.2);
//                finalList.add(s);
//            }
//        }
//        return finalList;
    }

    public List<Supplier2> getSupplier2Products() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.escuelajs.co/api/v1/products";
        ResponseEntity<Supplier2[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2[].class);
        return Arrays.stream(response.getBody())
                .filter(e -> e.getCreationAt().equals(e.getCategory().getCreationAt()))
                .peek(e -> e.setRetailPrice(e.getPrice() * 1.2))
                .toList();
    }

    public List<Supplier3Product> getSupplier3Products() {
//        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate); // UUS MÄLUKOHT
        String url = "https://dummyjson.com/products";
        ResponseEntity<Supplier3> response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier3.class);
        return response.getBody().getProducts().stream()
                .filter(e -> e.getRating() > 3)
                .peek(e -> e.setRetailPrice(e.getPrice() * 1.2))
                .toList();
    }
}
