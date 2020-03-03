package com.app.mdc.serviceImpl.system;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.mdc.mapper.system.MenuMapper;
import com.app.mdc.mapper.system.RoleMenuMapper;
import com.app.mdc.model.system.Menu;
import com.app.mdc.model.system.RoleMenu;
import com.app.mdc.model.system.User;
import com.app.mdc.service.system.MenuService;
import com.app.mdc.utils.TreeUtils;
import com.app.mdc.utils.viewbean.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuMapper roleMenuMapper;

    @Autowired
    public MenuServiceImpl(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addMenu(String status, String name, String icon, String url, Integer level, String parentId,
                                  Integer rank) {
        Menu menu = new Menu();
        menu.setStatus(status);
        menu.setName(name);
        menu.setIcon(icon);
        menu.setUrl(url);
        menu.setLevel(level);
        menu.setCreatetime(new Date());
        menu.setUpdatetime(new Date());
        menu.setRank(rank);
        menu.setDeleted(0);
        if (!StringUtils.isEmpty(parentId)) {
            menu.setParentId(parentId);
        } else {
            menu.setParentId("0");
        }

        int rowCount = this.baseMapper.insert(menu);
        //添加菜单时，自动给管理员角色添加该菜单的权限
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setMenuId(menu.getId());
        roleMenu.setRoleId("af1927fcb8d34acca2ef1c0686a37ed5");
        int roleCount = roleMenuMapper.insert(roleMenu);

        return rowCount == 1 && roleCount == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    public ResponseResult menuList(String name) {
        EntityWrapper<Menu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("deleted", 0);

        if (!StringUtils.isEmpty(name)) {
            entityWrapper.like("name", name);
        }
        entityWrapper.orderBy("rank");

        List<Menu> list = this.baseMapper.selectList(entityWrapper);
        JSONArray result = TreeUtils.listToTree(JSONArray.parseArray(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat)), "id", "parentId", "children");
        return ResponseResult.success().add(result);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult delMenu(String id) {
        Menu menu = this.baseMapper.selectById(id);
        menu.setDeleted(1);
        int rowCount = this.baseMapper.updateById(menu);
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    public ResponseResult getOneMenu(String id) {
        Menu menu = this.baseMapper.selectById(id);
        //取当前菜单的父级菜单的名字并传回前台
        Map<String,Object> map=new HashMap<>();
        map.put("status", menu.getStatus());
        map.put("name", menu.getName());
        map.put("icon", menu.getIcon());
        map.put("url", menu.getUrl());
        map.put("level", menu.getLevel());
        map.put("rank", menu.getRank());

        if (!StringUtils.isEmpty(menu.getParentId()) && !menu.getParentId().equals("0")) {
            Menu parent = this.baseMapper.selectById(menu.getParentId());
            map.put("pname", parent.getName());
        } else {
            map.put("pname", "");
        }
        return ResponseResult.success().add(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateMenu(String id, String status, String name, String icon, String url, Integer level,
                                     String parentId, Integer rank) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setStatus(status);
        menu.setName(name);
        menu.setIcon(icon);
        menu.setUrl(url);
        menu.setLevel(level);
        menu.setParentId(parentId);
        menu.setRank(rank);
        menu.setUpdatetime(new Date());

        int rowCount = this.baseMapper.updateById(menu);
        return rowCount == 1 ? ResponseResult.success() : ResponseResult.fail();

    }

    @Override
    public ResponseResult getMenusByPid(String parentId, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        map.put("userId", user.getId());
        List<Menu> menus = roleMenuMapper.getChildMenus(map);
        List<Menu> result = new ArrayList<>(menus);
        for (Menu menu : menus) {
            map.put("parentId", menu.getId());
            List<Menu> childs = roleMenuMapper.getChildMenus(map);
            if (childs.size() > 0) {
                result.addAll(childs);
            }
        }
        JSONArray array = TreeUtils.listToTree(JSONArray.parseArray(JSON.toJSONString(result)), "id", "parentId", "children");
        return ResponseResult.success().add(array);
    }

}
