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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="Result")
public class Result {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
    private Integer resultID;

	@Column(name="create_date")
	protected String createDate;
	@Column(name="update_date")
	protected String updateDate;
	
    @Column(name="subjectID")
    private Integer subject;
    private Integer score;
    private String answer;
    @Column(name="type")
    private Integer type;

    public Integer getId() {
		return resultID;
	}

	public void setId(Integer id) {
		this.resultID = id;
	}
	
	
	
	public Result() {
		 
    }

	public Integer getSubjectID() {
		return this.subject;
	}

	public void setSubjectID(Integer _subjectID) {
		this.subject = _subjectID;
	}
    
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String _answer) {
		this.answer = _answer;
	}
	
	
	public Integer getType()
	{
		return this.type;
	}
	
	public void setType(Integer _type)
	{
		this.type = _type;
	}
	
	
	public String getCustomerName()
	{
		return this.customer.getUsername();
	}
	
	public Customer getCustomer()
	{
		return this.customer;
	}
	
	public void setCustomer(Customer _customer)
	{
		this.customer = _customer;
	}
	
	public Integer getCustomerID()
	{
		return this.customer.getId();
	}
	
	public Integer getScore()
	{
		return this.score;
	}
	
	public void setScore(Integer _score)
	{
		this.score = _score;
	}
	
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
	
	@ManyToOne
	@JoinColumn(name="customerID", nullable=false)
	protected Customer customer;
	
	
}
