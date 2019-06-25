package orangeschool.service;

import java.util.List;

import orangeschool.model.Product;

public interface ProductService {
    void save(Product _transaction);

    Product findByName(String _name);
    Product findById(Integer _id);

    List<Product> findAll();
  
    void deleteById(Integer _id);
}