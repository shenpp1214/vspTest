package customerVehice;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class FeedbackView extends BaseService {

	@Test
	public void feedBackView() throws InterruptedException {
		entryPage("客户车辆", "车掌柜潜在客户");

		assertEquals("反馈查看", dr.findElement(By.xpath("//*[@id='main_content']/ul/li/a")).getText());
		assertEquals("序号", dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[1]")).getText());
		assertEquals("提交时间", dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[2]")).getText());
		assertEquals("您的单位(车队)名称",
				dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[3]")).getText());
		assertEquals("联系人姓名", dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[4]")).getText());
		assertEquals("联系人电话", dr.findElement(By.xpath("//*[@id='mainContentList']/table/thead/tr/th[5]")).getText());
	}

	@After
	public void logoutFeedbackView() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}
}
