package orangeschool.form;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ParagraphForm extends FileForm {

    private String content;
    
    private Integer pageOrder;
    private Integer status;
    
	public ParagraphForm() {
		 
    }
	
    public void setContent(String _content)
    {
    	this.content = _content;
    }
    
    public String getContent()
    {
    	return this.content;
    }
    
    public void setPageOrder(Integer _order)
    {
    	this.pageOrder = _order;
    }
    
    public Integer getPageOrder()
    {
    	return this.pageOrder;
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