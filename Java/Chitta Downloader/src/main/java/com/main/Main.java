package com.main;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static String path="/home/srithar/ChittaDownload/";
    static int num=0;
    static String name="";


    public static void main(String[] args) throws Exception {

        ChromeDriver driver = new ChromeDriver();
        driver.get("https://eservices.tn.gov.in/eservicesnew/land/chittaCheckNewRural_en.html?lan=en");

        FileInputStream fis=new FileInputStream(path+"Chitta.xls");
        Workbook wb =WorkbookFactory.create(fis);
        Sheet sheet=wb.getSheetAt(0);

        for(Row row:sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String district = getCellValue(row.getCell(0));
            String taluk = getCellValue(row.getCell(1));
            String village = getCellValue(row.getCell(2));
            String patta = getCellValue(row.getCell(3));
            String survey = getCellValue(row.getCell(4));
            String sub = getCellValue(row.getCell(5));
            name = getCellValue(row.getCell(6));

            num++;

            if(!checkEmpty(district,taluk,village)){
                System.out.println(num+"."+"Invalid Line");
                continue;
            }

            if(!survey.isEmpty() && !sub.isEmpty()){
                startDownload(driver,district,taluk,village,survey,sub);
            }else if(!patta.isEmpty()){
                startDownload(driver, district, taluk, village, patta);
            }else{
                System.out.println(num+"."+"Invalid Line");
            }

        }


        //driver.close();
        //driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
        driver.quit();
    }

    private static void startDownload(ChromeDriver driver,String district,String taluk,String village,String pattaNo) throws InterruptedException, IOException, TesseractException {
        boolean loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("districtCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("districtCode"))).selectByVisibleText(district);
        loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("talukCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("talukCode"))).selectByVisibleText(taluk);

        loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("villageCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("villageCode"))).selectByVisibleText(village);

        WebElement radio= driver.findElement(By.id("viewOpt"));
        radio.click();

        WebElement pattaElement= driver.findElement(By.id("pattaNo"));
        pattaElement.clear();
        pattaElement.sendKeys(pattaNo);

        if(num==1) {
            setCaptcha(driver);
        }else{
            refreshCaptcha(driver);
        }

        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).click(submitButton).keyUp(Keys.CONTROL).perform();
        Thread.sleep(2500);
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));

        //driver.findElements(By.cssSelector("div.row.form-group div font[color='red'] strong"));
        List<WebElement> fontElements = driver.findElements(By.xpath("//strong[contains(text(), 'Incorrect Value')]"));
        while(!fontElements.isEmpty() && fontElements.get(0).isDisplayed()) {
            driver.close();
            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
            refreshCaptcha(driver);
            WebElement submitButtonRefresh = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));
            Actions actions1 = new Actions(driver);
            actions1.keyDown(Keys.CONTROL).click(submitButtonRefresh).keyUp(Keys.CONTROL).perform();
            Thread.sleep(2500);
            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));
            fontElements = driver.findElements(By.xpath("//strong[contains(text(), 'Incorrect Value')]"));
        }
        Pdf pdf = ((PrintsPage) driver).print(new PrintOptions());
        Files.write(Paths.get(path+name+num +".pdf"),OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
        driver.close();
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
        System.out.println(num+"."+"Completed :: "+pattaNo);
    }

    private static void startDownload(ChromeDriver driver,String district,String taluk,String village,String survey,String sub) throws InterruptedException, IOException, TesseractException {
        boolean loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("districtCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("districtCode"))).selectByVisibleText(district);
        loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("talukCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("talukCode"))).selectByVisibleText(taluk);

        loaded=false;
        while(!loaded){
            if(new Select(driver.findElement(By.id("villageCode"))).getOptions().size()>1){
                loaded=true;
            }
            Thread.sleep(100);
        }
        new Select(driver.findElement(By.id("villageCode"))).selectByVisibleText(village);

        driver.findElements(By.id("viewOpt")).get(0).click();


        WebElement radio= driver.findElements(By.id("viewOpt")).get(1);
        radio.click();

        WebElement pattaElement= driver.findElement(By.id("surveyNo"));
        pattaElement.clear();
            pattaElement.sendKeys(survey);

        new Actions(driver).sendKeys(Keys.TAB).perform();


        loaded=false;
        long start=System.currentTimeMillis();
        while(!loaded){
            if(new Select(driver.findElement(By.id("subdivNo"))).getOptions().size()>1){
                loaded=true;
            }
            if(System.currentTimeMillis()-start>5000){
                System.out.println(num+"."+"Error :: Subdivision Number Not Loaded :: "+ survey +" - "+sub);
                return;
            }
            Thread.sleep(100);
        }

        try {
            new Select(driver.findElement(By.id("subdivNo"))).selectByVisibleText(sub);
        } catch (NoSuchElementException e) {
            System.out.println(num+"."+"Error :: Subdivision Number Not Found :: "+ survey +" - "+sub);
            return;
        }

        if(num==0) {
            setCaptcha(driver);
        }else{
            refreshCaptcha(driver);
        }

        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).click(submitButton).keyUp(Keys.CONTROL).perform();
        Thread.sleep(2500);
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));

        //driver.findElements(By.cssSelector("div.row.form-group div font[color='red'] strong"));
        List<WebElement> fontElements = driver.findElements(By.xpath("//strong[contains(text(), 'Incorrect Value')]"));
        while(!fontElements.isEmpty() && fontElements.get(0).isDisplayed()) {
            driver.close();
            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
            refreshCaptcha(driver);
            WebElement submitButtonRefresh = driver.findElement(By.xpath("//input[@type='submit' and @value='Submit']"));
            Actions actions1 = new Actions(driver);
            actions1.keyDown(Keys.CONTROL).click(submitButtonRefresh).keyUp(Keys.CONTROL).perform();
            Thread.sleep(2500);
            driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(1));
            fontElements = driver.findElements(By.xpath("//strong[contains(text(), 'Incorrect Value')]"));
        }
        Pdf pdf = ((PrintsPage) driver).print(new PrintOptions());
        Files.write(Paths.get(path+name+num +".pdf"),OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
        driver.close();
        driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));
        System.out.println(num+"."+"Completed :: "+survey+"-"+sub);
    }

    private static void refreshCaptcha(ChromeDriver driver) throws IOException, TesseractException, InterruptedException {
        WebElement refresh = driver.findElement(By.cssSelector("i.fa"));
        refresh.click();
        Thread.sleep(500);
        setCaptcha(driver);
    }

    private static void setCaptcha(ChromeDriver driver) throws IOException, TesseractException, InterruptedException {
        Tesseract tesseract=new Tesseract();
        tesseract.setDatapath(path+"config/tessdata");
        tesseract.setTessVariable("user_defined_dpi", "70");
        String text=tesseract.doOCR(driver.findElement(By.id("captcha_name")).getScreenshotAs(OutputType.FILE));
        tesseract.setPageSegMode(ITessAPI.TessPageSegMode.PSM_RAW_LINE);
        tesseract.setLanguage("eng");
        text=text.trim().replaceAll("\\W","");
        if(text.isEmpty()){
            refreshCaptcha(driver);
        }

        WebElement captcha= driver.findElement(By.id("captcha"));
        captcha.clear();
        captcha.sendKeys(text);
    }

    private static String getCellValue(Cell cell){
        if(cell==null){
            return "";
        }
        String cellValue="";
        CellType type=cell.getCellType();
        switch (type) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;

            case FORMULA:
                cellValue = cell.getCellFormula();
                break;

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = Integer.toString((int)cell.getNumericCellValue());
                }
                break;

            case BLANK:
                cellValue = "";
                break;

            case BOOLEAN:
                cellValue = Boolean.toString(cell.getBooleanCellValue());
                break;

        }
        return cellValue;
    }

    private static boolean checkEmpty(String... arg){
        for(String str:arg){
            if(str.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
