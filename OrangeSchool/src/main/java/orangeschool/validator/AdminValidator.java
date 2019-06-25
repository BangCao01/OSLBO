package orangeschool.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import orangeschool.form.*;
import orangeschool.service.AdminService;
import orangeschool.model.Admin;

@Component
public class AdminValidator implements Validator {
    @Autowired
    private AdminService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return SignUpForm.class.equals(aClass)||
        	   SignInForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	SignUpForm theForm = (SignUpForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (theForm.getUsername().length() < 6 || theForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(theForm.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (theForm.getPassword().length() < 6 || theForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!theForm.getConfirmPassword().equals(theForm.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
    
    public void validateSignInForm(Object o, Errors errors)
    {
    	SignInForm theForm = (SignInForm) o;
    	
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (theForm.getUsername().length() < 6 || theForm.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        
        Admin admin = userService.findByUsername(theForm.getUsername());
        if (admin == null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (theForm.getPassword().length() < 6 || theForm.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        
        if(admin != null && userService.match(admin, theForm.getPassword()))
        {
        	errors.rejectValue("password","Incorrect.userFrom.password");
        }

       
    	
    }
}