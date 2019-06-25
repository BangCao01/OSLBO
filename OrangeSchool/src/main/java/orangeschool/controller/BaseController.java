package orangeschool.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import orangeschool.model.Admin;
import orangeschool.service.AdminService;
import orangeschool.WebUtil;

public class BaseController {
	@Value("${welcome.message}")
	protected String message;

	@Value("${error.message}")
	protected String errorMessage;
	@Autowired
	protected AdminService userService;
	public static String GetTime() {
//    	TimeZone.setDefault(TimeZone.getTimeZone("CET"));
//    	Calendar calendar = Calendar.getInstance();
//    	calendar.set(2018, Calendar.AUGUST, 1, 12, 0);
//    	Date date = calendar.getTime();
		LocalDate localDate = LocalDate.now(ZoneId.of("GMT+07:00"));
		LocalTime time = LocalTime.now();
		LocalDateTime ldt = LocalDateTime.now();
		String dateandtime = ldt.toString();// localDate.toString()+":"+ time.toString();
		// System.out.print(" ttttttttttttttt:" + dateandtime);
		return dateandtime;
	}
	
	protected boolean checkForbidden(Principal _principal,  MenuCode _item)
	{
		
		Admin _admin = this.getAdminUser(_principal);
		if(!this.checkPermission(_admin, _item, null))
			return true;
		return false;
	}

	protected  boolean checkPermission(Admin _admin, MenuCode _item, Action _action)
	{
		boolean ret =false;
		if(_admin.getPermission() == 1)// admin
		{
			ret = true;
		}
		else if(_admin.getPermission() == 2)// sub-admin
		{
			ret = true;
			if(_item == MenuCode.Customer)
				ret = false;
		}
		else if(_admin.getPermission() == 3)// math-editor.
		{
			
			if(_item == MenuCode.Math || _item == MenuCode.Topic)
				ret = true;
		}
		else if(_admin.getPermission() == 4)// flashcard-editor.
		{
			
			if(_item == MenuCode.Flashcard )
				ret = true;
		}
		else if(_admin.getPermission() == 5)// story-editor.
		{
			
			if(_item == MenuCode.Story || _item == MenuCode.Topic)
				ret = true;
		}
		else if(_admin.getPermission() == 6)// ela-editor.
		{
			
			if(_item == MenuCode.Ela || _item == MenuCode.Topic)
				ret = true;
		}
		else if(_admin.getPermission() == 7)// translator-editor.
		{
			
			if(_item == MenuCode.Texts || _item == MenuCode.Translator)
				ret = true;
		}
		
		return ret;
	}
	
	protected Admin getAdminUser(Principal _principal)
	{
		User loginedUser = (User) ((Authentication) _principal).getPrincipal();
		Admin _author = this.userService.findByUsername(loginedUser.getUsername());
		return _author;
	}
	
	protected void setAccessCode(Model model, Principal _principal)
	{
		Admin _admin = getAdminUser(_principal);
		model.addAttribute("accesscodes", getAccessCode(_admin));
	}
	
	protected List<String> getAccessCode(Admin _admin)
	{
		List<String> ret = new ArrayList();
		ret.add("Home");
		
		if(checkPermission(_admin, MenuCode.AdminUser, null))
		{
			ret.add("AdminUser");
		}
		
		if(checkPermission(_admin, MenuCode.Customer, null))
		{
			ret.add("Customer");
		}
		
		if(checkPermission(_admin, MenuCode.Math, null))
		{
			ret.add("Math");
		}
		if(checkPermission(_admin, MenuCode.Ela, null))
		{
			ret.add("Ela");
		}
		if(checkPermission(_admin, MenuCode.Flashcard, null))
		{
			ret.add("Flashcard");
		}
		if(checkPermission(_admin, MenuCode.Story, null))
		{
			ret.add("Story");
		}
		
		if(checkPermission(_admin, MenuCode.Texts, null))
		{
			ret.add("Texts");
		}
		
		if(checkPermission(_admin, MenuCode.Sounds, null))
		{
			ret.add("Sounds");
		}
		if(checkPermission(_admin, MenuCode.Images, null))
		{
			ret.add("Images");
		}
		
		if(checkPermission(_admin, MenuCode.Category, null))
		{
			ret.add("Category");
		}
		
		if(checkPermission(_admin, MenuCode.Topic, null))
		{
			ret.add("Topic");
		}
		
		if(checkPermission(_admin, MenuCode.Product, null))
		{
			ret.add("Product");
		}
		if(checkPermission(_admin, MenuCode.Transaction, null))
		{
			ret.add("Transaction");
		}
		
		if(checkPermission(_admin, MenuCode.Result, null))
		{
			ret.add("Result");
		}
		
		if(checkPermission(_admin, MenuCode.Translator, null))
		{
			ret.add("Translator");
		}
		
		ret.add("Logout");
		return ret;
	}
	
	protected void updateMessageAtIndex(Model model) {
		if (message != null && !message.equalsIgnoreCase(WebUtil.deleteSuccessfull))
			message = null;
		model.addAttribute("message", message);
		message = null;
	}

	protected void updateMessageAtDetail(Model model) {

		model.addAttribute("message", message);
		message = null;
	}

	protected void updateMessageAtDelete(Model model) {

		message = WebUtil.deleteSuccessfull;
		// model.addAttribute("message", message);
	}

	protected void updateMessageAtAdd(Model model) {

		message = WebUtil.addSuccessfull;
		// model.addAttribute("message", message);
	}

	protected void updateMessageAtEdit(Model model) {

		message = WebUtil.editSuccessfull;
		// model.addAttribute("message", message);
	}
	protected void setMessageAt(Model model, MessageCode _messageCode)
	{
		if(_messageCode == MessageCode.Add)
			message = WebUtil.addSuccessfull;
		else if(_messageCode == MessageCode.Edit)
			message = WebUtil.editSuccessfull;
		else if(_messageCode == MessageCode.Delete)
			message = WebUtil.deleteSuccessfull;
		else if(_messageCode == MessageCode.Add)
			message = WebUtil.addSuccessfull;
		else if(_messageCode == MessageCode.Detail)
		{
			model.addAttribute("message", message);
			message = null;
		}
		else if(_messageCode == MessageCode.Index)
		{
			if (message != null && !message.equalsIgnoreCase(WebUtil.deleteSuccessfull))
				message = null;
			model.addAttribute("message", message);
			message = null;
		}
	}

	protected void setSelectedMenu(Model model, MenuCode _menuItem) {
    	model.addAttribute("menucode", _menuItem.toString());
	}
	
	protected void setSelectedActionMenu(Model model, ActionMenuCode _menuItem) {
    	model.addAttribute("actioncode", _menuItem.toString());
	}


	public enum MessageCode{
		Edit,Add,Delete,Index, Detail
		
	}
	public enum Action{
		List,View, Add, Edit, Delete
	}
	public enum MenuCode {
		Home,Customer,Flashcard, Story, Math,Ela, Images, Sounds, Texts, Asset,
		Category,Topic, Translator, Product,Transaction,Result, Logout, AdminUser,

	}
	public enum ActionMenuCode{
		List, Add, Pending, Approved, TopicEla,TopicMath,TopicStory
	}
	public enum AccessCode{
		Admin,SubAdmin,MathEditor,StoryEditor, ElaEditor, Translator
	}

	protected String menucode = "Home";
}
