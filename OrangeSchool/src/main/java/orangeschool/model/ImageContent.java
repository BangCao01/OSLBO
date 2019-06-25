package orangeschool.model;


import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Imagecontent")
public class ImageContent extends AbstractModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer imageID;

    private String uri;

    private String url;
    
    private String name;
    
    private String description;
    
   
	public Integer getId() {
		return imageID;
	}

	public void setId(Integer id) {
		this.imageID = id;
	}
	
	public ImageContent() {
		 
    }
	public ImageContent(String _uri) {
        this.uri = _uri;
    }
    public ImageContent(String _uri, String _name) {
        this.uri = _uri;
        this.name = _name;
    }
    
    public ImageContent(String _uri, String _name, String _description) {
        this.uri = _uri;
        this.name = _name;
        this.description = _description;
    }
    
    public void setUri(String _uri)
    {
    	this.uri = _uri;
    }
    
    public String getUri()
    {
    	return this.uri;
    }
    
    public void setName(String _name)
    {
    	this.name = _name;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public void setDescription(String _description)
    {
    	this.description = _description;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
    public void setUrl(String _url)
    {
    	this.url = _url;
    }
    
    public String getUrl()
    {
    	return this.url;
    }
    
    public String getSoundUrl()
    {
    	return (this.sound != null )?this.sound.getUrl() :"";
    }
    
    public SoundContent getSound()
    {
    	return this.sound;
    }
    
    
    public void setSound(SoundContent _sound)
    {
    	this.sound = _sound;
    }
    
    public String getSoundName()
    {
    	return (this.sound != null )?this.sound.getName() :"";
    }
    
//    @OneToOne(fetch=FetchType.LAZY, mappedBy="image")
//	private MathSubject mathSubject;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="image")
	private Story storyImage;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="thumb")
	private Story storyThumb;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="image")
	private Paragraph paragraphImage;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="soundID")
    private SoundContent sound;
    
}