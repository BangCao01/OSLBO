package orangeschool.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.ResultForm;
import orangeschool.model.Result;

import orangeschool.service.ResultService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/result") // This
public class ResultController extends BaseController {

	@Autowired
	private ResultService resultService;

	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(
			Model model,
			Principal _principal
			) {
		if (this.checkForbidden(_principal, MenuCode.Result)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("results", resultService.findAll());
		this.setSelectedMenu(model, MenuCode.Result);
		this.setAccessCode(model, _principal);
		return "result/index";
	}
	
	@RequestMapping(value = { "/index/{customerid}" }, method = RequestMethod.GET)
	public String showResultOfCustomerPage(
			@PathVariable("customerid") int customerid, 
			Model model,
			Principal _principal
			) {
		if (this.checkForbidden(_principal, MenuCode.Result)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("results", resultService.findByCustomerID(customerid));
		this.setSelectedMenu(model, MenuCode.Result);
		this.setAccessCode(model, _principal);
		return "result/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("result", resultService.findByResultID(id));
		this.setSelectedMenu(model, MenuCode.Transaction);
		this.setAccessCode(model, _principal);
		return "result/detail";
	}

	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Result)) {
			return "redirect:/home/404";
		}
		try {
			resultService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/result/index";
	}

	

}
