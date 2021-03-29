package com.news.update.service;

import com.news.update.entity.Attachment;
import com.news.update.entity.Category;
import com.news.update.entity.News;
import com.news.update.payload.FileResponse;
import com.news.update.repository.AttachmentRepository;
import com.news.update.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    public String save(MultipartFile multipartFile) {
        if (multipartFile != null) {
            Attachment attachment = new Attachment();
            attachment.setName(multipartFile.getOriginalFilename());
            attachment.setExtension(getExt(multipartFile.getOriginalFilename()));
            attachment.setFileSize(multipartFile.getSize());
            attachment.setContentType(multipartFile.getContentType());
            attachmentRepository.save(attachment);
            Date now = new Date();
            File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
                    1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));
            if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
                System.out.println("Aytilgan papkalar yaratildi");
            }

            attachment.setHashId(UUID.randomUUID().toString());
            attachment.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                    1900 + now.getYear(),
                    1 + now.getMonth(),
                    now.getDate(),
                    attachment.getHashId(),
                    attachment.getExtension()));
            attachmentRepository.save(attachment);

            uploadFolder = uploadFolder.getAbsoluteFile();
            File file = new File(uploadFolder, String.format("%s.%s", attachment.getHashId(), attachment.getExtension()));
            try {
                multipartFile.transferTo(file);
                return attachment.getHashId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Attachment findByHashId(String hashId) {
        return attachmentRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        Attachment attachment = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, attachment.getUploadPath()));
        if (file.delete()) {
            attachmentRepository.delete(attachment);
        }
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length()) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }

    public Map getPages(int page, int size){

        try {
            List<Attachment> tutorials = new ArrayList<Attachment>();
            Pageable paging = PageRequest.of(page, size, Sort.by("createAt").descending());

            Page<Attachment> pageTuts = attachmentRepository.findAll(paging);
            tutorials = pageTuts.getContent();

            List<FileResponse> files = tutorials.stream().map(dbFile -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("api/files/preview/")
                        .path(dbFile.getHashId())
                        .toUriString();

                return new FileResponse(
                        dbFile.getName(),
                        fileDownloadUri,
                        dbFile.getContentType(),
                        dbFile.getCreateAt(),
                        dbFile.getFileSize());
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("files", files);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return response;
        } catch (Exception e) {
            return null;
        }
    }


}
