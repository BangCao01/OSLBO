package orangeschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Englishtest")
public class Englishtest extends AbstractModel{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer englishtestID;

    //private Integer subjectID;

    //private Integer descriptionID;
    
    //private Integer imageID;
    //private Integer categoryID;
    
    private Integer status;
    
    
    public Integer getId() {
		return englishtestID;
	}

	public void setId(Integer id) {
		this.englishtestID = id;
	}
	
	public Englishtest() {
		 
    }
	
	public Integer getContentID() {
		return this.content.getId();
	}

	public void setContent(TextContent _content) {
		this.content = _content;
	}
	
	public String getContent()
	{
		return this.content.getContent();
	}
    
	public Integer getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Integer _status)
	{
		this.status = _status;
	}
	
	
	public Integer getTopicID()
	{
		return this.topic.getId();
	}
	
	
	public Category getCategory()
	{
		return this.category;
	}
	
	public void setCategory(Category _category)
	{
		this.category = _category;
	}
	
	public Integer getCategoryID()
	{
		return this.category.getId();
	}
	
	public void setTopic(Topic _topic)
	{
		this.topic = _topic;
	}
	
	public Topic getTopic()
	{
		return this.topic;
	}
	
	public String getTopicName()
	{
		return this.topic.getName();
	}
	
	
	public String getCategoryName()
	{
		return this.topic.getCategoryName();
	}
	
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contentID")
    private TextContent content;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="topicID")
    private Topic topic;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categoryID")
    private Category category;
	
}
