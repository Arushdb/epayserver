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
	SBIService theSBIService;

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
		String filename = "";
		filename = student.getRoll_number();
//        if ((category.equalsIgnoreCase("con")||
//        		(category.equalsIgnoreCase("CER")))){
//        			filename = student.getRoll_number();
//        		}else {
//        			filename = student.getRoll_number();	
//        		}

		Document document = new Document(PageSize.A4);

		String filepath = directory + sep + filename + ".pdf";

		try {

			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));

			document.open();
			// for window enviornment
			// String imgpath = context.getRealPath("/") +
			// "\\WEB-INF\\classes\\images\\deiNewLogo.jpg";
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
			// PdfPTable deiheadertable = new PdfPTable(new float[] { 1, 5 });
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

			// Category Student continue Specific
			// *

//			cell = new PdfPCell(new Phrase("Roll No."));
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//
//			cell = new PdfPCell(new Phrase(student.getRoll_number()));
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//
//			
//			cell = new PdfPCell(new Phrase("Course"));
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//
//			cell = new PdfPCell(new Phrase(student.getProgramname()));
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//
//			cell = new PdfPCell(new Phrase("Semester"));
//
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//
//			cell = new PdfPCell(new Phrase(student.getSemestercode()));
//			cell.setBorderWidth(1);
//			cell.setFixedHeight(30);
//			cell.setPadding(5);
//
//			studentdetail.addCell(cell);
//			
			PdfPTable headertable = new PdfPTable(1);
			setcategorywisereceipt(studentdetail, student, headertable);
			// Category continue End Specific

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

			// Start Header as per category
