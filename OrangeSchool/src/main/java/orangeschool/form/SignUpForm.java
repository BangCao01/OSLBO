package orangeschool.form;

public class SignUpForm {

	private String username;

    private String password;
    
    private String confirmPassword;
    
    private Integer permision;
    
	
    public void setUsername(String _username)
    {
    	this.username = _username;
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public void setPassword(String _password)
    {
    	this.password = _password;
    }
    
    public String getPassword()
    {
    	return this.password;
    }
    
    public void setPermision(Integer _permision)
    {
    	this.permision = _permision;
    }
    
    public Integer getPermision()
    {
    	return this.permision;
    }
    
    public void setConfirmPassword(String _confirmPassword)
    {
    	this.confirmPassword = _confirmPassword;
    }
    
    public String getConfirmPassword()
    {
    	return this.confirmPassword;
    }
    
}
