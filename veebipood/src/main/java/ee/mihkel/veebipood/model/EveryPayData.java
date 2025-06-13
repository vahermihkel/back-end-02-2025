package ee.mihkel.veebipood.model;

import lombok.Data;

@Data
public class EveryPayData {
    private String account_name;
    private String nonce;
    private String timestamp;
    private double amount;
    private String order_reference;
    private String customer_url;
    private String api_username;
}
