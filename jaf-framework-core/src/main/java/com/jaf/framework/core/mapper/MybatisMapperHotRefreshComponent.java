package com.jaf.framework.core.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * mybatis xml 配置文件热刷新插件
 *
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class MybatisMapperHotRefreshComponent implements InitializingBean {

    private static final Logger LOG = Logger.getLogger(MybatisMapperHotRefreshComponent.class);

    // 清理原有资源
    private static final String[] mapFieldNames = new String[]{
            "mappedStatements", "caches",
            "resultMaps", "parameterMaps",
            "keyGenerators", "sqlFragments"
    };

    private Configuration configuration;
    private SqlSessionFactory sqlSessionFactory;
    private String mapperPath = "";  // mapper 映射文件路径，e.g. D:\workspace-idea\jaf-framework\jaf-framework-core\target\test-classes\mybatis\mapping
    private int refreshTime = 5000;    // 刷新时间 (ms)
    private boolean debugModel = false;    // 是否开发模式，只有该值为 true 时才会进行刷新

    private final Set<File> xmlFiles = new HashSet<>();
    private final Map<String, Long> timestamp = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        if (!debugModel || StringUtils.isBlank(mapperPath))
            return;

        if(LOG.isInfoEnabled())
            LOG.info("=========== 已开启 mybatis mapper 文件自动刷新功能 ===========");

        File root = new File(mapperPath);
        if (root.exists()) {
            this.configuration = sqlSessionFactory.getConfiguration();
            scanFiles(root);
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            executor.scheduleWithFixedDelay(() -> {
                getRefreshFiles().forEach(this::refresh);
            }, 0, refreshTime, TimeUnit.MILLISECONDS);
        }
    }

    // 递归查找所有*Mapper.xml/*Dao.xml文件
    private void scanFiles(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                Stream.of(f.listFiles()).forEach(this::scanFiles);
            } else {
                String fileName = f.getName();
                if (fileName.endsWith("Mapper.xml") || fileName.endsWith("Dao.xml")) {
                    xmlFiles.add(f);
                    timestamp.put(f.getAbsolutePath(), f.lastModified());
                }
            }
        }
    }

    /**
     * 获取修改的Dao.xml文件
     *
     * @return
     */
    private List<File> getRefreshFiles() {
        List<File> result = new ArrayList<>();
        for (File file : xmlFiles) {
            long curLastModified = file.lastModified();
            long lastModified = timestamp.get(file.getAbsolutePath());
            if (curLastModified > lastModified) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * 执行刷新
     * 清理掉原来 configuration 的配置信息,让其重新加载一次
     */
    @SuppressWarnings("unchecked")
    private void refresh(File mapping) {
        InputStream inputStream = null;
        String resource = mapping.getAbsolutePath();
        String daoName = mapping.getName().replaceAll(".xml", ".").toUpperCase();
        String pack = getPackage(resource);
        try {
            inputStream = new FileInputStream(mapping);
            // 清理已加载的资源标识，方便让它重新加载。
            Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
            loadedResourcesField.setAccessible(true);
            Set<String> loadedResourcesSet = ((Set<String>) loadedResourcesField.get(configuration));
            for (String fieldName : mapFieldNames) {
                Field field = configuration.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Map<String, Object> map = ((Map<String, Object>) field.get(configuration));
                Object[] keys = map.keySet().toArray();
                for (Object key : keys) {
                    if (key.toString().toUpperCase().contains(daoName)) {
                        map.remove(key);
                    }
                    if (key.toString().contains(pack)) {
                        map.remove(key);
                    }
                }
                field.set(configuration, map);
            }
            List<String> removeList = new ArrayList<>();
            for (String str : loadedResourcesSet) {
                if (str.equals(resource)) {
                    removeList.add(resource);
                }
                if (str.contains(mapping.getName())) {
                    removeList.add(str);
                }
            }
            loadedResourcesSet.removeAll(removeList);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration,
                    resource, configuration.getSqlFragments());
            xmlMapperBuilder.parse();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ErrorContext.instance().reset();
            timestamp.put(mapping.getAbsolutePath(), mapping.lastModified());
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(LOG.isDebugEnabled()) {
                LOG.debug("****************** " + mapping.getName() + " 重新加载成功 *******************");
            }
        }
    }

    private String getPackage(String path) {
        path = path.replace(mapperPath, "").replace("Mapper.xml", "").replaceAll("\\\\", ".");
        return path.substring(1, path.length());
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setDebugModel(boolean debugModel) {
        this.debugModel = debugModel;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
