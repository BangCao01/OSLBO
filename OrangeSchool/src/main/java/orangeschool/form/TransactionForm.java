package orangeschool.form;

public class TransactionForm {
	private String billcode;
    private String customerName;
    private Integer customerID;
    private Integer status;
    
    public void setBillcode(String _billcode)
    {
    	this.billcode = _billcode;
    }
    
    public String getBillcode()
    {
    	return this.billcode;
    }
    
    
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setCustomerName(String _name)
    {
    	this.customerName = _name;
    }
    
    public String getCustomerName()
    {
    	return this.customerName;
    }
   
   
    public void setCustomerID(Integer _id)
    {
    	this.customerID = _id;
    }
    
    public Integer getCustomerID()
    {
    	return this.customerID;
    }
}
