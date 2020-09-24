package ru.inversionkavkaz.plcxlsxreader;

import com.google.devtools.common.options.OptionsParser;
import com.google.gson.Gson;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.inversionkavkaz.plcxlsxreader.entity.*;
import ru.inversionkavkaz.plcxlsxreader.utils.HibernateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ReadExcel {
    static {
        System.setProperty("logback.configurationFile", "xlsxreadersl4j.xml");
    }

    private Logger log = LoggerFactory.getLogger(ReadExcel.class);

    private void start(String[] args) throws IOException {
        int commitCountSize = 1000;
        OptionsParser parser = OptionsParser.newOptionsParser(ProgramOption.class);
        parser.parseAndExitUponError(args);
        ProgramOption options = parser.getOptions(ProgramOption.class);
        if (options.connection.isEmpty() || options.filePath.isEmpty()) {
            printUsage(parser);
            return;
        }

        File excelFile = new File(options.filePath);
        if (!excelFile.exists()) {
            log.error("Error^ import file not found " + options.filePath);
            return;
        }

        File controlFile = new File(options.controlFile);
        if (!controlFile.exists()) {
            log.error("Error^ control file not found " + options.controlFile);
            return;
        }

        String user = options.connection.split("/")[0];

        log.info("Import started file:" + excelFile.getName() + " user: " + user);

        String fileData = Files.readAllLines(Paths.get(controlFile.getPath())).stream().map(String::valueOf).collect(Collectors.joining());
        Gson gson = new Gson();
        XLSXTable xlsxTable = gson.fromJson(fileData, XLSXTable.class);

        Session session = HibernateUtils.getSession(options.connection);
        if (session == null) return;

        //Очищаем предыдущие результаты импорта
        String clearSQL = "delete from " + xlsxTable.getName() + " where upper(cusrlogname)='" + user.toUpperCase() + "'";

        session.beginTransaction();
        session.createNativeQuery(clearSQL).executeUpdate();
        session.getTransaction().commit();

        FileInputStream fis = new FileInputStream(excelFile);

        // we create an XSSF Workbook object for our XLSX Excel File
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(1000)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(8182)      // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(fis);

        String sqlStr = xlsxTable.getInsertSQL();
        NativeQuery insertQuery = session.createNativeQuery(sqlStr);

        session.beginTransaction();
        boolean isFinish = false;

        int commitCounter = 0;
        int rowInd = 1;
        int rowsProcessed = 0;
        int rowsSaved = 0;

        for (Row r : workbook.getSheetAt(0)) {
            //начинаем импорт с указанной строки
            if (rowInd++ < xlsxTable.getImportStartRowIndex()) continue;

            int oraParamIndex = 2;
            for (Map.Entry<Integer, TableColumn> tc : xlsxTable.getTableColumns().entrySet()) {
                oraParamIndex++;
                if (r.getCell(tc.getKey() - 1) == null) {
                    isFinish = true;
                    break;
                } else {
                    String valString = r.getCell(tc.getKey() - 1).getStringCellValue();
                    isFinish = tc.getValue().getNotNull() != null && tc.getValue().getNotNull() && (valString == null || valString.isEmpty());
                }
                if (isFinish) {
                    break;
                }
                switch (tc.getValue().getColumnType()) {
                    case String:
                        insertQuery.setParameter(oraParamIndex, r.getCell(tc.getKey() - 1).getStringCellValue());
                        break;
                    case Long:
                    case Double:
                        insertQuery.setParameter(oraParamIndex, r.getCell(tc.getKey() - 1).getNumericCellValue());
                        break;
                    case Date: case TimeStamp:
                        //пока так
                        insertQuery.setParameter(oraParamIndex, r.getCell(tc.getKey() - 1).getStringCellValue());
                        break;
                }
            }

            //проверить конец ли это
            if (isFinish) {
                break;
            } else {
                try {
                    rowsProcessed++;
                    //Id
                    insertQuery.setParameter(1, rowsProcessed);
                    //cusrlogname
                    insertQuery.setParameter(2, user.toUpperCase());

                    insertQuery.executeUpdate();
                    rowsSaved++;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Error executeUpdateQuery row: " + rowsProcessed, e);
                }
            }

            if (commitCounter++ > commitCountSize) {
                session.getTransaction().commit();
                session.beginTransaction();
                commitCounter = 0;
            }
        }

        finish(workbook, fis, session);

        if (rowsProcessed == rowsSaved)
            log.info("Import successfully finished. Processed/Saved " + rowsProcessed + "/" + rowsSaved + " rows. File: " + excelFile.getName() + " user: " + user);
        else
            log.error("Import finished with errors. Processed/Saved " + rowsProcessed + "/" + rowsSaved + " rows. File: " + excelFile.getName() + " user: " + user);
    }

    private void finish(Workbook workbook, FileInputStream fis, Session session) throws IOException {
        workbook.close();
        fis.close();
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) throws IOException {
        new ReadExcel().start(args);
    }

//    private static void saveRow(Session session, XLSXType26Row row) {
//        try {
//            session.save(row);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private static void printUsage(OptionsParser parser) {
        System.out.println("plcXLSXRader (C) InversionKavkaz.RU Valeriy Bugaev. Usage: java -jar plcxlsxreader.jar OPTIONS");
        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(),
                OptionsParser.HelpVerbosity.LONG));
    }

}