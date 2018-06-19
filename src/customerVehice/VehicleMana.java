package customerVehice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class VehicleMana extends BaseService {

	@Test
	public void vehicleMana() throws Exception {
		entryPage("客户车辆", "车辆档案");// 进入企业客户界面
		searchData();// 搜索数据
		vehicleDetail();// 操作 - 查看车辆详情
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
		downloadTemplate();// 下载导入模板
	}

	private void searchData() throws InterruptedException {
		select("searchBrand", "奔驰");
		select("searchProductId", "R级（进口）");
		select("searchBindStatus", "空闲");
		select("searchDrivingRecordSwitch", "否");
		select("searchOwnerType", "个人");

		dr.findElement(By.id("ownerSearch")).click();
		sleep(3000);
		dr.findElement(By.id("createUserName")).sendKeys("shenpp1214");
		dr.findElement(By.id("searchWebUserBtn")).click();
		sleep(2000);
		dr.findElement(By.xpath("//*[@id='searchWebUserList']/tbody/tr/td[4]/input")).click();
		dr.findElement(By.id("searchLpno")).sendKeys("苏J01234");

		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void vehicleDetail() throws InterruptedException {
		dr.findElement(By.name("viechlDetail")).click();
		sleep(3000);

		switchPage("销售信息", "1");
		switchPage("基本信息", "0");
		switchPage("保险信息", "2");
		closePrompt("viechlDetail", 1);// 关闭弹框
	}

	private void downloadTemplate() throws Exception {
		dr.findElement(By.id("excelImportTplDownload")).click();// 下载导入模板
		sleep(2000);

		assertEquals("姓名（必填）", getCellValue("Sheet1", 1, 1));
		assertEquals("手机号码（必填）", getCellValue("Sheet1", 1, 2));
		assertEquals("车牌号码（必填）", getCellValue("Sheet1", 1, 6));
		assertEquals("车架号（非必填）", getCellValue("Sheet1", 1, 7));
		assertEquals("车辆品牌（必填）", getCellValue("Sheet1", 1, 9));
	}

	protected void switchPage(String text, String num) throws InterruptedException {
		dr.findElement(By.linkText(text)).click();
		sleep(2000);

		assertEquals(text, dr.findElement(By.xpath("//a[@name='data_tab' and @val= '" + num + "']")).getText());
	}

}
