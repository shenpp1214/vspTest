package summary;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class SatisfactoryMaintenance extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void satisfactoryMaintenance() throws Exception {
		entryPage("统计报表", "保养满意度");
		checkData();// 校验时间
		customTime();// 自定义时间+导出数据
	}

	private void checkData() throws InterruptedException {
		String str[] = { "今天", "本月", "最近七天" };
		for (String s : str) {
			dr.findElement(By.xpath("//button[text()='" + s + "']")).click();
			sleep(2000);

			List<WebElement> elements = dr.findElements(By.xpath("//tr[@class='gradeA']/td[1]"));
			if (s.equals("今天") || s.equals("昨天"))
				assertEquals(1, elements.size());
			else if (s.equals("最近七天"))
				assertEquals(7, elements.size());

			for (int i = 0; i < elements.size(); i++) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -i);
				String date = DateFormatUtils.format(cal, "yyyy-MM-dd");

				assertEquals(date, elements.get(elements.size() - 1 - i).getText());
			}
		}
	}

	private void customTime() throws Exception {
		dr.findElement(By.id("customSet")).click();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		String date1 = DateFormatUtils.format(cal, "yyyy-MM");
		String date2 = DateFormatUtils.format(new Date(), "yyyy-MM");

		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		dr.findElement(By.id("searchBeginDate")).clear();
		dr.findElement(By.id("searchEndDate")).clear();
		dr.findElement(By.id("searchBeginDate")).sendKeys(date1 + "-15");
		dr.findElement(By.id("searchEndDate")).sendKeys(date);
		sleep(2000);
		dr.findElement(By.id("customSetSubmit")).click();
		sleep(2000);

		List<WebElement> elements = dr.findElements(By.xpath("//tr[@class='gradeA']/td[1]"));
		assertEquals(2, elements.size());
		assertEquals(date1, elements.get(0).getText());
		assertEquals(date2, elements.get(1).getText());
		exportData(date1, date2);// 导出数据
	}

	protected void exportData(String exceptData1, String exceptData2) throws Exception {
		dr.findElement(By.id("downloadSummary")).click();
		sleep(3000);

		assertEquals("日期", getCellValue1("Sheet0", 1, 1));
		assertEquals("满意", getCellValue1("Sheet0", 1, 2));
		assertEquals("一般", getCellValue1("Sheet0", 1, 3));
		assertEquals("不满意", getCellValue1("Sheet0", 1, 4));
		assertEquals(exceptData1, getCellValue1("Sheet0", 2, 1));
		assertEquals(exceptData2, getCellValue1("Sheet0", 3, 1));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}

}
