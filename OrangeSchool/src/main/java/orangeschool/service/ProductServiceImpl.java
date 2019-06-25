package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import orangeschool.repository.ProductRepository;
import orangeschool.model.Product;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product _transaction) {
        
        
    	productRepository.save(_transaction);
    }

    @Override
    public Product findByName(String _code) {
    	Product category =  productRepository.findByName(_code);
        return category;
    }
    
    @Override
    public Product findById(Integer _id) {
    	Product category = productRepository.findByProductID(_id);
        return category;
    }
    
    
    
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.productRepository.deleteById(_id);
    }
    
    

}