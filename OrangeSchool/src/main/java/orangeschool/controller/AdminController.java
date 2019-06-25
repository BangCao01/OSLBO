package orangeschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import orangeschool.form.*;
import orangeschool.repository.AdminRepository;
import orangeschool.WebUtil;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.service.AdminService;
import orangeschool.service.SecurityService;
import orangeschool.validator.AdminValidator;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/admin") // This
public class AdminController extends BaseController{
	
	@Autowired
    private AdminService userService;
    
    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String homeIndex(Model model, Principal _principal) {
 
        //model.addAttribute("message", message);
        model.addAttribute("admins", userService.findAll());
        model.addAttribute("menucode", this.menucode);
        this.setAccessCode(model, _principal);
        return "admin/index";
    }
    
	 @RequestMapping(value = "/detail/{adminid}", method = RequestMethod.GET)
	    public String showDetailPage(
	    		@PathVariable("adminid") int adminid,
	    		Model model, Principal _principal) {
	 
	        Admin user = this.userService.findByUserID(adminid);
	        model.addAttribute("user", user);
	        this.setAccessCode(model, _principal);
	        return "admin/detail";
	    }
	 
	 @RequestMapping(value = { "/add" }, method = RequestMethod.GET)
		public String showAddPage(
				Model model,
				Principal _principal
				) {
		 
		        SignUpForm theForm = new SignUpForm();
		        model.addAttribute("signUpForm", theForm);
		        this.setAccessCode(model, _principal);
		        return "admin/add";
		}
	 
	 @RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	    public String saveSignUpForm(
	    		Model model, //
	            @ModelAttribute("signupForm") SignUpForm signupForm,
	            Principal _principal
	            ) {
	 
		    //userValidator.validate(signupForm, bindingResult);
            errorMessage="";
	        if (IsValidate(signupForm)) {
	        	String username = signupForm.getUsername();
		        String password = signupForm.getPassword();
		        Admin n = new Admin();
		 		n.setUsername(username);
		 		n.setPassword(password);
		 		n.setPermission(8);
	 		
		        userService.save(n);

		        
			 
		        return "redirect:/admin/index";
	        	
	        }

	        this.setAccessCode(model, _principal);
	        model.addAttribute("errorMessage", errorMessage);
	        model.addAttribute("signUpForm", signupForm);
	        return "admin/add";

	    }
	 
	 @RequestMapping(value = "/edit/{adminid}", method = RequestMethod.GET)
	    public String showEditPage(
	    		@PathVariable("adminid") int adminid,
	    		Model model, Principal _principal) {
	 
		    Admin user = this.userService.findByUserID(adminid);
		    AdminEditForm theForm = new AdminEditForm();
		    theForm.setUsername(user.getUsername());
		    theForm.setPassword(user.getPassword());
		    theForm.setPermission(user.getPermission());
			model.addAttribute("adminEditForm", theForm);
			this.setAccessCode(model, _principal);
	        return "admin/edit";
	    }
	
	 @RequestMapping(value = "/edit/{adminid}", method = RequestMethod.POST)
	    public String showEditPost(
	    		@PathVariable("adminid") int adminid,
	    		@ModelAttribute("adminEditForm") AdminEditForm theForm,
	    		Model model, Principal principal) {
	 
		    Admin user = this.userService.findByUserID(adminid);
		    errorMessage="";
		    if(IsValidate(theForm))
		    {
		    	user.setPermission(theForm.getPermission());
		    	this.userService.save(user);
		    	return "redirect:/admin/detail/"+ user.getId();
		    }
			model.addAttribute("adminEditForm", theForm);
	      
	        return "admin/edit";
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
	 
	   
	   private boolean IsValidate(SignUpForm _theForm) {
		   if (_theForm.getUsername().length() < 6 || _theForm.getUsername().length() > 32) {
	            
	            errorMessage = "Username must have more than 6 characters";
				return false;
	        }
	        if (userService.findByUsername(_theForm.getUsername()) != null) {
	        	errorMessage = "Username already taken";
				return false;
	        }

	        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	        if (_theForm.getPassword().length() < 6 || _theForm.getPassword().length() > 32) {
	        	errorMessage = "Password must have more than 6 characters";
				return false;
	        }

	        if (!_theForm.getConfirmPassword().equals(_theForm.getPassword())) {
	        	errorMessage = "Confirm password get wrong";
				return false;
	        }
			return true;
		}
	   
}
