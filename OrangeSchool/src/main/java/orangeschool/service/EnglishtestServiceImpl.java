package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.CategoryRepository;
import orangeschool.repository.EnglishtestRepository;
import orangeschool.model.Category;
import orangeschool.model.Englishtest;

@Service
public class EnglishtestServiceImpl implements EnglishtestService {
    @Autowired
    private EnglishtestRepository englishtestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void save(Englishtest _subject) {
        
        
    	englishtestRepository.save(_subject);
    }

    @Override
    public Englishtest findByContent(String _content) {
    	Englishtest test =  englishtestRepository.findByContent(_content);
        return test;
    }
    
    @Override
    public Englishtest findWithId(Integer _id) {
    	Englishtest test =  englishtestRepository.findByEnglishtestID(_id);
        return test;
    }
    
    @Override
    public List<Englishtest> findByCategory(Category _category)
    {
    	return this.englishtestRepository.findByCategory(_category);
    }
    
    
    @Override
    public List<Englishtest> findAll() {
        return englishtestRepository.findAll();
    }

    @Override
    public List<Englishtest> findByCategoryID(Integer _categoryID)
    {
    	Category category = this.categoryRepository.findByCategoryID(_categoryID);
    	return this.findByCategory(category);
    }
    
    @Override
    public List<Englishtest> findByStatusAndCategory(Integer _status, Category _category)
    {
    	return this.englishtestRepository.findByStatusAndCategory(_status, _category);
    }
    
    @Override
    public List<Englishtest> findByStatusAndCategoryID(Integer _status, Integer _categoryID)
    {
    	Category category = this.categoryRepository.findByCategoryID(_categoryID);
    	return this.findByStatusAndCategory(_status, category);
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	englishtestRepository.deleteById(_id);
    }

}