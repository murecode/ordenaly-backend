package com.app.ordenaly.service;

import com.app.ordenaly.model.Ticket;
import com.app.ordenaly.infra.repository.TicketRepository;
import com.app.ordenaly.model.dtos.ticket.TicketCreateData;
import com.app.ordenaly.model.dtos.ticket.TicketData;
import com.app.ordenaly.model.utils.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepo;

  public Page<TicketData> findAllTickets(Pageable pageable) {
    return ticketRepo.findAll(pageable).map(TicketData::new);
  }

  public TicketData createTicket(TicketCreateData ticketData) {

    Ticket ticket = new Ticket();
    ticket.setCreatedAt(LocalTime.now());
    ticket.setNumberOfPeople(ticketData.getNumberOfPeople());
    ticket.setStatus(TicketStatus.WAITING);

    Ticket t = ticketRepo.save(ticket);

    return new TicketData(
            t.getId(),
            t.getCreatedAt(),
            t.getNumberOfPeople(),
            t.getStatus()
    );
  }

  public Ticket getTicketById(int id) {
    return ticketRepo.findById(id).get();
  }

}
