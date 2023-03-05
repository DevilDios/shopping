package project.shopping.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.shopping.Util;
import project.shopping.domain.*;
import project.shopping.service.OrderProductService;
import project.shopping.service.ProductService;
import project.shopping.web.login.Login;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping({"/orderProduct", "/order"})
@RequiredArgsConstructor
@Controller
public class OrderProductController {

    private final OrderProductService orderProductService;
    private final ProductService productService;

    //바로구매
    @PostMapping("/order/{productId}")
    public String order(@PathVariable Long productId, @Login Member loginMember, @RequestParam int quantity, HttpServletRequest request, HttpServletResponse response) {

        //계정 확인
        Util.checkLogin(request, response);

        //주문상품 정보 등록
        Order order = Order.createOrder(loginMember, LocalDateTime.now(), OrderStatus.ORDER);
        Product product = productService.findOne(productId);
        OrderProduct orderProduct = OrderProduct.createOrderProduct(order, product, quantity);
        orderProductService.save(orderProduct);
        return "mainPage";
    }

    //주문상품 목록
    @GetMapping("/list")
    public String orderList(@Login Member member, Model model, HttpServletRequest request, HttpServletResponse response) {

        //계정 확인
        Util.checkLogin(request, response);
        List<OrderProduct> orderProducts = orderProductService.findByUserId(member.getUserId());
        log.info("asdf {} ", member.getId());
        model.addAttribute("orderProducts", orderProducts);
        return "orderProduct/orderList";
    }


    //주문상품 취소
    @PostMapping("/{orderProductId}/cancel")
    public String cancel(@PathVariable Long orderProductId) {
        orderProductService.orderStatusUpdate(orderProductId, OrderStatus.CANCEL);
        return "redirect:/order/list";
    }






}
