package com.vgipl.server.apmc.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgipl.server.apmc.dao.VendorDao;
import com.vgipl.server.apmc.model.Category;
import com.vgipl.server.apmc.model.DeliveryAgency;
import com.vgipl.server.apmc.model.DeliveryBoy;
import com.vgipl.server.apmc.model.Help;
import com.vgipl.server.apmc.model.Inward;
import com.vgipl.server.apmc.model.KYC;
import com.vgipl.server.apmc.model.Market;
import com.vgipl.server.apmc.model.User;
import com.vgipl.server.apmc.model.UserImageData;
import com.vgipl.server.apmc.model.Vendor;
import com.vgipl.server.apmc.service.VendorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vendorservice;

	@Autowired
	VendorDao vendorDao;

//	@PostMapping("/insertProduct")
//	public String insertProduct(
//			 @RequestParam(value="category")String category,
//			 @RequestParam(value="productName")String productName,
//			 @RequestParam(value="unit")String unit) {
//		return vendorservice.insertProduct(category,productName,unit);
//	}

	@RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
	public @ResponseBody String getProductList(@RequestBody Help objHelp) {
		if (objHelp == null)
			return "null";

		System.out.println(objHelp.getCategory() + "::" + objHelp.getProductName() + "::" + objHelp.getUnit());

		return vendorservice.insertProduct(objHelp);
	}

	@RequestMapping(value = "/inwardEntry", method = RequestMethod.POST)
	public @ResponseBody String inwardEntry(@RequestBody Inward objInward) {
		if (objInward == null)
			return "null";

		System.out.println("INWARD ID CONTROLER==" + objInward.getInwardId() + "Amount" + objInward.getAmount()
				+ "FARMER ID==" + objInward.getFarmerId() + "DATE=" + objInward.getInwardDate() + "Vendor id"
				+ objInward.getVendorId());
		System.out.println("MKT Id==" + objInward.getMarketId() + "Product ID" + objInward.getProductId()
				+ "Price XML==" + objInward.getProductPrice() + "Qty=" + objInward.getQuantity() + "Rate="
				+ objInward.getRate() + "" + objInward.getUnitId());
		return vendorservice.inwardEntry(objInward);
	}

	@RequestMapping(value = "/getStock/{marketId}", method = RequestMethod.GET)
	public @ResponseBody String getStock(@PathVariable String marketId) {
		if (marketId == null)
			return "null";

		return vendorDao.getStock(marketId);
	}

	@RequestMapping(value = "/getStorewiseStock/{marketId}/{storeId}", method = RequestMethod.GET)
	public @ResponseBody String getStorewiseStock(@PathVariable String marketId,@PathVariable String storeId) {
		if (marketId == null)
			return "Please enter storeId";
		
		return vendorDao.getStorewiseStock(marketId, storeId);
	}
	
	@RequestMapping(value = "/getStorewiseStockWithAdd/{marketId}", method = RequestMethod.GET)
	public @ResponseBody String getStorewiseStockWithAdd(@PathVariable String marketId) {
		if (marketId == null)
			return "Please enter storeId";
		
		return vendorservice.getStorewiseStockWithAdd(marketId);
	}
	
	
	@RequestMapping(value = "/getStockBycategory/{attributeId}", method = RequestMethod.GET)
	public @ResponseBody String getStockBycategory(@PathVariable String attributeId) {
		if (attributeId == null)
			return "null";

		return vendorDao.getStockBycategory(attributeId);
	}

	@RequestMapping(value = "/getCompleteStock", method = RequestMethod.POST)
	public String getCompleteStockList() {

		return vendorservice.getCompleteStockList();
	}

	@RequestMapping(value = "/getproductAttribute", method = RequestMethod.POST)
	public @ResponseBody String getproductAttribute(@RequestBody Inward objInward) {
		if (objInward == null)
			return "null";

		return vendorservice.getproductAttribute(objInward);
	}

	@RequestMapping(value = "/getInwardDetails/{vendorId}", method = RequestMethod.GET)
	public @ResponseBody String getInwardDetails(@PathVariable String vendorId) {
		if (vendorId == null)
			return "null";

		return vendorDao.getInwardDetails(vendorId);
	}

	@RequestMapping(value = "/productPriceEntry", method = RequestMethod.POST)
	public @ResponseBody String doProductPriceEntry(@RequestBody Inward objInward) {
		if (objInward == null)
			return "null";

		return vendorDao.doProductPriceEntry(objInward);
	}

	@RequestMapping(value = "/getOrderReport/{vendorId}", method = RequestMethod.GET)
	public @ResponseBody String getOrderResport(@PathVariable String vendorId) {
		if (vendorId == null)
			return "null";

		return vendorservice.getOrderResport(vendorId);
	}

	@RequestMapping(value = "/createDeliveryBoy", method = RequestMethod.POST)
	public @ResponseBody String createDeliveryBoy(@RequestBody DeliveryBoy objDeliveryBoy) {
		System.out.println("objDeliveryBoy----"+objDeliveryBoy.toString());
		if (objDeliveryBoy == null)
			return "null";

		return vendorservice.createDeliveryBoy(objDeliveryBoy);
	}

	@RequestMapping(value = "/createDeliveryAgency", method = RequestMethod.POST)
	public @ResponseBody String createDeliveryAgency(@RequestBody DeliveryAgency objDeliveryAgency) {
		if (objDeliveryAgency == null)
			return "null";

		return vendorservice.createDeliveryAgency(objDeliveryAgency);
	}

	@RequestMapping(value = "/postKycDetails", method = RequestMethod.POST)
	public @ResponseBody String postKycDetails(@RequestBody KYC objKyc) {
		if (objKyc == null)
			return "null";

		System.out.println("father name in controller" + objKyc.getFathersName());

		return vendorservice.postKycDetails(objKyc);
	}

	@RequestMapping(value = "/getDeliveryAgencyDetails", method = RequestMethod.GET)
	public @ResponseBody String getDeliveryAgencyDetails() {

		return vendorDao.getDeliveryAgencyDetails();
	}

	@RequestMapping(value = "/getAreaHelp", method = RequestMethod.GET)
	public @ResponseBody String getAreaHelp() {

		return vendorDao.getAreaHelp();
	}

	@RequestMapping(value = "/getDetailReport/{orderId}", method = RequestMethod.GET)
	public @ResponseBody String getDetailReport(@PathVariable String orderId) {
		if (orderId == null)
			return null;

		return vendorDao.getDetailReport(orderId);
	}

	@RequestMapping(value = "/getDeliveryBoyList", method = RequestMethod.GET)
	public @ResponseBody String getDeliveryBoyList() {

		return vendorDao.getDeliveryBoyList();
	}

	@PutMapping("/allocateDeliveryBoy/{orderId}/{deliveryBoyId}")
	public @ResponseBody String allocateDeliveryBoy(@PathVariable String orderId, @PathVariable String deliveryBoyId) {
		return vendorDao.allocateDeliveryBoy(orderId, deliveryBoyId);
	}

	@PutMapping("/updateKycStatus/{deliveryBoyId}")
	public @ResponseBody String updateKycStatus(@PathVariable String deliveryBoyId) {
		return vendorDao.updateKycStatus(deliveryBoyId);
	}

	@RequestMapping(value = "/insertNewAttribute/{attribute}", method = RequestMethod.POST)
	public @ResponseBody String insertNewAttribute(@PathVariable String attribute) {
		if (attribute == null)
			return "no attribute found";

		return vendorservice.insertNewAttribute(attribute);
	}

	@RequestMapping(value = "/assignAttributeToProduct/{attribute}", method = RequestMethod.POST)
	public @ResponseBody String assignAttributeToProduct(@PathVariable String attribute) {
		if (attribute == null)
			return "no attribute found";

		return vendorservice.assignAttributeToProduct(attribute);
	}
	
	@RequestMapping(value = "/updateOrderStatus/{orderId}/{statusFlag}/{statusRemarks}", method = RequestMethod.GET)
	public @ResponseBody String updateOrderStatus(@PathVariable String orderId,@PathVariable String statusFlag,@PathVariable String statusRemarks) {
		if (orderId == null || statusFlag==null || statusRemarks ==null)
			return "no attribute found";

		return vendorservice.updateOrderStatus(orderId,statusFlag,statusRemarks);
	}
	
	

	@RequestMapping(value = "/getInwardByProdId", method = RequestMethod.POST)
	public @ResponseBody String getInwardByProdId(@RequestBody Inward objInward) {
		if (objInward == null)
			return "no inward object found";

		return vendorservice.getInwardByProdId(objInward);
	}

	@RequestMapping(value = "/getAttrwiseStock", method = RequestMethod.POST)
	public @ResponseBody String getAttrwiseStock(@RequestBody Inward objInward) {
		if (objInward == null)
			return "no  object found";

		return vendorservice.getAttrwiseStock(objInward);
	}

	@RequestMapping(value = "/postGradewiseStock", method = RequestMethod.POST)
	public @ResponseBody String postGradewiseStock(@RequestBody Inward objInward) {
		if (objInward == null)
			return "no inward object found";

		return vendorservice.postGradewiseStock(objInward);
	}

	@PutMapping("/verifyDeliveryBoy/{deliveryBoyId}")
	public @ResponseBody String verifyDeliveryBoy(@PathVariable String deliveryBoyId) {

		return vendorservice.verifyDeliveryBoy(deliveryBoyId);
	}

	@RequestMapping(value = "/getListForKYCVerification", method = RequestMethod.GET)
	public @ResponseBody String getListForKYCVerification() {

		return vendorDao.getListForKYCVerification();
	}

	@RequestMapping(value = "/postImage", method = RequestMethod.POST)
	public @ResponseBody String postImage(@RequestBody UserImageData objImgData) {
		if (objImgData.getStrImageData() == null || objImgData.getDeliveryBoyId()==null )
			return "no imgBase or clientId object found";

		return vendorDao.postImage(objImgData);
	}

}
