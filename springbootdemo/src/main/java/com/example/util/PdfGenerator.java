package com.example.util;

import com.example.model.Person;
import com.google.common.collect.Lists;
import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDate;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @描述: demo
 * @作者: tankejia
 * @创建时间: 2018/2/27-11:01 .
 * @版本: 1.0 .
 */
public class PdfGenerator {


    private String imageDomain;

    public static final String FONT_LOCAL = "font/WenQuanYiMicroHei.ttf";
    public static final String FONT = "/usr/share/fonts/pdf/WenQuanYiMicroHei.ttf";

    public PdfFormXObject template;
    public Image total;

    private PdfFont labelFont;

    	{
		try {
//			labelFont = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false, true);
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			labelFont = PdfFontFactory.createFont(contextClassLoader.getResource(FONT_LOCAL).getFile(), PdfEncodings.IDENTITY_H);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


//	public PdfGenerator(String imageDomain) {
//        this.imageDomain = imageDomain;

        //			InputStream in = ResourceUtil.getResourceStream(FONT);
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte[] b = new byte[10000];
//			while (true) {
//				int r = in.read(b);
//				if (r == -1)
//					break;
//				out.write(b, 0, r);
//			}
//
//			labelFont = PdfFontFactory.createFont(out.toByteArray(), PdfEncodings.IDENTITY_H);

//			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//			PdfFontFactory.registerDirectory(contextClassLoader.getResource(FONTDIR).getFile());
//			labelFont = PdfFontFactory.createRegisteredFont("notosanscjksc-regular", PdfEncodings.IDENTITY_H);

//        try {
//            labelFont = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);
//        } catch (Exception e) {
//            try {
//                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//                labelFont = PdfFontFactory.createFont(contextClassLoader.getResource(FONT_LOCAL).getFile(), PdfEncodings.IDENTITY_H);
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//
//    }

    public byte[] generate(Person person) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        generate(outputStream, person);
        return outputStream.toByteArray();
    }

    public void generate(ByteArrayOutputStream outputStream, Person obj) throws IOException {
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        // Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);

        // Initialize document
        Document document = new Document(pdfDoc, PageSize.A4.rotate());


        template = new PdfFormXObject(new Rectangle(765, 25, 30, 30));
        PdfCanvas canvas = new PdfCanvas(template, pdfDoc);
        total = new Image(template);
        total.setRole(PdfName.Artifact);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderHandler());
        pdfDoc.getCatalog().setLang(new PdfString("en-us"));
        PdfDictionary parameters = new PdfDictionary();
        parameters.put(PdfName.ModDate, new PdfDate().getPdfObject());

        Table table = new Table(new float[]{850, 118, 90});
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(
                new Paragraph("文件标题").setMarginLeft(280).setFont(labelFont).setFontSize(24).setFontColor(Color.RED)));

        // 根据单号生成条形码
        		Barcode128 code128 = new Barcode128(pdfDoc);
		code128.setCode(obj.getId().toString());
		code128.setCodeType(Barcode128.CODE128);
		Image code128Image = new Image(code128.createFormXObject(pdfDoc));
        table.addCell(new Cell().setBorder(Border.NO_BORDER).add(code128Image));

        table.addCell(new Cell().setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE).add(new Paragraph("单号：").setFont(labelFont).setBold()));
        // 直接取条形码地址（已经生成好条形码）
//        String text = obj.getBarCodeUrl();
//        if (StringUtils.isNotEmpty(text)) {
//            try {
//                Image code128Image = new Image(ImageDataFactory.create(startsWith(text, "http://") || startsWith(text, "https://") ? text
//                        : (imageDomain + (startsWith(text, "/") ? text : "/" + text)))).setHeight(40);
//                table.addCell(new Cell().setBorder(Border.NO_BORDER).add(code128Image));
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                table.addCell(new Cell().setBorder(Border.NO_BORDER));
//            }
//        } else {
//            table.addCell(new Cell().setBorder(Border.NO_BORDER));
//        }

        document.add(table);

        document.add(new Paragraph());
        document.add(headerTable(obj));

