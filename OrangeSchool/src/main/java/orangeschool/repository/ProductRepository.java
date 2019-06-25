package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import orangeschool.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Product findByName(String _code);
	Product findByProductID(Integer _id);
	

}
