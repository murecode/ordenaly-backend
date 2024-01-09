package com.app.ordenaly.model;

import com.app.ordenaly.utils.OrderStatus;
import com.app.ordenaly.utils.PaymentStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"ORDER\"")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_ID")
  private Integer id;
  @OneToOne
  @JoinColumn(name = "TICKET")
  private Ticket ticket;
  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "WAITER")
  private User waiter;
  @Column(name = "\"TABLE\"")
  private int table;
  @ElementCollection
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Item> itemlist = new ArrayList<>();
  @Column(name = "ORDER_STATUS")
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
  @Column(name = "PAYMENT_STATUS")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;
  @Column(name = "NOTES")
  private String notes = " ";

  public Order() {};

  public Order(Ticket ticket, User waiter) {
    this.ticket = ticket;
    this.waiter = waiter;
  };

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  public User getUser() {
    return waiter;
  }

  public void setUser(User waiter) {
    this.waiter = waiter;
  }

  public int getTable() {
    return table;
  }

  public void setTable(int table) {
    this.table = table;
  }

  public List<Item> getItemList() {
    return itemlist;
  }

  public void setItemList(List<Item> itemlist) {
    this.itemlist = itemlist;
  }

  public void addItem(Item item) {
    itemlist.add(item);
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}


