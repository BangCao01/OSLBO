package orangeschool.service;

import java.util.List;

import orangeschool.model.Flashcard;

public interface FlashcardService {
    void save(Flashcard _story);

    //Flashcard findByWordID(Integer _wordID);
    Flashcard findWithId(Integer _id);
    List<Flashcard> findAll();
    //List<Flashcard> findAllByStoryID(Integer _storyid);
    void deleteById(Integer _id);
    
}