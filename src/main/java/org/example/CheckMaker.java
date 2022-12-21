package org.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class CheckMaker {

    Map<Integer, Purchase> products = Map.ofEntries(
            entry(1, new Purchase("Cheese", 2.2)),
            entry(2, new Purchase("Bread", 0.5)),
            entry(3, new Purchase("Soap", 1.6)),
            entry(4, new Purchase("Water", 1.2)),
            entry(5, new Purchase("Coca-Cola", 1.5)),
            entry(6, new Purchase("Caviar", 35.3)),
            entry(7, new Purchase("M&M", 1.7)),
            entry(8, new PurchaseWithDiscount("Beer", 2.0)),
            entry(9, new PurchaseWithDiscount("Honey", 8.0)),
            entry(10, new PurchaseWithDiscount("Jam", 4.9))
    );

    Map<Integer, DiscountCard> discountCard = Map.ofEntries(
            entry(1, new DiscountCard("Card1", 0.95)),
            entry(2, new DiscountCard("Card2", 0.9)),
            entry(3, new DiscountCard("Card3", 0.85)),
            entry(4, new DiscountCard("Card4", 0.8)),
            entry(5, new DiscountCard("Card5", 0.75))
    );

    public CheckMaker(Map<Integer, Integer> AmountProd) {

        MakeHead();

        int amountOfDiscPurchases = 0;
        double discount = 0;
        double cost = 0;
        double def;
        double dis;

        try {

            for (Map.Entry<Integer, Integer> map : AmountProd.entrySet()) {
                if (map.getKey() > 7) amountOfDiscPurchases += map.getValue();
            }

            for (Map.Entry<Integer, Integer> map : AmountProd.entrySet()) {

                products.get(map.getKey()).setAmount(map.getValue());
                cost += products.get(map.getKey()).getPrice() * products.get(map.getKey()).getAmount();

                    if (amountOfDiscPurchases >= 5) {
                        def = products.get(map.getKey()).getPrice();
                        dis = products.get(map.getKey()).getPrice() * 0.9;
                        products.get(map.getKey()).setPrice(dis);
                        discount += (def - dis) * map.getValue();
                    }

                System.out.println(products.get(map.getKey()));
            }
        } catch (Exception e) {
            System.err.println("SHOP HAS NOT SOME PRODUCTS");
        }

        MakeEnd(cost, discount, 0);
    }

    public CheckMaker(Map<Integer, Integer> AmountProd, String discountCard) {

        MakeHead();

        int amountOfDiscPurchases = 0;
        double discount = 0;
        double cost = 0;
        double def;
        double dis;
        double cardDiscount = 1.0;

        //checking name of discount card
        for (Map.Entry<Integer, DiscountCard> map : this.discountCard.entrySet()) {
            if (Objects.equals(map.getValue().getName(), discountCard)) {
                cardDiscount = map.getValue().getDiscount();
            }
        }

        //checking purchases
        try {
            for (Map.Entry<Integer, Integer> map : AmountProd.entrySet()) {
                //find purchases with discount
                if (map.getKey() > 7) amountOfDiscPurchases += map.getValue();
            }

            for (Map.Entry<Integer, Integer> map : AmountProd.entrySet()) {

                //enter amount of products
                products.get(map.getKey()).setAmount(map.getValue());
                //make default cost without discounts
                cost += products.get(map.getKey()).getPrice() * products.get(map.getKey()).getAmount();

                //make discount
                if (map.getKey()>7 && amountOfDiscPurchases >= 5) {
                    def = products.get(map.getKey()).getPrice();
                    dis = products.get(map.getKey()).getPrice() * 0.9 * cardDiscount;
                    products.get(map.getKey()).setPrice(dis);
                    discount += (def - dis) * map.getValue();
                }
                else {
                    def = products.get(map.getKey()).getPrice();
                    dis = products.get(map.getKey()).getPrice() * cardDiscount;
                    discount += (def - dis) * map.getValue();
                }

                System.out.println(products.get(map.getKey()));
            }
        } catch (Exception e) {
            System.err.println("SHOP HAS NOT SOME PRODUCTS");
        }

        MakeEnd(cost, discount, (1.0 - cardDiscount) * 100);
    }

    //create head of checklist
    private void MakeHead() {

        final int NumberOfCashier = 3;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        System.out.println("              CASH RECEIPT");
        System.out.println("          Supermarket BestShop");
        System.out.printf("CASHIER: №%d    Time: %s\n", NumberOfCashier, dateFormat.format(cal.getTime()));
        System.out.println("----------------------------------------");
        System.out.println(" Product       Price     Amount   Total");
    }

    //create end of checklist
    private void MakeEnd(double cost, double discount, double cardDisc) {

        System.out.println("----------------------------------------");
        System.out.printf(" Card's discount:                 %.2f%%\n", cardDisc);
        System.out.printf(" Cost:                            $%.2f\n", cost);
        System.out.printf(" DISCOUNT:                        $%.2f\n\n", discount);
        System.out.printf(" TOTAL cost:                      $%.2f\n\n", cost - discount);
    }
}