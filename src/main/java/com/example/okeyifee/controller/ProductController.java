//package com.example.okeyifee.controller;
//
//import com.decagon.kindredhair.dto.ProfileDTO;
//import com.decagon.kindredhair.models.Product;
//import com.decagon.kindredhair.payload.ApiResponse;
//import com.decagon.kindredhair.repository.ProductRepository;
//import com.decagon.kindredhair.service.UserProfileRecommendation;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/product")
//@AllArgsConstructor
//public class ProductController {
//
//    UserProfileRecommendation userProfileRecommendation;
//    ProductRepository productRepository;
//
//    @PostMapping("/recommendation")
//    public ResponseEntity<ApiResponse<Map<String, List<Product>>>> getProductRecommendation(@RequestBody ProfileDTO profileDTO) {
//        Map<String, List<Product>> message = userProfileRecommendation.generateProductRecommendation(profileDTO);
//        ApiResponse<Map<String, List<Product>>> response = new ApiResponse<>();
//        HttpStatus status = HttpStatus.OK;
//        response.setData(message);
//        response.setStatus(status);
//        return new ResponseEntity<>(response, status);
//    }
//}