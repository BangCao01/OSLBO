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

@Entity // This tells Hibernate to make a table out of this class
public class Category {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer categoryID;

    private String name;
    @Column(name="skill_count")
    private Integer skillcount;

    private String description;
    @Column(name="item_order")
    private Integer item_order;

    public Integer getId() {
		return categoryID;
	}

	public void setId(Integer id) {
		this.categoryID = id;
	}
	
	
	
	public Category() {
		 
    }

	public String getName() {
		return this.name;
	}

	public void setName(String _name) {
		this.name = _name;
	}
    
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String _description) {
		this.description = _description;
	}
	
	public Integer getOrder()
	{
		return this.item_order;
	}
	
	public void setOrder(Integer _order)
	{
		this.item_order = _order;
	}
	

	
	public Integer getSkillcount()
	{
		return this.skillcount;
	}
	
	public void setSkillcount(Integer _count)
	{
		this.skillcount = _count;
	}
	

	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Topic> topics;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<MathSubject> mathsubjects;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Englishtest> englishtests;
}
