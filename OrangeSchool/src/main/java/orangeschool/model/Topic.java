package orangeschool.model;

import java.util.ArrayList;
import java.util.Iterator;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Topic")
public class Topic extends AbstractModel{
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer topicID;

    //private String name;
    private Integer type;
    
    @Column(name="topic_order")
    private Integer item_order;

    public Integer getId() {
		return this.topicID;
	}

	public void setId(Integer id) {
		this.topicID = id;
	}
	
	public Topic() {
		 
    }

	public String getName() {
		return this.name.getContent();
	}

	public void setName(TextContent _name) {
		this.name = _name;
	}
    
	public Integer getType() {
		return this.type;
	}
    public String getTypeName()
    {
    	return (this.type == 1)? "English" : "Math";
    }
	public void setType(Integer _type) {
		this.type = _type;
	}
	
	public Integer getOrder()
	{
		return this.item_order;
	}
	
	public void setOrder(Integer _order)
	{
		this.item_order = _order;
	}
	
	public void setParent(Topic _parent)
	{
		this.parent = _parent;
	}
	
	public Topic getParent()
	{
		return this.parent;
	}
	
	public Integer getParentID()
	{
		return (this.parent != null) ? this.parent.getId() : 0;
	}
	public String getParentName()
	{
		return (this.parent !=null) ? this.parent.getName(): "";
	}
	
	
	public Set<Topic> getChildrens()
	{
		return this.childrens;
	}
	
	public Integer getCategoryID() {
		return this.category.getId();
	}

	public void setCategory(Category _category) {
		this.category = _category;
	}
	
	public String getCategoryName()
	{
		return this.category.getName();
	}
	
	
	public String toJsonString()
	{
		String ret = "{";
		ret +="\"topicID\":";
		ret +=this.getId();
		ret +=",";
		
		ret +="\"parentID\":";
		ret +=this.getParentID();
		ret +=",";
		
		ret +="\"categoryID\":\"";
		ret +=this.getCategoryID();
		ret +=",";
		
		
		
		ret +="\"name\":\"";
		ret +=this.getName();
		ret +="\",";
		
		
		
		ret +="\"createDate\":\"";
		ret +=this.getCreateDate();
		ret +="\",";
		
		
		ret +="\"updateDate\":\"";
		ret +=this.getUpdateDate();
		ret +="\",";
		
		if(this.childrens.size()>0)
		{
			ret +="\"children\":[";
			for (Topic one : childrens) {
				
				ret +=one.toJsonString();
				ret +=",";
			}
			ret +="],";
		}
		
		
		ret +="}";
		
		
		
		return ret;
	}
	
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Topic> childrens;
	@ManyToOne
	@JoinColumn(name="parentID", nullable=true)
	protected Topic parent;
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private Set<MathSubject> mathSubjects;
	
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private Set<Englishtest> englishTests;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categoryID")
    private Category category;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contentID")
    private TextContent name;
}
