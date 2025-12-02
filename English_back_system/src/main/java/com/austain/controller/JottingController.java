package com.austain.controller;

import com.austain.domain.dto.PageResult;
import com.austain.domain.dto.Result;
import com.austain.domain.po.Jotting;
import com.austain.srevice.JottingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jotting")
public class JottingController {

    @Autowired
    private JottingService jottingService;

    @GetMapping("/list")
    public Result getList(@RequestParam Integer page, @RequestParam Integer size){
        PageResult<Jotting> jpageResult = jottingService.getList(page, size);
        return Result.success(jpageResult);
    }

    @PostMapping("/add")
    public Result addJotting(@RequestBody Jotting jotting){
        int result = jottingService.addJotting(jotting);
        return result > 0 ? Result.success() : Result.error("添加失败");
    }

    @PutMapping("/update")
    public Result updateJotting(@RequestBody Jotting jotting){
        int result = jottingService.updateJotting(jotting);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteJotting(@PathVariable String id){
        int result = jottingService.deleteJotting(id);
        return result > 0 ? Result.success() : Result.error("删除失败");
    }

    /**
     * 批量删除
     */
    @PostMapping("/batchDelete")
    public Result batchDeleteJotting(@RequestBody List<String> ids){
        int result = jottingService.batchDeleteJotting(ids);
        return result > 0 ? Result.success() : Result.error("批量删除失败");
    }
}
