package com.app.ordenaly.dto.mapper;

import com.app.ordenaly.dto.ProductDto;
import com.app.ordenaly.models.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

  @Mappings({
          @Mapping(source = "id", target = "product_id"),
          @Mapping(source = "productName", target = "product_name"),
          @Mapping(source = "description", target = "description"),
          @Mapping(source = "price", target = "price"),
          @Mapping(source = "inStock", target = "in_stock")
  })
  ProductDto productToProductDto(Product product);
  @InheritInverseConfiguration
  Product ProductDtoToProduct(ProductDto productDto);
  List<ProductDto> productsDto(List<Product> productList);

}
