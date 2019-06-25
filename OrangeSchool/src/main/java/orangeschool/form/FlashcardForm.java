package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class FlashcardForm extends FileForm {

    private String word;
    private Integer status;
    
	public FlashcardForm() {
		 
    }
	
    public void setWord(String _word)
    {
    	this.word = _word;
    }
    
    public String getWord()
    {
    	return this.word;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    
}