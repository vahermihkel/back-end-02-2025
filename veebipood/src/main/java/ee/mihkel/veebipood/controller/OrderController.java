package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.annotations.CurrentPerson;
import ee.mihkel.veebipood.entity.*;
import ee.mihkel.veebipood.model.EveryPayLink;
import ee.mihkel.veebipood.model.ParcelMachine;
import ee.mihkel.veebipood.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.sql.ast.tree.expression.Every;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Log4j2
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("orders")
    public EveryPayLink addOrder(@RequestBody List<OrderRow> orderRows,
                                 @CurrentPerson Person person) throws ExecutionException {

        log.info("ORDERS");
        log.info(person);
        log.info(person.getEmail());
        Order order = orderService.addOrder(orderRows, person);
        return orderService.makePayment(order);
    }

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines() {
        return orderService.getParcelMachines();
    }

    @GetMapping("payment-status")
    public PaymentStatus getPaymentStatus(@RequestParam String reference) {
        return orderService.getPaymentStatus(reference);
    }
}

// ?order_reference=41231201&payment_reference=f9719a589d29f2566c8908bdfb1b6280d46db9b998a1206748d55a51cd1b65e1
// ?order_reference=41231202&payment_reference=4149f5a0d9968ea207f310fc6ac7f68ab92ece29454e067438ad19d6d76b463e