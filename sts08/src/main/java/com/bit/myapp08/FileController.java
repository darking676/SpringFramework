package com.bit.myapp08;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

	String filePath = "C:\\spring\\spring2018\\upload\\";

	public String form() {
		return "uploadForm";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@RequestParam("sabun") int sabun, 
			@RequestParam("file") MultipartFile file, 
			Model model) throws IOException {
		System.out.println(sabun);
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		
		String refilename = file.getOriginalFilename()+"@@@"+System.currentTimeMillis();
		File target = new File(filePath+refilename);
		
		file.transferTo(target);
		
//		InputStream is = null;
//		FileOutputStream fos = null;
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		
//		try {
//			is = file.getInputStream();
//			fos = new FileOutputStream(target);
//			bis = new BufferedInputStream(is);
//			bos = new BufferedOutputStream(fos);
//			byte[] buff = new byte[256];
//			int su=0;
//			while((su=bis.read(buff))>0) {
//				bos.write(buff, 0, su);
//			}
//			bos.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			is.close();
//			fos.close();
//		}
		
		model.addAttribute("editFile", refilename);
		model.addAttribute("originFile", file.getOriginalFilename());
		
		return "result";
	}
	
	@RequestMapping("/download")
	public void download(String origin, String edit, HttpServletResponse res) throws IOException {
		File source = new File(filePath+edit);
		byte[] buff = new byte[256];
		FileInputStream fis = null;
		ServletOutputStream os = null;
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
		
		res.setContentType("application/octest-stream; charset=UTF-8");
		res.setHeader("Content-Dispostion", "attachment; filename=\""+origin+"\"");
		
		try {
			fis = new FileInputStream(source);
			os = res.getOutputStream();
//			bis = new BufferedInputStream(fis);
//			bos = new BufferedOutputStream(bos);
//			int su = 0;
//			while((su=bis.read(buff))>0) {
//				bos.write(buff, 0, su);
//			}
//			bos.flush();
			
			FileCopyUtils.copy(fis, os);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
//		finally {
//			fis.close();
//			os.close();
//		}
	}
	
	@RequestMapping("/{origin }")
	public void down(@PathVariable("origin") String origin, String edit, HttpServletResponse res) throws IOException {
		File source = new File(filePath+edit);
		byte[] buff = new byte[256];
		FileInputStream fis = null;
		ServletOutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		res.setContentType("application/octest-stream; charset=UTF-8");
		
		try {
			fis = new FileInputStream(source);
			os = res.getOutputStream();
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(os);
			int su = 0;
			while((su=bis.read(buff))>0) {
				bos.write(buff, 0, su);
			}
			bos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
			os.close();
		}
	}
	
}
