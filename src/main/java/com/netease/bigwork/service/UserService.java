package com.netease.bigwork.service;

import com.netease.bigwork.pojo.BGS;
import com.netease.bigwork.pojo.BuyerGoods;
import com.netease.bigwork.pojo.Goods;
import com.netease.bigwork.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by feng on 2018/2/7.
 */
public interface UserService {
    public boolean check(User user);
    public List<Goods> getAllGoods();
    public int updateGoods(Goods goods);
    public List<Goods> getAllById(int userId);
    public Goods getItemById(int id);
    public int deleteItemById(int id);
    public int insertGoods(Goods good);
    public List<Goods> getBuyGoods();
    public List<Goods> getNotBuyGoods();
    public int insertBuyerGoods(BuyerGoods buyerGoods);
    public int updateBuyAndSell(int gid,boolean buy, boolean sell);
    public List<BGS> getAllFromBGS(int uid);
    public int getIdByName(String name);
    public BuyerGoods getBuyGoodsBy(int id);
    public int getIdByTitle(String title);
    public String getImageByGid(int gid);
    public int updateSellCountByGid(int gid,int sellCount);
}
