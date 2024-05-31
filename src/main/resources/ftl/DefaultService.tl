package ${packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${packageName}.entity.${Entity};

public interface ${Entity}Service extends IService<${Entity}> {

    Page findPage(Page page);

    void insert(${Entity} ${entity});

    void update(${Entity} ${entity});

    void delById(${PrimaryType} ${Primary});

}
