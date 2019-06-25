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
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.FlashcardForm;
import orangeschool.form.StoryForm;
import orangeschool.model.Admin;
import orangeschool.model.Asset;
import orangeschool.model.Englishtest;
import orangeschool.model.ImageContent;
import orangeschool.model.SoundContent;
import orangeschool.model.Flashcard;
import orangeschool.model.TextContent;
import orangeschool.service.AdminService;
import orangeschool.service.AssetService;
import orangeschool.service.ImageContentService;
import orangeschool.service.FlashcardService;
import orangeschool.service.StoryService;
import orangeschool.service.TextContentService;

@Controller
@RequestMapping(path = "/flashcard") // This
public class FlashcardController extends BaseController {

	@Autowired
	private FlashcardService flashcardService;
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private TextContentService textContentService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(
			Model model,
			Principal _principal
			) {
		
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("flashcards", flashcardService.findAll());
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("flashcard", flashcardService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/detail";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		model.addAttribute("flashcardID", id);

		Flashcard flashcard = flashcardService.findWithId(id);
		FlashcardForm theForm = new FlashcardForm();
		theForm.setWord(flashcard.getWordText());
		theForm.setStatus(flashcard.getStatus());
		model.addAttribute("flashcardForm", theForm);
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String showEditPost(
			@PathVariable("id") int id, 
			HttpServletRequest request, 
			Model model,
			@ModelAttribute("flashcardForm") FlashcardForm _flashcardForm, 
			Principal _principal) {
		
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		String title = _flashcardForm.getWord();
		Integer status = _flashcardForm.getStatus();
		errorMessage = "";
		if (this.IsValidate(_flashcardForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textTitle = this.textContentService.findByContent(title);
			Flashcard flashcard = this.flashcardService.findWithId(id);
			if (textTitle == null) {
				textTitle = new TextContent();
				textTitle.setContent(title);
				textTitle.setStatus(status);
				textTitle.setAuthor(_author);
				textTitle.setCreateDate(WebUtil.GetTime());

			}

			flashcard.setWord(textTitle);
			flashcard.setStatus(status);
			
			if (IsValidateAssetEdit(_flashcardForm)) {
				if (!this.doUploadAssets(request, model, _flashcardForm, _author, flashcard, "edit")) {
					errorMessage = "Asset file goes wrong";
				}
			}

			if (errorMessage.isEmpty()) {
				flashcard.setUpdateDate(WebUtil.GetTime());
				this.textContentService.save(textTitle);
				this.assetService.save(flashcard.getAsset());
				this.flashcardService.save(flashcard);
				this.setMessageAt(model, MessageCode.Edit);
				return "redirect:/flashcard/detail/" + flashcard.getId();
			}
		}

		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("flashcardID", id);
		model.addAttribute("flashcardForm", _flashcardForm);
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/edit";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		try {
			Flashcard p = flashcardService.findWithId(id);
			Asset tmp = p.getAsset();
			if (tmp != null) {
				File oldFile = new File(tmp.getUri());
				try {
					oldFile.delete();
				} catch (Exception ex) {
					errorMessage = "Deleting old asset was not succesful";
				}
			}
			flashcardService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/flashcard/index";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddFlashcardPage(
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		FlashcardForm theForm = new FlashcardForm();
		model.addAttribute("flashcardForm", theForm);
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/add";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String doPostFlashcard(
			HttpServletRequest request, 
			Model model, //
			@ModelAttribute("flashcardForm") FlashcardForm _flashcardForm, 
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		String title = _flashcardForm.getWord();
		Integer status = 1;
		errorMessage = "";
		if (this.IsValidate(_flashcardForm)) {

			User loginedUser = (User) ((Authentication) _principal).getPrincipal();
			Admin _author = this.userService.findByUsername(loginedUser.getUsername());
			TextContent textTitle = this.textContentService.findByContent(title);
			if (textTitle == null) {
				textTitle = new TextContent();
				textTitle.setContent(title);
				textTitle.setStatus(status);
				textTitle.setAuthor(_author);
				textTitle.setCreateDate(WebUtil.GetTime());

			}
			Flashcard flashcard = new Flashcard();
			flashcard.setWord(textTitle);
			flashcard.setStatus(status);
			flashcard.setAuthor(_author);
			flashcard.setCreateDate(WebUtil.GetTime());
			if (IsValidateAsset(_flashcardForm)) {
				if (!this.doUploadAssets(request, model, _flashcardForm, _author, flashcard, "add")) {
					errorMessage = "Asset file goes wrong";
				}
			}

			if (errorMessage.isEmpty()) {
				this.textContentService.save(textTitle);
				this.assetService.save(flashcard.getAsset());
				this.flashcardService.save(flashcard);
				this.setMessageAt(model, MessageCode.Add);
				return "redirect:/flashcard/detail/" + flashcard.getId();
			}
		}
		this.setAccessCode(model, _principal);
		this.setSelectedActionMenu(model, ActionMenuCode.Add);
		model.addAttribute("flashcardForm", _flashcardForm);
		model.addAttribute("errorMessage", errorMessage);
		this.setSelectedMenu(model, MenuCode.Flashcard);
		return "flashcard/add";
	}

	private boolean IsValidate(FlashcardForm _theForm) {
		if (_theForm.getWord().length() <= 0) {
			errorMessage = "Word field must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateAsset(FlashcardForm _theForm) {
		if (!_theForm.IsValidate()) {
			errorMessage = "Asset file must be not empty";
			return false;
		}

		return true;
	}

	private boolean IsValidateAssetEdit(FlashcardForm _theForm) {
		if (!_theForm.IsValidate()) {
			return false;
		}

		return true;
	}

	private boolean doUploadAssets(HttpServletRequest request, Model model, //
			FlashcardForm myUploadForm, Admin _author, Flashcard _flashcard, String _mode) {

		String name = myUploadForm.getWord();
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
		File flashcardDir = new File(uploadRootDir, "flashcard");
		if (!flashcardDir.exists()) {
			flashcardDir.mkdirs();
		}

		File assetDir = new File(flashcardDir, "assets");
		if (!assetDir.exists()) {
			assetDir.mkdirs();
		}
		String hashName = name.substring(0, 3);
		File hashDir = new File(assetDir, hashName);
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
					// delete old file before create new one.
					Asset tmp = _flashcard.getAsset();

					File oldFile = new File(tmp.getUri());
					try {
						oldFile.delete();
					} catch (Exception ex) {
						errorMessage = "Deleting old sound was not succesful";
						return false;
					}
				}
				try {
					// Tạo file tại Server.
					fname = name + fname + WebUtil.GetTime();

					url = "/upload/flashcard/images/" + hashName + File.separator + fname.hashCode() + ext;
					uri = hashDir.getAbsolutePath() + File.separator + fname.hashCode() + ext;
					File serverFile = new File(uri);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);
					System.out.println("Time: " + WebUtil.GetTime());

					if (_mode.equalsIgnoreCase("add")) {
						if (!url.isEmpty()) {
							Asset asset = new Asset();
							asset.setName(name);
							asset.setUri(uri);
							asset.setUrl(url);
							asset.setAuthor(_author);
							asset.setCreateDate(WebUtil.GetTime());
							_flashcard.setAsset(asset);
							// assetService.save(asset);
						} else {
							return false;
						}
					} else if (_mode.equalsIgnoreCase("edit")) {
						if (!url.isEmpty()) {
							Asset asset = _flashcard.getAsset();
							asset.setUri(uri);
							asset.setUrl(url);
							asset.setUpdateDate(WebUtil.GetTime());

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

		// model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return true;
	}
}
