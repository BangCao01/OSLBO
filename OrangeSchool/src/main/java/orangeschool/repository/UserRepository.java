package orangeschool.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import orangeschool.model.Customer;


public interface UserRepository extends PagingAndSortingRepository<Customer, Integer> {
	Customer findByUsername(String _username);
	Customer findByCustomerID(Integer _id);
	Customer findByPhonenumber(String _phonenumber);
}