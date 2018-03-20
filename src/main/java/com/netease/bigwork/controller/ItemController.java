package com.netease.bigwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.bigwork.Model.HostHolder;
import com.netease.bigwork.pojo.*;
import com.netease.bigwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by feng on 2018/2/8.
 */
@Controller
@RequestMapping("/")
public class ItemController {

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping("publish")
    String publish(Model model,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(hostHolder.getUser()==null){
            redirect(httpServletRequest,httpServletResponse);
        }
        return "publish";
    }

    @ResponseBody
    @RequestMapping("/api/upload")
    public Result picUpLoad(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        //根据id删除对应的图片
        if (file != null) {// 判断上传的文件是否为空
            String path = null;// 文件路径
            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 文件原名称
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/image/");
                    // 自定义的文件名称
                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
                    // 设置存放图片文件的路径
                    path = realPath +System.getProperty("file.separator")+trueFileName;
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    result.setResult("/image/" + trueFileName);
                    return result;
                } else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                }
            } else {
                System.out.println("文件类型为空");
            }
        } else {
            System.out.println("没有找到相对应的文件");
        }
        return result;
    }

    @RequestMapping(value = "publicSubmit",method = RequestMethod.POST )
    public String itemMessage(Goods goods, Model model){//SellerItem sellerItem
//        sellerItem.setTime(new Date());
//        sellerItem.setCount(100);
//        sellerItem.setSell(0);
        Date date1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String buyTime = format1.format(date1.getTime());

        Date date2 = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sellTime = format2.format(date1.getTime());

        goods.setBuyPrice(0);
        goods.setBuyTime(buyTime);
        goods.setTime(sellTime);
        goods.setTotalCount(1000);
        goods.setTotalCount(0);
        goods.setBuyCount(0);
        goods.setSell(false);
        goods.setBuy(false);
        try{
            userService.insertGoods(goods);
            model.addAttribute("flag","success");
        }
        catch (java.lang.Exception e){
            model.addAttribute("flag","error");
            System.out.println(e.getMessage());
       }
        model.addAttribute("id",userService.getIdByTitle(goods.getTitle()));
        return "publicSubmit";
    }
    @RequestMapping("show")
    public String sellerShow(@RequestParam("id")int id,Model model,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(hostHolder.getUser()==null){
            redirect(httpServletRequest,httpServletResponse);
        }
        Goods sellerItem = userService.getItemById(id);
        BuyerGoods buyerGoods = userService.getBuyGoodsBy(id);

        sellerItem.setBuyPrice(buyerGoods!=null?buyerGoods.getBuyPrice():sellerItem.getBuyPrice());
        model.addAttribute("sellerItem",sellerItem);
        return "sellerShow";
    }
    @RequestMapping("edit")
    public String edit(@RequestParam("id")int id,Model model,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(hostHolder.getUser()==null){
            redirect(httpServletRequest,httpServletResponse);
        }
        try{
            Goods sellerItem = userService.getItemById(id);
            model.addAttribute("sellerItem",sellerItem);
            model.addAttribute("flag","success");
        }catch (java.lang.Exception e){
            model.addAttribute("flag","error");
        }
        return "edit";
    }
    @RequestMapping("editSubmit")
    public String editSubmit(Goods goods,Model model,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(hostHolder.getUser()==null){
            redirect(httpServletRequest,httpServletResponse);
        }
        try{
            Date ss = new Date();
            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format0.format(ss.getTime());
            goods.setTime(time);
            int gid = goods.getGid();
            String image = userService.getImageByGid(gid);
            userService.updateGoods(goods);
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/WEB-INF/");
            image = realPath+System.getProperty("file.separator")+image;
            try{
                if(image!=null){
                    File f = new File(image);
                    f.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            int id = goods.getGid();
            model.addAttribute("id",id);
            model.addAttribute("flag","success");
        }catch (java.lang.Exception e){
            model.addAttribute("flag","error");
        }
        return "editSubmit";
    }
    @RequestMapping("delete")
    public String deleteItem(Model model,@RequestParam("id")int id,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        if(hostHolder.getUser()==null){
            redirect(httpServletRequest,httpServletResponse);
        }
        try{
            String image = userService.getImageByGid(id);
            userService.deleteItemById(id);
            model.addAttribute("flag","success");
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/WEB-INF/");
            image = realPath+System.getProperty("file.separator")+image;
            if(image!=null){
                File f = new File(image);
                f.delete();
            }
        }catch (java.lang.Exception e){
            model.addAttribute("flag","error");
        }
        return "deleteSubmit";
    }

    public void redirect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            httpServletRequest.getRequestDispatcher("login").forward(httpServletRequest, httpServletResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("cart")
    public String cart(Model model){
        return "cart";
    }

    @RequestMapping("buy")
    @ResponseBody
    public Map buy(@RequestBody List<Data> data, HttpServletRequest request, HttpServletResponse response){
        Map responseText = new HashMap();
        try{
            for (int i = 0;i<data.size();i++) {
                BuyerGoods buyerGoods = new BuyerGoods();
                buyerGoods.setGid(Integer.valueOf(data.get(i).getGid()));
                buyerGoods.setNum(Integer.valueOf(data.get(i).getNumber()));
                buyerGoods.setBuyPrice(Float.valueOf(data.get(i).getBuyPrice()));
                Date date3 = new Date();
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format2.format(date3.getTime());
                buyerGoods.setTime(time);
                userService.insertBuyerGoods(buyerGoods);
            }
            responseText.put("code",200);
        }catch (Exception e){
            responseText.put("message","数据格式错误");
        }
        return  responseText;
    }

    @RequestMapping("account")
    public String account(Model model,HttpServletRequest request){
        int uid = -1;
        if (request.getCookies() != null) {//hostHolder.getUser().getUsername()
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("user")){
                    uid = userService.getIdByName(cookie.getValue());
                }
            }
        }
        List<BGS>list = new ArrayList<>();
        try{
            list =  userService.getAllFromBGS(uid);
        }catch (Exception e){
            return "error";//这里需要改动
        }
        List<Goods> result = new ArrayList<>();
        float total = 0;
        float price = 0;
        for (BGS b:list) {
            BuyerGoods buyerGoods = userService.getBuyGoodsBy(b.getId());
            Goods goods = userService.getItemById(buyerGoods.getGid());
            int num = buyerGoods.getNum();
            goods.setBuyCount(num);
            price = buyerGoods.getBuyPrice();
            total += num*price;
            goods.setBuyPrice(price);
            goods.setBuyTime(buyerGoods.getTime());
            goods.setSell(true);
            goods.setBuy(true);
            result.add(goods);
        }
        model.addAttribute("result",result);
        model.addAttribute("total",total);
        return "account";
    }

    @RequestMapping("/notBuy")
    String indexNotBuy( HttpServletRequest request, HttpServletResponse response,Model model){
        model.addAttribute("userName","");
        List<Goods>list = new ArrayList<>();
        model.addAttribute("showType","not");
        list = userService.getNotBuyGoods();
        model.addAttribute("list",list);
        return "index2";
    }
}
