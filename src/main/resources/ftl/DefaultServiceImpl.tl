package ${packageName}.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${packageName}.entity.${Entity};
import ${packageName}.mapper.${Entity}Mapper;
import ${packageName}.service.${Entity}Service;

@Service
public class ${Entity}ServiceImpl extends ServiceImpl<${Entity}Mapper, ${Entity}> implements ${Entity}Service {


    @Override
    public Page findPage(Page page) {
        return page(page);
    }

    @Override
    public void insert(${Entity} ${entity}) {
        save(${entity});
    }

    @Override
    public void update(${Entity} ${entity}) {
        updateById(${entity});
    }

    @Override
    public void delById(${PrimaryType} ${Primary}) {
        removeById(${Primary});
    }
}
