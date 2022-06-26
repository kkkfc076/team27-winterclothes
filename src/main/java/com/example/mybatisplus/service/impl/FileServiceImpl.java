package com.example.mybatisplus.service.impl;


import com.example.mybatisplus.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMM");

    @Value("${file-upload-path}")
    private String fileUploadPath;// ./file   当前项目路径下的文件夹file

    @Override
    public Map upload(MultipartFile file) throws IOException {
        Map<String, String> map = storeFile(file, Paths.get(fileUploadPath, "image").toString());
        return map;
    }

    @Override
    public void download(HttpServletResponse response) {
        //
        File file = Paths.get(fileUploadPath, "./image/202206/-144158df39d34d7894139e870ba1a14f.jpeg").toFile();
        //File file = new File("E:\\FTP\\soft\\阿里巴巴Java开发手册（华山版）.pdf");
        //application/pdf
        response.setContentType("application/pdf");
        try {
            response.setHeader("Content-Disposition","attachment; filename="+new String("阿里巴巴Java开发手册（华山版）.pdf".getBytes("gbk"),"iso8859-1"));
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream os = response.getOutputStream();

            byte[] tong = new byte[1024];
            int len = 0;
            while ((len=fis.read(tong))!=-1){
                os.write(tong,0,len);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> storeFile(MultipartFile file, String fileUploadPath) throws IOException {
        //   ./file/image
        String yearMonth = SDF.format(new Date());//当前年月
        //String random = "" + (int) (Math.random() * 1000);//随机4位数,没补0
        String fileName = file.getOriginalFilename();//文件全名
        String suffix = suffix(fileName);//文件后缀
        String relPath = "/" + yearMonth + "/" + "-" + UUID.randomUUID().toString().replaceAll("-","") + suffix;
        //  /202206/-123lkndajk1jk3nnm,d1jk31nm.png
        String toPath = fileUploadPath + relPath;
        FileOutputStream out = null;

        File toFile = new File(toPath).getParentFile();
        if (!toFile.exists()) {
            toFile.mkdirs(); //自动创建目录
        }
        try {
            out = new FileOutputStream(toPath);
            out.write(file.getBytes());
            out.flush();
            Map<String, String> map = new HashMap();
            //   ./image/202206/-123lkndajk1jk3nnm,d1jk31nm.png
            map.put("url", "./image" + relPath);
            log.info(relPath);
            return map;
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 后缀名或empty："a.png" => ".png"
     */
    private static String suffix(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i == -1 ? "" : fileName.substring(i);
    }
}
