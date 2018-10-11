package jsrbActivityMana;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class ChannelMgr extends BaseService {

	@Test
	public void channelMgr() throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", SeleniumConstants.del_channel);
		entryPage("运营中心", "渠道管理");
		newChannel();// 新增渠道
		searchChannel();// 搜索新增的渠道
		detailAndModify();// 查看详情+修改
		resetTest("resetBtn", "searchBtn", "pages");// 重置校验
	}

	private void newChannel() throws InterruptedException {
		String pages = dr.findElement(By.id("pages")).getText();
		int ps = Integer.valueOf(pages.substring(1, pages.length() - 3)) + 1;

		clickEle("//*[@id='addChannelBtn']", 1500);// 点击新增按钮
		clearInp("channelNameInfo", "新增渠道名称");
		clearInp("domainInfo", "新渠道");
		closePrompt("channelInfo", 1, 2000);// 点新建按钮

		assertEquals("成功 操作成功", dr.findElement(By.className("noty_text")).getText());
		assertEquals("共" + ps + "条数据", dr.findElement(By.id("pages")).getText());
	}

	private void searchChannel() throws InterruptedException {
		clearInp("searchName", "新增渠道名称");
		clearInp("searchDomain", "新渠道");
		clickEle("//*[@id='searchBtn']", 1500);// 点击搜索按钮

		assertEquals("共1条数据", dr.findElement(By.id("pages")).getText());
	}

	private void detailAndModify() throws InterruptedException {
		clickEle("//*[@name='j-accountDetail']", 1500);
		assertEquals("新增渠道名称", dr.findElement(By.id("channelNameInfo")).getAttribute("value"));
		assertEquals("新渠道", dr.findElement(By.id("domainInfo")).getAttribute("value"));

		closePrompt("channelInfo", 1, 2000);// 点修改按钮
		assertEquals("成功 操作成功", dr.findElement(By.className("noty_text")).getText());
	}
}
