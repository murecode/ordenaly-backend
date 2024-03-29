package com.app.ordenaly.repositories;

import com.app.ordenaly.models.User;
import com.app.ordenaly.models.*;
import com.app.ordenaly.utils.OrderStatus;
import com.app.ordenaly.utils.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //(*)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class OrderRepositoryTest {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void testGenerateOrder() {
    Ticket ticket = entityManager.find(Ticket.class, 2);
    User waiter = entityManager.find(User.class, 45);

    Order order = new Order();
    order.setTicket(ticket);
    order.setUser(waiter);
    order.setTable(62);
    order.setOrderStatus(OrderStatus.PENDIENTE);
    order.setPaymentStatus(PaymentStatus.PENDIENTE);
    order.setNotes("Agregar nota");

    Order saveOrder = orderRepository.save(order);

    //Se asocia el id de la orden con el ticket
    ticket.setOrder(order);

    assertTrue(saveOrder.getId() > 0);
    assertTrue(waiter.getId().equals( 4 ));
  }

  @Test
  void testUpdateOrderStatus() {
    Order order = entityManager.find(Order.class, 2);

    order.setOrderStatus(OrderStatus.PENDIENTE);
    order.setPaymentStatus(PaymentStatus.PENDIENTE);

    orderRepository.save(order);

    assertTrue( order.getOrderStatus() == OrderStatus.PENDIENTE );
    assertTrue( order.getPaymentStatus() == PaymentStatus.PENDIENTE );
  }

  @Test
  void testDeleteOrder() {
    Order order = entityManager.find(Order.class, 29);
    assertNotNull( order );

    orderRepository.deleteById(order.getId());
  }

}

//(*) Configurar y personalizar las pruebas unitarias centradas en la capa de acceso a datos