package orangeschool.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.FlashcardForm;
import orangeschool.form.StoryForm;
import orangeschool.model.Admin;
import orangeschool.model.Asset;
import orangeschool.model.ImageContent;
import orangeschool.model.Flashcard;
import orangeschool.model.TextContent;
import orangeschool.service.AdminService;
import orangeschool.service.AssetService;
import orangeschool.service.ImageContentService;
import orangeschool.service.FlashcardService;
import orangeschool.service.StoryService;
import orangeschool.service.TextContentService;

@Controller    
@RequestMapping(path="/asset") // This
public class AssetController extends BaseController{

	@Autowired
    private AssetService assetService;
	
	
	
    @RequestMapping(value = {"/index" }, method = RequestMethod.GET)
    public String showIndexPage(
    		Model model,
    		Principal _principal) {
    	if (this.checkForbidden(_principal, MenuCode.Asset)) {
			return "redirect:/home/404";
		}
    	model.addAttribute("message", message);
        model.addAttribute("assets", assetService.findAll());
        this.menucode = "Asset";
    	model.addAttribute("menucode", this.menucode);
    	this.setAccessCode(model, _principal);
        return "asset/index";
    }
    
    @RequestMapping(value="/detail/{id}", method = RequestMethod.GET)
    public String showDetailPage(
    		@PathVariable("id") int id, 
    		Model model,
    		Principal _principal){
    	if (this.checkForbidden(_principal, MenuCode.Asset)) {
			return "redirect:/home/404";
		}
    	model.addAttribute("message", message);
        model.addAttribute("asset", assetService.findWithId(id));
        this.menucode = "Asset";
    	model.addAttribute("menucode", this.menucode);
    	this.setAccessCode(model, _principal);
    	return "asset/detail";
    }
    
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public String showEditPage(
    		@PathVariable("id") int id, 
    		Model model,
    		Principal _principal){
    	if (this.checkForbidden(_principal, MenuCode.Asset)) {
			return "redirect:/home/404";
		}
    	model.addAttribute("message", message);

        model.addAttribute("assetID", id);
        
        model.addAttribute("asset", assetService.findWithId(id));
        this.menucode = "Asset";
    	model.addAttribute("menucode", this.menucode);
    	this.setAccessCode(model, _principal);
    	return "asset/edit";
    }
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal){
    	if (this.checkForbidden(_principal, MenuCode.Asset)) {
			return "redirect:/home/404";
		}
    	try {
    	  assetService.deleteById(id);
    	}catch(Exception ex)
    	{
    		
    	}
    	return "redirect:/asset/index";
    }
    
    
    
}
