package ee.mihkel.veebipood.model;

import lombok.Data;

@Data
public class EveryPayPaymentMethod {
        private String source;
        private String display_name;
        private String country_code;
        private String payment_link;
        private String logo_url;
        private Object applepay_available;
        private boolean googlepay_available;
        private Object wallet_display_name;
        private boolean available;
}
