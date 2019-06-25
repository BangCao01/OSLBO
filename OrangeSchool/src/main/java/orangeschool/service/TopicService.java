package orangeschool.service;

import java.util.List;

import org.springframework.data.domain.Page;

import orangeschool.model.Category;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;

public interface TopicService {
    void save(Topic _topic);

    Topic findByName(String _name);
    Topic findById(Integer _id);
    //List<Topic> findAll();
    Page<Topic> findAll(Integer _start, Integer _limit);
    //Page<Topic> findAll(Integer _start, Integer _limit, Integer _type);
    void deleteById(Integer _id);
    List<Topic> findByType(Integer _type);
    List<Topic> findByTypeParent(Integer _type, Topic _parent);
    List<Topic> findByTypeCategoryID(Integer _type, Integer _categoryID);
    List<Topic> findByTypeCategory(Integer _type, Category _category);
    List<Topic> findByTypeCategoryParent(Integer _type, Category _category, Topic _parent);
    
}