//        document.add(new Paragraph());
//        document.add(itemTable(obj.getAccounts()));

        document.add(new Paragraph());
        document.add(itemTotalTable(obj));

        document.close();
        pdfWriter.close();
        pdfDoc.close();
    }

    private Cell labelCell(String text) {
        return new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY)).setFont(labelFont).setBold());
    }

    private Cell fieldCell(String text) {
        return new Cell().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5F))
                .add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY))).setFont(labelFont);
    }

    private Table headerTable(Person obj) {
        float[] columnWidths = {80, 120, 80, 250, 80, 120, 100, 60};
        Table table = new Table(columnWidths);
        table.addCell(labelCell("单号："));
        table.addCell(fieldCell(obj.getId().toString()));
        table.addCell(labelCell("姓名："));
        table.addCell(fieldCell(obj.getName()));
        table.startNewRow();
        table.addCell(labelCell("年龄："));
        table.addCell(fieldCell(obj.getAge().toString()));
        table.addCell(labelCell("生日："));
        table.addCell(fieldCell(new DateTime(obj.getBirthday()).toString("yyyy-MM-dd")));
        return table;
    }

    private Table itemTable(List<Integer> objs) {
        float[] columnWidths = {50, 120, 270, 120, 100, 150, 130, 50, 100, 100, 100};
        Table table = new Table(columnWidths);

        List<String> headers = Lists.newArrayList("行号", "商品编码", "商品名称", "商品条码", "商品规格", "产地", "采购内装数", "单位", "订货数量",
                "订货单价", "订货金额");
        for (String header : headers) {
            table.addCell(headerCell().add(new Paragraph(header).setBold().setFont(labelFont)));
        }
        table.startNewRow();

        for (int i = 0; i < objs.size(); i++) {
            Integer orderItem = objs.get(i);
            table.addCell(bodyCell(String.valueOf(i + 1)));
//            table.addCell(bodyCell(objs.getProductCode()));
//            table.addCell(bodyCell(objs.getProductName()));
//            table.addCell(bodyCell(objs.getInternationalCode()));
//            table.addCell(bodyCell(objs.getPackingSpecifications()));
//            table.addCell(bodyCell(objs.getProducePlace()));
//            table.addCell(bodyCell(String.valueOf(objs.getPurchaseInsideNumber())));
//            table.addCell(bodyCell(objs.getUnitExplanation()));
//            table.addCell(bodyCell(String.valueOf(objs.getPurchaseNumber())));
//            table.addCell(bodyCell(String.valueOf(objs.getPurchasePrice().setScale(2, BigDecimal.ROUND_HALF_UP))));
//            table.addCell(bodyCell(String.valueOf(objs.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP))));
            if (i != 0 && i != objs.size() - 1) {
                table.startNewRow();
            }
        }

        return table;
    }

    private Cell headerCell() {
        return new Cell().setBackgroundColor(new DeviceGray(0.85f)).setTextAlignment(TextAlignment.CENTER);
    }


    private Cell bodyCell(String text) {
        return new Cell().add(new Paragraph(StringUtils.defaultString(text, StringUtils.EMPTY)).setFont(labelFont));
    }

    private Table itemTotalTable(Person person) {
        float[] columnWidths = {100, 80, 100, 100, 50, 50, 150, 330};
        Table table = new Table(columnWidths);
//        table.addCell(labelCell("合计数量："));
//        table.addCell(fieldCell(person.getTotalNumber().toString()));
//        table.addCell(labelCell("合计金额："));
//        table.addCell(fieldCell(String.valueOf(person.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP))));
//        table.addCell(labelCell(""));
//        table.addCell(labelCell(""));
//        table.addCell(labelCell("合计金额（大写）："));
//        table.addCell(fieldCell(person.getNumberToCN()));
        return table;
    }

    public static Person generatePerson() {
        Person person = new Person();
        person.setId(1001);
        person.setName("赵四");
        person.setAge(18);
        person.setBirthday(new Date());
//        List<Integer> accounts = new ArrayList<>();
//
//        for (int i = 0; i < RandomUtils.nextInt(1,10)*100; i++) {
//            accounts.add(i);
//
//        }
        return person;
    }

    public class HeaderHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNum = docEvent.getDocument().getPageNumber(page);
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.beginText();
            canvas.setFontAndSize(labelFont, 8);
            canvas.beginMarkedContent(PdfName.Artifact);
            canvas.moveText(34, 25);
//      canvas.showText("Test");
            canvas.moveText(703, 0);
            canvas.showText(String.format("第 %d 页 ", pageNum));
            canvas.endText();
            canvas.stroke();
            canvas.addXObject(template, 0, 0);
            canvas.endMarkedContent();
            canvas.release();
        }
    }

}
