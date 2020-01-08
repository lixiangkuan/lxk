package com.example.lxk.demolxk;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 给entity加上swagger的注解
 */
public class FileControllerTest {
    /**
     * 需要转换的.java文件路径
     */
    private static final String SOURCE ="D:/data/source/test/";
    /**
     * 转换后.java文件路径
     */
    private static final String TARGET ="D:/data/source/target/";

    private static final String REGEX_NOTE_BLOCK ="\\/\\*(\\s|.)*?\\*\\/";
    private static final String REGEX_NOTE_LINE ="\\/\\/[^\\n]*";



    public static void main(String[] args) throws IOException {

        File[] source = listFiles(SOURCE);
        int fileNum = source.length;
        for (int i = 0; i < source.length; i++) {
            System.out.println("正在处理第"+(i+1)+"个  总数"+fileNum+" 文件名"+source[i].getName());
            processFile(readFileToList(source[i]),source[i].getName());
        }

    }


    private static File[] listFiles(String path){
        File file = new File(path);
        if (file.isDirectory()) {
            return file.listFiles(f-> f.getName().endsWith(".java"));
        }
        return null;
    }

    private static List<String> readFileToList(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath()));
    }

    private static void processFile(List<String> text,String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        String code;
        boolean firstMapping = false;
        for (int i = 0; i < text.size(); i++) {
            code = null;

            if(isNeedMethod(text.get(i)) != -1
                    &&!firstMapping){
                firstMapping = true;
                result.add(text.get(i));
                continue;
            }

            if(isNeedMethod(text.get(i)) == 0){
                code = StringUtils.replace(text.get(i),"(","(value = ");
                code = StringUtils.replace(code,")",",method = {RequestMethod.GET,RequestMethod.POST})");
                result.add(code);
                continue;
            }else if(isNeedMethod(text.get(i))==1){
                result.add(StringUtils.replace(text.get(i),")",",method = {RequestMethod.GET,RequestMethod.POST})"));
                continue;
            }
            result.add(text.get(i));
            code = null;

        }
//        result.stream().forEach(System.out::println);
        Files.write(Paths.get(TARGET+fileName),result, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        result.clear();
    }

    private static int isNeedMethod(String text){
        if(StringUtils.indexOf(text,"@RequestMapping" )>-1&&
                StringUtils.indexOf(text,"value" )>-1&&
                StringUtils.indexOf(text,"method" )==-1){

            return 1;
        }else if(StringUtils.indexOf(text,"@RequestMapping" )>-1&&
                StringUtils.indexOf(text,"value" )==-1&&
                StringUtils.indexOf(text,"method" )==-1){

            return 0;
        }
        return -1;
    }
}
