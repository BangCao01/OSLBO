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
import orangeschool.form.CategoryForm;
import orangeschool.form.TopicForm;
import orangeschool.model.Category;
import orangeschool.model.TextContent;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.TopicService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/category") // This
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TopicService topicService;

	// Category
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddCategoryPage(
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		CategoryForm theForm = new CategoryForm();
		model.addAttribute("categoryForm", theForm);
		this.setSelectedMenu(model, MenuCode.Category);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		return "category/add";
	}

	// Category
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostCategory(
			Model model, //
			@ModelAttribute("categoryForm") CategoryForm categoryForm,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}

		String name = categoryForm.getName();
		String description = categoryForm.getDescription();
		Integer order = categoryForm.getOrder();

		if (this.IsValidate(categoryForm)) {
			Category n = new Category();
			n.setName(name);
			n.setOrder(order);
			n.setDescription(description);
			categoryService.save(n);
			this.setMessageAt(model, MessageCode.Add);
			return "redirect:/category/detail/" + n.getId();
		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		model.addAttribute("categoryForm", categoryForm);
		this.setSelectedMenu(model, MenuCode.Category);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "category/add";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showCategoryPage(
			Model model,
			Principal _principal
			) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("categories", categoryService.findAll());
		this.setSelectedMenu(model, MenuCode.Category);
		this.setAccessCode(model, _principal);
		return "category/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("category", categoryService.findById(id));
		this.setSelectedMenu(model, MenuCode.Category);
		this.setAccessCode(model, _principal);
		return "category/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		Category category = categoryService.findById(id);

		model.addAttribute("categoryID", id);
		CategoryForm theForm = new CategoryForm();
		theForm.setName(category.getName());
		theForm.setOrder(category.getOrder());
		theForm.setDescription(category.getDescription());
		theForm.setSkillcount(category.getSkillcount());
		model.addAttribute("categoryForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Category);

		return "category/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(
			@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("topicForm") CategoryForm categoryForm,
			Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		String name = categoryForm.getName();
		String description = categoryForm.getDescription();
		Integer order = categoryForm.getOrder();
		Integer skillcount = categoryForm.getSkillcount();
		if (this.IsValidate(categoryForm)) {
			Category category = categoryService.findById(id);

			category.setName(name);
			category.setOrder(order);
			category.setDescription(description);
			category.setSkillcount(skillcount);
			categoryService.save(category);
			this.setMessageAt(model, MessageCode.Edit);
			return "redirect:/category/detail/" + id;
		}
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Category);
		model.addAttribute("categoryID", id);
		model.addAttribute("categoryForm", categoryForm);
		model.addAttribute("errorMessage", errorMessage);
		return "category/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		try {
			categoryService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/category/index";
	}

	private boolean IsValidate(CategoryForm _theForm) {
		if (_theForm.getName().length() <= 0) {
			errorMessage = "Category's name field must be not empty";
			return false;
		}

		else if (_theForm.getOrder() <= 0) {
			errorMessage = "Order field must be not empty";
			return false;
		}

		return true;
	}

}
