package manageCenter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;

public class AccountSetting extends BaseService {

	@Test
	public void accountSetting() throws InterruptedException {
		entryPage("管理中心", "账号设置");// 进入界面
		modPwd();// 修改密码
		modInfo();// 修改个人信息
	}

	private void modPwd() throws InterruptedException {
		assertPrompt("modPwdBtn", "警告 请输入原密码");
		dr.findElement(By.id("password")).sendKeys("111111");
		assertPrompt("modPwdBtn", "警告 请输入新密码");
		dr.findElement(By.id("newPassword")).sendKeys("123456");
		assertPrompt("modPwdBtn", "警告 请输入确认密码");
		dr.findElement(By.id("newPasswordConfirm")).sendKeys("12345");
		assertPrompt("modPwdBtn", "警告 新密码和确认密码不一致");
		dr.findElement(By.id("newPasswordConfirm")).sendKeys("123456");
		clickConfirmPwd("modPwdBtn");
		confirmAndClick("用戶原密码不正确。");

		dr.findElement(By.id("password")).sendKeys("000000");
		dr.findElement(By.id("newPassword")).sendKeys("1");
		dr.findElement(By.id("newPasswordConfirm")).sendKeys("1");

		assertPrompt("modPwdBtn", "成功 修改密码成功");
		logoutVsp();// 退出登录
		loginVsp("vspuser", "vsppwd1", "val");// 新密码登录
		entryPage("管理中心", "账号设置");

		dr.findElement(By.id("password")).sendKeys("1");
		dr.findElement(By.id("newPassword")).sendKeys("000000");
		dr.findElement(By.id("newPasswordConfirm")).sendKeys("000000");
		assertPrompt("modPwdBtn", "成功 修改密码成功");
	}

	private void modInfo() throws InterruptedException {
		String realname = dr.findElement(By.id("name_m")).getAttribute("value");
		String email = dr.findElement(By.id("email_m")).getAttribute("value");

		dr.findElement(By.id("name_m")).clear();
		dr.findElement(By.id("email_m")).clear();
		assertPrompt("modInfoBtn", "警告 Email不能为空");

		dr.findElement(By.id("name_m")).sendKeys("猪猪女孩");
		dr.findElement(By.id("email_m")).sendKeys("11111@");
		assertPrompt("modInfoBtn", "警告 Email格式不对!");

		dr.findElement(By.id("email_m")).sendKeys("qq.com");
		assertPrompt("modInfoBtn", "成功 修改用户信息成功");
		assertEquals("猪猪女孩", dr.findElement(By.id("name_m")).getAttribute("value"));
		assertEquals("11111@qq.com", dr.findElement(By.id("email_m")).getAttribute("value"));

		dr.findElement(By.id("name_m")).clear();
		dr.findElement(By.id("email_m")).clear();
		dr.findElement(By.id("name_m")).sendKeys(realname);
		dr.findElement(By.id("email_m")).sendKeys(email);
		assertPrompt("modInfoBtn", "成功 修改用户信息成功");
		assertEquals(realname, dr.findElement(By.id("name_m")).getAttribute("value"));
		assertEquals(email, dr.findElement(By.id("email_m")).getAttribute("value"));
	}

	protected void assertPrompt(String miid, String text) throws InterruptedException {
		clickConfirmPwd(miid);
		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
		sleep(2000);
	}

	protected void clickConfirmPwd(String mid) throws InterruptedException {
		dr.findElement(By.id(mid)).click();
		sleep(2000);
	}

	protected void confirmAndClick(String text) throws InterruptedException {
		assertEquals(text, dr.findElement(By.id("popup_message")).getText());

		dr.findElement(By.id("popup_ok")).click();
		sleep(2000);
	}
}
