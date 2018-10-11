package jsrbActivityMana;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import baseService.BaseService;
import baseService.SeleniumConstants;

public class CommonTools4App extends BaseService {

	@Test
	public void commonTools4App() throws Exception {
		mysqlModify(SeleniumConstants.del_category);
		mysqlModify(SeleniumConstants.del_column);
		mysqlModify(SeleniumConstants.insert_category);// 插入后服栏目

		entryPage("运营中心", "汽车后服务");
		commonTest();// 汽车后服务列表
		categoryTypeTest();// 后服栏目
	}

	protected void commonTest() throws Exception {
		newData("addCategoryBtn", "addPanel", "成功 创建汽车后服务成功");// 新建汽车后服务
		searchCategory("测试Test");// 搜索新建的栏目
		operateData("j-edit", "//*[@id='editPanel']/following-sibling::div//button[1]", "成功 编辑汽车后服务成功");// 编辑
		operateData("j-open", "//div[@class='noty_buttons']/button[1]", "成功 停用成功");// 停用
		select("commonToolsStatus4Search", "停用", 1000);
		clickEle("//*[@id='searchBtn']", 1500);
		operateData("j-delete", "//div[@class='noty_buttons']/button[1]", "成功 删除成功");// 删除

		assertEquals("没有查询到数据", dr.findElement(By.xpath("//div[@id='mainContentList']//td")).getText());

		mysqlModify(SeleniumConstants.del_category);// 清理数据
	}

	protected void categoryTypeTest() throws Exception {
		clickEle("//a[text()='后服栏目']", 1000);
		sortTest();// 排序
		newData("addCategoryTypeBtn", "addPanelType", "成功 创建后服栏目成功");// 新增后服栏目
		searchCategory("百宝箱");// 搜索新建的栏目
		operateData("j-editType", "//*[@id='editPanelType']/following-sibling::div//button[1]", "成功 编辑后服栏目成功");// 编辑后服务栏目
		select("commonToolsStatus4Search", "停用", 1000);
		clickEle("//*[@id='searchBtn']", 1500);
		operateData("j-deleteType", "//div[@class='noty_buttons']/button[1]", "成功 删除后服栏目成功");// 删除

		assertEquals("没有查询到数据", dr.findElement(By.xpath("//div[@id='mainContentList']//td")).getText());// 删除后服务栏目
	}

	private void sortTest() throws InterruptedException {
		if (isElementExsit(dr, "//*[@id='mainContentList']/table/tbody/tr[3]")) {
			String name = dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr[3]/td[3]")).getText();

			clickEle("//td[text()='" + name + "']/following-sibling::td/a[@name='j-sortType']", 1500);// 点击第3行的排序按钮
			clearInp("categoryTypeSort", "1");
			closePrompt("sortPanelType", 1, 2000);

			assertEquals("成功 排序成功", dr.findElement(By.className("noty_text")).getText());
			assertEquals(name, dr.findElement(By.xpath("//*[@id='mainContentList']//tr[2]/td[3]")).getText());
		}
	}

	private void newData(String aid, String did, String text) throws InterruptedException {
		clickEle("//*[@id='" + aid + "']", 1500);

		if (isElementExsit(dr, "//li[@class='active']/a[text()='后服栏目']")) {
			clearInp("categoryTypeNameAdd", "百宝箱");// 输入栏目名称
		} else {
			select("typeIdAdd", "百宝箱", 1000);
			select("sysModuleListAdd", "违章查询", 1000);
			clearInp("categoryNameAdd", "测试Test");// 输入栏目名称

			dr.findElement(By.xpath("//div[@id='addPanel']/table/tbody/tr[3]/td[2]//li//input"))
					.sendKeys(getTemplatePath("candy1"));// 上传图片
			sleep(5000);
		}
		closePrompt(did, 1, 2000);

		assertEquals(text, dr.findElement(By.className("noty_text")).getText());
	}

	private void searchCategory(String text) throws InterruptedException {
		select("commonToolsStatus4Search", "启用", 1000);
		clearInp("commonToolsName4Search", text);
		clickEle("//*[@id='searchBtn']", 1500);

		assertEquals("1", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[2]")).getText());
		assertEquals(text, dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[3]")).getText());
		assertEquals("启用", dr.findElement(By.xpath("//*[@id='mainContentList']/table/tbody/tr/td[4]")).getText());
	}

	private void operateData(String oid, String ox, String editText) throws InterruptedException {
		clickEle("//*[@name='" + oid + "']", 1500);

		if (oid == "j-editType")
			select("categoryTypeStatusEdit", "停用", 1000);

		clickEle(ox, 2000);// 点击确定按钮

		assertEquals(editText, dr.findElement(By.className("noty_text")).getText());
	}

	private static void mysqlModify(String aaa) throws Exception {
		mysqlConnect("jdbc.driver", "jdbc.url", "jdbc.username", "jdbc.password", aaa);
	}
}
