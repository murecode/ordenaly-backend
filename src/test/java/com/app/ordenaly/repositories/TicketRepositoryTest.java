package com.app.ordenaly.repositories;

import com.app.ordenaly.models.Ticket;
import com.app.ordenaly.services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class TicketRepositoryTest {

  @Autowired
  TicketRepository ticketRepo;

  @Test
  void testGenerateTicket() {

    Ticket newTicket = new Ticket();
    newTicket.setTimestamp(LocalTime.now());

    Ticket generate = ticketRepo.save(newTicket);

    assertTrue(generate.getId() > 0);

  }

}