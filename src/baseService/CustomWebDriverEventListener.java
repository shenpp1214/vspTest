/*******************************************************************************
 *                                                                              
 *  COPYRIGHT (C) 2016 JWRJ Limited - ALL RIGHTS RESERVED.                  
 *                                                                                                                                 
 *  Creation Date: 2016年9月30日                                                      
 *                                                                              
 *******************************************************************************/

package baseService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

/**
 * @author shenpp
 *
 */
public class CustomWebDriverEventListener extends AbstractWebDriverEventListener {
    @Override
    public void onException(Throwable throwable, WebDriver driver) {

        String dir_name = "screenshot";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String time = sdf.format(new Date());

        try {
            File source_file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = dir_name + File.separator + time;
            FileUtils.copyFile(source_file, new File(fileName + ".png"));
            logErrorInfo(throwable, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logErrorInfo(Throwable throwable, String fileName) throws IOException {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("Cause:\n");
        sbuf.append(throwable.getCause());
        sbuf.append("Message:\n");
        sbuf.append(throwable.getMessage());
        FileUtils.writeStringToFile(new File(fileName + ".txt"), sbuf.toString());
    }
}
