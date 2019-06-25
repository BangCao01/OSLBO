package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import orangeschool.repository.StoryRepository;
import orangeschool.model.Story;

@Service
public class StoryServiceImpl implements StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Override
    public void save(Story _story) {
        
        
    	storyRepository.save(_story);
    }

    @Override
    public Story findByTitle(String _name) {
    	Story category =  storyRepository.findByTitle(_name);
        return category;
    }
    
    @Override
    public Story findWithId(Integer _id) {
    	Story category = storyRepository.findWithId(_id);
        return category;
    }
    
    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }

    @Override
    public void deleteById(Integer _id)
    {
    	storyRepository.deleteById(_id);
    }

}