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
public class FileSwaggerTest {
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
        int start=0;
        String notes;
        for (int i = 0; i < text.size(); i++) {
            notes = null;

            if(isNeedNotes(text.get(i))){
                notes = getNotes(text,start,i);
                if(StringUtils.equals(notes,"javaclass")){
                    result.add("@ApiModel(value =\""+StringUtils.replace(fileName,".java","")+"\")");
                    notes = null;
                }
                start=i+1;

            }
            if(StringUtils.isNotBlank(notes)){
                result.add("    @ApiModelProperty(value = \""+notes+"\" ,example = \"1\")");

            }
            result.add(text.get(i));
            if(StringUtils.indexOf(text.get(i),"package ")>-1){
                result.add("\n");
                result.add("import io.swagger.annotations.ApiModel;");
                result.add("import io.swagger.annotations.ApiModelProperty;");
            }

        }
//        result.stream().forEach(System.out::println);
        Files.write(Paths.get(TARGET+fileName),result, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        result.clear();
    }

    private static boolean isNeedNotes(String text){
        if(StringUtils.indexOf(text," class " )>-1){
            return true;
        }else if(StringUtils.indexOf(text," private" )>-1 &&
                StringUtils.indexOf(text,"serialVersionUID")==-1&&
                !StringUtils.startsWith(text,"//")){
            return true;
        }
        return false;
    }

    private static String getNotes(List<String> text,int start,int end){
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end+1; i++) {
            sb.append(text.get(i)).append("\n");
        }
        return findNotes(sb.toString());
    }

    private static String findNotes(String text){
        String note = null;
        if(StringUtils.indexOf(text," class " )>-1){
            return "javaclass";
        }

        Pattern pattern = Pattern.compile(REGEX_NOTE_BLOCK);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            note =  matcher.group(0);
        } else{
            String[] tmp = StringUtils.split(text,"\n");
            pattern = Pattern.compile(REGEX_NOTE_LINE);
            matcher = pattern.matcher(tmp[tmp.length-1]);
            if(matcher.find()){
                note = matcher.group(0);
            }
        }

        note = StringUtils.replace(note,"*","");
        note = StringUtils.replace(note,"/","");
        note = StringUtils.replace(note,"\n","");
        note = StringUtils.replace(note,"<br>","");
        note = StringUtils.replace(note,"'","");
        note = StringUtils.replace(note,"\"","");
        note = StringUtils.trim(note);
        return note;
    }
}
