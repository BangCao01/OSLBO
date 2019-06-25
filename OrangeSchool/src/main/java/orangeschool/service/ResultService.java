package orangeschool.service;

import java.util.List;

import orangeschool.model.Customer;
import orangeschool.model.Result;

public interface ResultService {
    void save(Result _result);

    Result findByResultID(Integer _id);
    List<Result> findByCustomerID(Integer _id);
    Result findByCustomerAndSubjectAndType(
    		Integer _customer,
    		Integer _subject,
    		Integer _type);
    List<Result> findAll();
    
    void deleteById(Integer _id);
}