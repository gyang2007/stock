package org.stock.db.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties p = new Properties();

    static{
        try{
            p.load(new BufferedReader(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream("config/jdbc.properties"), "UTF-8")));
        }catch(Exception e){
            logger.error("load properties error:", e);
        }
    }

    public static String getProperties(String key, String defaultValue){
        String value = p.getProperty(key, defaultValue);
        logger.info("config is : "+ key + " : " + value);
        return value;
    }

    public static void main(String[] args){
        System.out.println(PropertiesUtil.getProperties("mysql.jdbc.driver", ""));
    }

}
