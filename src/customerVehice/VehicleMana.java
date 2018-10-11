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
		downloadTemplate();// 下载导入模板
	}

	private void searchData() throws InterruptedException {
		select("searchBrand", "奔驰", 2000);
		select("searchProductId", "R级（进口）", 1500);
		select("searchBindStatus", "空闲", 1000);
		select("searchDrivingRecordSwitch", "否", 1000);
		select("searchOwnerType", "个人", 1000);
		clickEle("//*[@id='ownerSearch']", 3000);
		clearInp("createUserName", "shenpp1214");
		clickEle("//*[@id='searchWebUserBtn']", 2000);
		clickEle("//*[@id='searchWebUserList']//tr/td[4]/input", 1000);
		clearInp("searchLpno", "苏J01234");
		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void vehicleDetail() throws InterruptedException {
		clickEle("//*[@name='viechlDetail']", 3000);
		switchPage("销售信息", "1");
		switchPage("基本信息", "0");
		switchPage("保险信息", "2");
		closePrompt("viechlDetail", 1, 1500);// 关闭弹框
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
	}

	private void downloadTemplate() throws Exception {
		clickEle("//*[@id='excelImportTplDownload']", 2000);
		assertEquals("姓名（必填）", getCellValue("Sheet1", 1, 1));
		assertEquals("手机号码（必填）", getCellValue("Sheet1", 1, 2));
		assertEquals("车牌号码（必填）", getCellValue("Sheet1", 1, 6));
		assertEquals("车架号（非必填）", getCellValue("Sheet1", 1, 7));
		assertEquals("车辆品牌（必填）", getCellValue("Sheet1", 1, 9));
	}

	protected void switchPage(String t, String num) throws InterruptedException {
		clickEle("//a[text()='" + t + "']", 2000);

		assertEquals(t, dr.findElement(By.xpath("//a[@name='data_tab' and @val= '" + num + "']")).getText());
	}
}
