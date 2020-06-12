package com.zy.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.zy.common.code.Code;
import com.zy.item.service.SpecificationService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("spec")
@RestController
public class SpecificationController {

    /**
     * rest风格
     * 增加put 修改get
     */
    @Autowired
    private SpecificationService specService;

    /**
     * 根据分类id查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}") //根据前端要求返回值是个List集合
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){//动态获取参数
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }
    @PostMapping("group")//新增规格分组
    public ResponseEntity<Void>insertGropByIdByName(@RequestBody SpecGroup specGroup){
            specService.insertGropByIdByName(specGroup);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void>delectGroupById(@PathVariable("id")Long id){
            specService.delecteGroupById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("group")
    public ResponseEntity<Void>updateGroupById(@RequestBody SpecGroup specGroup){
            specService.updateGroupById(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询参数的集合
     * @param gid 组id
     * @param cid 分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")//，没有占位符所以不能用pathvariable
    //返回值当下组下的所有规格参数
    //因为另外一个请求路径跟这个一样，只是参数不一样，这里我们把他们合并
    //三个都不是必须的 ，传任意一个都可以
    public ResponseEntity<List<SpecParam>>querySpecParamByList(
            @RequestParam(value="gid",required = false)Long gid,
            @RequestParam(value="cid",required=false)Long cid,
            @RequestParam(value="searching",required=false)Boolean searching){
           return ResponseEntity.ok(specService.querySpecParamList(gid,cid,searching));
    }
    @PostMapping("param")
    public ResponseEntity<Void>insertIntoSpecParam(@RequestBody SpecParam specParam){
            specService.insertIntoSceParam(specParam);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("param")
    public ResponseEntity<Void>updateSpecParam(@RequestBody SpecParam specParam){
            specService.updateSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void>deleteSpecParam(@PathVariable("id")long id){
            specService.deleteSpecParam(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
