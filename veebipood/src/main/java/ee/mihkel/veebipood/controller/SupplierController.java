package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.model.Supplier1;
import ee.mihkel.veebipood.model.Supplier2;
import ee.mihkel.veebipood.model.Supplier3Product;
import ee.mihkel.veebipood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("supplier1")
    public List<Supplier1> getSupplier1Products() {
        return supplierService.getSupplier1Products();
    }

    @GetMapping("supplier2")
    public List<Supplier2> getSupplier2Products() {
        return supplierService.getSupplier2Products();
    }

    @GetMapping("supplier3")
    public List<Supplier3Product> getSupplier3Products() {
        return supplierService.getSupplier3Products();
    }
}
