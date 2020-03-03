package com.app.mdc.utils.file;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    //导出所有企业
    public static String createCompanyExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=18;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("企业名称"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("企业简称"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("法人代码"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("所属行政区"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("企业地址"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("污染源类型"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("统计行业"));
            cell = sheet.getRow(1).getCell(8);
            cell.setCellValue(new HSSFRichTextString("所属流域"));
            cell = sheet.getRow(1).getCell(9);
            cell.setCellValue(new HSSFRichTextString("组织机构代码"));
            cell = sheet.getRow(1).getCell(10);
            cell.setCellValue(new HSSFRichTextString("法人联系电话"));
            cell = sheet.getRow(1).getCell(11);
            cell.setCellValue(new HSSFRichTextString("环保负责人电话"));
            cell = sheet.getRow(1).getCell(12);
            cell.setCellValue(new HSSFRichTextString("传真号码"));
            cell = sheet.getRow(1).getCell(13);
            cell.setCellValue(new HSSFRichTextString("经纬度"));
            cell = sheet.getRow(1).getCell(14);
            cell.setCellValue(new HSSFRichTextString("企业类型"));
            cell = sheet.getRow(1).getCell(15);
            cell.setCellValue(new HSSFRichTextString("所属环保局"));
            cell = sheet.getRow(1).getCell(16);
            cell.setCellValue(new HSSFRichTextString("监督级别"));
            cell = sheet.getRow(1).getCell(17);
            cell.setCellValue(new HSSFRichTextString("固定资产"));
            cell = sheet.getRow(1).getCell(18);
            cell.setCellValue(new HSSFRichTextString("企业简介"));

            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }



    private static void whiteToExcelSheet(List<Map<String, Object>> excelData, int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylebody) {
        HSSFRow row;
        HSSFCell cell;
        for (int i = 2; i < excelData.size() + 2; i++) {
            Map<String, Object> data = excelData.get(i - 2);
            row = sheet.createRow(i);
            for (int j = 0; j <= cellSize; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStylebody);
                if (j == 0) {
                    cell.setCellValue(new HSSFRichTextString((i - 1) + ""));
                } else {
                    String value=data.get("col" + j)!=null?data.get("col" + j).toString():"";
                    cell.setCellValue(new HSSFRichTextString(value));
                }
            }
        }
    }

    //导出所有的监控点数据
    public static String createPointExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=7;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetTitle(cellSize, sheet, cellStylehead, cellStyletitle);

            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("公司名称"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("监控点名称"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("监控点mn号"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("监控点类型"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("监测因子"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("监控点状态"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("监管级别"));


            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

    private static void whiteToExcelSheetTitle(int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylehead, HSSFCellStyle cellStyletitle) {
        HSSFRow row;
        HSSFCell cell;
        for (int i = 0; i < 2; i++) {//
            row = sheet.createRow(i);
            for (int j = 0; j <= cellSize; j++) {
                // 设置列宽，分为序号列和别的列
                if (j == 0) {
                    sheet.setColumnWidth(j, 2000);
                } else {
                    sheet.setColumnWidth(j, 8000);
                }
                cell = row.createCell(j);
                if (i == 0) {
                    cell.setCellStyle(cellStyletitle);
                } else {
                    cell.setCellStyle(cellStylehead);
                }
            }
        }
    }

    //导出所有设备
    public static String createEquipmentExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=11;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("所属公司名称"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("所属监控点"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("设备名称"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("设备型号"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("出厂编号"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("生产日期"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("安装日期"));
            cell = sheet.getRow(1).getCell(8);
            cell.setCellValue(new HSSFRichTextString("保修时长"));
            cell = sheet.getRow(1).getCell(9);
            cell.setCellValue(new HSSFRichTextString("生产产商"));
            cell = sheet.getRow(1).getCell(10);
            cell.setCellValue(new HSSFRichTextString("污染因子"));
            cell = sheet.getRow(1).getCell(11);
            cell.setCellValue(new HSSFRichTextString("标准值"));


            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }


    //导出所有排污许可
    public static String createPermitExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=9;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("所属公司名称"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("年份"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("污染因子名称"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("排污许可量"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("许可证编号"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("发证日期"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("发证机关"));
            cell = sheet.getRow(1).getCell(8);
            cell.setCellValue(new HSSFRichTextString("有效期限"));
            cell = sheet.getRow(1).getCell(9);
            cell.setCellValue(new HSSFRichTextString("排污类别"));



            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }


    //导出所有备件
    public static String createReplacementExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=12;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("产品分类"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("备件名称"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("编号"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("规格型号"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("生产厂家"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("单位"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("安装日期"));
            cell = sheet.getRow(1).getCell(8);
            cell.setCellValue(new HSSFRichTextString("保修日期"));
            cell = sheet.getRow(1).getCell(9);
            cell.setCellValue(new HSSFRichTextString("类别"));
            cell = sheet.getRow(1).getCell(10);
            cell.setCellValue(new HSSFRichTextString("更新时间"));
            cell = sheet.getRow(1).getCell(11);
            cell.setCellValue(new HSSFRichTextString("可借数量"));
            cell = sheet.getRow(1).getCell(12);
            cell.setCellValue(new HSSFRichTextString("损坏数量"));


            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }



    //导出所有易耗品
    public static String createConsumableExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=7;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("易耗品编号"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("易耗品名称"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("可用库存量"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("单位"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("质保日期"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("生产厂家"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("更新时间"));



            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }


    //导出所有车辆
    public static String createCarExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=7;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("车辆编号"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("品牌"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("车牌号"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("型号"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("状态"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("购买日期"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("最新更新"));



            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

    public static String createAssessExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir) {
        //列数
        int cellSize=6;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("运维人员"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("巡检计划完成量"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("任务安排完成量"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("巡检计划逾期任务量"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("任务安排逾期任务量"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("异常上报解决量"));



            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            whiteToExcelSheet(excelData, cellSize, sheet, cellStylebody);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

    public static String createTaskExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir,Map<String,Object> params) {
        //列数
        int cellSize=params.size();

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.taskColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            Map<String,Object> merge = new HashMap<>();
            Map<String,Object> cellValueData = new HashMap<>();
            params.forEach((key,value)->{
                String[] s = value.toString().split("-");
                HSSFCell c = sheet.getRow(1).getCell(Integer.parseInt(s[0]));
                c.setCellValue(s[1]);
                merge.put(s[0],s[2]);
                cellValueData.put(s[0],key);
            });




            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列


            //4.加入数据
            exportTask(excelData,cellSize,sheet,cellStylebody,wb,merge,cellValueData);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

    private static void exportTask(List<Map<String, Object>> excelData, int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylebody,HSSFWorkbook wb,Map<String,Object> params,Map<String,Object> cellValueData){
        HSSFRow row;
        HSSFCell cell;
        //记录taskId，起始合并row，最后合并row
        String checkTaskId = "";
        int firstRow = 2;
        int lastRow = 2;
        int index = 0;
        for (int i = 2; i < excelData.size() + 2; i++) {
            Map<String, Object> data = excelData.get(i - 2);
            row = sheet.createRow(i);
            String taskId = (String) data.get("taskId");
            //判断上一个id和当前id是否一致，不一致代表转到下一个task
            if(checkTaskId.equals(taskId)){
                //同一个task，最后合并row+1
                lastRow = lastRow+1;
                //当前一直到最后都是同一个task，合并task部分内容
                if(i == excelData.size()+1){
                    mergeTask(cellSize, sheet, params, firstRow, lastRow);
                }
            }else{
                //不是同一个task，将之前的task部分合并，并更新监测的taskId，重置起始合并row，结束合并row
                checkTaskId = taskId;
                //当前不是最初的task
                if(i != 2){
                    mergeTask(cellSize, sheet, params, firstRow, lastRow);
                }
                firstRow = i;
                lastRow = i;
                index = index +1;
            }
            for (int j = 0; j <= cellSize; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStylebody);
                if (j == 0) {
                    cell.setCellValue(index);
                } else {
                    String value = (String) data.get(cellValueData.get(String.valueOf(j)));
                    cell.setCellValue(new HSSFRichTextString(value));
                }
            }
        }
    }

    private static void mergeTask(int cellSize, HSSFSheet sheet, Map<String, Object> params, int firstRow, int lastRow) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,0,0));
        for (int j = 1; j <= cellSize; j++) {
            if ("task".equals(params.get(String.valueOf(j)))) {
                sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, j, j));// 开始行，结束行，开始列，结束列
            }
        }
    }

    public static String createTaskHistoryExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir,String savePath,String downloadUrl) {
        //列数
        int cellSize=7;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工

        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式

        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("任务名称"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("填报人员"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("今日已完成"));
            cell = sheet.getRow(1).getCell(4);
            cell.setCellValue(new HSSFRichTextString("今日未完成"));
            cell = sheet.getRow(1).getCell(5);
            cell.setCellValue(new HSSFRichTextString("需要协调"));
            cell = sheet.getRow(1).getCell(6);
            cell.setCellValue(new HSSFRichTextString("备注"));
            cell = sheet.getRow(1).getCell(7);
            cell.setCellValue(new HSSFRichTextString("记录时间"));



            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
            exportTaskHistory(excelData, cellSize, sheet, cellStylebody,wb,savePath,downloadUrl,false,"");
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

    private static void exportTaskHistory(List<Map<String, Object>> excelData, int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylebody,HSSFWorkbook wb,String savePath,String downloadUrl,boolean isTask,String rowLink){
        HSSFRow row;
        HSSFCell cell;
        BufferedImage bufferImg = null;
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        for (int i = 2; i < excelData.size() + 2; i++) {
            Map<String, Object> data = excelData.get(i - 2);
            String imgs = data.get("imgs") == null ? "" : (String) data.get("imgs");
            String files = data.get("files") == null ? "" : (String) data.get("files");
            String filesName = data.get("filesName") == null ? "" : (String) data.get("filesName");
            String[] img = imgs.split(",");
            String[] file = files.split(",");
            String[] fileName = filesName.split(",");
            row = sheet.createRow(i);
            for (int j = 0; j <= cellSize; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStylebody);
                if (j == 0) {
                    cell.setCellValue(new HSSFRichTextString((i - 1) + ""));
                } else {
                    String value=data.get("col" + j)!=null?data.get("col" + j).toString():"";
                    cell.setCellValue(new HSSFRichTextString(value));
                    if(isTask && j == 1){
                        HSSFCellStyle hlink_style = wb.createCellStyle();
                        HSSFFont hlink_font = wb.createFont();
                        hlink_font.setUnderline(HSSFFont.U_SINGLE);
                        hlink_font.setColor(HSSFColor.BLUE.index);
                        hlink_style.setFont(hlink_font);
                        hlink_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
                        hlink_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
                        hlink_style.setWrapText(true);
                        hlink_style.setLeftBorderColor(HSSFColor.BLACK.index);
                        hlink_style.setBorderLeft((short) 1);
                        hlink_style.setRightBorderColor(HSSFColor.BLACK.index);
                        hlink_style.setBorderRight((short) 1);
                        hlink_style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
                        hlink_style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
                        hlink_style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
                        HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
                        String address = "'任务安排'!"+rowLink;
                        link.setAddress(address);
                        cell.setHyperlink(link);
                        cell.setCellStyle(hlink_style);
                    }
                }
            }
            for(int m = 0;m <= img.length+file.length-1;m++){
                if(m < img.length){
                    try {
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        if(!"".equals(img[m])){
                            File imgFile = new File(savePath+img[m]);
                            if(imgFile.exists()){
                                bufferImg = ImageIO.read(imgFile);
                                ImageIO.write(bufferImg, img[m].split("\\.")[1], byteArrayOut);
                                HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 800,255,(short) (cellSize+1+m), i, (short)(cellSize+1+m), i);
                                patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    HSSFCellStyle hlink_style = wb.createCellStyle();
                    HSSFFont hlink_font = wb.createFont();
                    hlink_font.setUnderline(HSSFFont.U_SINGLE);
                    hlink_font.setColor(HSSFColor.BLUE.index);
                    hlink_style.setFont(hlink_font);
                    cell = row.createCell(m+1+cellSize);
                    cell.setCellValue(fileName[m-img.length]);
                    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                    link.setAddress(downloadUrl+file[m-img.length]);
                    cell.setHyperlink(link);
                    cell.setCellStyle(hlink_style);
                }
            }
        }
    }

    private static void whiteToExcelSheetStyleTitle(int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylehead, HSSFCellStyle cellStyletitle) {
        HSSFRow row;
        HSSFCell cell;
        for (int i = 0; i < 2; i++) {//
            row = sheet.createRow(i);
            for (int j = 0; j <= cellSize; j++) {
                // 设置列宽，分为序号列和别的列
                if (j == 0) {
                    sheet.setColumnWidth(j, 2000);
                }else if(j==18){
                    sheet.setColumnWidth(j, 20000);
                }else {
                    sheet.setColumnWidth(j, 8000);
                }
                cell = row.createCell(j);
                if (i == 0) {
                    cell.setCellStyle(cellStyletitle);
                } else {
                    cell.setCellStyle(cellStylehead);
                }
            }
        }
    }

    //导出所有企业
    public static String createQuestionExcel(String titleName, String excelName, List<Map<String, Object>> excelData, String dir,String questionUrl) {
        //列数
        int cellSize=3;

        String excelPath = "";
        HSSFWorkbook wb = new HSSFWorkbook();// 创建一个Excel文件
        HSSFSheet sheet = wb.createSheet(titleName);// 创建一个Excel的Sheet
        // 定义样式
        HSSFCellStyle cellStylehead = HSSFCellStyleUtil.initColumnHeadStyle(wb);// 表头样工
        HSSFCellStyle cellStylebody = HSSFCellStyleUtil.initColumnBodyStyle(wb);// 单元格样式
        HSSFCellStyle cellStyletitle = HSSFCellStyleUtil.initTitleStyle(wb);// 单元格样式
        FileOutputStream fileOut = null;
        try {
            HSSFRow row = null;
            HSSFCell cell = null;
            // ---------------------------1.初始化带边框的表头------------------------------
            whiteToExcelSheetStyleTitle(cellSize, sheet, cellStylehead, cellStyletitle);
            // ---------------------------2.指定单元格填充数据------------------------------
            cell = sheet.getRow(0).getCell(0);
            cell.setCellValue(new HSSFRichTextString(titleName));
            cell = sheet.getRow(1).getCell(0);
            cell.setCellValue(new HSSFRichTextString("序号"));
            cell = sheet.getRow(1).getCell(1);
            cell.setCellValue(new HSSFRichTextString("设备类型"));
            cell = sheet.getRow(1).getCell(2);
            cell.setCellValue(new HSSFRichTextString("问题标题"));
            cell = sheet.getRow(1).getCell(3);
            cell.setCellValue(new HSSFRichTextString("解决方案"));
            // ---------------------------3.合并单元格------------------------------
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,cellSize));// 开始行，结束行，开始列，结束列

            //4.加入数据
			exportQuestionExcel(excelData, cellSize, sheet, cellStylebody,wb,questionUrl);
            fileOut = new FileOutputStream(dir + File.separator + excelName + ".xls");
            wb.write(fileOut);
            excelPath = dir + "//" + excelName + ".xls";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                logger.error("文件创建失败，管理员请查找原因！");
            }
        }
        return excelPath;
    }

	private static void exportQuestionExcel(List<Map<String, Object>> excelData, int cellSize, HSSFSheet sheet, HSSFCellStyle cellStylebody,HSSFWorkbook hssfWorkbook,String questionUrl) {
		HSSFRow row;
		HSSFCell cell;
		//记录questionType，起始合并row，最后合并row
		String beforeQuestionType = "";
		int firstRow = 2;
		int lastRow = 2;
		for (int i = 2; i < excelData.size() + 2; i++) {
			Map<String, Object> data = excelData.get(i - 2);
			row = sheet.createRow(i);
			String questionType = (String) data.get("col1");
			//判断上一个id和当前id是否一致，不一致代表转到下一个
			if(beforeQuestionType.equals(questionType)){
				//同一个task，最后合并row+1
				lastRow++;
				//当前一直到最后都是同一个task，合并task部分内容
				if(i == excelData.size()+1){
					sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,1,1));
				}
			}else{
				//不是同一个questionType，将之前的合并，并更新questionType，重置起始合并row，结束合并row
				beforeQuestionType = questionType;
				//当前不是最初的task
				if(i != 2){
					sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,1,1));
				}
				firstRow = i;
				lastRow = i;
			}
			for (int j = 0; j <= cellSize; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(cellStylebody);
				if (j == 0) {
					cell.setCellValue(new HSSFRichTextString((i - 1) + ""));
				} else if(j == 3){
					String url=questionUrl+"?id="+data.get("col4");
					cell.setCellFormula("HYPERLINK(" + "\""+url+"\"" + "," + "\"点击查看详细解决方案\")");
					cell.setCellStyle(linkStyle(hssfWorkbook));
				} else {
					String value=data.get("col" + j)!=null?data.get("col" + j).toString():"";
					cell.setCellValue(new HSSFRichTextString(value));
				}
			}
		}
	}

	/**
	 * 设置超链接等样式
	 * @param hssfWorkbook
	 * @return
	 */
	public static HSSFCellStyle linkStyle(HSSFWorkbook hssfWorkbook) {
		// 生成并设置另一个样式
		HSSFCellStyle linkStyle = hssfWorkbook.createCellStyle();
		linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		linkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		//设置单元格边框
        linkStyle.setBorderBottom((short) 1);
        linkStyle.setBorderLeft((short) 1);
        linkStyle.setBorderRight((short) 1);
        linkStyle.setBorderTop((short) 1);
		//设置单元格背景颜色
//        linkStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//        linkStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFFont font = hssfWorkbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		//设置字体下划线
		font.setUnderline((byte) 1);
		//设置字体颜色
		font.setColor(HSSFColor.BLUE.index);
		//设置字体
		linkStyle.setFont(font);
		// 生成另一个字体
//        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		linkStyle.setFont(font);
		return linkStyle;
	}


	public static String exportWord( String wordName,String content, String dir) {
		String path="";
		FileOutputStream fileOut = null;
		try {
			byte b[] = content.getBytes("GBK");  //这里是必须要设置编码的，不然导出中文就会乱码。
			ByteArrayInputStream bais = new ByteArrayInputStream(b);//将字节数组包装到流中

			/*
			 * 关键地方
			 * 生成word格式 */
			POIFSFileSystem poifs = new POIFSFileSystem();
			DirectoryEntry directory = poifs.getRoot();
			DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);

			fileOut = new FileOutputStream(dir + File.separator + wordName + ".docx");
			poifs.writeFilesystem(fileOut);

			path = dir + "//" + wordName + ".docx";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException e) {
				logger.error("文件创建失败，管理员请查找原因！");
			}
		}
		return path;
	}

}
