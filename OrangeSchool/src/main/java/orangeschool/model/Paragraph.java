package orangeschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Paragraph")
public class Paragraph extends AbstractModel{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer paragraphID;

    //private Integer storyID;
    
    //private Integer textContentID;
    //private Integer imageID;
    
    private Integer status;
    @Column(name="page_order")
    private Integer pageOrder;

    public Integer getId() {
		return paragraphID;
	}

	public void setId(Integer id) {
		this.paragraphID = id;
	}
	
	public Paragraph() {
		 
    }
	
	
	
	public Integer getStoryID() {
		return this.story.getId();
	}
	
	public Story getStory()
	{
		return this.story;
	}

	public void setStory(Story _story) {
		this.story = _story;
	}
    
	public ImageContent getImage() {
		return this.image;
	}
	
    public String getImageUrl()
    {
    	return this.image.getUrl();
    }
	
	public void setImage(ImageContent _image) {
		this.image = _image;
	}
	
	public TextContent getTextContent()
	{
		return this.content;
	}
	
	public String getText()
	{
		return this.content.getContent();
	}
	
	public void setTextContent(TextContent _content)
	{
		this.content = _content;
	}
	
	
	public Integer getPageOrder()
	{
		return this.pageOrder;
	}
	
	public void setPageOrder(Integer _order)
	{
		this.pageOrder = _order;
	}
	
	public Integer getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Integer _status)
	{
		this.status = _status;
	}
	
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="text_contentID")
    private TextContent content;
	
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="imageID")
    private ImageContent image;
	
	@ManyToOne
	@JoinColumn(name="storyID", nullable=false)
	protected Story story;
	
}
