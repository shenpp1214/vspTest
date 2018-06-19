package summary;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class AppClickStatic extends BaseService {

	@Test
	public void appClickStatic() throws Exception {
		entryPage("统计报表", "功能点击统计");
		checkData();// 校验时间
		customTime();// 自定义时间+导出数据
		switchPages();// 切换tab页+导出数据
	}

	private void checkData() throws InterruptedException {
		String str[] = { "昨天", "最近七天", "本月" };
		for (String s : str) {
			dr.findElement(By.xpath("//button[text()='" + s + "']")).click();
			sleep(2000);
		}
	}

	private void customTime() throws Exception {
		dr.findElement(By.id("customSet")).click();

		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");

		dr.findElement(By.id("searchBeginDate")).clear();
		dr.findElement(By.id("searchEndDate")).clear();
		dr.findElement(By.id("searchBeginDate")).sendKeys(SeleniumConstants.beDate);
		dr.findElement(By.id("searchEndDate")).sendKeys(SeleniumConstants.beDate);
		sleep(2000);
		dr.findElement(By.id("customSetSubmit")).click();
		sleep(2000);
		exportData();// 导出数据
	}

	private void switchPages() throws Exception {
		switchPage("路眼");// 进入路眼界面
		exportData();// 导出数据
		switchPage("汽车后服务");// 进入汽车后服务
		exportData();// 导出数据
	}

	protected void exportData() throws Exception {
		String fun1 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[1]/td[5]")).getText();
		String times1 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[1]/td[6]")).getText();
		String oneUser1 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[1]/td[7]")).getText();
		String fun2 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[5]/td[5]")).getText();
		String times2 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[5]/td[6]")).getText();
		String oneUser2 = dr.findElement(By.xpath("//*[@id='maintainList']/table/tbody/tr[5]/td[7]")).getText();

		dr.findElement(By.id("downloadSummary")).click();
		sleep(3000);

		assertEquals(fun1, getCellValue1("Sheet0", 2, 2));
		assertEquals(times1 + ".0", getCellValue1("Sheet0", 2, 3));
		assertEquals(oneUser1 + ".0", getCellValue1("Sheet0", 2, 4));
		assertEquals(fun2, getCellValue1("Sheet0", 6, 2));
		assertEquals(times2 + ".0", getCellValue1("Sheet0", 6, 3));
		assertEquals(oneUser2 + ".0", getCellValue1("Sheet0", 6, 4));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}

	protected void switchPage(String text) throws InterruptedException {
		dr.findElement(By.linkText(text)).click();
		sleep(2000);
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
