package user;

import base.BaseJunitTest;
import com.alibaba.druid.support.logging.Log;
import com.netease.bigwork.mapper.UserMapper;
import com.netease.bigwork.pojo.BuyerGoods;
import com.netease.bigwork.pojo.Goods;
import com.netease.bigwork.pojo.User;
import com.netease.bigwork.service.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by feng on 2018/2/5.
 */

public class UserTest extends BaseJunitTest{
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
//    private static Logger logger = LoggerFactory.getLogger(User.class);
    @Test
    public void getUser(){
        User user = userMapper.getUserByName("seller");
      List<Goods>goods= userMapper.getAllById(1);
        System.out.println("买家:"+  userMapper.getIdByName("buyer"));
        System.out.println("卖家:" +userMapper.getIdByName("seller"));


        //改成打印日志
        if(goods!=null){
            System.out.println("not null:");
            for(Goods list:goods){
                System.out.println("data is:"+list.getTitle()+ list.getGid());
            }
        }else{
            System.out.println("null");
        }
        if(user!=null){
            System.out.println("name:"+user.getUsername());
            System.out.println("password:"+user.getPassword());
        }else{
            System.out.println("not exist");
        }
//        logger.error(user.getPassword());
//        logger.info(user.getPassword());
    }

    @Test
    public void testUser(){
        Goods goods = new Goods();
        goods.setGid(2);
        goods.setTitle("测试1111");
        goods.setDetail("正文1111");
        goods.setImage("www.baidu.com");
        goods.setSummary("摘要111");
        goods.setSellPrice(99);
//        userService.insertGoods(goods);
        userService.updateGoods(goods);

    }
    @Test
    public void testBuy(){
//        BuyerGoods buyerGoods = new BuyerGoods();
//        buyerGoods.setTime(new Date());
//        buyerGoods.setId(2);
//        buyerGoods.setNum(2);
//        buyerGoods.setBuyPrice(10);
//        userService.insertBuyerGoods(buyerGoods);
        Date ss = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime());//这个就是把时间戳经过处理得到期望格式的时间
        System.out.println("时间"+time);
    }

}
