package ee.mihkel.veebipood.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3 {
    private ArrayList<Supplier3Product> products;
    private int total;
    private int skip;
    private int limit;
}
