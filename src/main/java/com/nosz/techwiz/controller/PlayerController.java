package com.nosz.techwiz.controller;

import com.nosz.techwiz.dto.PlayerDto;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.service.FileStorageService;
import com.nosz.techwiz.service.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    private FileStorageService fileStorageService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<PlayerDto>> getPlayersPaged(@PageableDefault(size = 5, sort = "name",direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PlayerDto> playersPage = playerService.getPlayers(pageable);
        return ResponseEntity.ok(playersPage);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadPlayerImageFileAsResource(filename);

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception ex){
            throw new CustomException("File not found");
        }
        if (contentType == null){
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""
                        + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/images",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile imageFile){
        var dto = fileStorageService.storePlayerImageFile(imageFile);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping ("/images/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename){
        fileStorageService.deletePlayerImageFile(filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id) {
        PlayerDto player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
        PlayerDto createdPlayer = playerService.createPlayer(playerDto);
        return ResponseEntity.ok(createdPlayer);
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id, @RequestBody PlayerDto playerDto) {
        PlayerDto updatedPlayer = playerService.updatePlayer(id, playerDto);
        return ResponseEntity.ok(updatedPlayer);
    }

    @Secured({"ROLE_ADMIN"})

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
