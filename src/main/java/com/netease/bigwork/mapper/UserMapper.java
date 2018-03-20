package com.netease.bigwork.mapper;

import com.netease.bigwork.pojo.BGS;
import com.netease.bigwork.pojo.BuyerGoods;
import com.netease.bigwork.pojo.Goods;
import com.netease.bigwork.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by feng on 2018/2/5.
 */
public interface UserMapper {
    public User getUserByName(String username);


    public List<Goods> getAllById(int userId);
    public List<Goods> getAllGoods();
    public List<Goods> getNotBuyGoods();


    public int getIdByName(String name);
    public Goods getItemById(int gid);
    public int updateGoods(Goods goods);
    public int deleteItemById(int id);
    public int insertGoods(Goods good);
    public int getIdByTitle(String title);
    public String getImageByGid(int gid);

    /****中间表增删改查***/
    public int insertUG(@Param("uid") int uid, @Param("gid") int gid);
    public int deleteUG(int gid);
    public int updateUG(Goods goods);

    /**********买家***********/
    public int updateBuyerItem(@Param("gid")int id,@Param("buyCount")int number);
    public int insertBuyGoods(BuyerGoods buyergoods);
    public int insertUGS(@Param("uid") int uid, @Param("id") int id);
    public BuyerGoods getBuyGoodsBy(int gid);

    public int updateBuyAndSell(@Param("gid")int gid,@Param("buy") boolean buy,@Param("sell")boolean sell);
    public List<BGS> getAllFromBGS(int uid);
    public int updateSellCountByGid(@Param("gid")int gid,@Param("sellCount")int sellCount);
    public int getSellCountByGid(int gid);

}
