package com.mm.util.gen.controller;

import com.mm.util.gen.freemarker.CodeFile;
import com.mm.util.gen.freemarker.CodeService;
import com.mm.util.gen.metadata.DatabaseFactory;
import com.mm.util.gen.utils.ApplicationUtils;
import com.mm.util.gen.utils.ZipUtils;
import com.mm.util.gen.wapper.DatabaseWapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/codeGen")
public class GenController {


    @RequestMapping("/getCatalogs")
    @ResponseBody
    public Object getCatalogs(String token) throws Exception {
        String mm = "";
        JdbcTemplate jdbctemplate = SelectorController.jtMap.get(token);
        if (jdbctemplate == null) {
            return null;
        }
        DatabaseMetaData metaData = jdbctemplate.getDataSource().getConnection().getMetaData();
        ResultSet resultSet = metaData.getCatalogs();
        while (resultSet.next()) {
            String db = resultSet.getString("TABLE_CAT");
            mm += db + ",";
        }
        mm.substring(0, mm.length() - 1);
        return mm;
    }

    @RequestMapping("/getTables")
    @ResponseBody
    public Object getTable(String token, String database) throws Exception {
        String mm = "";
        JdbcTemplate jdbctemplate = SelectorController.jtMap.get(token);
        if (jdbctemplate == null) {
            return null;
        }
        List<Map<String, Object>> ll = jdbctemplate.queryForList("select table_name from information_schema.tables where table_schema='" + database + "'");
        for (Map m : ll) {
            mm += m.get("table_name") + ",";
        }
        mm.substring(0, mm.length() - 1);
        return mm;
    }


    /**
     * http://localhost:8080/demo/handle01
     */
    @RequestMapping("/generate")
    @ResponseBody
    public synchronized Object generate(HttpSession session, String packageName, String databaseName, String table, String token) throws Exception {
        // ?????????
        Map<String, Object> config = new HashMap<String, Object>();
        // ????????????  ??? todo ????????????????????????????????????
        config.put("basepkg", "com.sendinfo.dataplus." + packageName);
        // WEB???????????? todo ???????????????????????????????????????
        config.put("catalog", packageName);
        // ?????? ??????(?????????)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        config.put("date", dateFormat.format(new Date()));
        config.put("user", "humian");
        // ???????????????????????? todo ??????????????????????????????????????????????????????
        String rootPath = session.getServletContext().getRealPath("/") + "code-gen";
        ZipUtils.deleteDir(rootPath);
        // ?????????catalog(?????????)
        String catalog = "";
        // ?????????schema???????????????????????? todo ?????????????????????????????????
        String schema = databaseName;
        // ????????????????????????????????????????????????Like?????? todo ?????????????????????????????????????????????????????????????????????
        String tableName = table;
        // ????????????????????????all(??????)???entity(???????????????)???java(?????????Java??????)???page(???????????????)???settle(????????????)??????????????????
        String taskName = "easyui";

        ApplicationContext context = ApplicationUtils.application;
        //DatabaseFactory databaseFactory = (DatabaseFactory) context.getBean("databaseFactory");
        DatabaseFactory databaseFactory = SelectorController.dbMap.get(token);
        if (databaseFactory == null) {
            return null;
        }
        //??????
        //DatabaseWapper database = databaseFactory.readDatabaseWapper(catalog.toUpperCase(), schema.toUpperCase(), tableName.toUpperCase());
        //??????
        DatabaseWapper database = databaseFactory.readDatabaseWapper(catalog.toLowerCase(), schema.toLowerCase(), tableName.toLowerCase());
        System.out.println("Tables: " + Arrays.asList(database.getTableNames()));
        CodeService service = (CodeService) context.getBean("codeService");
        List<CodeFile> filelist = service.gencode(database, taskName, config);
        writeCodeFile(rootPath, filelist);
        return "success";
    }

    /*
     * ??????????????????
     */
    private static void writeCodeFile(String rootPath, List<CodeFile> filelist) throws Exception {
        File root = new File(rootPath);
        if (!root.exists()) {
            root.mkdir();
        } else {
            //FileUtils.deleteDirectory(root);
            //root.mkdir();
        }
        for (CodeFile file : filelist) {
            String path = file.getPath().replace(".", "/");
            path = rootPath + "/" + path;
            File pathFile = new File(path);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            path += "/" + file.getName();
            OutputStream os = new FileOutputStream(new File(path));
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(os, "utf-8");
            oStreamWriter.append(file.getContent());
            oStreamWriter.close();
            //IOUtils.write(file.getContent(), os);
            IOUtils.closeQuietly(os);
        }
    }

    /*
     * ??????????????????
     */
    public static void printCodeFile(List<CodeFile> filelist) {
        for (CodeFile file : filelist) {
            System.out.println("---------------------------------------------------------");
            System.out.println(file.getPath() + "///" + file.getName());
            System.out.println(file.getContent());
        }
    }

    @RequestMapping("/download")
    public synchronized void download(HttpServletResponse response, HttpSession session) throws IOException {
        String sourceFilePath = session.getServletContext().getRealPath("/") + "code-gen";

        File sourceDir = new File(sourceFilePath);
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            String baseDir = "/";
            ZipUtils.compress(sourceDir, baseDir, zos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if(zos!=null)
                try {
                    zos.close();
                    response.getOutputStream().close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }


    }
}
