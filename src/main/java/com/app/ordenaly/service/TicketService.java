package com.app.ordenaly.service;

import com.app.ordenaly.dto.TicketDto;
import com.app.ordenaly.dto.mapper.TicketMapper;
import com.app.ordenaly.model.Ticket;
import com.app.ordenaly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
  @Autowired
  private TicketRepository ticketRepository;
  @Autowired
  private TicketMapper ticketMapper;


  public List<TicketDto> getAllTickets() {
    List<Ticket> tickets = ticketRepository.findAll();
    return tickets.stream()
            .map(ticketMapper::ticketToTicketDto)
            .collect(Collectors.toList());
  }

  public Ticket generateNewTicket() {
    Ticket ticket = new Ticket();
    ticket.setTime(LocalTime.now());
    return ticketRepository.save(ticket);
  }

}