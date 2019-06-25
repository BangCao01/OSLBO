package orangeschool.model;


import java.util.List;
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
@Table(name = "text_content")
public class TextContent extends AbstractModel{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer textID;

    private String content;

    //private Integer soundID;
    
    private Integer status;
    
   
    
	public Integer getId() {
		return textID;
	}

	public void setId(Integer id) {
		this.textID = id;
	}
	
	public TextContent() {
		 
    }
	public TextContent(String _content) {
        this.content = _content;
        this.status = 1;
    }
//    public TextContent(String _content, Integer _soundID) {
//        this.content = _content;
//        this.soundID = _soundID;
//        this.status = 1;
//    }
//   
//    public TextContent(String _content, Integer _soundID, Integer _status) {
//        this.content = _content;
//        this.soundID = _soundID;
//        this.status = _status;
//    }
   
    
    public void setContent(String _content)
    {
    	this.content = _content;
    }
    
    public String getContent()
    {
    	return this.content;
    }
    
    public void setSound(SoundContent _sound)
    {
    	this.sound = _sound;
    }
    
    public Integer getSoundID()
    {
    	if(this.sound !=null)
    		return this.sound.getId();
    	else 
    		return 0;
    }
    
    public String getSoundUrl()
    {
    	if(this.sound !=null)
    		return this.sound.getUrl();
    	else 
    		return "";
    }
    
    public String getSoundUri()
    {
    	if(this.sound !=null)
    		return this.sound.getUri();
    	else 
    		return "";
    }
    public String getSoundName()
    {
    	if(this.sound !=null)
    		return this.sound.getName();
    	else 
    		return ""; 
    }
    
    public SoundContent getSound()
    {
    	return this.sound;
    }
    
    public String getSoundDescription()
    {
    	if(this.sound !=null)
    		return this.sound.getDescription();
    	else 
    		return ""; 
    }
    
    public void setSoundName(String _name)
    {
    	if(this.sound !=null)
    	this.sound.setName(_name);
    }
    
    public void setSoundDescription(String _name)
    {
    	if(this.sound !=null)
    	this.sound.setDescription(_name);
    }
    
    public void setSoundUrl(String _name)
    {
    	if(this.sound !=null)
    	this.sound.setUrl(_name);
    }
    
    
    public void setSoundUri(String _name)
    {
    	if(this.sound !=null)
    	this.sound.setUri(_name);
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="soundID")
    private SoundContent sound;
    
    
    @OneToMany(mappedBy = "subject", fetch=FetchType.LAZY)
    private Set<MathSubject> subjectOfMaths;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="description")
   	private Set<MathSubject> descriptionOfMaths;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="title")
   	private Story titleOfStory;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="description")
   	private Story descriptionOfStory;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="content")
	private Paragraph paragraphContent;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="word")
	private Flashcard flashcardWord;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="content")
	private Englishtest englishTest;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="name")
	private Set<Topic> topics;
    
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="name")
   	private Product nameOfProduct;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="description")
   	private Product descriptionOfProduct;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="content")
   	private Translator translator;
}