package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.CategoryRepository;
import orangeschool.repository.MathSubjectRepository;
import orangeschool.model.Category;
import orangeschool.repository.TopicRepository;
import orangeschool.model.MathSubject;
import orangeschool.model.Topic;

@Service
public class MathSubjectServiceImpl implements MathSubjectService {
    @Autowired
    private MathSubjectRepository mathSubjectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TopicRepository topicRepository;
    @Override
    public void save(MathSubject _subject) {
        
        
    	mathSubjectRepository.save(_subject);
    }

    @Override
    public MathSubject findBySubject(String _subject) {
    	MathSubject content =  mathSubjectRepository.findBySubject(_subject);
        return content;
    }
    
	@Override
    
    public List<MathSubject> findByTopic(Topic _topic)
    {
    	return this.mathSubjectRepository.findByTopic(_topic);
    }
    @Override
    
    public List<MathSubject> findByTopicID(Integer _topicID)
    {
    	Topic topic = this.topicRepository.findByTopicID(_topicID);
    	return this.findByTopic(topic);
    }
    
    @Override
    public List<MathSubject> findByCategory(Category _category)
    {
    	return this.mathSubjectRepository.findByCategory(_category);
    }
    
    @Override
    public List<MathSubject> findByCategoryID(Integer _categoryID)
    {
    	Category category = this.categoryRepository.findByCategoryID(_categoryID);
    	
    	return this.findByCategory(category);
    }
    
    @Override
    public List<MathSubject> findByStatusAndCategory(Integer _status, Category _category)
    {
    	return this.mathSubjectRepository.findByStatusAndCategory(_status, _category);
    }
    
    @Override
    public List<MathSubject> findByStatusAndCategoryID(Integer _status, Integer _categoryID)
    {
    	Category category = this.categoryRepository.findByCategoryID(_categoryID);
    	return this.findByStatusAndCategory(_status, category);
    }
    
    @Override
    public List<MathSubject> findAll() {
        return mathSubjectRepository.findAll();
    }
    
    @Override
    public void deleteById(Integer _id) {
    	mathSubjectRepository.deleteById(_id);
    }
    @Override
    public MathSubject findById(Integer _id) {
    	
    	return mathSubjectRepository.findByMathID(_id);
    }

}