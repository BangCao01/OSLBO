package orangeschool.service;

import java.util.List;

import orangeschool.model.ImageContent;
import orangeschool.model.SoundContent;

public interface SoundContentService {
    void save(SoundContent _image);

    SoundContent findByName(String _name);
    //SoundContent findBy(Integer _id);
    List<SoundContent> findAll();
    void deleteById(Integer _id);
    SoundContent findById(Integer _id);
    
}