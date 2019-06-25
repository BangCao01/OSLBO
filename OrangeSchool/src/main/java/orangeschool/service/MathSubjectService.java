package orangeschool.service;

import java.util.List;

import orangeschool.model.Category;
import orangeschool.model.MathSubject;
import orangeschool.model.Topic;

public interface MathSubjectService {
    void save(MathSubject _subject);

    MathSubject findBySubject(String _subject);
    List<MathSubject> findAll();
    List<MathSubject> findByTopic(Topic _topic);
    List<MathSubject> findByTopicID(Integer _topicID);
    List<MathSubject> findByCategory(Category _category);
    List<MathSubject> findByCategoryID(Integer _categoryID);
    List<MathSubject> findByStatusAndCategory(Integer _status, Category _category);
    List<MathSubject> findByStatusAndCategoryID(Integer _status, Integer _categoryID);
   
    void deleteById(Integer _id);
    MathSubject findById(Integer _id);
}