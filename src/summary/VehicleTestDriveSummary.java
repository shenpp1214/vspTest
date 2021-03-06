package summary;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class VehicleTestDriveSummary extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void vehTestDriSum() throws Exception {
		entryPage("统计报表", "试驾统计");
		checkData();// 校验按各个时间统计时统计详情的数据遍历
		customTime();// 校验跨月自定义时间的统计详情-按月份显示统计
		exportData();// 导出数据并校验excel数据
		foreachClick();// 查看详情
	}

	private void checkData() throws InterruptedException {
		String str[] = { "今天", "本月", "最近七天" };
		for (String s : str) {
			dr.findElement(By.xpath("//button[text()='" + s + "']")).click();
			sleep(2000);

			List<WebElement> elements = dr.findElements(By.xpath("//div[@id='home']//td[1]"));
			switch (s) {
			case "今天":
				assertEquals(1, elements.size());
				break;
			case "本月":
				assertEquals(Calendar.getInstance().get(Calendar.DATE), elements.size());
				break;
			default:
				assertEquals(7, elements.size());
				break;
			}

			for (int i = 0; i < elements.size(); i++) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -i);
				String date = DateFormatUtils.format(cal, "yyyy-MM-dd");

				assertEquals(date, elements.get(elements.size() - 1 - i).getText());
			}
		}
	}

	private void customTime() throws InterruptedException {
		clickEle("//*[@id='customSet']", 1000);

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, -2);
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.MONTH, -1);
		String date1 = DateFormatUtils.format(cal1, "yyyy-MM");
		String date2 = DateFormatUtils.format(cal2, "yyyy-MM");

		removeReadonly("searchBeginDate");
		removeReadonly("searchEndDate");
		clearInp("searchBeginDate", date1 + "-15");
		clearInp("searchEndDate", date2 + "-15");
		clickEle("//*[@id='customSetSubmit']", 2000);

		List<WebElement> elements = dr.findElements(By.xpath("//div[@id='home']/table//tr/td[1]"));
		assertEquals(2, elements.size());
		assertEquals(date1, elements.get(0).getText());
		assertEquals(date2, elements.get(1).getText());
	}

	private void foreachClick() throws InterruptedException {
		List<WebElement> elements = dr.findElements(By.xpath("//div[@id='home']//tr[1]//a"));
		for (int i = 1; i < elements.size(); i++) {
			elements.get(i).click();
			sleep(1000);
			closePrompt("testDriveDetail", 1, 1000);
		}
	}

	private void exportData() throws Exception {
		clickEle("//*[@id='downloadSummary']", 3500);

		assertEquals("日期", getCellValue1("sheet1", 1, 1));
		assertEquals("预约数量", getCellValue1("sheet1", 1, 2));
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}
}
