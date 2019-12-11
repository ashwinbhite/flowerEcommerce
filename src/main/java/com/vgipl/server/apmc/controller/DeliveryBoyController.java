package com.vgipl.server.apmc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.DeliveryBoyDao;
import com.vgipl.server.apmc.model.DeliveryBoy;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/delivery")
public class DeliveryBoyController {

	@Autowired
	DeliveryBoyDao deliveryBoyDao;

	@RequestMapping(value = "/doLoginDBoy", method = RequestMethod.POST)
	public @ResponseBody String doLoginDBoy(@RequestBody DeliveryBoy objDBoy) {
		if (objDBoy == null)
			return "null";

		return deliveryBoyDao.doLoginDBoy(objDBoy);
	}

	@RequestMapping(value = "/getOrderDetails/{orderId}/{marketId}", method = RequestMethod.GET)
	public @ResponseBody String getOrderDetails(@PathVariable String orderId, @PathVariable String marketId) {
		if (orderId == null || marketId == null)
			return "null";

		System.out.println("orderId" + orderId + "marketId" + marketId);

		return deliveryBoyDao.getOrderDetails(orderId, marketId);
	}

	@PutMapping("/updateOrderDetails/{orderId}/{deliveryStatus}")
	public String updateOrderDetails(@PathVariable String orderId, @PathVariable String deliveryStatus) {
		return deliveryBoyDao.updateOrderDetails(orderId, deliveryStatus);

	}

	@RequestMapping(value = "/getOrderIdList/{marketId}", method = RequestMethod.GET)
	public @ResponseBody String getOrderIdList(@PathVariable String marketId) {
		if (marketId == null)
			return "null";

		System.out.println("marketId" + marketId);

		return deliveryBoyDao.getOrderIdList(marketId);
	}

	@RequestMapping(value = "/getDeliveryCharges/{weight}/{distance}/{unitid}", method = RequestMethod.GET)
	public @ResponseBody String getDeliveryCharges(@PathVariable Double weight, @PathVariable Double distance,
			@PathVariable Integer unitid) {
		return deliveryBoyDao.getDeliveryCharges(weight, distance, unitid);
	}

	@RequestMapping(value = "/getDeliveryOrdersList/{dBoyId}", method = RequestMethod.GET)
	public @ResponseBody String getDeliveryOrdersList(@PathVariable String dBoyId) {
		if (dBoyId == null)
			return "Id not Present";

		return deliveryBoyDao.getDeliveryOrdersList(dBoyId);
	}

	@RequestMapping(value = "/getDetailsByOrderId/{orderId}", method = RequestMethod.GET)
	public @ResponseBody String getDetailsByOrderId(@PathVariable String orderId) {
		if (orderId == null)
			return "Id not Present";
		return deliveryBoyDao.getDetailsByOrderId(orderId);

	}

	@PutMapping("/updateDeliveryStatus/{orderId}")
	public String updateDeliveryStatus(@PathVariable String orderId) {
		if (orderId == null)
			return "record not Present";
		return deliveryBoyDao.updateDeliveryStatus(orderId);

	}

	@RequestMapping(value = "/getKycStatus/{deliveryBoyId}", method = RequestMethod.GET)
	public @ResponseBody String getKycStatus(@PathVariable String deliveryBoyId) {
		if (deliveryBoyId == null)
			return "Id not Present";
		return deliveryBoyDao.getKycStatus(deliveryBoyId);

	}

	@RequestMapping(value = "/genOtp/{mobileNO}", method = RequestMethod.GET)
	public @ResponseBody String genOtp(@PathVariable String mobileNO) {
		if (mobileNO == null)
			return "mobileNO not Present";
		return deliveryBoyDao.genOtp(mobileNO);

	}

	
	  @RequestMapping(value ="/getKycDetails/{dboyId}" , method = RequestMethod.GET) 
	  public @ResponseBody String getKycDetails(@PathVariable String dboyId){
		  if( dboyId==null) return "dboyId not Present";
	  return deliveryBoyDao.getKycDetails(dboyId);
	  
	  }
	 

	@GetMapping("/verifyUploadingStatus/{dboyId}")
	public String verifyUploadingStatus(@PathVariable String dboyId) {
		return deliveryBoyDao.verifyUploadingStatus(dboyId);
	}
	
	
	@GetMapping("/getDboyPhoto/{dboyId}")
	public String getDboyPhoto(@PathVariable String dboyId) {
		return deliveryBoyDao.getDboyPhoto(dboyId);
	}

	/*
	 * @RequestMapping(value = "/getDboyPhoto/{dboyId}", method =
	 * RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE) public
	 * ResponseEntity<byte[]> getImage(@PathVariable String dboyId ) throws
	 * IOException {
	 * 
	 * // var imgFile = new ClassPathResource("image/sid.jpg"); // byte[] bytes =
	 * StreamUtils.copyToByteArray(imgFile.getInputStream());
	 * 
	 * // File filepath = new
	 * File("E:\\Ashwin Workspace\\imageForWMkt\\license\\3.jpg"); // BufferedImage
	 * bufferedImage = ImageIO.read(filepath); // // ByteArrayOutputStream baos =
	 * new ByteArrayOutputStream(); // ImageIO.write( bufferedImage, "jpg", baos );
	 * // baos.flush(); // byte[] imageInByte = baos.toByteArray(); // baos.close();
	 * 
	 * // File imgFile = new
	 * ClassPathResource("E:\\Ashwin Workspace\\imageForWMkt\\license\\3.jpg").
	 * getFile();
	 * 
	 * File docFile = new
	 * File("E:\\Ashwin Workspace\\imageForWMkt\\license\\3.jpg"); BufferedImage
	 * image = ImageIO.read(docFile); ByteArrayOutputStream baos = new
	 * ByteArrayOutputStream(); ImageIO.write(image, "jpg", baos); byte[]
	 * imageInByte = baos.toByteArray();
	 * 
	 * 
	 * 
	 * return ResponseEntity .ok() .contentType(MediaType.IMAGE_JPEG)
	 * .body(imageInByte); }
	 */
	
}
