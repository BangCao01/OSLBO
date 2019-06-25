package orangeschool.service;

import java.util.List;

import orangeschool.model.Paragraph;

public interface ParagraphService {
    void save(Paragraph _story);

    //Paragraph findByTitle(String _title);
    Paragraph findWithId(Integer _id);
    List<Paragraph> findAll();
    List<Paragraph> findAllByStoryID(Integer _storyid);
    void deleteById(Integer _id);
}