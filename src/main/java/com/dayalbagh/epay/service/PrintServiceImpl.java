package com.dayalbagh.epay.service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.dayalbagh.epay.AES256Bit;

import com.dayalbagh.epay.model.Defaulters;
import com.dayalbagh.epay.model.Epayerrorlog;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.PendingPayment;
import com.dayalbagh.epay.model.Student;

import com.dayalbagh.epay.repository.DefaulterRepository;
import com.dayalbagh.epay.repository.EpayExceptionRepository;

import com.dayalbagh.epay.repository.PaymentRepository;
import com.dayalbagh.epay.repository.PendingPaymentRepository;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;

import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

@PropertySource("classpath:message.properties")
@Service
public class PrintServiceImpl implements PrintService {
	// String key_Array =
	// "A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A";
	// static String gatewayUrl =
	// "https://test.sbiepay.sbi/payagg/statusQuery/getStatusQuery" ;
	// static String Merchant_ID = "1000112";

	@Value("${paymentgatewaylive}")
	private String paymentgatewaylive;

	@Value("${testmerchantid}")
	private String testmerchantid;

	@Value("${testAggregatorId}")
	private String testAggregatorId;

	@Value("${testgatewayUrl}")
	private String testgatewayUrl;

	@Value("${testSuccessURL}")
	private String testSuccessURL;

	@Value("${testFailURL}")
	private String testFailURL;

	@Value("${testkey_Array}")
	private String testkey_Array;

	//////

	@Value("${livemerchantid}")
	private String livemerchantid;

	@Value("${liveAggregatorId}")
	private String liveAggregatorId;

	@Value("${livegatewayUrl}")
	private String livegatewayUrl;

	@Value("${liveSuccessURL}")
	private String liveSuccessURL;

	@Value("${liveFailURL}")
	private String liveFailURL;

	@Value("${livekey_Array}")
	private String livekey_Array;

	@Value("${testaeskey}")
	private String testaeskey;

	@Value("${liveaeskey}")
	private String liveaeskey;

	@Value("${Institutecintactno}")
	private String Institutecintactno;

	@Value("${GSTIN}")
	private String GSTIN;

	static String MerchantId;
	static String AggregatorId;

	public String getMerchantId() {
		return MerchantId;
	}

	@Value("${run-frquency.minutes}")
	private int rate;

	static String SuccessURL;
	static String FailURL;
	static String gatewayUrl;
	static String key_Array;
	static String aeskey;

	@Autowired
	private ServletContext context;

	@Autowired
	PaymentRepository thepaymentrepository;

	@Autowired
	PendingPaymentRepository thePendingPaymentRepository;

	@Autowired
	EpayExceptionRepository theEpayExceptionRepository;

	@Autowired
	DefaulterRepository theDefaulterRepository;

//	public SBIServiceImpl(PaymentRepository paymentrepository) {
//		
//		this.thepaymentrepository = paymentrepository;
//	}

	@PostConstruct
	private void init() {
		if (paymentgatewaylive.equalsIgnoreCase("no")) {
			MerchantId = testmerchantid;
			AggregatorId = testAggregatorId;
			SuccessURL = testSuccessURL;
			FailURL = testFailURL;
			gatewayUrl = testgatewayUrl;
			key_Array = testkey_Array;
			aeskey = testaeskey;
		}
		if (paymentgatewaylive.equalsIgnoreCase("yes")) {
			MerchantId = livemerchantid;
			AggregatorId = liveAggregatorId;
			SuccessURL = liveSuccessURL;
			FailURL = liveFailURL;
			gatewayUrl = livegatewayUrl;
			key_Array = livekey_Array;
			aeskey = liveaeskey;
		}

	}

