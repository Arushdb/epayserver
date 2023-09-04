package com.dayalbagh.epay.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dayalbagh.epay.AES256Bit;
import com.dayalbagh.epay.model.Payment;
import com.dayalbagh.epay.model.PendingPayment;
import com.dayalbagh.epay.model.Student;
import com.dayalbagh.epay.model.Studentfeereceipt;
import com.dayalbagh.epay.repository.PendingPaymentRepository;
import com.dayalbagh.epay.service.CertificateService;
import com.dayalbagh.epay.service.PrintService;
import com.dayalbagh.epay.service.SBIService;
import com.dayalbagh.epay.service.StudentService;

@Controller

public class PaymentController {

	@Autowired
	private SBIService sbiservice;

	@Autowired
	private StudentService studentservice;

	@Autowired
	private CertificateService certificateService;

	@Autowired
	private PendingPaymentRepository thePendingPaymentRepository;

	@Autowired
	private PrintService printService;

	@GetMapping("/makepayment")
	public String showForm(Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Student student = new Student();

		String totalfee = sbiservice.Aesdecrypt(request.getParameter("totalfee"));
		String Otherdetail = sbiservice.Aesdecrypt(request.getParameter("Otherdetail"));

		// for testing

		String merchantid = sbiservice.getMerchantId();

		String encryptdata = sbiservice.encrypt(totalfee, Otherdetail);

		System.out.println("encrypted String:" + encryptdata);

		System.out.println("Decrypted String:" + sbiservice.decrypt(encryptdata));

		// String decryptdata =sbiservice.decrypt(encryptdata);

		student.setEncryptTrans(encryptdata);
		student.setMerchIdVal(merchantid);

		model.addAttribute("student", student);
		// model.addAttribute("EncryptTrans",str1);

		return "payment_request";
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@PostMapping("/paymentsuccess")
	public String Paymentsuccess(@ModelAttribute("encData") String encData,
			@ModelAttribute("merchIdVal") String merchIdVal, @ModelAttribute("Bank_Code") String Bank_Code,
			@ModelAttribute("method") String method, Model model)

	{

		if (method.equalsIgnoreCase(""))
			method = "browserResponse";
		

		System.out.println("encData :" + method + encData);

		// String str1 = request.getAttribute("encData").toString();

		encData = encData.replaceAll(" ", "+");

		String str = sbiservice.decrypt(encData);
		System.out.println("decrypt  :" + str);

		String resdata[] = str.split("\\|");
		System.out.println("encData :" + resdata[0] + "data:" + encData);

		// Verify via Double verification
		String dvdata = sbiservice.verifyPayment(resdata);

		System.out.println("DV Data:" + dvdata);

		String[] dvdata_ary = dvdata.split("\\|");

		String trxstatus = dvdata_ary[2];
		String statusdesc = dvdata_ary[8];

		// Save Browser response and dvstatus

		Student student = sbiservice.savePayment(resdata, trxstatus, statusdesc, method);
		student.setMethod(method);
		// check if writing into payment table is error
		if (student.getMessage().equalsIgnoreCase("error")) {
			return "payment_failure";

		}

		// if double verification is success;
		System.out.println("payment success encrypted Data:" + encData);
		System.out.println("payment success Decrypted String:" + resdata);
        String paysts = trxstatus.toUpperCase();
		model.addAttribute("message", paysts);

		model.addAttribute("student", student);

		if (trxstatus.equalsIgnoreCase("Success")) {

			String category = student.getCategory();
			if (category.equalsIgnoreCase("CON") || category.equalsIgnoreCase("newadm")) {
				String studentfeestatus = studentservice.savestudentfee(student);
				if (studentfeestatus.equalsIgnoreCase("error"))
					return "payment_failure";
				if (student.getMethod().equalsIgnoreCase("browserResponse"))
					return "con_payment_success";
			}

			// For Application Fee

			if (category.equalsIgnoreCase("appfee")) {
				String studentfeestatus = studentservice.saveStudentAppfee(student);
				if (studentfeestatus.equalsIgnoreCase("error"))
					return "payment_failure";
			}

			if (category.equalsIgnoreCase("CER")) {
				String studentfeestatus = certificateService.savecertificateDetail(student);
				if (studentfeestatus.equalsIgnoreCase("error"))
					return "payment_failure";
			}

		}
		String category = student.getCategory();
		if (category.equalsIgnoreCase("CON") || category.equalsIgnoreCase("newadm")) {
		return "con_payment_success";}
//
//		if (trxstatus.equalsIgnoreCase("Pending"))
//			return "payment_pending";
//
//		if (trxstatus.equalsIgnoreCase("fail"))
//			return "payment_fail";

		return null;

	}

	@PostMapping("/pushpayment")
	public ModelAndView redirecttoPaymentSuccess(HttpServletRequest request,
			@ModelAttribute("pushRespData") String encData, @ModelAttribute("merchIdVal") String merchIdVal,
			@ModelAttribute("Bank_Code") String Bank_Code, Model model, RedirectAttributes redirectAttributes)

	{

		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		redirectAttributes.addAttribute("encData", encData);
		redirectAttributes.addAttribute("merchIdVal", merchIdVal);
		redirectAttributes.addAttribute("Bank_Code", Bank_Code);
		redirectAttributes.addAttribute("method", "pushresponse");
		System.out.println("in side push response");
		return new ModelAndView("redirect:/paymentsuccess");
	}

	@PostMapping("/paymentfailure")
	public ModelAndView paymentFailure(HttpServletRequest request,@ModelAttribute("encData") String encData,
			@ModelAttribute("merchIdVal") String merchIdVal, 
			@ModelAttribute("Bank_Code") String Bank_Code,
			Model model,
			 RedirectAttributes redirectAttributes) {

		System.out.print("inside payment failure");
		System.out.println("encrypted Data:" + encData);
		System.out.println("Decrypted String:" + sbiservice.decrypt(encData));
		System.out.println(merchIdVal);
		System.out.println(Bank_Code);
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		redirectAttributes.addAttribute("encData", encData);
		redirectAttributes.addAttribute("merchIdVal", merchIdVal);
		redirectAttributes.addAttribute("Bank_Code", Bank_Code);
		redirectAttributes.addAttribute("method", "failureresponse");
		System.out.println("in side push response");
		return new ModelAndView("redirect:/paymentsuccess");
		

		
	}

	@Transactional()
	@Scheduled(fixedRateString = "${run-frquency.minutes}", timeUnit = TimeUnit.MINUTES)
	public void processpendingpayment() {

		String status = "success";
		Payment payment = null;
		String dvdata = "";
		String[] dvdata_ary;
		List<PendingPayment> pendingpayment = new ArrayList<>();
		String data[] = new String[5];
		System.out.println("in side process pending payment method ");

		pendingpayment = thePendingPaymentRepository.findAllByTrxstatusNot(status);
		if (pendingpayment.size() > 0) {

			for (PendingPayment pending : pendingpayment) {
				data[0] = pending.getMerchant_Order_Number();
				data[1] = pending.getATRN();
				data[3] = pending.getAmount().toString();
				dvdata = sbiservice.verifyPayment(data);
				dvdata_ary = dvdata.split("\\|");
				String trxstatus = dvdata_ary[2];
				String statusdesc = dvdata_ary[8];
				pending.setTrxstatus(trxstatus);
				pending.setModifiedBy("processpendingpayment");

				pending.setModification_time(new Date());
				// pending.setProcess_time(null);

				thePendingPaymentRepository.save(pending);
				payment = sbiservice.findPaymentByMerchantorderno(pending.getMerchant_Order_Number());
				payment.setDv_status(trxstatus);
				payment.setStatusdescription(statusdesc);
				payment.setModifiedby("processpendingpayment");
				payment.setModification_time(new Date());

				sbiservice.updatePaymentstatus(payment);

				// if double verification is success;

				if (trxstatus.equalsIgnoreCase("Success")) {

					Student student = sbiservice.ParseDVResponse(dvdata_ary);
					student.setPayment(payment);
					student.setMethod("processpendingpayment");

					String category = student.getCategory();
					if (category.equalsIgnoreCase("CON") || category.equalsIgnoreCase("newadm")) {

						studentservice.savestudentfee(student);

					}

					// For Application Fee

					if (category.equalsIgnoreCase("appfee")) {
						studentservice.saveStudentAppfee(student);

					}

					if (category.equalsIgnoreCase("CER")) {
						certificateService.savecertificateDetail(student);

					}

				} else {

				}

			}

		}
	}

//	 @RequestMapping(method = RequestMethod.GET, value = "/explorer/file/{id:.+}", produces = "application/pdf")
//	 @ResponseBody
//	 public ResponseEntity<?> getFile(@PathVariable Integer id) {
//		 
//		 Path path = Paths.get(file.getAbsolutePath());
//	   //  String filename = explorerService.getById(id).getPath();
//	     try {
//	         return ResponseEntity.ok(resourceLoader.getResource("file:" + filename));
//	     } catch (Exception e) {
//	         return ResponseEntity.notFound().build();
//	     }
//	 }

	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download(Student student) throws IOException {

		String filepath = "";

		if (student.getCategory().equalsIgnoreCase("con")) {
			filepath = printService.exportContinuePDF(student);
		}

		String curdir = System.getProperty("user.dir");
		;
		File file = new File(filepath);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		// headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+
		// file.getName() + "\"");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
