package com.example.controller;

import com.example.form.MyFileUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MyFileUploadController {
    @InitBinder

    public void initBinder(WebDataBinder dataBinder) {

        Object target = dataBinder.getTarget();

        if (target == null) {

            return;

        }

        System.out.println("Target=" + target);

        if (target.getClass() == MyFileUploadForm.class) {


            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        }


    }
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)

    public String uploadOneFileHandler(Model model) {

        MyFileUploadForm myUploadForm = new MyFileUploadForm();

        model.addAttribute("myUploadForm", myUploadForm);

        // Forward to "/WEB-INF/pages/uploadOneFile.jsp".

        return "uploadOneFile";

    }

    // POST: Xử lý Upload

    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)

    public String uploadOneFileHandlerPOST(HttpServletRequest request, Model model, @ModelAttribute("myUploadForm") MyFileUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    // GET: Hiển thị trang form upload

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)

    public String uploadMultiFileHandler(Model model) {

        MyFileUploadForm myUploadForm = new MyFileUploadForm();

        model.addAttribute("myUploadForm", myUploadForm);

        // Forward to "/WEB-INF/pages/uploadMultiFile.jsp".

        return "uploadMultiFile";

    }



    // POST: Xử lý Upload

    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)

    public String uploadMultiFileHandlerPOST(HttpServletRequest request, Model model, @ModelAttribute("myUploadForm") MyFileUploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    private String doUpload(HttpServletRequest request, Model model, MyFileUploadForm myUploadForm) {

        String description = myUploadForm.getDescription();

        System.out.println("Description: " + description);

        // Thư mục gốc upload file.

        String uploadRootPath = request.getServletContext().getRealPath("upload");

        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);

        // Tạo thư mục gốc upload nếu nó không tồn tại.

        if (!uploadRootDir.exists()) {

            uploadRootDir.mkdirs();

        }

        CommonsMultipartFile[] fileDatas = myUploadForm.getFileDatas();

        Map<File, String> uploadedFiles = new HashMap<>();

        for (CommonsMultipartFile fileData : fileDatas) {

            // Tên file gốc tại Client.

            String name = fileData.getOriginalFilename();

            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {

                try {

                    // Tạo file tại Server.

                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    // Luồng ghi dữ liệu vào file trên Server.

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                    stream.write(fileData.getBytes());

                    stream.close();

                    uploadedFiles.put(serverFile, name);

                    System.out.println("Write file: " + serverFile);

                } catch (Exception e) {

                    System.out.println("Error Write file: " + name);

                }

            }

        }

        model.addAttribute("description", description);

        model.addAttribute("uploadedFiles", uploadedFiles);

        return "uploadResult";

    }
}
