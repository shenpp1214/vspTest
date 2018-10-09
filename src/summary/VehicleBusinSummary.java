package summary;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import baseService.BaseService;

public class VehicleBusinSummary extends BaseService {
	String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@BeforeClass
	public static void openBrowerVehBusSummary() throws Exception {
		openBrower("vsp_url");
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void vehBusSummary() throws Exception {
		entryPage("统计报表", "业务统计");
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

			List<WebElement> elements = dr.findElements(By.xpath("//tr[@class='gradeA']/td[1]"));
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

		List<WebElement> elements = dr.findElements(By.xpath("//tr[@class='gradeA']/td[1]"));
		assertEquals(2, elements.size());
		assertEquals(date1, elements.get(0).getText());
		assertEquals(date2, elements.get(1).getText());
	}

	private void exportData() throws Exception {
		clickEle("//*[@id='downloadSummary']", 4000);

		assertEquals("日期", getCellValue1("sheet1", 1, 1));
		assertEquals("故障维修", getCellValue1("sheet1", 1, 2));
		assertEquals("保养", getCellValue1("sheet1", 1, 3));
		assertEquals("碰撞", getCellValue1("sheet1", 1, 4));
		assertEquals("保险", getCellValue1("sheet1", 1, 5));
		assertEquals("救援", getCellValue1("sheet1", 1, 6));
	}

	private void foreachClick() throws InterruptedException {
		List<WebElement> elements = dr.findElements(By.xpath("//tr[1][@class='gradeA']/td"));
		for (int i = 1; i < elements.size(); i = i + 3) {
			elements.get(i).click();
			sleep(1000);
			closePrompt("businDetail", 1);
		}
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
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
}
