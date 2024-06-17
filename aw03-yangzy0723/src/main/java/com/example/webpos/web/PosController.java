package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        posService.newCart();
        return refreshModel(model);
    }

    @GetMapping("/add")
    public String add(Model model, @RequestParam(name = "pid") String pid) {
        posService.add(pid, 1);
        return refreshModel(model);
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam("pid") String productId, @RequestParam(value = "quantity") Integer quantity) {
        posService.modifyCart(productId, quantity);
        return refreshModel(model);
    }

    @GetMapping("/cancel")
    public String cancel(Model model) {
        posService.emptyCart();
        return refreshModel(model);
    }

    private String refreshModel(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("total", posService.totalCart());
        return "index";
    }
}
