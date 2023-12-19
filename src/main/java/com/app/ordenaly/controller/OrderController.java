package com.app.ordenaly.controller;

import com.app.ordenaly.dto.OrderDto;
import com.app.ordenaly.dto.mapper.OrderMapper;
import com.app.ordenaly.model.Order;
import com.app.ordenaly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
  @Autowired
  OrderService orderService;
  @Autowired
  OrderMapper orderMapper;

  @PostMapping(value = "")
  public ResponseEntity<Order> newOrder(
          @RequestParam("ticketId") int ticketId,
          @RequestParam("userId") int userId,
          @RequestBody Order orderBody) {
    Order newOrder = orderService.createOrder(ticketId, userId, orderBody);
    if(orderBody != null) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
//    return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
  }

  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> addItemToOrder(
          @RequestParam(name = "orderId") int orderId,
          @RequestParam(name = "productId") int productId) {
    orderService.addItemToOrder(orderId, productId);
    return ResponseEntity.ok("Item agregado al pedido");
  }

  @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateItemQuantity(
          @RequestParam(name = "item") int itemId,
          @RequestParam(name = "quantity") int quantity) {
    orderService.updateQuantity(itemId, quantity);
    return ResponseEntity.ok("Actualizado");
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getOrder(@PathVariable Integer id) {
    OrderDto orderDto = orderService.findOrderById( id );
    if (orderDto != null) {
      return ResponseEntity.ok(orderDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("")
  public List<OrderDto> getOrders() {
    return orderService.getOrders();
  }

//  @PatchMapping("/{id}")
//  public ResponseEntity<Order> updateOrder(@PathVariable("id") int id, @RequestBody Order order ) {
//    Order orderUpdated = orderService.updateOrder(id, order);
//    return new ResponseEntity<>(orderUpdated, HttpStatus.OK);
//  }

  @DeleteMapping("/item/{id}")
  public ResponseEntity<String> removeItemFromOrder(@PathVariable("id") Integer itemId) {
    orderService.deleteItem(itemId);
    return new ResponseEntity<>("Item eliminado", HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeOrder(@PathVariable("id") Integer id) {
    orderService.deleteOrder(id);
    return new ResponseEntity<>( HttpStatus.OK );
  }

}

