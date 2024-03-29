package com.app.ordenaly.services;

import com.app.ordenaly.dto.mapper.ItemMapper;
import com.app.ordenaly.models.Item;
import com.app.ordenaly.models.Product;
import com.app.ordenaly.repositories.ItemRepository;
import com.app.ordenaly.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
  @Autowired
  ItemRepository itemRepository;
  @Autowired
  ItemMapper itemMapper;
  @Autowired
  ProductRepository productRepository;


  public Item getItemById(int id) {
    return itemRepository.findById(id).orElse(null);
  }

  public Item generateItem(int productId) {
    Product product = productRepository.findById(productId).get();
      Item item = new Item();
      item.setProduct(product);
      item.setQuantity(item.getQuantity());
      return itemRepository.save(item);
  }

  public void updateQuantity( int itemId, Item itemBody) {
    Item item = itemRepository.findById(itemId).get();
    if ( item != null ) {
      item.setQuantity(itemBody.getQuantity());
      itemRepository.save(item);
    }
  }

  public void deleteItem(int itemId) {
    Item item = itemRepository.findById(itemId).get();
    itemRepository.deleteById(item.getId());
  }

}

// .get() de la clase Optional devuelve el valor si esta presente sino arrojará una excepción NoSuchElementException.
