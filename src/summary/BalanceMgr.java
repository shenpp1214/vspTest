package summary;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class BalanceMgr extends BaseService {
	public static final String balanceNo = "170930105000530354";// 结算单号
	public static final String balanceNo1 = "170930105000745199";// 结算单号
	public static final String merchantname = "spp小店铺大开心";// (商户结算-商户名称)

	@Test
	public void salesRankings() throws Exception {
		entryPage("统计报表", "结算管理");
		platformSettlement();// 平台结算测试
		merchantSettlement();// 商户结算测试
	}

	private void platformSettlement() throws Exception {
		clearInp("searchMonth", "2017-08");
		clearInp("balanceNo", balanceNo);
		searchTest("searchBtn", "pages");// 搜索测试
		clickEle("//*[@id='exportBalanceBtn']", 3000);// 导出平台结算数据校验测试

		String amountReceivable = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[3]")).getText();// 应收金额
		String serviceCharge = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[4]")).getText();// 服务费
		String amountCollected = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[5]")).getText();// 实收金额

		assertEquals(true,
				Double.valueOf(amountReceivable) == Double.valueOf(amountCollected) + Double.valueOf(serviceCharge));
		assertEquals(balanceNo, getCellValue1("Sheet0", 2, 1));
		assertEquals("2017-08", getCellValue1("Sheet0", 2, 2));
		assertEquals(amountReceivable, getCellValue1("Sheet0", 2, 3));
		assertEquals(serviceCharge, getCellValue1("Sheet0", 2, 4));
		assertEquals(amountCollected, getCellValue1("Sheet0", 2, 5));

		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索测试
	}

	private void merchantSettlement() throws Exception {
		clickEle("//a[text()='商户结算']", 2000);// 进入商户结算界面
		clearInp("searchMerName", merchantname);
		clearInp("searchMonth", "2017-08");
		clearInp("balanceNo", balanceNo1);
		searchTest("searchBtn", "pages");
		operate();// 操作
		lookOrder();// 查看订单+详情
		clickEle("//*[@id='exportBalanceBtn']", 3000);// 导出数据

		String amountReceivable = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[5]")).getText();// 应收金额
		String serviceCharge = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[6]")).getText();// 服务费
		String amountCollected = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[7]")).getText();// 实收金额
		String status = dr.findElement(By.xpath("//*[@id='staticList']//tr/td[8]")).getText();// 状态

		assertEquals(true,
				Double.valueOf(amountReceivable) == Double.valueOf(amountCollected) + Double.valueOf(serviceCharge));
		assertEquals(balanceNo1, getCellValue1("Sheet0", 2, 1));
		assertEquals("2017-08", getCellValue1("Sheet0", 2, 2));
		assertEquals(merchantname, getCellValue1("Sheet0", 2, 3));
		assertEquals(amountReceivable, getCellValue1("Sheet0", 2, 4));
		assertEquals(serviceCharge, getCellValue1("Sheet0", 2, 5));
		assertEquals(amountCollected, getCellValue1("Sheet0", 2, 6));
		assertEquals(status, getCellValue1("Sheet0", 2, 7));

		resetTest("resetBtn", "searchBtn", "pages");// 重置搜索测试
	}

	protected void operate() throws InterruptedException {
		clickEle("//*[@id='staticList']//tr[1]//a[1]", 2000);

		String fee2 = dr.findElement(By.id("balanceTotalPay")).getText();

		modifyMoney("6.66");// 修改金额
		clearInp("balanceNo4Handle", "2017123456481001");
		clickEle("//*[@id='confirmBtn']", 2000);

		assertEquals("错误 请先等待商户确认结算单结果", dr.findElement(By.className("noty_text")).getText());

		modifyMoney(fee2);// 修改金额
		clickEle("//span[text()='结算操作']/following-sibling::button", 2000);// 关闭操作界面
	}

	protected void modifyMoney(String fee) throws InterruptedException {
		clickEle("//*[@id='modFeeBtn']", 2000);// 修改金额
		clearInp("balanceFee4Mod", fee);
		clearInp("reason4Mod", "修改金额测试");
		closePrompt("modFeePanel", 1, 1500);

		assertEquals(fee, dr.findElement(By.id("balanceTotalPay")).getText());
	}

	protected void lookOrder() throws Exception {
		clickEle("//*[@id='staticList']//tr[1]//a[2]", 2000);
		String orderNum = dr.findElement(By.xpath("//*[@id='mainContentList1']//tr[1]/td[2]")).getText();
		String orderName = dr.findElement(By.xpath("//*[@id='mainContentList1']//tr[1]/td[7]")).getText();
		String orderType = dr.findElement(By.xpath("//*[@id='mainContentList1']//tr[1]/td[9]")).getText();

		clearInp("searchSpOrderId", orderNum);
		clearInp("searchSpName4Order", orderName);
		select("payType", orderType, 1000);
		searchTest("searchBtn4Order", "pages4Order");// 搜索测试
		clickEle("//*[@id='order_exportBtn']", 3000);// 导出数据

		assertEquals(orderNum, getCellValue1("Sheet0", 2, 1));
		assertEquals(orderName, getCellValue1("Sheet0", 2, 6));
		assertEquals(orderType, getCellValue1("Sheet0", 2, 8));

		clickEle("//*[@id='mainContentList1']//tr[1]//a", 2000);// 查看订单详情

		assertEquals(orderNum, dr.findElement(By.id("orderIdInfo")).getAttribute("value"));
		assertEquals(orderName, dr.findElement(By.id("goodsNameInfo")).getAttribute("value"));

		closePrompt("orderDetailPanel", 1, 1500);// 关闭详情页
		closePrompt("orderListPanel", 1, 1500);// 关闭订单列表界面
	}
}
