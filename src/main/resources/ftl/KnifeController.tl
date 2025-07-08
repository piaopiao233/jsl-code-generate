package ${packageName}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import ${controllerResultPackagePath};
import ${packageName}.entity.${Entity};
import ${packageName}.service.${Entity}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
${ControllerImport}

@RestController
@RequestMapping("/${Entity}")
@Validated
@Tag(name = "${tableComment}管理")
public class ${Entity}Controller {

    @Autowired
    private ${Entity}Service ${entity}Service;

    @GetMapping("/findPage")
    @Operation(summary = "${tableComment}分页查询")
    public ${PageResultName} findPage(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        Page<${PageGenerics}> page = new Page(current, size);
        ${entity}Service.findPage(page);
        return ${ResultName}.succ(page);
    }


    @PostMapping("/insert")
    @Operation(summary = "${tableComment}新增")
    public ${ResultName} insert(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.insert(${entity});
        return ${ResultName}.succ();
    }

    @PostMapping("/update")
    @Operation(summary = "${tableComment}修改")
    public ${ResultName} update(@Validated @RequestBody ${Entity} ${entity}) {
        ${entity}Service.update(${entity});
        return ${ResultName}.succ();
    }

    @GetMapping("/delById")
    @Operation(summary = "${tableComment}删除")
    public ${ResultName} delById(@NotNull ${PrimaryType} ${Primary}) {
        ${entity}Service.delById(${Primary});
        return ${ResultName}.succ();
    }
}