	public String exportContinuePDF(Student student) throws MalformedURLException, IOException {

		String sep = System.getProperty("file.separator");

		String directory = context.getRealPath("/") + "REPORTS";
		// String directory=context.getContextPath()+"/"+"REPORTS";
		System.out.println("Real Path" + directory);
		File file = new File(directory);

		file.mkdirs();
        String category = student.getCategory();
        String filename="";
        if ((category.equalsIgnoreCase("con")||
        		(category.equalsIgnoreCase("CER")))){
        			filename = student.getRoll_number();
        		}else {
        			filename = student.getApplicationnumber();	
        		}
        		

		Document document = new Document(PageSize.A4);

		String filepath = directory + sep + filename + ".pdf";

		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));

			document.open();
             // for window enviornment
			//String imgpath = context.getRealPath("/") + "\\WEB-INF\\classes\\images\\deiNewLogo.jpg";
			String imgpath = context.getRealPath("/") + "//WEB-INF//classes//images//deiNewLogo.jpg";
			URL url = ResourceUtils.getURL(imgpath);

			Image headerimg = Image.getInstance(url);

			headerimg.setAlignment(Element.ALIGN_MIDDLE);
			headerimg.setAbsolutePosition(50, 750);
			headerimg.scalePercent(25f, 25f);

			PdfPCell cell;

			// document.add(Chunk.NEWLINE);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");

			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
			Paragraph h1 = new Paragraph("e-receipt for DEI epayment ");

			PdfPTable studentdetail;

			// studentdetail = new PdfPTable(new float[] { 0.5f, 3, 2, 3, 2, 2, 2 });

			studentdetail = new PdfPTable(new float[] { 3, 3 });
			//PdfPTable deiheadertable = new PdfPTable(new float[] { 1, 5 });
			PdfPTable deiheadertable = new PdfPTable(1);
			
			 cell = new PdfPCell(new Phrase("Dayalbagh Educational Institute"));
			 cell.setBorder(0);
			 cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
			 deiheadertable.addCell(cell);
			 
			 
			 cell = new PdfPCell(new Phrase("Dayalbagh, Agra UP - 282005"));
			 cell.setBorder(0);
			 
			 cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
			 deiheadertable.addCell(cell);
			deiheadertable.setSpacingBefore(20);

			cell = new PdfPCell(new Phrase("Payment Status"));

			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getMessage().toUpperCase()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);
			
			cell = new PdfPCell(new Phrase("SBIePay Reference ID "));

			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getATRN()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Merchant Order No"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getMerchantorderno()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Bank Reference Number"));

			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getBankReferenceNumber()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Name of student"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getStudentname()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5);
			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Roll No."));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getRoll_number()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Course"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getProgramname()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Semester"));

			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getSemestercode()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Date of Payment"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			String trxdate = df.format(student.getTransactiondate());

			cell = new PdfPCell(new Phrase(trxdate));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			

			cell = new PdfPCell(new Phrase("Institute Contact Number"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(Institutecintactno));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("GSTIN"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(GSTIN));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			String amt = String.valueOf(student.getAmount());

			cell = new PdfPCell(new Phrase(amt));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			
			PdfPTable headertable = new PdfPTable(1);
			
			

			cell = new PdfPCell(new Phrase("e-receipt for DEI epayment "));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			// h1.setAlignment(Cell.ALIGN_CENTER);
			headertable.addCell(cell);
			String semester = dateFormat.format(student.getSemesterstartdate());
			cell = new PdfPCell(new Phrase("Continue Student-" + semester));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			headertable.addCell(cell);

			cell = new PdfPCell(new Phrase(" "));

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			headertable.addCell(cell);
			

			document.add(headerimg);
			document.add(deiheadertable);
			headertable.setSpacingBefore(50);
		//	document.add(headertable1);
			document.add(headertable);

			studentdetail.setSpacingBefore(10);
			document.add(studentdetail);

			// document.add(footer);
			document.close();
//			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// PdfWriter writer = PdfWriter.getInstance(document, new
		// FileOutputStream(directory + sep + input.getRequestNo()+ ".pdf"));

		return filepath;
	}

//	protected class Custompageevent extends PdfPageEventHelper {
//
//		int pagenumber = 0;
//
//		@Override
//		public void onStartPage(PdfWriter writer, Document document) {
//			try {
//				pagenumber++;
//				document.add(new Phrase("\n\n\n"));
//				document.add(new Phrase("Hello Arush"));
//
//				String filename = "/images/deiLogoHeader.png";
//				// File file = new File(getClass().getResource(filename).getFile());
//
////				Image headerimg = Image.getInstance(
////						java.awt.Toolkit.getDefaultToolkit().createImage(getClass().getResource(filename)),null);
//
//				Image headerimg = Image.getInstance(filename);
//
//				headerimg.setAlignment(Element.ALIGN_MIDDLE);
//				headerimg.setAbsolutePosition(0, 750);
//				headerimg.scalePercent(25f, 16f);
//
//				writer.getDirectContent().addImage(headerimg);
//
//			} catch (Exception x) {
//				x.printStackTrace();
//				System.out.println("error in start page " + x.getMessage());
//			}
//		}
//
////		@Override
////		public void onEndPage(PdfWriter writer, Document document) {
////			
////			Rectangle rect = writer.getBoxSize("art");
////		
////		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
////				new Phrase(String.format("page %d", pagenumber)), rect.getRight(), rect.getLeft(), 0);
////		}
////		
//	}

}
