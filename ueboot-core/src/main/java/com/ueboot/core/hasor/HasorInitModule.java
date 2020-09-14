package com.ueboot.core.hasor;

import com.ueboot.core.service.HttpRequestValidatorService;
import lombok.extern.slf4j.Slf4j;
import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.dataql.runtime.ThrowRuntimeException;
import net.hasor.dataway.spi.ApiInfo;
import net.hasor.dataway.spi.PreExecuteChainSpi;
import net.hasor.dataway.spi.ResultProcessChainSpi;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.utils.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * hasor框架初始化使用
 * @author yangkui
 */
@DimModule
@Component
@Slf4j
@ConditionalOnMissingBean(name = "hasorInitModule")
public class HasorInitModule implements net.hasor.core.Module {
    @Autowired
    private DataSource dataSource ;

    @Autowired
    private List<HttpRequestValidatorService> httpRequestValidatorServices;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        //将 Spring 使用的数据源导入到 Hasor 环境共 Dataway 使用
        apiBinder.installModule(new JdbcModule(Level.Full,this.dataSource));
        //响应结果改写
        apiBinder.bindSpiListener(ResultProcessChainSpi.class, new ResultProcessChainSpi() {
            @Override
            public Object callAfter(boolean formPre, ApiInfo apiInfo, Object result) {
                String json = "";
                if(result!=null){
                    json =  JSON.toString(result);
                }
                log.info("dataway返回结果:apiPath:{},result:{}",apiInfo.getApiPath(),json);
                return result;
            }

            @Override
            public Object callError(boolean formPre, ApiInfo apiInfo, Throwable e) {
                //记录异常信息
                log.error(e.getMessage(),e);
                if (e instanceof ThrowRuntimeException) {
                    return ((ThrowRuntimeException) e).getResult().unwrap();
                } else {
                    return e.getMessage();
                }
            }
        });
        //拦截所有请求，进行权限等校验
        apiBinder.bindSpiListener(PreExecuteChainSpi.class, (apiInfo, future) -> {
            //调用权限框架进行校验，校验不通过直接抛出异常
            this.httpRequestValidatorServices.forEach((service)->service.preExecute(apiInfo,future));
        });

    }
}