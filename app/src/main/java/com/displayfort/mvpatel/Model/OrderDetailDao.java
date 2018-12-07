package com.displayfort.mvpatel.Model;

/**
 * Created by pc on 14/11/2018 13:08.
 * MVPatel
 */
public class OrderDetailDao {
    public int OrderId;
    public Long productId, roomId;
    public String name;
    public String code;
    public String detail;
    public Integer productTypeId;
    public Integer price;
    public double discountPrice;
    public Integer colorId;
    public boolean status;
    public String colorText;
    public int attachId;
    public String ImageUrl;
    public long created;
    public int Qty;
    public double discountValue;
    public String discountType;

    public OrderDetailDao() {

    }

    public OrderDetailDao(int orderId, Long productId, String name, String code, String detail, Integer productTypeId, Integer price, Integer colorId, boolean status, String colorText, int attachId, String imageUrl, long created, int qty) {
        this.OrderId = orderId;
        this.productId = productId;
        this.name = name;
        this.code = code;
        this.detail = detail;
        this.productTypeId = productTypeId;
        this.price = price;
        this.discountPrice = price;
        this.colorId = colorId;
        this.status = status;
        this.colorText = colorText;
        this.attachId = attachId;
        this.ImageUrl = imageUrl;
        this.created = created;
        this.Qty = qty;
    }
}
