package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import orangeschool.repository.CategoryRepository;
import orangeschool.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void save(Category _category) {
        
        
    	categoryRepository.save(_category);
    }

    @Override
    public Category findByName(String _name) {
    	Category category =  categoryRepository.findByName(_name);
        return category;
    }
    
    @Override
    public Category findById(Integer _id) {
    	Category category = categoryRepository.findByCategoryID(_id);
        return category;
    }
    
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findWithOrderInRange(Integer _min, Integer _max)
    {
    	List<Category> ret = new ArrayList<Category>();
    	List<Category> categories = this.findAll();
    	for(int i =0; i < categories.size();i++)
    	{
    		Category c = categories.get(i);
    		if(c.getOrder() >= _min && c.getOrder()<= _max)
    			ret.add(c);
    	}
    	return ret;
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.categoryRepository.deleteById(_id);
    }
    
    

}