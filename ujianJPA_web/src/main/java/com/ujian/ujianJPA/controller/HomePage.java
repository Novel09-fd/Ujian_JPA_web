package com.ujian.ujianJPA.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ujian.ujianJPA.entity.BiodataModel;
import com.ujian.ujianJPA.repository.BiodataRepository;
import com.ujian.ujianJPA.util.FileUploadUtil;



@Controller
public class HomePage {

	@Autowired
	BiodataRepository bioRepository;
	
	@GetMapping("/")
	public String viewIndex(Model model) {
		model.addAttribute("biodatamodel" , new BiodataModel());
		return "index.html";
	}
	
	@PostMapping("/biodata/add")
	public String bioBooking (@RequestParam("name") String name , @RequestParam("email") String email , @RequestParam("platform") String platform , @RequestParam("cv") MultipartFile file,Model model) {
		String fileName =StringUtils.cleanPath(file.getOriginalFilename());
		BiodataModel biodataModel =  new BiodataModel(0,name,email,platform,fileName);
		biodataModel.setCv(fileName);
		this.bioRepository.save(biodataModel);
		
		
		String uploadDir = "G:/cv/"+fileName;
		try {
			FileUploadUtil.saveFile(uploadDir, fileName, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return"redirect:/";
	}
	
	
}
