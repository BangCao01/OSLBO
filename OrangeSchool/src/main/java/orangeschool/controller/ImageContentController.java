package orangeschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.ImageUploadForm;
import orangeschool.model.Admin;
import orangeschool.model.ImageContent;
import orangeschool.service.AdminService;
import orangeschool.service.ImageContentService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/image") // This
public class ImageContentController extends BaseController {

	@Autowired
	private ImageContentService imageContentService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(Model model,
			Principal _principal
			) {
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("images", imageContentService.findAll());
		this.setSelectedMenu(model, MenuCode.Images);
		this.setAccessCode(model, _principal);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setAccessCode(model, _principal);
		return "image/index";
	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showAddPage(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		ImageUploadForm myUploadForm = new ImageUploadForm();
		model.addAttribute("imageUploadForm", myUploadForm);
		this.setSelectedMenu(model, MenuCode.Images);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		return "image/add";
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String doAddPost(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("imageUploadForm") ImageUploadForm myUploadForm, 
			Principal _principal) {
		
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		User loginedUser = (User) ((Authentication) _principal).getPrincipal();
		Admin _author = this.userService.findByUsername(loginedUser.getUsername());
		
		ImageContent image = new ImageContent();
		errorMessage = "";
		if (this.IsValidate(myUploadForm)) {
			if (!this.doUploadImage(request, model, myUploadForm, _author, image, "add")) {
				errorMessage = "Image input go wrong";
			}
			if (errorMessage.isEmpty()) {
				this.imageContentService.save(image);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/image/detail/" + image.getId();
			}

		}
		this.setSelectedMenu(model, MenuCode.Images);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		this.setAccessCode(model, _principal);
		model.addAttribute("imageUploadForm", myUploadForm);
		model.addAttribute("errorMessage", errorMessage);
		return "image/add";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		ImageContent image = imageContentService.findById(id);

		model.addAttribute("imageID", id);
		ImageUploadForm theForm = new ImageUploadForm();

		theForm.setName(image.getName());
		theForm.setDescription(image.getDescription());
		theForm.setUrl(image.getUrl());
		theForm.setUri(image.getUri());
		model.addAttribute("imageUploadForm", theForm);
		this.setAccessCode(model, _principal);
		this.setSelectedMenu(model, MenuCode.Images);
		return "image/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(
			@PathVariable("id") int id, 
			Model model,
			@ModelAttribute("imageUploadForm") ImageUploadForm theForm, 
			HttpServletRequest request,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		
		String name = theForm.getName();
		String description = theForm.getDescription();
		errorMessage = "";
		if (this.IsValidateEditForm(theForm)) {
			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			ImageContent image = imageContentService.findById(id);
			if (this.IsValidateImageFile(theForm)) {

				if (!this.doUploadImage(request, model, theForm, _author, image, "edit")) {
					errorMessage = "Image input go wrong";
				}

			}

			if (errorMessage.isEmpty()) {
				image.setName(name);
				image.setDescription(description);
				image.setUpdateDate(WebUtil.GetTime());
				imageContentService.save(image);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/image/detail/" + id;

			}

		}

		model.addAttribute("imageID", id);
		model.addAttribute("imageUploadForm", theForm);
		this.setSelectedMenu(model, MenuCode.Images);
		this.setAccessCode(model, _principal);
		model.addAttribute("errorMessage", errorMessage);
		return "image/edit";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("image", imageContentService.findById(id));
		this.setSelectedMenu(model, MenuCode.Images);
		this.setAccessCode(model, _principal);
		return "image/detail";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Images)) {
			return "redirect:/home/404";
		}
		try {
			ImageContent image = imageContentService.findById(id);
			if(image!=null)
				deteleImageBy(image.getUri());
			imageContentService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/image/index";
	}

	private boolean IsValidateEditForm(ImageUploadForm _theForm) {
		String name = _theForm.getName();
		if (name.length() < 3) {
			errorMessage = "Image's name must be longer more than 3 letters";
			return false;
		}

		return true;
	}

	private boolean IsValidateImageFile(ImageUploadForm _theForm) {

		MultipartFile[] fileDatas = _theForm.getFileDatas();
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			if (len == 0) {
				// errorMessage = "Image must be in png or jpg.";
				return false;
			}
			String ext = fname.substring(len - 4, len);
			if (!(ext.equalsIgnoreCase(".png") || ext.equalsIgnoreCase(".jpg"))) {
				errorMessage = "Image must be in png or jpg.";
				return false;
			}

		}
		return true;
	}

	private boolean IsValidate(ImageUploadForm _theForm) {
		String name = _theForm.getName();
		if (name.length() < 3) {
			errorMessage = "Image's name must be longer more than 3 letters";
			return false;
		}

		return IsValidateImageFile(_theForm);
	}

	private boolean deteleImageBy(String _uri)
	{
		boolean ret = true;
		File image = new File(_uri);
		try {
			image.delete();
		} catch (Exception ex) {
			errorMessage = "Deleting old image was not succesful";
			return false;
		}
		return ret;
	}
	private boolean doUploadImage(HttpServletRequest request, Model model, //
			ImageUploadForm myUploadForm, Admin _author, ImageContent _image, String _mode) {

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
		File imagesDir = new File(uploadRootDir, "images");
		if (!imagesDir.exists()) {
			imagesDir.mkdirs();
		}
		String hashName = name.substring(0, 3);
		File hashDir = new File(imagesDir, hashName);
		if (!hashDir.exists()) {
			hashDir.mkdirs();
		}

		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String fname = fileData.getOriginalFilename();
			int len = fname.length();
			String ext = fname.substring(len - 4, len);
			System.out.println("Client File Name = " + ext);

			if (fname != null && fname.length() > 0) {

				if (_mode.equalsIgnoreCase("edit")) {
					// delete old image before create new one.
					ImageContent tmp = _image;

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
//                	MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//                	messageDigest.update(fname.getBytes());
//                	String encryptedString = new String(messageDigest.digest());
					url = "/upload/images/" + hashName + File.separator + fname.hashCode() + ext;
					uri = hashDir.getAbsolutePath() + File.separator + fname.hashCode() + ext;
					File serverFile = new File(uri);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);

					if (_mode.equalsIgnoreCase("add")) {
						if (!url.isEmpty()) {

							_image.setName(name);
							_image.setDescription(description);
							_image.setUri(uri);
							_image.setUrl(url);
							_image.setAuthor(_author);
							_image.setCreateDate(WebUtil.GetTime());

						} else {
							return false;
						}

					} else if (_mode.equalsIgnoreCase("edit")) {
						if (!url.isEmpty()) {
							_image.setUri(uri);
							_image.setUrl(url);
						} else {
							return false;
						}

					}

				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
					return false;
				}
			}
		}

		System.out.println("Time: " + WebUtil.GetTime());

		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return true;

	}

}
