package com.jade.showcase.user.controller;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jade.showcase.common.BaseController;
import com.jade.showcase.user.entity.User;
import com.jade.showcase.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jade
 * @since 2019-01-29
 */
@RestController
@CrossOrigin
@RequestMapping("/user/user")
public class UserController extends BaseController {
    @CreateCache(expire = 100, cacheType = CacheType.REMOTE)
    private Cache<Integer, User> userCache;
    @Autowired
    IUserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("getUsername")
    public String getUsername() {
      //  userCache.put(100000012 ,userService.getUserByCache(110));

        ValueOperations<String, Integer> operations=redisTemplate.opsForValue();
        operations.set("hanyu", 100);
        Integer name = operations.get("hanyu");
        System.out.println(name + "========================");
        System.out.println(name + "========================");
        return "jade";
    }



    @RequestMapping("getUserByCache")
    @ResponseBody
    public  User getUserByCache(Integer userId){

       return userService.getUserByCache(userId);
    }

    @RequestMapping("getUserList")
    public  List<User> getLists(){
        IPage<User> page = new Page();
        page.setSize(3);
        page.setCurrent(1);
        IPage<User> userList = userService.lambdaQuery().page(page);
        List<User> records = userList.getRecords();
        return  records;
    }

    @RequestMapping("addUser")
    public  String addUser(@RequestBody User user){
        System.out.println("=======================");
        userService.save(user);
        return user.getId().toString();
    }

    @RequestMapping("getUsernameByDB")
    public String getUsernameByDB() {
        User user = userService.getById(100);
        List<User> list = userService.list();
        List<Map<String, Object>> map = userService.listMaps();
        Wrapper<User> query = new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public String getCustomSqlSegment() {
                return null;
            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        };
        userService.list(query);
        IPage<User> page = new Page();
        page.setSize(3);
        page.setCurrent(1);
        IPage<User> userList = userService.lambdaQuery().page(page);
        List<User> records = userList.getRecords();
        System.out.println(records.size());
        System.out.println(records.get(0).toString());
        User newUser =  new User();
        newUser.setName("mybatis plus");
        newUser.setAge(123);
//        newUser.setId(1000);
        userService.save(newUser);

        return newUser.getId().toString();
    }


}
