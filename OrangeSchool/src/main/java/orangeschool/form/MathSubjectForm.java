package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class MathSubjectForm extends FileForm {

    private String subject;
    private String description;
    private Integer topicID;
    private Integer categoryID;
    private Integer status;
    
    private String answers;
    private String correctAnswer;
    private String imagename;
	
	public MathSubjectForm() {
		 
    }
	
    public void setSubject(String _subject)
    {
    	this.subject = _subject;
    }
    
    public String getSubject()
    {
    	return this.subject;
    }
    
    public void setTopicID(Integer _topicID)
    {
    	this.topicID = _topicID;
    }
    
    public Integer getTopicID()
    {
    	return this.topicID;
    }
    
    public Integer getCategoryID()
    {
    	return this.categoryID;
    }
    
    public void setCategoryID(Integer _categoryID)
    {
    	this.categoryID = _categoryID;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setCorrectAnswer(String _correctanswer)
    {
    	this.correctAnswer = _correctanswer;
    }
    
    public String getCorrectAnswer()
    {
    	return this.correctAnswer;
    }
    
    public void setAnswers(String _answers)
    {
    	this.answers = _answers;
    }
    
    
    public String getAnswers()
    {
    	return this.answers;
    }
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
    public void setImagename(String _imagename)
    {
    	this.imagename = _imagename;
    }
    
    public String getImagename()
    {
    	return this.imagename;
    }
    
    
}