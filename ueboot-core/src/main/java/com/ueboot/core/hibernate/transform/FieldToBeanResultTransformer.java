package com.ueboot.core.hibernate.transform;

import java.util.Arrays;

import com.ueboot.core.hibernate.utils.CamelUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * 数据库返回的大写带下划线的字段属性数据直接转驼峰规则的字段实体类
 *
 * @author xiangli.ma
 * @date 2018/8/6
 */
@Slf4j
public class FieldToBeanResultTransformer extends AliasToBeanResultTransformer {

    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;

    public <T> FieldToBeanResultTransformer(Class<T> resultClass) {
        super(resultClass);
        this.isInitialized = false;
        this.resultClass = resultClass;
    }

    @Override
    public boolean isTransformedValueATupleElement(String aliases[], int tupleLength) {
        return false;
    }

    @Override
    public Object transformTuple(Object tuple[], String aliases[]) {
        try {
            if (!this.isInitialized) {
                this.initialize(aliases);
            } else {
                this.check(aliases);
            }

            Object result = this.resultClass.newInstance();

            for (int i = 0; i < aliases.length; ++i) {
                if (this.setters[i] != null) {
                    this.setters[i].set(result, tuple[i], (SessionFactoryImplementor) null);
                }
            }

            return result;
        } catch (InstantiationException var5) {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
        } catch (IllegalAccessException var6) {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
        }
    }

    private void initialize(String aliases[]) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(new PropertyAccessStrategy[]{PropertyAccessStrategyBasicImpl.INSTANCE, PropertyAccessStrategyFieldImpl.INSTANCE});
        this.aliases = new String[aliases.length];
        this.setters = new Setter[aliases.length];

        for (int i = 0; i < aliases.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                this.aliases[i] = alias;
                alias = CamelUtils.underlineToCamel(alias);
                try {
                    this.setters[i] = propertyAccessStrategy.buildPropertyAccess(this.resultClass, alias).getSetter();
                } catch (Exception e) {
                    this.setters[i] = null;
                }
            }
        }

        isInitialized = true;
    }

    private void check(String aliases[]) {
        if (!Arrays.equals(aliases, this.aliases)) {
            throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            FieldToBeanResultTransformer that = (FieldToBeanResultTransformer) o;
            if (!this.resultClass.equals(that.resultClass)) {
                return false;
            } else {
                return Arrays.equals(this.aliases, that.aliases);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = this.resultClass.hashCode();
        result = 31 * result + (this.aliases != null ? Arrays.hashCode(this.aliases) : 0);
        return result;
    }
}
