package project.shopping.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.shopping.domain.Image;
import project.shopping.domain.Product;
import project.shopping.dto.ProductSaveForm;
import project.shopping.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
@Controller
public class ProductController {

    private final ProductService productService;

    //파일 저장 경로
    @Value("${file.dir}")
    private String fileDir;

    //상품 등록
    @GetMapping("/saveForm")
    public String productSaveForm(@ModelAttribute ProductSaveForm productSaveForm) {
        return "product/productSaveForm";
    }
    @PostMapping("/saveForm")
    public String productSave(@RequestParam MultipartFile file, @Validated @ModelAttribute ProductSaveForm productSaveForm,
                              BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("memberModifySave bindingResult error: {}", bindingResult);
            return "product/saveForm";
        }

        //파일명
        String fileName = file.getOriginalFilename();
        //확장자명
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        //이름 중복방지 수정
        String uuidFileName = UUID.randomUUID().toString() + fileExtensionName;

        //상품정보 등록
        Image image = Image.createImage(fileName, uuidFileName);
        Product product = Product.createProduct(image, productSaveForm.getName(), productSaveForm.getPrice(), productSaveForm.getStock());
        productService.saveProduct(product);

        //파일저장
        if (!file.isEmpty()) {
            String fullPath = fileDir + uuidFileName;
            log.info("fullPath: {} ", fullPath);
            file.transferTo(new File(fullPath));
        }

        return "redirect:/product/saveForm";
    }

    //상품목록
    @GetMapping("/list")
    public String productList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

    //상품 상세 페이지
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findOne(id);
        model.addAttribute("product", product);
        return "product/productDetail";
    }




}
