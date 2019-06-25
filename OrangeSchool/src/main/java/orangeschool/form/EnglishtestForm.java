package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class EnglishtestForm extends FileForm {

    private String content;
    private Integer categoryID;
    private Integer questionType;
    private Integer topicID;
    
    private String answers;
    private String correctAnswer;
    private String soundName;
    private String imagename;
	private Integer status;
	public EnglishtestForm() {
		 
    }
	
    public void setContent(String _content)
    {
    	this.content = _content;
    }
    
    public String getContent()
    {
    	return this.content;
    }
    
    
    public void setTopicID(Integer _topicID)
    {
    	this.topicID = _topicID;
    }
    
    public Integer getTopicID()
    {
    	return this.topicID;
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
    
    public void setQuestionType(Integer _type)
    {
    	this.questionType = _type;
    }
    public Integer getQuestionType()
    {
    	return this.questionType;
    }
    
    public String getSoundName()
    {
    	return this.soundName;
    }
    
    public void setSoundName(String _soundName)
    {
    	this.soundName = _soundName;
    }
    
    public String getImagename()
    {
    	return this.imagename;
    }
    
    public void setImagename(String _imagename)
    {
    	this.imagename = _imagename;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public Integer getCategoryID()
    {
    	return this.categoryID;
    }
    
    public void setCategoryID(Integer _categoryID)
    {
    	this.categoryID = _categoryID;
    }
    
    
}