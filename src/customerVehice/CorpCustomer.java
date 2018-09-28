package customerVehice;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class CorpCustomer extends BaseService {

	@Test
	public void corpCustomer() throws InterruptedException {
		entryPage("客户车辆", "企业客户");// 进入企业客户界面
		searchData();// 搜索数据
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
		openHideTest();// 隐藏展开测试
		flip();// 翻页
	}

	private void searchData() throws InterruptedException {
		String corpname = dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr[1]/td[2]")).getText();
		String shortname = dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr[1]/td[3]")).getText();

		dr.findElement(By.id("searchDepart")).click();
		dr.findElement(By.xpath("//span[text()='迪纳运营']")).click();
		sleep(2000);
		dr.findElement(By.id("corpName")).sendKeys(corpname);
		dr.findElement(By.id("corpDomain")).sendKeys(shortname);

		searchTest("searchBtn", "pages");// 搜索测试
	}

	private void openHideTest() throws InterruptedException {
		openHide(false);// 隐藏搜索
		openHide(true);// 展开搜索
	}
}
