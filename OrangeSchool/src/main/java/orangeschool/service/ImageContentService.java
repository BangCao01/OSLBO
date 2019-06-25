package orangeschool.service;

import java.util.List;

import orangeschool.model.ImageContent;
import orangeschool.model.Topic;

public interface ImageContentService {
    void save(ImageContent _image);

    ImageContent findByName(String _name);
    List<ImageContent> findAll();
    void deleteById(Integer _id);
    ImageContent findById(Integer _id);
    
}