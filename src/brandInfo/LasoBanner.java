package brandInfo;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;

public class LasoBanner extends BaseService {
	String currentDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@Test
	public void lasoBanner() throws InterruptedException {
		entryPage("信息发布", "资讯发布");
		addLasoBanner();// 新增品牌资讯
		modifyLasoBanner();// 修改品牌资讯
		deleteLasoBanner();// 删除新增的品牌资讯
		resetTest("restBtn", "searchSubmit_active", "pages");// 重置测试
	}

	private void addLasoBanner() throws InterruptedException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		String date = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");

		dr.findElement(By.id("addActiveBtn")).click();// 打开新增弹框
		sleep(2000);

		clickpropt("addActive", "警告 资讯标题不能为空");
		rollScreen("infoTitleAdd");

		dr.findElement(By.id("infoTitleAdd")).sendKeys("testAdd品牌资讯");
		removeReadonly("expireTimeAdd");
		dr.findElement(By.id("expireTimeAdd")).sendKeys(date);

		uploadPng("上传小图标", "candy2");
		assertEquals("此功能仅开放给金融使用", dr.findElement(By.xpath("//div[@id='main_content']/ol/span")).getText());
		new WebDriverWait(dr, 3).until(
				ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//div[@id='edui1_iframeholder']/iframe")));

		dr.findElement(By.tagName("body")).sendKeys("资讯发布");
		dr.switchTo().defaultContent();
		clickpropt("addActive", "成功 新增成功");
	}

	private void modifyLasoBanner() throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='activeList']//tbody/tr[1]//a[@title='修改']")).click();
		sleep(3000);
		clickpropt("modifyActive", "成功 修改成功");

		dr.findElement(By.id("infoTitle")).sendKeys("testAdd品牌资讯");
		dr.findElement(By.id("publishTimeBegin")).sendKeys(currentDate);
		searchTest("searchSubmit_active", "pages");// 搜索新增的数据
	}

	private void deleteLasoBanner() throws InterruptedException {
		abled("停用", "成功 停用成功");// 停用新增的品牌资讯
		abled("恢复", "成功 恢复成功");// 重新启用
		abled("停用", "成功 停用成功");
		abled("移除", "成功 删除成功");
	}

	protected void abled(String text1, String text2) throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='activeList']//tbody/tr[1]//a[@title='" + text1 + "']")).click();
		sleep(2000);

		assertEquals(text2, dr.findElement(By.className("noty_text")).getText());
	}

	protected void clickpropt(String iid, String text) throws InterruptedException {
		rollScreen(iid);

		dr.findElement(By.id(iid)).click();
		sleep(3000);

		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}

	protected void rollScreen(String sid) {
		((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", dr.findElement(By.id(sid)));
	}

	protected void uploadPng(String text1, String file) throws InterruptedException {
		dr.findElement(By.xpath("//a[text()='" + text1 + "']/following-sibling::div//input"))
				.sendKeys(getTemplatePath(file));
		sleep(4000);
	}

	protected void removeReadonly(String outid) {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById(\"" + outid + "\").removeAttribute('readonly');");
	}

	@After
	public void logoutLasoBanner() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}
}
