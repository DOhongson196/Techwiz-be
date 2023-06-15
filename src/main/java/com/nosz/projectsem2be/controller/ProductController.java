package com.nosz.projectsem2be.controller;



import com.nosz.projectsem2be.dto.ProductDto;
import com.nosz.projectsem2be.exception.FileStorageException;
import com.nosz.projectsem2be.service.FileStorageService;
import com.nosz.projectsem2be.service.MapValidationErrorService;
import com.nosz.projectsem2be.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto dto, BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity != null){
            return responseEntity;
        }
        var productDto = productService.insertProduct(dto);

        return new ResponseEntity<>(productDto,HttpStatus.CREATED);
    }
    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable Long id,
                                               @RequestBody ProductDto productDto,
                                               BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity!=null){
            return responseEntity;
        }
        return new ResponseEntity<>(productService.updateProduct(id,productDto),HttpStatus.OK);
    }
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return  new ResponseEntity<>("Product id " + id + " was deleted",HttpStatus.OK);
    }

    @PostMapping(value = "/images/one",
    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    MediaType.APPLICATION_JSON_VALUE},
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile imageFile){
        var dto = fileStorageService.storeProductImageFile(imageFile);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.CREATED);
    }



    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request){
        Resource resource = fileStorageService.loadProductImageFileAsResource(filename);

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception ex){
            throw new FileStorageException("File not found");
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

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping ("/images/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename){
        fileStorageService.deleteProductImageFile(filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/find")
    public ResponseEntity<?> getProductBriefByName(@RequestParam("query") String query,
                                        @PageableDefault(size = 5, sort = "name",direction = Sort.Direction.ASC)
                                        Pageable pageable){
        return new ResponseEntity<>(productService.getProductBriefByName(query,pageable),HttpStatus.OK);
    }

    @GetMapping("header/find")
    public ResponseEntity<?> getProductBriefByNameHeader(@RequestParam("query") String query){
        return new ResponseEntity<>(productService.getProductBriefSearchHeader(query),HttpStatus.OK);
    }

    @GetMapping("/top/view")
    public ResponseEntity<?> getProductTop10View(){
        return new ResponseEntity<>(productService.getProductTop10View(),HttpStatus.OK);
    }

    @GetMapping("/top/buy")
    public ResponseEntity<?> getProductTop10Buy(){
        return new ResponseEntity<>(productService.getProductTop10Buy(),HttpStatus.OK);
    }

    @GetMapping("/top/featured")
    public ResponseEntity<?> getProductTop10Featured(){
        return new ResponseEntity<>(productService.getProductTop10Featured(),HttpStatus.OK);
    }

    @GetMapping("/brief")
    public ResponseEntity<?> getProductBrief(@PageableDefault(size = 5, sort = "name",direction = Sort.Direction.ASC)
                                                   Pageable pageable){
        return new ResponseEntity<>(productService.getProductBrief(pageable),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @GetMapping("/brief/{id}")
    public ResponseEntity<?> getProductBriefById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductBriefById(id),HttpStatus.OK);
    }
}
