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
import orangeschool.form.TransactionForm;
import orangeschool.model.Transaction;
import orangeschool.model.TextContent;
import orangeschool.service.TransactionService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/transaction") // This
public class TransactionController extends BaseController {

	@Autowired
	private TransactionService transactionService;

	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showCategoryPage(
			Model model,
			Principal _principal
			) {
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("transactions", transactionService.findAll());
		this.setSelectedMenu(model, MenuCode.Transaction);
		this.setAccessCode(model, _principal);
		return "transaction/index";
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
		model.addAttribute("transaction", transactionService.findByTransactionID(id));
		this.setSelectedMenu(model, MenuCode.Transaction);
		this.setAccessCode(model, _principal);
		return "transaction/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		Transaction transaction = transactionService.findByTransactionID(id);

		model.addAttribute("transactionID", id);
		TransactionForm theForm = new TransactionForm();
		theForm.setBillcode(transaction.getBillcode());
		theForm.setStatus(transaction.getStatus());
		theForm.setCustomerName(transaction.getCustomerName());
		theForm.setCustomerID(transaction.getCustomerID());
		model.addAttribute("transactionForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Transaction);

		return "transaction/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(
			@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("transactionForm") TransactionForm theForm,
			Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		String billcode = theForm.getBillcode();
		Integer status = theForm.getStatus();
		
		if (this.IsValidate(theForm)) {
			Transaction transaction = transactionService.findByTransactionID(id);

			transaction.setStatus(status);
			transactionService.save(transaction);
			this.setMessageAt(model, MessageCode.Edit);
			return "redirect:/transaction/detail/" + id;
		}
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Transaction);
		model.addAttribute("transactionID", id);
		model.addAttribute("transactionForm", theForm);
		model.addAttribute("errorMessage", errorMessage);
		return "transaction/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		try {
			transactionService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/transaction/index";
	}

	private boolean IsValidate(TransactionForm _theForm) {
		if (_theForm.getStatus() <= 0) {
			errorMessage = "Bill's status field must be an integer";
			return false;
		}

		return true;
	}

}
