package com.packt.webstore.controller;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.sun.org.apache.regexp.internal.StreamCharacterIterator;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

/**
 * Created by dman on 7/7/16.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping
    public String list(Model model) {
//        Product iphone = new Product("P1234","iPhone 5s", new
//                BigDecimal(500));
//        iphone.setDescription("Apple iPhone 5s smartphone with 4.00-inch 640x1136 display and 8-megapixel rear camera");
//                iphone.setCategory("Smart Phone");
//        iphone.setManufacturer("Apple");
//        iphone.setUnitsInStock(1000);
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @RequestMapping("/{category}")
    public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
        List<Product> products = productService.getProductsByCategory(productCategory);
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundUnderCategoryException();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/filter/{ByCriteria}")
    public String getProductsByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams, Model model) {
        model.addAttribute("products", productService.getProductsByFilter(filterParams));
        return "products";
    }

    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "product";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
//        Product newProduct = new Product();
//        model.addAttribute("newProduct", newProduct);
        return "addProduct";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNewProductForm(
            @ModelAttribute("newProduct") @Valid Product productToBeAdded,
            BindingResult result, HttpServletRequest request) {

        if(result.hasErrors()) {
            return "addProduct";
        }

        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        MultipartFile productImage = productToBeAdded.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/home/dman/Pictures/");

        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(rootDirectory+"resources/images/"+productToBeAdded.getProductId() + ".png"));
            } catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }


        productService.addProduct(productToBeAdded);
        return "redirect:/products";
    }

    @RequestMapping("/invalidPromoCode")
    public String invalidPromoCode() {
        return "invalidPromoCode";
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {

        // binder.setDisallowedFields("unitsInOrder", "discontinued");
        binder.setAllowedFields("productId",
                "name","unitPrice","description","manufacturer",
                "category","unitsInStock", "productImage");
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest request,
                                    ProductNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidProductId", exception.getProductId());
        mav.addObject("exception", exception);
        mav.addObject("url",
                request.getRequestURL()+"?"+request.getQueryString());
        mav.setViewName("productNotFound");
        return mav;
    }
}