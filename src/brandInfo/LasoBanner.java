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
	}

	private void addLasoBanner() throws InterruptedException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		String date = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");

		clickEle("//*[@id='addActiveBtn']", 2000);
		clickpropt("addActive", "警告 资讯标题不能为空");
		rollScreen("infoTitleAdd");
		clearInp("infoTitleAdd", "testAdd品牌资讯");
		removeReadonly("expireTimeAdd");
		clearInp("expireTimeAdd", date);
		uploadPng("上传小图标", "candy2");

		assertEquals("此功能仅开放给金融使用", dr.findElement(By.xpath("//div[@id='main_content']/ol/span")).getText());
		new WebDriverWait(dr, 3).until(
				ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//div[@id='edui1_iframeholder']/iframe")));

		dr.findElement(By.tagName("body")).sendKeys("资讯发布");
		dr.switchTo().defaultContent();
		clickpropt("addActive", "成功 新增成功");
	}

	private void modifyLasoBanner() throws InterruptedException {
		clickEle("//div[@id='activeList']//tr[1]//a[@title='修改']", 3000);
		clickpropt("modifyActive", "成功 修改成功");
		clearInp("infoTitle", "testAdd品牌资讯");
		clearInp("publishTimeBegin", currentDate);
		searchTest("searchSubmit_active", "pages");// 搜索新增的数据
	}

	private void deleteLasoBanner() throws InterruptedException {
		abled("停用", "成功 停用成功");// 停用新增的品牌资讯
		abled("恢复", "成功 恢复成功");// 重新启用
		abled("停用", "成功 停用成功");
		abled("移除", "成功 删除成功");
		resetTest("restBtn", "searchSubmit_active", "pages");// 重置测试
	}

	protected void abled(String t1, String t2) throws InterruptedException {
		clickEle("//div[@id='activeList']//tr[1]//a[@title='" + t1 + "']", 2000);

		assertEquals(t2, dr.findElement(By.className("noty_text")).getText());
	}

	protected void clickpropt(String iid, String text) throws InterruptedException {
		rollScreen(iid);
		clickEle("//*[@id='" + iid + "']", 2000);

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
