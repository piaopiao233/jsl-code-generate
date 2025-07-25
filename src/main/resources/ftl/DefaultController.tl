package ${packageName}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import ${controllerResultPackagePath};
import ${packageName}.entity.${Entity};
import ${packageName}.service.${Entity}Service;
${ControllerImport}

@RestController
@RequestMapping("/${Entity}")
@Validated
public class ${Entity}Controller {

    @Autowired
    private ${Entity}Service ${entity}Service;

    @GetMapping("/findPage")
    public ${PageResultName} findPage(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        Page<${PageGenerics}> page = new Page<>(current, size);
        ${entity}Service.findPage(page);
        return ${ResultName}.succ(page);
    }


    @PostMapping("/insert")
    public ${ResultName}<?> insert(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.insert(${entity});
        return ${ResultName}.succ();
    }

    @PostMapping("/update")
    public ${ResultName}<?> update(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.update(${entity});
        return ${ResultName}.succ();
    }

    @GetMapping("/delById")
    public ${ResultName}<?> delById(@NotNull ${PrimaryType} ${Primary}) {
        ${entity}Service.delById(${Primary});
        return ${ResultName}.succ();
    }

    @GetMapping("/getById")
    public ${ResultName}<${Entity}> getById(@NotNull ${PrimaryType} ${Primary}) {
        ${Entity} ${entity} = ${entity}Service.getById(${Primary});
        return ${ResultName}.succ(${entity});
    }
}
