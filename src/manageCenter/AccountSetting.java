package manageCenter;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
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
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("警告 请输入原密码", dr.findElement(By.className("noty_text")).getText());

		clearInp("password", "111111");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("警告 请输入新密码", dr.findElement(By.className("noty_text")).getText());

		clearInp("newPassword", "123456");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("警告 请输入确认密码", dr.findElement(By.className("noty_text")).getText());

		clearInp("newPasswordConfirm", "12345");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("警告 新密码和确认密码不一致", dr.findElement(By.className("noty_text")).getText());

		clearInp("newPasswordConfirm", "123456");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("用戶原密码不正确。", dr.findElement(By.id("popup_message")).getText());

		clickEle("//*[@id='popup_ok']", 2000);
		clearInp("password", "000000");
		clearInp("newPassword", "1");
		clearInp("newPasswordConfirm", "1");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("成功 修改密码成功", dr.findElement(By.className("noty_text")).getText());

		logoutVsp();// 退出登录
		loginVsp("vspuser", "vsppwd1", "val");// 新密码登录
		entryPage("管理中心", "账号设置");
		clearInp("password", "1");
		clearInp("newPassword", "000000");
		clearInp("newPasswordConfirm", "000000");
		clickEle("//*[@id='modPwdBtn']", 2000);
		assertEquals("成功 修改密码成功", dr.findElement(By.className("noty_text")).getText());
	}

	private void modInfo() throws InterruptedException {
		String realname = dr.findElement(By.id("name_m")).getAttribute("value");
		String email = dr.findElement(By.id("email_m")).getAttribute("value");

		clearInp("name_m", "");
		clearInp("email_m", "");
		clickEle("//*[@id='modInfoBtn']", 2000);
		assertEquals("警告 Email不能为空", dr.findElement(By.className("noty_text")).getText());

		clearInp("name_m", "猪猪女孩");
		clearInp("email_m", "11111@");
		clickEle("//*[@id='modInfoBtn']", 2000);
		assertEquals("警告 Email格式不对!", dr.findElement(By.className("noty_text")).getText());

		clearInp("email_m", "11111@qq.com");
		clickEle("//*[@id='modInfoBtn']", 2000);
		assertEquals("成功 修改用户信息成功", dr.findElement(By.className("noty_text")).getText());

		assertEquals("猪猪女孩", dr.findElement(By.id("name_m")).getAttribute("value"));
		assertEquals("11111@qq.com", dr.findElement(By.id("email_m")).getAttribute("value"));

		clearInp("name_m", realname);
		clearInp("email_m", email);
		clickEle("//*[@id='modInfoBtn']", 2000);
		assertEquals("成功 修改用户信息成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals(realname, dr.findElement(By.id("name_m")).getAttribute("value"));
		assertEquals(email, dr.findElement(By.id("email_m")).getAttribute("value"));
	}

	@After
	public void logoutServiceProvider() throws InterruptedException {
		logoutVsp();
	}

	@AfterClass
	public static void tearDown() {
		close();
	}
}
