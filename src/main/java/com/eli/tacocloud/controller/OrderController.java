package com.eli.tacocloud.controller;

import com.eli.tacocloud.model.TacoOrder;
import com.eli.tacocloud.model.TacoUser;
import com.eli.tacocloud.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("tacoOrder") TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                                @AuthenticationPrincipal TacoUser user) {
        if (errors.hasErrors()){
            return "orderForm";
        }

        order.setUser(user);
        log.info("Order submitted: {}", order);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
