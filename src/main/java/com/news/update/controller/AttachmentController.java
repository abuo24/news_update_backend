package com.news.update.controller;

import com.news.update.entity.Attachment;
import com.news.update.model.ResultSucces;
import com.news.update.payload.FileResponse;
import com.news.update.repository.AttachmentRepository;
import com.news.update.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile){
        attachmentService.save(multipartFile);
        return ResponseEntity.ok(multipartFile.getOriginalFilename()+" file saqlandi");
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws IOException {
        Attachment attachment =  attachmentService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\""+ URLEncoder.encode(attachment.getName()))
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        attachment.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity download(@PathVariable String hashId) throws IOException {
        Attachment attachment =  attachmentService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+ URLEncoder.encode(attachment.getName()))
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        attachment.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId){
        attachmentService.delete(hashId);
        return ResponseEntity.ok("file yo'q qilindi");
    }

    @GetMapping("/all")
    public ResponseEntity<List<FileResponse>> getListFiles() {
        List<FileResponse> files = attachmentRepository.findAll().stream().map(dbFile -> {
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
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files")
    public ResponseEntity getShortNewsRelease(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.ok(new ResultSucces(true, attachmentService.getPages(page, size)));
    }
}
