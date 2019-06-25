package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import orangeschool.repository.FlashcardRepository;
import orangeschool.model.Flashcard;

@Service
public class FlashcardServiceImpl implements FlashcardService {
    @Autowired
    private FlashcardRepository flashcardRepository;
    
    @Override
    public void save(Flashcard _flashcard) {
        
        
    	flashcardRepository.save(_flashcard);
    }

//    @Override
//    public Flashcard findByWordID(Integer _wordID) {
//    	Flashcard flashcard =  flashcardRepository.findByWordID(_wordID);
//        return flashcard;
//    }
    
    @Override
    public Flashcard findWithId(Integer _id) {
    	Flashcard flashcard = flashcardRepository.findByWordID(_id);
        return flashcard;
    }
    
    @Override
    public List<Flashcard> findAll() {
        return flashcardRepository.findAll();
    }

    @Override
    public void deleteById(Integer _id)
    {
    	flashcardRepository.deleteById(_id);
    }

    
    
}