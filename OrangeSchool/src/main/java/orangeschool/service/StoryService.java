package orangeschool.service;

import java.util.List;

import orangeschool.model.Story;

public interface StoryService {
    void save(Story _story);

    Story findByTitle(String _title);
    Story findWithId(Integer _id);
    List<Story> findAll();
    void deleteById(Integer _id);
}