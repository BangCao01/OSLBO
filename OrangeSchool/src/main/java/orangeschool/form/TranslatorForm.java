package orangeschool.form;

public class TranslatorForm {
	private Integer contentID;
    private String translatedContent;
    private String languageID;
    private Integer status;
    
    public void setTranslatedContent(String _translatedContent)
    {
    	this.translatedContent = _translatedContent;
    }
    
    public String getTranslatedContent()
    {
    	return this.translatedContent;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
    
    public void setLanguage(String _id)
    {
    	this.languageID = _id;
    }
    
    public String getLanguage()
    {
    	return this.languageID;
    }
   
   
    public void setContentID(Integer _id)
    {
    	this.contentID = _id;
    }
    
    public Integer getContentID()
    {
    	return this.contentID;
    }
}
