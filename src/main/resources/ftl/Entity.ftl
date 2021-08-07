package ${config.basepkg}.entity;

import com.sendinfo.dataplus.core.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
/**
* @类名: ${table.comment}
* @描述: 实体对象 ${table.comment}
* @作者: ${config.user}
* @日期: ${config.date}
*/
@Setter
@Getter
@Table(name = "${table.name}")
public class ${table.javaName} extends BaseEntity<Long> {

    private static final long serialVersionUID = ${helper.serialVersionUID};

    // ~~~~实体属性
    <#list table.columns as column>
        <#if column.view>
            /**
            * ${column.comment}
            */
            @ApiModelProperty(value = "${column.comment}")
            <#if column.name == 'ID'>
                @ID
            <#else>
                @Column(name = "${column.name}")
            </#if>
            <#if column.javaType=='java.util.Date'>
                @DateTimeFormat(pattern = "yyyy-MM-dd")
            </#if>
            private ${column.javaType} ${column.javaName};
        </#if>
    </#list>

    @Override
    public Long getId() {
    return super.getId();
    }

    @Override
    public void setId(Long id) {
    super.setId(id);
    }

    }
