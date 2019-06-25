package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import orangeschool.repository.ResultRepository;
import orangeschool.repository.UserRepository;
import orangeschool.model.Customer;
import orangeschool.model.Result;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void save(Result _history) {
    	resultRepository.save(_history);
    }

    @Override
    public Result findByResultID(Integer  _id) {
    	Result ret =  resultRepository.findByResultID(_id);
        return ret;
    }
    
    @Override
    public List<Result> findByCustomerID(Integer _id) {
    	Customer customer = userRepository.findByCustomerID(_id);
    	return resultRepository.findByCustomer(customer);
    }
    
    @Override
    public Result findByCustomerAndSubjectAndType(
    		Integer _customerID,
    		Integer _subjectID,
    		Integer _type
    		) {
    	Customer customer = userRepository.findByCustomerID(_customerID);
    	
    	return resultRepository.findByCustomerAndSubjectAndType(
    			customer, _subjectID, _type);
        
    }
    
    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    
    @Override
    public void deleteById(Integer _id)
    {
    	this.resultRepository.deleteById(_id);
    }
    

}