package summary;

import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class BalanceMgr extends BaseService {

	@Test
	public void salesRankings() throws Exception {
		entryPage("统计报表", "结算管理");
		platformSettlement();// 平台结算测试
		merchantSettlement();// 商户结算测试
	}

	private void platformSettlement() throws Exception {
		dr.findElement(By.id("searchMonth")).sendKeys(SeleniumConstants.searchMonth);
		dr.findElement(By.id("balanceNo")).sendKeys(SeleniumConstants.balanceNo);

		searchTest("searchBtn", "pages");// 搜索测试
		exportData("exportBalanceBtn");// 导出平台结算数据校验测试
		String amountReceivable = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[3]")).getText();// 应收金额
		String serviceCharge = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[4]")).getText();// 服务费
		String amountCollected = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[5]")).getText();// 实收金额
		if ((Double.valueOf(amountCollected) + Double.valueOf(serviceCharge)) != Double.valueOf(amountReceivable))
			fail();

		assertEquals(SeleniumConstants.balanceNo, getCellValue1("sheet0", 2, 1));
		assertEquals(SeleniumConstants.searchMonth, getCellValue1("sheet0", 2, 2));
		assertEquals(amountReceivable, getCellValue1("sheet0", 2, 3));
		assertEquals(serviceCharge, getCellValue1("sheet0", 2, 4));
		assertEquals(amountCollected, getCellValue1("sheet0", 2, 5));
		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索测试
	}

	private void merchantSettlement() throws Exception {
		entryLinkPage("商户结算");// 进入商户结算界面
		dr.findElement(By.id("searchMerName")).sendKeys(SeleniumConstants.merchantname);
		dr.findElement(By.id("searchMonth")).sendKeys(SeleniumConstants.searchMonth);
		dr.findElement(By.id("balanceNo")).sendKeys(SeleniumConstants.balanceNo1);

		searchTest("searchBtn", "pages");
		operate();// 操作
		lookOrder();// 查看订单+详情
		exportData("exportBalanceBtn");// 导出数据
		String amountReceivable = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[5]")).getText();// 应收金额
		String serviceCharge = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[6]")).getText();// 服务费
		String amountCollected = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[7]")).getText();// 实收金额
		String status = dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr/td[8]")).getText();// 状态
		if ((Double.valueOf(amountCollected) + Double.valueOf(serviceCharge)) != Double.valueOf(amountReceivable))
			fail();

		assertEquals(SeleniumConstants.balanceNo1, getCellValue1("sheet0", 2, 1));
		assertEquals(SeleniumConstants.searchMonth, getCellValue1("sheet0", 2, 2));
		assertEquals(SeleniumConstants.merchantname, getCellValue1("sheet0", 2, 3));
		assertEquals(amountReceivable, getCellValue1("sheet0", 2, 4));
		assertEquals(serviceCharge, getCellValue1("sheet0", 2, 5));
		assertEquals(amountCollected, getCellValue1("sheet0", 2, 6));
		assertEquals(status, getCellValue1("sheet0", 2, 7));
		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索测试
	}

	protected void operate() throws InterruptedException {
		entryLinkPage("操作");
		String fee2 = dr.findElement(By.id("balanceTotalPay")).getText();
		modifyMoney(SeleniumConstants.fee1);// 修改金额

		dr.findElement(By.id("balanceNo4Handle")).sendKeys(SeleniumConstants.serialNum);
		dr.findElement(By.id("confirmBtn")).click();
		sleep(2000);

		assertEquals("错误 请先等待商户确认结算单结果", dr.findElement(By.className("noty_text")).getText());
		modifyMoney(fee2);// 修改金额
		closePage("结算操作");// 关闭操作界面
	}

	protected void modifyMoney(String fee) throws InterruptedException {
		dr.findElement(By.id("modFeeBtn")).click();// 修改金额
		sleep(2000);

		dr.findElement(By.id("balanceFee4Mod")).clear();
		dr.findElement(By.id("balanceFee4Mod")).sendKeys(fee);
		dr.findElement(By.id("reason4Mod")).clear();
		dr.findElement(By.id("reason4Mod")).sendKeys(SeleniumConstants.reason);

		closePrompt("modFeePanel", 1);
		assertEquals(fee, dr.findElement(By.id("balanceTotalPay")).getText());
	}

	protected void lookOrder() throws Exception {
		entryLinkPage("查看订单");
		String orderNum = dr.findElement(By.xpath("//*[@id='mainContentList1']/table/tbody/tr[1]/td[2]")).getText();
		String orderName = dr.findElement(By.xpath("//*[@id='mainContentList1']/table/tbody/tr[1]/td[7]")).getText();
		String orderType = dr.findElement(By.xpath("//*[@id='mainContentList1']/table/tbody/tr[1]/td[9]")).getText();

		dr.findElement(By.id("searchSpOrderId")).sendKeys(orderNum);
		dr.findElement(By.id("searchSpName4Order")).sendKeys(orderName);
		sleep(2000);

		select("payType", orderType);
		searchTest("searchBtn4Order", "pages4Order");// 搜索测试
		exportData("order_exportBtn");// 导出数据
		assertEquals(orderNum, getCellValue1("Sheet0", 2, 1));
		assertEquals(orderName, getCellValue1("Sheet0", 2, 6));
		assertEquals(orderType, getCellValue1("Sheet0", 2, 8));
		orderDetail(orderNum, orderName, orderType);// 查看订单详情
		closePage("订单列表");// 关闭订单列表界面
	}

	protected void orderDetail(String oNum, String oName, String oType) throws InterruptedException {
		entryLinkPage("订单详情");
		assertEquals(oNum, dr.findElement(By.id("orderIdInfo")).getAttribute("value"));
		assertEquals(oName, dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));
		closePrompt("orderDetailPanel", 1);
	}

	protected void exportData(String eid) throws InterruptedException {
		dr.findElement(By.id(eid)).click();
		sleep(3000);
	}

	protected void entryLinkPage(String linkText) throws InterruptedException {
		dr.findElement(By.linkText(linkText)).click();
		sleep(2000);
	}

	protected void closePage(String sText) throws InterruptedException {
		dr.findElement(By.xpath("//span[text()='" + sText + "']/following-sibling::button")).click();
		sleep(2000);
	}
}
