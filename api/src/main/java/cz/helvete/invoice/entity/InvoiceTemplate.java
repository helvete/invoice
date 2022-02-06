package cz.helvete.invoice.entity;

import cz.helvete.invoice.db.entity.Invoice;
import cz.helvete.invoice.util.LocalDateTimeFormat;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public enum InvoiceTemplate {
    NUMBER("number"),
    PROVIDER_NAME("provider_name"),
    PROVIDER_ADDRESS("provider_address"),
    PROVIDER_CITY("provider_city"),
    PROVIDER_ID_NUMBER("provider_idnumber"),
    PROVIDER_VAT_ID_NUMBER("provider_vat_idnumber"),
    PROVIDER_PHONE_NUMBER("provider_phone_number"),
    PROVIDER_EMAIL_ADDRESS("provider_email_address"),
    PROVIDER_BANK_ACCOUNT("provider_bank_account"),
    ACCEPTOR_NAME("acceptor_name"),
    ACCEPTOR_ADDRESS("acceptor_address"),
    ACCEPTOR_CITY("acceptor_city"),
    ACCEPTOR_ID_NUMBER("acceptor_idnumber"),
    ACCEPTOR_VAT_ID_NUMBER("acceptor_vat_idnumber"),
    ISSUE_DATE("issue_date"),
    DUE_DATE("due_date"),
    TOTAL("total");

    private static DateTimeFormatter formatter
        = DateTimeFormatter.ofPattern(LocalDateTimeFormat.FORMAT_TEMPLATE);

    private String token;

    private InvoiceTemplate(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    // TODO: second attr + retrieve using reflection?
    // TODO: handle invoice items
    // TODO: rename nad move under template dir
    public static Map<InvoiceTemplate, String> loadTokens(Invoice invoice) {
        Map<InvoiceTemplate, String> map = new EnumMap<>(InvoiceTemplate.class);
        map.put(NUMBER, String.valueOf(invoice.getNumber()));
        map.put(ISSUE_DATE, invoice.getIssuedAt().format(formatter));
        map.put(DUE_DATE, invoice.getDueDate().format(formatter));
        map.put(TOTAL, numberFormat(invoice.getTotal()));
        map.put(PROVIDER_NAME, invoice.getProvider().getName());
        map.put(PROVIDER_ADDRESS, String.format("%s %s",
                    invoice.getProvider().getAddress().getStreet(),
                    invoice.getProvider().getAddress().getLandRegistryNumber()));
        map.put(PROVIDER_CITY, String.format("%s %s",
                    formatZip(invoice.getProvider().getAddress().getZip()),
                    invoice.getProvider().getAddress().getCity()));
        map.put(PROVIDER_ID_NUMBER, invoice.getProvider().getBusinessIdnumber());
        map.put(PROVIDER_PHONE_NUMBER,
                formatPhoneNumber(invoice.getProvider().getPhoneNumber()));
        map.put(PROVIDER_EMAIL_ADDRESS, invoice.getProvider().getEmailAddress());
        map.put(PROVIDER_BANK_ACCOUNT, invoice.getProvider().getBankAccount());
        map.put(ACCEPTOR_NAME, invoice.getAcceptor().getName());
        map.put(ACCEPTOR_ADDRESS, String.format("%s %s",
                    invoice.getAcceptor().getAddress().getStreet(),
                    invoice.getAcceptor().getAddress().getLandRegistryNumber()));
        map.put(ACCEPTOR_CITY, String.format("%s %s",
                    formatZip(invoice.getAcceptor().getAddress().getZip()),
                    invoice.getAcceptor().getAddress().getCity()));
        map.put(ACCEPTOR_ID_NUMBER, invoice.getAcceptor().getBusinessIdnumber());
        map.put(ACCEPTOR_VAT_ID_NUMBER, invoice.getAcceptor().getVatIdnumber());
        return map;
    }

    private static String formatZip(String zip) {
        if (zip.length() < 4) {
            return zip;
        }
        return zip.substring(0, 3) + " " + zip.substring(3);
    }

    private static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 9) {
            return phoneNumber;
        }
        return phoneNumber.substring(0, 3)
            + " " + phoneNumber.substring(3, 6)
            + " " + phoneNumber.substring(6, 9);
    }

    private static String numberFormat(Integer number) {
        double krones = number / 100.00;
        return NumberFormat
            .getCurrencyInstance(new Locale("cs", "CZ"))
            .format(krones);
    }
}
