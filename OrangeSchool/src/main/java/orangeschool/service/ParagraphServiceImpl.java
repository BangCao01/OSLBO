package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import orangeschool.repository.ParagraphRepository;
import orangeschool.model.Paragraph;

@Service
public class ParagraphServiceImpl implements ParagraphService {
    @Autowired
    private ParagraphRepository paragraphRepository;

    @Override
    public void save(Paragraph _story) {
        
        
    	paragraphRepository.save(_story);
    }
    @Override
    public List<Paragraph> findAllByStoryID(Integer _id)
    {
    	return this.paragraphRepository.findByStoryId(_id);
    }
    
    @Override
    public Paragraph findWithId(Integer _id) {
    	Paragraph category = paragraphRepository.findByParagraphID(_id);
        return category;
    }
    
    @Override
    public List<Paragraph> findAll() {
        return paragraphRepository.findAll();
    }

    @Override
    public void deleteById(Integer _id)
    {
    	paragraphRepository.deleteById(_id);
    }

}