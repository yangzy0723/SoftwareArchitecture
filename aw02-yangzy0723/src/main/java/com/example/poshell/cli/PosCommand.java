package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "products")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products())
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "register")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "add")
    public String addToCart(String productId, int amount) {
        if (posService.getCart() == null)
            return "Please Register a Cart Before Add a Product to It!";

        if (posService.add(productId, amount))
            return posService.getCart().toString();
        return "ERROR";
    }

    @ShellMethod(value = "Empty the Cart", key = "empty")
    public String emptyCart() {
        if (posService.getCart() == null)
            return "Please Register a Cart Before Empty It!";

        if (posService.emptyCart())
            return "Empty Cart Succeeded!";
        else
            return "Empty Cart Failed!";
    }

    @ShellMethod(value = "Print the Cart", key = "print")
    public String printCart() {
        if (posService.getCart() == null)
            return "Please Register a Cart Before Print it!";

        return posService.getCart().toString();
    }

    @ShellMethod(value = "Modify the Cart", key = "modify")
    public String modifyCart(String productId, int newAmount) {
        if (posService.getCart() == null)
            return "Please Register a Cart Before Modify It!";

        if (posService.modifyCart(productId, newAmount))
            return posService.getCart().toString();
        else
            return "No Product " + productId + " Found in Cart!";
    }

    @ShellMethod(value = "Pay for the Products in Cart", key = "checkout")
    public String checkoutCart() {
        if (posService.getCart() == null)
            return "Please Register a Cart First!";
        posService.checkout(posService.getCart());
        return "扫不出来请轻微上下摇动手机：）";
    }
}