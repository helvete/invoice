package cz.helvete.invoice.template;

import com.lowagie.text.pdf.BaseFont;
import cz.helvete.invoice.db.entity.Invoice;
import cz.helvete.invoice.entity.InvoiceTemplate;
import cz.helvete.invoice.entity.InvoiceItemTemplate;
import cz.helvete.invoice.rest.AppException;
import cz.helvete.invoice.rest.ResponseResultCode;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import java.io.InputStream;
import org.stringtemplate.v4.ST;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class TemplateRenderer {
    private static final String template = "template_invoice.html";
    private static final String templateItem = "template_invoice_item.html";
    private static final char DELIM = '$';

    @Inject
    private Logger logger;

    public String render(Map<String, String> replacement, String templateName) {
        String template;
        try {
            template = fetchTemplate(templateName);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Invoice template not found", e);
            throw new AppException(ResponseResultCode.SERVER_ERROR);
        }
        return replace(template, replacement);
    }

    public String render(Invoice invoice) {
        String itemRows = invoice
            .getItems()
            .stream()
            .map(x -> render(InvoiceItemTemplate.loadTokens(x), templateItem))
            .collect(Collectors.joining());
        Map<String, String> data = InvoiceTemplate.loadTokens(invoice, itemRows);

        return render(data, template);
    }

    public ByteArrayOutputStream renderPdf(Invoice invoice) throws IOException {
        return generatePdf(render(invoice));
    }

    private String replace(String template, Map<String, String> data) {
        ST st = new ST(template, DELIM, DELIM);
        data.forEach((k, v) -> st.add(k, v));
        return st.render();
    }

    private ByteArrayOutputStream generatePdf(String html) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver resolver = renderer.getFontResolver();
        resolver.addFont(
                getClass().getResource("/fonts/NotoSerif-Regular.ttf").getPath(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(html.length())) {
            renderer.createPDF(baos);
            return baos;
        }
    }

    private String fetchTemplate(String templateName) throws FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(templateName);
        StringBuilder template = new StringBuilder();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                template.append(scanner.nextLine() + System.lineSeparator());
            }
        }
        return template.toString();
    }
}
