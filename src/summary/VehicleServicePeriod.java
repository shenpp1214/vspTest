package summary;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class VehicleServicePeriod extends BaseService {

	@Test
	public void vehServicePeriod() throws Exception {
		entryPage("统计报表", "服务期统计");// 进入页面
		search();// 搜索测试
		serviceBalanceMana();// 服务期余额管理
		exportData();// 导出数据校验
		openAlertExport();// 打开小弹框并导出检验excel
	}

	private void search() throws InterruptedException {
		assertEquals("赠送服务期余额(月)", dr.findElement(By.xpath("//div[@id='main_content']/table//th[1]")).getText());
		assertEquals("续约服务期余额(月)", dr.findElement(By.xpath("//div[@id='main_content']/table//th[2]")).getText());
		select("periodMode", "按年查询", 1500);
		assertEquals(false, dr.findElement(By.id("searchMonth")).isDisplayed());
		assertEquals(true, dr.findElement(By.id("searchYear")).isDisplayed());

		clickEle("//*[@id='searchOrgName']", 1000);
		clickEle("//ul[@id='treeSearch']//ul/li[1]//span", 2000);// 选择组织机构
		clickEle("//*[@id='searchBtn']", 3000);// 搜索
	}

	private void serviceBalanceMana() throws InterruptedException {
		clickEle("//*[@id='serviceBalanceMana']", 2000);
		clickQuery("警告 请选择调入机构");
		clickEle("//*[@id='deptIn']", 1000);
		clickEle("//ul[@id='treeSelectIn']/li/ul/li[1]//span", 2000);
		clickQuery("警告 请正确填写调配服务期");
		closePrompt("serviceBalanceManaPanel", 2, 1000);
	}

	private void exportData() throws Exception {
		clickEle("//*[@id='downloadSummary']", 3500);// 搜索

		assertEquals("组织机构", getCellValue1("sheet1", 1, 1));
		assertEquals("河南锐众汽车有限公司", getCellValue1("sheet1", 2, 1));
		assertEquals("合同自动到期车辆数(个)", getCellValue1("sheet1", 1, 2));
		assertEquals("3年期设备（绑定）", getCellValue1("sheet1", 1, 8));
	}

	private void openAlertExport() throws Exception {
		List<WebElement> elements = dr.findElements(By.xpath("//div[@id='home']/table/tbody//td"));
		String[] str = { "", "serviceEndDateVehicleDetail", "", "", "servicePeriodConsumptionDetail", "oneYearBind", "",
				"", "", "fiveYearBind", "" };
		for (int i = 1; i < elements.size() - 6; i = i + 3) {
			elements.get(i).click();
			sleep(1000);
			clickEle("//div[@id='" + str[i] + "']/ul//button", 3000);

			assertEquals("用户姓名", getCellValue1("sheet1", 1, 1));
			assertEquals("手机号码", getCellValue1("sheet1", 1, 2));
			assertEquals("车牌号码", getCellValue1("sheet1", 1, 3));
			closePrompt(str[i], 1, 1000);
		}

		for (int i = 5; i < elements.size(); i = i + 4) {
			elements.get(i).click();
			sleep(1000);
			clickEle("//div[@id='" + str[i] + "']/ul//button", 3000);

			assertEquals("设备编号", getCellValue1("sheet1", 1, 1));
			assertEquals("设备服务期年限", getCellValue1("sheet1", 1, 2));
			assertEquals("绑定状态", getCellValue1("sheet1", 1, 3));
			closePrompt(str[i], 1, 1000);
		}
	}

	protected void clickQuery(String text) throws InterruptedException {
		closePrompt("serviceBalanceManaPanel", 1, 1000);
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}
}
