package ${packageName}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import ${controllerResultPackagePath};
import ${packageName}.entity.${Entity};
import ${packageName}.service.${Entity}Service;

@RestController
@RequestMapping("/${Entity}")
@Validated
public class ${Entity}Controller {

    @Autowired
    private ${Entity}Service ${entity}Service;

    @GetMapping("/findPage")
    public ${controllerResultName} findPage(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        Page page = new Page(current, size);
        ${entity}Service.findPage(page);
        return ${controllerResultName}.succ(page);
    }


    @PostMapping("/insert")
    public ${controllerResultName} insert(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.insert(${entity});
        return ${controllerResultName}.succ();
    }

    @PostMapping("/update")
    public ${controllerResultName} update(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.update(${entity});
        return ${controllerResultName}.succ();
    }

    @GetMapping("/delById")
    public ${controllerResultName} delById(@NotNull ${PrimaryType} ${Primary}) {
        ${entity}Service.delById(${Primary});
        return ${controllerResultName}.succ();
    }
}
