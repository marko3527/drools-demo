package five.agency.droolsdemo.model;

import lombok.Data;

@Data
public class Order {

    private String cardType;
    private String name;
    private int discount;
    private int price;

}
