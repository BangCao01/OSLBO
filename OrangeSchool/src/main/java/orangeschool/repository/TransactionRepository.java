package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import orangeschool.model.Customer;
import orangeschool.model.Product;
import orangeschool.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	Transaction findByBillcode(String _code);
	List<Transaction> findByCustomer(Customer _customer);
	Transaction findByTransactionID(Integer _transactionID);
	Transaction findByCustomerAndProduct(Customer _customer, Product _product);
	
	//List<Transaction> findByUserID(Integer _userID);
	
	

}
