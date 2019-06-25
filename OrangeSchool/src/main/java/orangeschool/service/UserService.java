package orangeschool.service;


import java.util.List;

import org.springframework.data.domain.Page;

import orangeschool.model.Customer;

public interface UserService {
    void save(Customer _user);
    Customer findById(Integer _id);
    Customer findByUsername(String _username);
    Page<Customer> findAll(Integer _page, Integer _limit);
    void deleteById(Integer _id);
    boolean match(Customer _user, String _password);
    
}