package com.vue.adminlte4j.support.store;


import com.vue.adminlte4j.util.EnvUtils;

import com.vue.adminlte4j.util.ReflectUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/2/5.
 */
public interface BaseStore {


    String WORKSPACE_DIR = "ui-model" ;
    String APP_INFO_FILE = "app_info.s" ;
    String MENU_ITEM_FILE = "menu_items.s";

    /**
     * 获取fileName的存储文件路劲
     * @param fileName
     * @return
     */
    default Path getStorePath(String fileName) {
        if(EnvUtils.isDevelopment)
            return getWorkSpacePath(fileName) ;
        return getPathFromClassPath(fileName) ;
    }

    /**
     * 获取当前的工作空间
     * @param dir
     * @return
     */
    default Path getWorkSpacePath(String dir) {
        Path path = EnvUtils.getJavaResourcesPath();
        return Paths.get(path.toString(), WORKSPACE_DIR, dir) ;
    }


    default Path getPathFromClassPath(String fileName) {
        String configPath = this.getClass().getClassLoader().getResource(WORKSPACE_DIR).getPath();
        return Paths.get(configPath , fileName) ;
    }


    default File getOrCreateFile(String fileName) throws IOException {
        if(!EnvUtils.isDevelopment)
            throw new IllegalStateException("current not in development Mode !") ;

        File storeFile =  getWorkSpacePath(fileName).toFile() ;
        if(!storeFile.exists()) {
            File parentFile = storeFile.getParentFile() ;
            if(!parentFile.exists())
                parentFile.mkdirs() ;
            storeFile.createNewFile() ;
        }
        return storeFile;

    }

    default void writeObject(Object data , String fileName) throws IOException {
        //indexUrl , name first line is head
        //

        List<Object> datas = new ArrayList<>() ;
        if(data instanceof List) {
            datas.addAll((List)data) ;
        } else {
            datas.add(data) ;
        }

        List<Field> fieldList = ReflectUtils.findAllField(datas.get(0).getClass());

        if(fieldList.isEmpty())
            return;


        StringBuilder headerBuilder = new StringBuilder() ;
        List<String> lines = new ArrayList<>() ;
        datas.forEach(e->{
            boolean isEmpty = lines.isEmpty() ;
            StringBuilder valueBuilder = new StringBuilder() ;
            for(Field field : fieldList) {
                if(!(field.getType().isPrimitive() || field.getType().getSimpleName().equals("String")))
                    continue;
                if(Modifier.isFinal(field.getModifiers()))
                    continue;
                if(isEmpty)
                    headerBuilder.append(field.getName()).append(",") ;
                Object val = ReflectUtils.getValue(field , e) ;
                if(val != null)
                    valueBuilder.append(val) ;
                valueBuilder.append(",") ;
            }
            if(isEmpty) {
                lines.add(headerBuilder.toString());
            }
            lines.add(valueBuilder.toString()) ;
        });

        Files.write(getOrCreateFile(fileName).toPath() , lines);

    }

    default <T> T readObject(String fileName , Class<T> requiredType) throws Exception {
        List<T> resultLists = readListObject(fileName , requiredType) ;
        if(resultLists.isEmpty()) {
            return  null ;
        }
        return resultLists.get(0) ;
    }

    default <T> List<T> readListObject(String fileName , Class<T> requiredType) throws Exception {

        List<String> lines = Files.readAllLines(getStorePath(fileName));
        List<T> results = new ArrayList<>() ;
        String[] headers = lines.get(0).split(",");
        for(int i = 1 ; i < lines.size() ; i++ ) {
            T o = requiredType.newInstance() ;
            String[] values = lines.get(i).split(",") ;
            for(int j = 0 ; j < headers.length ; j++ ) {
                Field field = requiredType.getDeclaredField(headers[j]) ;
                if(field.getType().isPrimitive()) {
                    String typeName = field.getType().getName() ;
                    if(typeName.equals("int")  )
                        ReflectUtils.setValue(field , o , Integer.valueOf(values[j]));
                } else {
                    ReflectUtils.setValue(field , o , values[j]);
                }

            }
            results.add(o) ;
        }
        return  results ;
    }

}
