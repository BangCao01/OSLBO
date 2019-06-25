package orangeschool.service;

import java.util.List;

import orangeschool.model.Customer;
import orangeschool.model.Product;
import orangeschool.model.Transaction;

public interface TransactionService {
    void save(Transaction _transaction);

    Transaction findByBillcode(String _code);
    List<Transaction> findByCustomerID(Integer _id);
    Transaction findByCustomerAndProduct(Customer _customer, Product _product);
    
    Transaction findByTransactionID(Integer _id);
    List<Transaction> findAll();
    //List<Transaction> findByCreatedate(String _fromdate, String _todate);
    void deleteById(Integer _id);
}