//			PdfPTable headertable = new PdfPTable(1);
//			
//			
//
//			cell = new PdfPCell(new Phrase("e-receipt for DEI epayment "));
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBorder(0);
//			cell.setFixedHeight(30);
//			// h1.setAlignment(Cell.ALIGN_CENTER);
//			headertable.addCell(cell);
//			String semester = dateFormat.format(student.getSemesterstartdate());
//			cell = new PdfPCell(new Phrase("Continue Student-" + semester));
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBorder(0);
//			cell.setFixedHeight(30);
//			headertable.addCell(cell);
//
//			cell = new PdfPCell(new Phrase(" "));
//
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBorder(0);
//
//			headertable.addCell(cell);

			// end Header as per category

			document.add(headerimg);
			document.add(deiheadertable);
			headertable.setSpacingBefore(50);
			// document.add(headertable1);
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

	public void setcategorywisereceipt(PdfPTable studentdetail, Student student, PdfPTable headertable) {

		String category = student.getCategory().toLowerCase();
		String rectype = "";

		if (category.equalsIgnoreCase("con") || category.equalsIgnoreCase("newadm")

		) {
			rectype = "student";
		}

		if (category.equalsIgnoreCase("appfee") || category.equalsIgnoreCase("POS")

		) {
			rectype = "applicant";
		}

		if (category.equalsIgnoreCase("HOS")

		) {
			rectype = "hosteler";
		}

		if (category.equalsIgnoreCase("CER")

		) {
			rectype = "exstudent";
		}

		// con ,newadm ,CER,HOS,POS,appfee

		PdfPCell cell;
		switch (rectype) {
		case ("student"):

			if (category.equalsIgnoreCase("con"))
				cell = new PdfPCell(new Phrase("Roll No."));
			else
				cell = new PdfPCell(new Phrase("Application Number"));

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

			// Adding header information

			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");

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

			break;

		case ("applicant"):
			cell = new PdfPCell(new Phrase("Application Number"));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getRoll_number()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase("e-receipt for DEI epayment "));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			// h1.setAlignment(Cell.ALIGN_CENTER);
			headertable.addCell(cell);

			dateFormat = new SimpleDateFormat("yyyy");
			semester = dateFormat.format(student.getSemesterstartdate());
			cell = new PdfPCell(new Phrase("Application Fee-" + semester));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			headertable.addCell(cell);

			cell = new PdfPCell(new Phrase(" "));

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			headertable.addCell(cell);

			break;

		case ("exstudent"):

			String text = "";

			if (student.getCertificatetype().equalsIgnoreCase("trn")
					|| student.getCertificatetype().equalsIgnoreCase("mig"))
				text = "Enrolment Number";
			else
				text = "Roll Number";

			cell = new PdfPCell(new Phrase(text));

			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getRoll_number()));
			cell.setBorderWidth(1);
			cell.setFixedHeight(30);
			cell.setPadding(5);

			studentdetail.addCell(cell);
			
		 if (student.getCertificatetype().equalsIgnoreCase("res")){
				text = "Semesters";

				cell = new PdfPCell(new Phrase(text));

				cell.setBorderWidth(1);
				cell.setFixedHeight(30);
				cell.setPadding(5);

				studentdetail.addCell(cell);
				
				cell = new PdfPCell(new Phrase(student.getSemestercode()));
				cell.setBorderWidth(1);
				cell.setFixedHeight(30);
				cell.setPadding(5);

				studentdetail.addCell(cell);

			 
		 }
			
			
			
			// Header Information for certificate

			cell = new PdfPCell(new Phrase("e-receipt for DEI epayment "));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			// h1.setAlignment(Cell.ALIGN_CENTER);
			headertable.addCell(cell);

			dateFormat = new SimpleDateFormat("yyyy");
			String year = dateFormat.format(new Date());
			
			if (student.getCertificatetype().equalsIgnoreCase("mig"))
				text = "Migration Certificate Fee :"+year ;
			if (student.getCertificatetype().equalsIgnoreCase("trn"))
				text = "Transscript Certificate Fee :"+year ;
			if (student.getCertificatetype().equalsIgnoreCase("deg"))
				text = "Duplicate Degree/Diploma Certificate Fee :"+year ;
			if (student.getCertificatetype().equalsIgnoreCase("pro"))
				text = "Provisional Certificate Fee:"+year ;
			if (student.getCertificatetype().equalsIgnoreCase("res"))
				text = "Duplicate Result Card Fee :"+year ;
								
			cell = new PdfPCell(new Phrase(text));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setFixedHeight(30);
			headertable.addCell(cell);

			cell = new PdfPCell(new Phrase(" "));

			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);

			headertable.addCell(cell);

			break;

		}

	}

	@Override
	public Student getFeeData(String ordeno, String ATRN, String amount) throws Exception {
		// TODO Auto-generated method stub
		Double amount1 = Double.parseDouble(amount);
		BigDecimal amt = BigDecimal.valueOf(amount1);
		Float floatamt = Float.valueOf(amount);
		Payment payment = null;
		Student student = new Student();
		String category = "";
		String Other_Details;

		if (ATRN != "") {

			payment = theSBIService.findPaymentByATRNAndAmount(ATRN, amt);
		}

		if (ordeno != "" && payment == null) {

			payment = theSBIService.findPaymentByByMerchantordernoAndAmount(ordeno, amt);

		}

		if (payment == null)
			return null;
		category = payment.getCategory();
		Other_Details = payment.getOtherdetail();
		String OtherDetails_data[] = Other_Details.split("\\,");

		if (category.equalsIgnoreCase("CER"))
			student = theSBIService.otherdetailforcertificate(student, OtherDetails_data);

		if (category.equalsIgnoreCase("CON") || category.equalsIgnoreCase("newadm")
				|| category.equalsIgnoreCase("appfee"))

			student = theSBIService.otherdetailforcontinue(student, OtherDetails_data);

		student.setATRN(ATRN);
		student.setMerchantorderno(payment.getMerchantorderno());
		student.setCategory(payment.getCategory());
		student.setAppfee(amt);
		student.setMessage(payment.getTransaction_status());
		student.setAmount(floatamt);
		student.setTransactiondate(payment.getTransaction_date());
		student.setBankReferenceNumber(payment.getBank_Reference_Number());

		return student;

	}

}
