package download.controller;

import download.model.Music;
import download.repository.MusicRepo;
import download.service.MovieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class MusicController {


    @Autowired
    private  MovieService service;


    @Value("${upload.dir}")
    private  String uploadDir;


    @GetMapping("/")
    public String viewHome(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        model.addAttribute("songs",
                keyword != null ? service.search(keyword) : service.listAll());
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("title") String title,
                         @RequestParam("artist") String artist,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam(value = "externalUrl", required = false, defaultValue = "") String externalUrl) throws IOException{
            String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Music song = new Music();
        song.setTitle(title);
        song.setArtist(artist);
        song.setFilePath("/music/" + fileName);
        service.save(song);

        return "redirect:/";
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        File file = new File(uploadDir + "/" + fileName);
        if (file.exists()) {
            response.setContentType("audio/mpeg");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            Files.copy(file.toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}