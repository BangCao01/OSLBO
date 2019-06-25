package orangeschool.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.ProductForm;
import orangeschool.form.StoryForm;
import orangeschool.model.Admin;
import orangeschool.model.ImageContent;
import orangeschool.model.Product;
import orangeschool.model.Story;
import orangeschool.model.TextContent;
import orangeschool.service.ImageContentService;
import orangeschool.service.ProductService;
import orangeschool.service.TextContentService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/product") // This
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private TextContentService textContentService;
	@Autowired
	private ImageContentService imageContentService;

	// Category
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddPage(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		
		ProductForm theForm = new ProductForm();
		model.addAttribute("productForm", theForm);
		this.setSelectedMenu(model, MenuCode.Product);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		return "product/add";
	}

	// Category
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostProduct(HttpServletRequest request, Model model, //
			@ModelAttribute("productForm") ProductForm theForm, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}

		String name = theForm.getName();
		String description = theForm.getDescription();
		Integer price = theForm.getPrice();
		errorMessage = "";
		if (this.IsValidateAdd(theForm)) {
			Product n = new Product();
			Admin author = this.getAdminUser(_principal);
			TextContent nameContent = this.textContentService.findByContent(name);
			if (nameContent == null) {
				nameContent = new TextContent();
				nameContent.setContent(name);
				nameContent.setCreateDate(WebUtil.GetTime());
				
				nameContent.setAuthor(author);
				
				// this.textContentService.save(nameContent);
			}

			TextContent descriptionContent = this.textContentService.findByContent(description);
			if (descriptionContent == null) {
				descriptionContent = new TextContent();
				descriptionContent.setContent(description);
				descriptionContent.setCreateDate(WebUtil.GetTime());
				descriptionContent.setAuthor(author);
				// this.textContentService.save(descriptionContent);
			}

			if (!this.doUploadImageOfProduct(request, model, theForm, author, n, "add")) {
				errorMessage = "Image input go wrong";
			}
			if (errorMessage.isEmpty()) {
				
				n.setName(nameContent);
				n.setDescription(descriptionContent);
				n.setPrice(price);
				
				this.textContentService.save(nameContent);
				this.textContentService.save(descriptionContent);
				this.imageContentService.save(n.getImage());
				productService.save(n);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/product/detail/" + n.getId();
			}
		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		model.addAttribute("productForm", theForm);
		this.setSelectedMenu(model, MenuCode.Product);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "product/add";
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showProductsPage(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("products", productService.findAll());
		this.setSelectedMenu(model, MenuCode.Product);
		this.setAccessCode(model, _principal);
		return "product/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("product", productService.findById(id));
		this.setSelectedMenu(model, MenuCode.Product);
		this.setAccessCode(model, _principal);
		return "product/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		Product product = productService.findById(id);

		model.addAttribute("productID", id);
		ProductForm theForm = new ProductForm();
		theForm.setName(product.getName());
		theForm.setPrice(product.getPrice());
		theForm.setDescription(product.getDescription());

		model.addAttribute("productForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Product);

		return "product/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(@PathVariable("id") int id, HttpServletRequest request, Model model,
			@ModelAttribute("productForm") ProductForm theForm, Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		String name = theForm.getName();
		String description = theForm.getDescription();
		Integer price = theForm.getPrice();
        errorMessage="";
		if (this.IsValidate(theForm)) {
			Admin author = this.getAdminUser(_principal);
			Product n = this.productService.findById(id);
			TextContent nameContent = this.textContentService.findByContent(name);
			if (nameContent == null) {
				nameContent = new TextContent();
				nameContent.setContent(name);
				nameContent.setCreateDate(WebUtil.GetTime());
				nameContent.setAuthor(author);

			}

			TextContent descriptionContent = this.textContentService.findByContent(description);
			if (descriptionContent == null) {
				descriptionContent = new TextContent();
				descriptionContent.setContent(description);
				descriptionContent.setCreateDate(WebUtil.GetTime());
				descriptionContent.setAuthor(author);
			}

			if (theForm.IsValidate()) {
				if (!this.doUploadImageOfProduct(request, model, theForm, author, n, "edit")) {
					errorMessage = "Image input go wrong";
				}
			}
			if (errorMessage.isEmpty()) {
				this.textContentService.save(nameContent);
				this.textContentService.save(descriptionContent);
				this.imageContentService.save(n.getImage());
				n.setName(nameContent);
				n.setDescription(descriptionContent);
				n.setPrice(price);
				productService.save(n);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/product/detail/" + n.getId();
			}
		}
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Product);
		model.addAttribute("productID", id);
		model.addAttribute("productForm", theForm);
		model.addAttribute("errorMessage", errorMessage);
		return "product/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Product)) {
			return "redirect:/home/404";
		}
		try {
			Product p = productService.findById(id);
			ImageContent tmp = p.getImage();
			
			File oldFile = new File(tmp.getUri());
			try {
				oldFile.delete();
			} catch (Exception ex) {
				errorMessage = "Deleting old image was not succesful";
			}
			
			productService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/product/index";
	}

	private boolean IsValidate(ProductForm _theForm) {
		if (_theForm.getName().length() <= 0) {
			errorMessage = "Product's name field must be not empty";
			return false;
		}
		else if (_theForm.getDescription().length() <= 0) {
			errorMessage = "Product's description field must be not empty";
			return false;
		}

		else if (_theForm.getPrice() <= 0) {
			errorMessage = "Price field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateAdd(ProductForm _theForm) {
		if (!_theForm.IsValidate()) {
			errorMessage = "Image field must be not empty";
			return false;
		}
		return this.IsValidate(_theForm);
	}

	private boolean doUploadImageOfProduct(HttpServletRequest request, Model model, //
			ProductForm myUploadForm, Admin _author, Product _product, String _mode) {

		String description = myUploadForm.getDescription();
		System.out.println("Description: " + description);
		String name = myUploadForm.getName();
		String uri = "";
		String url = "";
		// Thư mục gốc upload file.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);

		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();

		}
		File productDir = new File(uploadRootDir, "product");
		if (!productDir.exists()) {
			productDir.mkdirs();
		}

		File imageDir = new File(productDir, "images");
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		String hashName = name.substring(0, 3);
		File hashDir = new File(imageDir, hashName);
		if (!hashDir.exists()) {
			hashDir.mkdirs();
		}
		List<ImageContent> images = new ArrayList<ImageContent>();

		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : fileDatas) {

			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			String ext = fname.substring(len - 4, len);
			System.out.println("Client File Name = " + ext);

			if (fname != null && fname.length() > 0) {

				if (_mode.equalsIgnoreCase("edit")) {
					// delete old image before create new one.
					ImageContent tmp = null;
					if (fileData.getName() == "image") {
						tmp = _product.getImage();
					}

					File oldFile = new File(tmp.getUri());
					try {
						oldFile.delete();
					} catch (Exception ex) {
						errorMessage = "Deleting old image was not succesful";

						return false;

					}
				}

				try {

					// Tạo file tại Server.
					fname = name + fname + WebUtil.GetTime();

					url = "/upload/product/images/" + hashName + File.separator + fname.hashCode() + ext;
					uri = hashDir.getAbsolutePath() + File.separator + fname.hashCode() + ext;
					File serverFile = new File(uri);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);

					if (_mode.equalsIgnoreCase("add")) {
						ImageContent image = new ImageContent();
						image.setName(name);
						image.setDescription(description);
						image.setUri(uri);
						image.setUrl(url);
						image.setAuthor(_author);
						image.setCreateDate(WebUtil.GetTime());
						
						_product.setImage(image);
						
					} else if (_mode.equalsIgnoreCase("edit")) {
						ImageContent image = _product.getImage();
						image.setUri(uri);
						image.setUrl(url);
						
					}

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
					return false;
				}
			}
		}

		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return true;
	}

}
