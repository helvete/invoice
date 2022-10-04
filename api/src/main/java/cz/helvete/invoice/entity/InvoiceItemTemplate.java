package cz.helvete.invoice.entity;

import cz.helvete.invoice.db.entity.Item;
import cz.helvete.invoice.util.LocalDateTimeFormat;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum InvoiceItemTemplate {
    NAME("item_name"),
    UNIT_COUNT("item_unit_count"),
    UNIT_PRICE("item_price_per_unit"),
    TOTAL("item_total");

    private String token;

    private InvoiceItemTemplate(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static Map<String, String> loadTokens(Item item) {
        Map<String, String> map = new HashMap<>();
        map.put(NAME.getToken(), item.getName());
        map.put(UNIT_COUNT.getToken(), numberFormat(item.getUnitsCount()));
        map.put(UNIT_PRICE.getToken(), numberFormat(item.getPricePerUnit()));
        map.put(TOTAL.getToken(), numberFormat(item.getTotal()));
        return map;
    }

    private static String numberFormat(Integer number) {
        return InvoiceTemplate.numberFormat(number);
    }

    private static String numberFormat(double number) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("cs", "CZ"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(number);
    }
}
