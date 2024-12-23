package ma.banouidoha.springbatch;

import org.springframework.batch.item.ItemProcessor;

import java.util.logging.Logger;

public class OrderItemProcessor implements ItemProcessor<Order, Order> {
    private final Logger LOGGER = Logger.getLogger(OrderItemProcessor.class.getName());
    @Override
    public Order process(Order order) {
        // Appliquer une remise de 10% sur le montant
        Double remise = order.amount() * 0.1;
        Double newAmount = order.amount() - remise;
        Order newOrder = new Order(order.orderId(), order.customerName(), newAmount);
        LOGGER.info("CustomerName: "+order.customerName()+"Montant initial: " + order.amount() + " Montant après remise: " + newAmount);
        return newOrder;
    }
}
