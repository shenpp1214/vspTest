package summary;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;

public class AppClickStatic extends BaseService {
	public static final String beDate = "2017-05-17";

	@Test
	public void appClickStatic() throws Exception {
		entryPage("统计报表", "功能点击统计");
		customTime();// 自定义时间+导出数据
		switchPages();// 切换tab页+导出数据
	}

	private void customTime() throws Exception {
		clickEle("//*[@id='customSet']", 1000);
		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		clearInp("searchBeginDate", beDate);
		clearInp("searchEndDate", beDate);
		clickEle("//*[@id='customSetSubmit']", 2000);
		exportData();// 导出数据
	}

	private void switchPages() throws Exception {
		clickEle("//a[text()='路眼']", 2000);// 进入路眼界面
		exportData();// 导出数据
		clickEle("//a[text()='汽车后服务']", 2000);// 进入汽车后服务
		exportData();// 导出数据
	}

	protected void exportData() throws Exception {
		clickEle("//*[@id='downloadSummary']", 3000);

		String fun = dr.findElement(By.xpath("//*[@id='maintainList']//tr[1]/td[5]")).getText();
		String times = dr.findElement(By.xpath("//*[@id='maintainList']//tr[1]/td[6]")).getText();
		String oneUser = dr.findElement(By.xpath("//*[@id='maintainList']//tr[1]/td[7]")).getText();

		assertEquals(fun, getCellValue1("Sheet0", 2, 2));
		assertEquals(times + ".0", getCellValue1("Sheet0", 2, 3));
		assertEquals(oneUser + ".0", getCellValue1("Sheet0", 2, 4));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('" + outid + "').removeAttribute('readonly');");
	}

	protected void clearInp(String id, String d) throws InterruptedException {
		dr.findElement(By.id(id)).clear();
		dr.findElement(By.id(id)).sendKeys(d);
		sleep(1500);
	}

	protected void clickEle(String xid, int sec) throws InterruptedException {
		dr.findElement(By.xpath(xid)).click();
		sleep(sec);
	}

	@After
	public void logoutAppClickStatic() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}

}
