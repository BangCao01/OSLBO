package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import orangeschool.model.Customer;
import orangeschool.model.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {
	
	List<Result> findByCustomer(Customer _customer);
	Result findByResultID(Integer _id);
	Result findByCustomerAndSubjectAndType(
			Customer _customer, 
			Integer _subjectID, 
			Integer _type);
	
	}
