package orangeschool.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Story")
public class Story extends AbstractModel{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer id;

    private Integer status;

    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Story() {
		 
    }
	
	
	public Integer getCategoryID() {
		return this.category.getId();
	}

	public String getCategoryName() {
		return this.category.getName();
	}
	public Category getCategory()
	{
		return this.category;
	}
	public void setCategory(Category _category) {
		this.category = _category;
	}
	
	
	
	public String getTitle() {
		return this.title.getContent();
	}
	
	public TextContent getTitleContent()
	{
		return this.title;
	}
	
	public void setTitle(TextContent _title)
	{
		this.title = _title;
	}

	public String getDescription() {
		return this.description.getContent();
	}
	
	public TextContent getDescriptionContent()
	{
		return this.description;
	}
	
	public void setDescription(TextContent _description)
	{
		this.description = _description;
	}
	
	
	public Integer getStatus()
	{
		return this.status;
	}
	
	public void setStatus(Integer _status)
	{
		this.status = _status;
	}
	
	public String getImageUrl()
	{
		return (this.image !=null)?this.image.getUrl() :"";
	}
	
	public void setImage(ImageContent _image)
	{
		this.image = _image;
	}
	
	public ImageContent getImage()
	{
		return this.image;
	}
	
	public String getThumbUrl()
	{
		return (this.thumb !=null)?this.thumb.getUrl() :"";
	}
	
	public void setThumb(ImageContent _thumb)
	{
		this.thumb = _thumb;
	}
	
	public ImageContent getThumb()
	{
		return this.thumb;
	}
	
	public Set<Paragraph> getParagraphs()
	{
		return this.paragraphs;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categoryID")
    private Category category;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="titleID")
    private TextContent title;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="descriptionID")
    private TextContent description;
	
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="imageID")
    private ImageContent image;
	

	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="thumbID")
    private ImageContent thumb;
//	
//	@OneToMany()
//    @JoinTable(name="Paragraph", joinColumns={
//    	    @JoinColumn(name="storyID", referencedColumnName="id")
//    	})
	
	@OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private Set<Paragraph> paragraphs;
}
