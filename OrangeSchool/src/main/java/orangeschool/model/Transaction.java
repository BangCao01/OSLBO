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
@Table(name="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer transactionID;

    //private Integer productID;

    //private Integer userID;
    
    @Column(name="trans_value")
    private String transValue;
    @Column(name="trans_value_word")
    private String transValueByWord;
    private Integer status;
    private String description;
    @Column(name="create_date")
    private String createDate;
    @Column(name="update_date")
    private String updateDate;
    @Column(name="expire_date")
    private String expireDate;
    @Column(name="bill_code")
    private String billcode;
    
   
    
	public Integer getId() {
		return transactionID;
	}

	public void setId(Integer id) {
		this.transactionID = id;
	}
	
	public Transaction() {
		 
    }
	
    
    
    public void setProduct(Product _product)
    {
    	this.product = _product;
    }
    
    public Integer getProductID()
    {
    	return (this.product == null)? 0:this.product.getId();
    }
    
    public String getProductName()
    {
    	return (this.product == null)? "":this.product.getName();
    }
    
    
    public void setCustomer(Customer _customer)
    {
    	this.customer = _customer;
    }
    
    
    public String getCustomerName()
    {
    	return (this.customer == null)? "":this.customer.getUsername();
    }
    
    public Integer getCustomerID()
    {
    	return (this.customer == null)? 0:this.customer.getId();
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setTransValue(String _transValue)
    {
    	this.transValue = _transValue;
    }
    
    public String getTransValue()
    {
    	return this.transValue;
    }
    
    public void setTransValueByWord(String _transValueByWord)
    {
    	this.transValueByWord = _transValueByWord;
    }
    
    public String getTransValueByWord()
    {
    	return this.transValueByWord;
    }
    
    public String getDescription()
    {
    	return this.description;
    }
    
    public void setDescription(String _description)
    {
    	this.description = _description;
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
    
    
    public void setExpireDate(String _expireDate)
    {
    	this.expireDate = _expireDate;
    }
    
    public String getExpireDate()
    {
    	return this.expireDate;
    }
    
    public void setBillcode(String _code)
    {
    	this.billcode = _code;
    }
    
    public String getBillcode()
    {
    	return this.billcode;
    }
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productID")
    private Product product;
    
    @ManyToOne
	@JoinColumn(name="customerID", nullable=false)
	protected Customer customer;
    
}