package customerVehice;

import org.junit.Test;
import org.openqa.selenium.By;
import static org.junit.Assert.*;

import baseService.BaseService;

public class CorpCustomer extends BaseService {

	@Test
	public void corpCustomer() throws InterruptedException {
		entryPage("客户车辆", "企业客户");// 进入企业客户界面
		searchData();// 搜索数据
		openHideTest();// 隐藏展开测试
		clickEle("//a[@title='Go to page 3']", 4000);

		assertEquals("3", dr.findElement(By.xpath("//div[@id='page']//li[@class='active']/a")).getText());
	}

	private void searchData() throws InterruptedException {
		String corpname = dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[2]")).getText();
		String shortname = dr.findElement(By.xpath("//*[@id='mainContentList']//tr[1]/td[3]")).getText();

		clickEle("//*[@id='searchDepart']", 1000);
		clickEle("//span[text()='迪纳运营']", 2000);
		clearInp("corpName", corpname);
		clearInp("corpDomain", shortname);
		searchTest("searchBtn", "pages");// 搜索测试
		resetTest("resetBtn", "searchBtn", "pages");// 重置测试
	}

	private void openHideTest() throws InterruptedException {
		clickEle("//div[@id='j-searchPanel']/div[2]", 1500);
		assertEquals(false, dr.findElement(By.id("searchBtn")).isDisplayed());// 隐藏搜索
		clickEle("//div[@id='j-searchPanel']/div[2]", 1500);
		assertEquals(true, dr.findElement(By.id("searchBtn")).isDisplayed());// 展开搜索
	}
}
