package com.news.update.service;

import com.news.update.entity.Attachment;
import com.news.update.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

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
}
