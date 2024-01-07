package com.example.utils;

import com.example.pojo.Student;
import com.example.pojo.Teacher;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.example.utils.BCryptPasswordUtils.encodePassword;

public class ExcelRead {

    /**
     * 学生excel处理
     * @param inputStream
     * @return
     */
    public ArrayList<Student> Read(InputStream inputStream) {
        ArrayList<Student> excel = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            // 获取第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);

            // 迭代每一行
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                // 跳过表头行
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                // 创建学生对象
                Student student = new Student();

                // 迭代每一列
                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    // 根据列索引设置学生对象的属性
                    switch (columnIndex) {
                        case 0:
                            student.setStuId((int) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            student.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            student.setMajor(currentCell.getStringCellValue());
                            break;
                        case 3:
                            student.setCollege(currentCell.getStringCellValue());
                            break;
                        case 4:
                            student.setUniversity(currentCell.getStringCellValue());
                            break;
                        case 5:
                            student.setGrand((int) currentCell.getNumericCellValue());
                            break;
                        default:
                            // 处理其他列...
                    }
                }

                // 将学生对象添加到列表
                excel.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excel;
    }

    /**
     * 老师excel导入
     * @param inputStream
     * @return
     */

    public ArrayList<Teacher> Read2(InputStream inputStream) {
        ArrayList<Teacher> excel = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            // 获取第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);

            // 迭代每一行
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                // 跳过表头行
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                // 创建学生对象
                Teacher teacher = new Teacher();

                // 迭代每一列
                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int columnIndex = currentCell.getColumnIndex();

                    // 根据列索引设置对象的属性
                    switch (columnIndex) {
                        case 0:
                            teacher.setStaffId((int) currentCell.getNumericCellValue());
                            break;
                        case 1:
                            teacher.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            teacher.setPermission((int)currentCell.getNumericCellValue());
                            break;
                        case 3:
                            teacher.setUniversity(currentCell.getStringCellValue());
                            break;
                        case 4:
                            teacher.setCollege(currentCell.getStringCellValue());
                            break;
                        default:
                            // 处理其他列...
                    }
                }

                // 将学生对象添加到列表
                excel.add(teacher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excel;
    }
}
