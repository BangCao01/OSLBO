package orangeschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import orangeschool.model.Admin;
import orangeschool.model.Customer;
import orangeschool.model.TextContent;
import orangeschool.form.*;
import orangeschool.repository.AdminRepository;
import orangeschool.WebUtil;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.service.UserService;
import orangeschool.service.SecurityService;
import orangeschool.validator.AdminValidator;

@Controller    
@RequestMapping(path="/customer")
public class CustomerController extends BaseController{
	
	@Autowired
    private UserService userService;
    
    @RequestMapping(value = { "/index/{pagenumber}" }, method = RequestMethod.GET)
    public String homeIndex(
    		@PathVariable("pagenumber") int pagenumber,
    		Model model, 
    		Principal _principal) {
 
        //model.addAttribute("message", message);
    	Page<Customer> page = userService.findAll(pagenumber,100);
        model.addAttribute("customers", page);
        model.addAttribute("menucode", this.menucode);
        this.setAccessCode(model, _principal);
        return "customer/index";
    }
    
	 @RequestMapping(value = "/detail/{customerid}", method = RequestMethod.GET)
	    public String showDetailPage(
	    		@PathVariable("customerid") int customerid,
	    		Model model, Principal _principal) {
	 
	        Customer user = this.userService.findById(customerid);
	        model.addAttribute("customer", user);
	        this.setAccessCode(model, _principal);
	        return "customer/detail";
	    }
	 
	 
	 
	 @RequestMapping(value = "/edit/{customerid}", method = RequestMethod.GET)
	    public String showEditPage(
	    		@PathVariable("customerid") int customerid,
	    		Model model, Principal _principal) {
	 
		    Customer user = this.userService.findById(customerid);
		    CustomerEditForm theForm = new CustomerEditForm();
		    theForm.setUsername(user.getUsername());
		    theForm.setPassword(user.getPassword());
		    theForm.setStatus(user.getStatus());
		    
			model.addAttribute("customerEditForm", theForm);
			this.setAccessCode(model, _principal);
	        return "customer/edit";
	    }
	
	 @RequestMapping(value = "/edit/{customerid}", method = RequestMethod.POST)
	    public String showEditPost(
	    		@PathVariable("customerid") int customerid,
	    		@ModelAttribute("customerEditForm") CustomerEditForm theForm,
	    		Model model, Principal principal) {
	 
		    Customer user = this.userService.findById(customerid);
		    errorMessage="";
		    if(IsValidate(theForm))
		    {
		    	user.setStatus(theForm.getStatus());
		    	this.userService.save(user);
		    	return "redirect:/customer/detail/"+ user.getId();
		    }
			model.addAttribute("customerEditForm", theForm);
	      
	        return "customer/edit";
	    }
	 
	 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
		public String showDeletePage(
				@PathVariable("id") int id, 
				Model model,
				Principal _principal) {
			
			if(this.checkForbidden(_principal,MenuCode.AdminUser))
			{
				return "redirect:/home/404";
			}
			try {
				userService.deleteById(id);
			} catch (Exception ex) {

			}
			this.setMessageAt(model, MessageCode.Delete);
			return "redirect:/admin/index";
		}

	 
	   private boolean IsValidate(AdminEditForm _theForm) {
			if (_theForm.getPermission() <= 0) {
				errorMessage = "Permission field must be integer, value greater than 0";
				return false;
			}
			return true;
		}
	 
	   
	   private boolean IsValidate(CustomerEditForm _theForm) {
		   if (_theForm.getStatus()<=0) {
	            errorMessage = "Status must be an integer, value greater than 0";
				return false;
	        }
	        
			return true;
		}
	   
}
