package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import orangeschool.repository.UserRepository;
import orangeschool.model.Customer;
import orangeschool.model.TextContent;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Customer _user) {
        _user.setPassword(bCryptPasswordEncoder.encode(_user.getPassword()));
        //_admin.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(_user);
    }

    @Override
    public Customer findById(Integer _id)
    {
    	return this.userRepository.findByCustomerID(_id);
    }
    
    @Override
    public Customer findByUsername(String _username) {
    	Customer user =  userRepository.findByUsername(_username);
        return user;
    }
    
    @Override
    public Page<Customer> findAll(Integer _page, Integer _limit)
    {
        Pageable firstPage = PageRequest.of(_page, _limit);
    	
        Page<Customer> ret = this.userRepository.findAll(firstPage);
        
        System.out.println("customer :" + ret.getNumber());
        
        return ret;
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.userRepository.deleteById(_id);
    }
    
    public boolean match(Customer _user, String _password)
    {
    	return bCryptPasswordEncoder.encode(_password) == _user.getPassword();
    }
}