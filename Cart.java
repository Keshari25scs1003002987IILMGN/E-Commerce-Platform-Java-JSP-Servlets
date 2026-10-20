package com.cyrostack.ecommerce.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Shopping cart kept in the HTTP session. */
public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product p, int qty) {
        CartItem existing = items.get(p.getId());
        int newQty = (existing == null ? 0 : existing.getQuantity()) + qty;
        items.put(p.getId(), new CartItem(p, Math.min(newQty, p.getStock())));
    }

    public void update(int productId, int qty) {
        CartItem item = items.get(productId);
        if (item == null) return;
        if (qty <= 0) items.remove(productId);
        else item.setQuantity(Math.min(qty, item.getProduct().getStock()));
    }

    public void remove(int productId) { items.remove(productId); }
    public void clear() { items.clear(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public Collection<CartItem> getItems() { return items.values(); }

    public int getCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
