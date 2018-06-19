package summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class SalesStatic extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void salesStatic() throws Exception {
		entryPage("统计报表", "销量统计");
		testData();// 校验按各个时间统计时统计详情的数据遍历
		customTime();// 校验跨月自定义时间的统计详情-按月份显示统计
	}

	private void testData() throws InterruptedException {
		dr.findElement(By.xpath("//button[text()='今天']")).click();
		sleep(2000);

		if (!dr.findElement(By.id("totalOrder")).getText().equals("0")) {
			List<WebElement> elements = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[1]"));
			assertEquals(1, elements.size());

			assertEquals(date, elements.get(0).getText());
		}
	}

	private void customTime() throws Exception {
		dr.findElement(By.id("customSet")).click();

		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		
		dr.findElement(By.id("searchBeginDate")).clear();
		dr.findElement(By.id("searchEndDate")).clear();
		dr.findElement(By.id("searchBeginDate")).sendKeys(SeleniumConstants.date1);
		dr.findElement(By.id("searchEndDate")).sendKeys(SeleniumConstants.date2);
		sleep(2000);
		dr.findElement(By.id("customSetSubmit")).click();
		sleep(3000);

		List<WebElement> elements = dr.findElements(By.xpath("//*[@id='staticList']/table/tbody/tr/td[1]"));
		assertEquals(2, elements.size());
		assertEquals(SeleniumConstants.date3 + "-31", elements.get(0).getText());
		assertEquals(SeleniumConstants.date3 + "-30", elements.get(1).getText());

		double order1 = Double
				.valueOf(dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[2]")).getText());
		double order2 = Double
				.valueOf(dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[2]/td[2]")).getText());
		double pay1 = Double
				.valueOf(dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[1]/td[3]")).getText());
		double pay2 = Double
				.valueOf(dr.findElement(By.xpath("//*[@id='staticList']/table/tbody/tr[2]/td[3]")).getText());
		double orders = Integer.valueOf(dr.findElement(By.id("totalOrder")).getText());
		double money = Double.valueOf(dr.findElement(By.id("totalMoney")).getText());

		if ((order1 + order2) != orders || (pay1 + pay2) != money)
			fail();
		exportData();// 导出数据并校验excel数据
		assertEquals(String.valueOf(order1), getCellValue1("Sheet0", 2, 3));
		assertEquals(String.valueOf(order2), getCellValue1("Sheet0", 3, 3));
		assertEquals(String.valueOf(pay1), getCellValue1("Sheet0", 2, 4));
		assertEquals(String.valueOf(pay2), getCellValue1("Sheet0", 3, 4));
	}

	protected void exportData() throws Exception {
		dr.findElement(By.id("downloadSummary")).click();
		sleep(3000);

		assertEquals("排名", getCellValue1("Sheet0", 1, 1));
		assertEquals("日期", getCellValue1("Sheet0", 1, 2));
		assertEquals("订单总数", getCellValue1("Sheet0", 1, 3));
		assertEquals("订单总金额(元)", getCellValue1("Sheet0", 1, 4));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
