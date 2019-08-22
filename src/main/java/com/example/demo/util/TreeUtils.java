package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.SysPermission;

import java.util.List;

/**
 * 菜单树
 * @param parentId
 * @param permissionAll
 * @param array
 */
public class TreeUtils {
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionsAll, JSONArray array) {
         for(SysPermission per:permissionsAll){
             if(per.getParentId().equals(parentId)){
                 String string = JSONObject.toJSONString(per);
                 JSONObject parent= (JSONObject) JSONObject.parse(string);
                 array.add(parent);
                 if(permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() !=null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getId(),permissionsAll,child);
                 }
             }
         }
    }
}
