package ${packageName}.mapper;

import ${packageName}.entity.${Entity};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
${mapperImport}
public interface ${Entity}Mapper extends BaseMapper<${Entity}> {

${mapperMethod}
}
