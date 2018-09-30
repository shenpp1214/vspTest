package merchantOperate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class SensitiveWordManage extends BaseService {

	@Test
	public void sensitiveWordMan() throws InterruptedException {
		entryPage("商户运营", "敏感词管理");

		dr.findElement(By.id("detail")).clear();
		dr.findElement(By.id("save_btn")).click();
		sleep(2000);

		assertEquals("警告 请输入敏感词", dr.findElement(By.className("noty_text")).getText());

		dr.findElement(By.id("detail")).sendKeys("We位1$We位111$We位1$We位1$We位1$We位1$We位");
		dr.findElement(By.id("save_btn")).click();
		sleep(2000);

		assertEquals("成功 敏感词编辑成功", dr.findElement(By.className("noty_text")).getText());
	}
}
