package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import orangeschool.repository.ProductRepository;
import orangeschool.repository.TransactionRepository;
import orangeschool.repository.UserRepository;
import orangeschool.model.Customer;
import orangeschool.model.Product;
import orangeschool.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void save(Transaction _transaction) {
        
        
    	transactionRepository.save(_transaction);
    }

    @Override
    public Transaction findByBillcode(String _code) {
    	Transaction category =  transactionRepository.findByBillcode(_code);
        return category;
    }
    
    @Override
    public List<Transaction> findByCustomerID(Integer _id) {
    	Customer customer = userRepository.findByCustomerID(_id);
    	List<Transaction> transactions = transactionRepository.findByCustomer(customer);
        return transactions;
    }
    
    @Override
    public Transaction findByTransactionID(Integer _id) {
    	Transaction category = transactionRepository.findByTransactionID(_id);
        return category;
    }
    
    @Override
    public Transaction findByCustomerAndProduct(Customer _customer, Product _product)
    {
    	return this.transactionRepository.findByCustomerAndProduct(_customer, _product);
    }
    
    
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.transactionRepository.deleteById(_id);
    }
    
    

}