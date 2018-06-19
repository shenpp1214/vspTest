package brandInfo;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseService.BaseService;

public class BrandInfo extends BaseService {
	String currentDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");

	@BeforeClass
	public static void openBrowerBrandInfo() throws Exception {
		openBrower("vsp_url");
	}

	@Before
	public void setUp() throws InterruptedException {
		loginVsp("vspuser", "vsppwd", "val");
	}

	@Test
	public void brandInfo() throws InterruptedException {
		entryPage("信息发布", "品牌资讯");
		addBrandInfo();// 新增品牌资讯
		modifyBrandInfo();// 修改品牌资讯
		deleteBrandInfo();// 删除新增的品牌资讯
		resetTest("restBtn", "searchSubmit", "pagesList");// 重置测试
	}

	private void addBrandInfo() throws InterruptedException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		String date = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
		dr.findElement(By.id("addBrandInfo")).click();// 打开新增弹框
		sleep(2000);

		clickpropt("addBrandInfoBtn", "警告 请填写资讯标题");
		rollScreen("infoTitleAdd");
		select("brandIdAdd", "奥迪");

		dr.findElement(By.id("infoTitleAdd")).sendKeys("testAdd品牌资讯");
		dr.findElement(By.id("expireTimeAdd")).sendKeys(date);
		dr.findElement(By.id("isBannerAdd")).click();

		uploadPng("上传大图标", "candy1");
		uploadPng("上传小图标", "candy2");
		assertEquals("品牌资讯", dr.findElement(By.id("typeIdAdd")).getText());
		new WebDriverWait(dr, 3).until(
				ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//div[@id='edui1_iframeholder']/iframe")));

		dr.findElement(By.tagName("body")).sendKeys("这是奥迪的时代");
		dr.switchTo().defaultContent();
		clickpropt("addBrandInfoBtn", "成功 新增成功");
	}

	private void modifyBrandInfo() throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='brandInfoList']//tbody/tr[1]//a[@title='修改']")).click();
		sleep(2000);

		clickpropt("modifyBrandInfoBtn", "成功 修改成功");
		select("brandId", "奥迪");

		dr.findElement(By.id("infoTitle")).sendKeys("testAdd品牌资讯");
		dr.findElement(By.id("publishTimeBegin")).sendKeys(currentDate);

		searchTest("searchSubmit", "pagesList");// 搜索新增的数据
	}

	private void deleteBrandInfo() throws InterruptedException {
		abled("停用", "成功 停用成功");// 停用新增的品牌资讯
		abled("恢复", "成功");// 重新启用
		abled("停用", "成功 停用成功");
		abled("移除", "成功 删除成功");
	}

	protected void abled(String text1, String text2) throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='brandInfoList']//tbody/tr[1]//a[@title='" + text1 + "']")).click();
		sleep(2000);

		assertEquals(text2, dr.findElement(By.className("noty_text")).getText());
	}

	protected void clickpropt(String iid, String text) throws InterruptedException {
		rollScreen(iid);

		dr.findElement(By.id(iid)).click();
		sleep(2000);
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}

	protected void rollScreen(String sid) {
		((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", dr.findElement(By.id(sid)));
	}

	protected void uploadPng(String text1, String file) throws InterruptedException {
		dr.findElement(By.xpath("//a[text()='" + text1 + "']/following-sibling::div//input"))
				.sendKeys(getTemplatePath(file));
		sleep(3000);
	}
}
