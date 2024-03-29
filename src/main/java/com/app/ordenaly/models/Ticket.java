package com.app.ordenaly.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "TICKET")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TICKET_ID")
  private Integer id;
  @Column(name = "TICKET_TIME", length = 6)
  private LocalTime time;
  @OneToOne
  @JoinColumn(name = "ORDER_ASOC")
  private Order order;

  public Ticket() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

}
