package com.example.poshell.biz;

import com.example.poshell.db.PosDB;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;
    private double totalMoney = 0;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {
        return posDB.getCart();
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public void checkout(Cart cart) {
        generateQRCode();
        System.out.println("欢迎使用微信付款：）");
        System.out.println("请支付" + totalMoney + "￥");
    }

    @Override
    public void total(Cart cart) {

    }

    @Override
    public boolean add(Product product, int amount) {
        return false;
    }

    @Override
    public boolean add(String productId, int amount) {
        Product product = posDB.getProduct(productId);
        if (product == null) return false;

        totalMoney += product.getPrice() * amount;

        boolean inCart = false;
        for (Item item : posDB.getCart().getItems())
            if (Objects.equals(item.getProduct().getId(), productId)) {
                item.setAmount(item.getAmount() + amount);
                return true;
            }

        this.getCart().addItem(new Item(product, amount));
        return true;
    }

    @Override
    public boolean emptyCart() {
        totalMoney = 0;
        return this.getCart().emptyItem();
    }

    @Override
    public boolean modifyCart(String productId, int newAmount) {
        Cart cart = posDB.getCart();
        for (int i = 0; i < cart.getItems().size(); i++) {
            Item item = cart.getItems().get(i);
            if (Objects.equals(item.getProduct().getId(), productId)) {
                int oldAmount = item.getAmount();
                double price = item.getProduct().getPrice();
                if (newAmount == 0)
                    cart.getItems().remove(i);
                else
                    item.setAmount(newAmount);
                totalMoney += (newAmount - oldAmount) * price;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    public void generateQRCode() {
        try {
            String text = "wxp://f2f0Zz8N-fQyhwfcR_kEo1Zr9AmoOZrVWupcmS7m_CDAD3s";
            int width = 35;
            int height = 35;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
            // 打印二维码到控制台
            for (int y = 0; y < height; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < width; x++)
                    sb.append(bitMatrix.get(x, y) ? "██" : "  "); // 使用ASCII码表示黑白像素
                System.out.println(sb);
            }
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
