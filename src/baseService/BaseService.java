package baseService;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import baseService.poi.SeleniumExcel;
import baseService.poi.SeleniumExcel1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;
import baseService.PropertiesReader;

public class BaseService {
	protected static WebDriver dr;
	protected static Properties props;
	protected static SeleniumExcel excel;
	protected static SeleniumExcel1 excel1;
	@SuppressWarnings("deprecation")
	@Rule
	public Timeout globalTimeout = new Timeout(10 * 60 * 1000);// 设置超时10分钟

	public static void openBrower(String url) throws Exception {
		props = new PropertiesReader().load();

		System.setProperty("webdriver.chrome.driver", props.getProperty("driver_path"));

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory",
				System.getProperty("user.dir") + System.getProperty("file.separator") + "temp");// 指定chrome下载文件路径

		ChromeOptions option = new ChromeOptions();
		option.setExperimentalOption("prefs", chromePrefs);
		dr = new ChromeDriver(option);
		dr.manage().window().maximize();
		dr.get(props.getProperty(url));
	}

	public void entryPage(String modelname, String pagename) throws InterruptedException {
		if (dr.findElement(By.xpath("//ol[@class='breadcrumb']/li[2]")).getText().equals(modelname)) {
			dr.findElement(By.linkText(pagename)).click();// 默认在要测试页面所在的模块下，直接进入页面就好了
			sleep(2000);
		} else {
			dr.findElement(By.linkText(modelname)).click();// 不在要测试页面所在的模块下，就先进入该模块儿
			sleep(2000);
			if (!dr.findElement(By.xpath("//ol[@class='breadcrumb']/li[3]")).getText().equals(pagename)) {
				dr.findElement(By.linkText(pagename)).click();
				sleep(4000);
			}
		}
	}

	public static void mysqlConnect(String dri, String url, String username, String pwd, String sql) throws Exception {
		Class.forName(props.getProperty(dri));
		Connection connect = DriverManager.getConnection(props.getProperty(url), props.getProperty(username),
				props.getProperty(pwd));

		Statement stmt = connect.createStatement();
		stmt.executeUpdate(sql);
	}

	public static void close() {
		dr.close();
	}

	public void loginVsp(String user, String pwd, String val) throws InterruptedException {
		clearInp("loginemail", props.getProperty(user));// 输入用户名
		clearInp("loginpassword", props.getProperty(pwd));// 输入密码
		clearInp("loginvalidate", props.getProperty(val));// 输入验证码
		clickEle("//*[@id='loginbtn']", 8000);// 登录
	}

	public void logoutVsp() throws InterruptedException {
		dr.switchTo().defaultContent();
		clickEle("//*[@id='logout']", 3000);

		assertEquals("用户登录", dr.findElement(By.className("border-bottom")).getText());
	}

	public void select(String sid, String sval, int n) throws InterruptedException {
		Select sel1 = new Select(dr.findElement(By.id(sid)));
		sel1.selectByVisibleText(sval);
		sleep(n);
	}

	public void searchTest(String bid, String pid) throws InterruptedException {
		clickEle("//*[@id='" + bid + "']", 1500);// 根据关键字搜索

		assertEquals("共1条数据", dr.findElement(By.id(pid)).getText());
	}

	public void resetTest(String bid1, String bid2, String pid) throws InterruptedException {
		clickEle("//*[@id='" + bid1 + "']", 1000);// 重置搜索条件
		clickEle("//*[@id='" + bid2 + "']", 3000);// 搜索

		if (dr.findElement(By.id(pid)).getText().equals("共1条数据"))
			fail();
	}

	protected void clearInp(String id, String d) throws InterruptedException {
		dr.findElement(By.id(id)).clear();
		dr.findElement(By.id(id)).sendKeys(d);
		sleep(1000);
	}

	protected void clickEle(String xid, int sec) throws InterruptedException {
		dr.findElement(By.xpath(xid)).click();
		sleep(sec);
	}

	public static void closePrompt(String did, int num, int sec) throws InterruptedException {
		dr.findElement(By.xpath("//div[@id='" + did + "']/following-sibling::div//button[" + num + "]")).click();
		sleep(sec);
	}

	public static void sleep(int num) throws InterruptedException {
		Thread.sleep(num);
	}

	public boolean isElementExsit(WebDriver driver, String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected String getTemplatePath(String key) {
		return System.getProperty("user.dir") + System.getProperty("file.separator") + "template"
				+ System.getProperty("file.separator") + props.getProperty(key);
	}

	/**
	 * 
	 * @param sheetName
	 *            sheet名字
	 * @param row
	 *            行号
	 * @param column
	 *            列号
	 * @return
	 * @throws Exception
	 */
	protected static String getCellValue(String sheetName, int row, int column) throws Exception {
		String templateDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "temp"
				+ System.getProperty("file.separator");
		File[] files = new File(templateDir).listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				return (int) (file2.lastModified() - file1.lastModified());
			}
		});

		excel = new SeleniumExcel(files[0], sheetName);
		return excel.getCellValue(row - 1, column - 1);
	}

	protected static String getCellValue1(String sheetName, int row, int column) throws Exception {
		String templateDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "temp"
				+ System.getProperty("file.separator");
		File[] files = new File(templateDir).listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				return (int) (file2.lastModified() - file1.lastModified());
			}
		});

		excel1 = new SeleniumExcel1(files[0], sheetName);
		return excel1.getCellValue(row - 1, column - 1);
	}
}
