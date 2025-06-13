package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.entity.*;
import ee.mihkel.veebipood.model.*;
import ee.mihkel.veebipood.repository.OrderRepository;
import ee.mihkel.veebipood.repository.PersonRepository;
import ee.mihkel.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${everypay.username}")
    private String everypayUsername;

    @Value("${everypay.password}")
    private String everypayPassword;

    @Value("${everypay.customer-url}")
    private String everypayCustomerUrl;

    @Value("${everypay.base-url}")
    private String everypayBaseUrl;


    public Order addOrder(List<OrderRow> orderRows, Person person) throws ExecutionException {
        Order order = new Order();
        order.setCreated(new Date());
        order.setOrderRows(orderRows);

//        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setStatus(PaymentStatus.INITIAL);
        order.setTotalSum(calculateSum(orderRows));

        return orderRepository.save(order);
    }

    @Autowired
    ProductCacheService productCacheService;

    private double calculateSum(List<OrderRow> orderRows) throws ExecutionException {
        double sum = 0;
        for (OrderRow o: orderRows) {
            Product product = productCacheService.getProduct(o.getProduct().getId());
            sum += product.getPrice() * o.getQuantity();
        }
        return sum;
    }

    public List<Order> getOrders() {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return orderRepository.findByPerson_Id(personId);
    }

    public List<ParcelMachine> getParcelMachines() {
//        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.omniva.ee/locations.json";
        ResponseEntity<ParcelMachine[]> response = restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class);
        return Arrays.stream(response.getBody())
                .filter(e -> e.getA0_NAME().equals("EE"))
                .toList();
    }

    public EveryPayLink makePayment(Order order) {
//        {
//            "account_name": "EUR3D1",
//                "nonce": "dasdsadasadqew312312wd",
//                "timestamp": "2025-03-20T07:34:32Z",
//                "amount": 313,
//                "order_reference": "84sda21321a6c",
//                "customer_url": "http://err.ee",
//                "api_username": "92ddcfab96e34a5f"
//        }



        EveryPayData data = new EveryPayData();
        data.setAccount_name("EUR3D1");
        data.setNonce(ZonedDateTime.now().toString() + Math.random());
        data.setTimestamp(ZonedDateTime.now().toString());
        data.setAmount(order.getTotalSum());
        data.setOrder_reference(order.getId().toString());
        data.setCustomer_url(everypayCustomerUrl);
        data.setApi_username(everypayUsername);

        String url = everypayBaseUrl + "/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(everypayUsername, everypayPassword);

        HttpEntity<EveryPayData> entity = new HttpEntity<>(data, headers);

        ResponseEntity<EveryPayResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class);

        EveryPayLink link = new EveryPayLink();
        link.setLink(response.getBody().getPayment_link());
        return link;
    }

    public PaymentStatus getPaymentStatus(String reference) {
        String username = everypayUsername;
        String url = everypayBaseUrl + "/payments/" + reference + "?api_username=" + username + "&detailed=false";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everypayUsername, everypayPassword);

        HttpEntity entity = new HttpEntity(null, headers);

        ResponseEntity<EveryPayResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, EveryPayResponse.class);

        PaymentStatus status = PaymentStatus.valueOf(response.getBody().getPayment_state().toUpperCase());

        Order order = orderRepository.findById(Long.parseLong(response.getBody().getOrder_reference())).orElseThrow();
        order.setStatus(status);
        return status;
    }
}
