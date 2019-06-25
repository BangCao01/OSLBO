package orangeschool.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class AbstractModel implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Column(name="create_date")
	protected String createDate;
	@Column(name="update_date")
	protected String updateDate;
 
	public Admin getAuthor() {
		return this.author;
	}

	public String getAuthorName()
	{
		if(this.author != null)
		return this.author.getUsername();
		return "";
	}
	
	public void setAuthor(Admin _author) {
		this.author = _author;
	}
	 @ManyToOne
	 @JoinColumn(name="authorID", nullable=false)
	 protected Admin author;
	
	public void setCreateDate(String _createDate)
    {
    	this.createDate = _createDate;
    }
    
    public String getCreateDate()
    {
    	return this.createDate;
    }
    
    public void setUpdateDate(String _updateDate)
    {
    	this.updateDate = _updateDate;
    }
    
    public String getUpdateDate()
    {
    	return this.updateDate;
    }
}
