package com.model.demo;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public  static  abstract  class   Supermarket{

        // 仓库
        protected Map<String,Integer> storage = new HashMap<String,Integer>();

        protected Map<String ,Fruit> storage_fruit = new HashMap<String,Fruit>();




        // 超市有存储的功能
        private void  storage(Fruit fruit,int num){
            this.storage.put(fruit.getName(),num);
            storage_fruit.put(fruit.getName(),fruit);
        }
        /**
         * 超市有定价的功能(给水果赋予价值)，模板方式
         * @param price 定价
         * @param fruit 水果类型
         * @param num  仓库数量
         */
        protected Supermarket  setPrice(Fruit fruit,Price price,int num){
            // 给水果赋予价值
            fruit.setPrice(price);
            // 入库
            storage(fruit,num);
            return  this;

        }



        // 超市有收费的功能
         public   double  charge(Map<String,Integer> basketMap){
             double result =0.0;
             if(basketMap==null || basketMap.isEmpty()) return result;
             for (Map.Entry<String, Integer> basket : basketMap.entrySet()) {
                 // 购买的数量
                 Integer value = basket.getValue();
                 // 购买的水果
                 String fruitName = basket.getKey();
                 // 获取价格信息
                 Fruit fruit = storage_fruit.get(fruitName);
                 if(fruit==null){
                     System.out.println(fruit.getName()+":已经买没了");
                 }
                 //  获取库存信息
                 Integer integer = storage.get(fruitName);
                 if(value>integer){
                     value=integer;
                     // 更新库存
                     storage.put(fruitName,0);
                 }
                 // 水果的价格
                 Price price = fruit.getPrice();
                 // 计算价格
                 result+=value*price.price*price.discount;
             }
              // 拓展点，不同的水果商店满减方式不一样  
             return  doCharge(result);

         }
        // 超市具有特征收费的功能，一个拓展点
        protected abstract double doCharge(double result);

        // 开门营业，初始化库存和价格
        protected    abstract void doOpen();


    }
    // 喜洋洋超市
    public static class  YXX extends   Supermarket{


        @Override
        protected void doOpen() {
            setPrice(new Apple(),new Price(8,1),100);
            setPrice(new Strawberry(),new Price(13,0.8),100);
            setPrice(new Mango(),new Price(20,1),100);

        }
        @Override
        protected double doCharge(double result) {
            return result>100?result-10:result;
        }

    }

    public static void main(String[] args) {
        // 喜洋洋超市买10个苹果
        YXX yxx = new YXX();
        // 喜洋洋开门了
        yxx.doOpen();;

        Map<String,Integer> basketMap= new HashMap<>();
        basketMap.put("苹果",10);
        basketMap.put("草莓",20);
        basketMap.put("芒果",1);
        System.out.println(yxx.charge(basketMap));


    }


    // 价值
    public static  class   Price{
        // 价格
        private  double price;

        // 折扣
        private  double discount;

        public Price(double price, double discount) {
            this.price = price;
            this.discount = discount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }
    }

    // 水果的特征
    public abstract static  class Fruit{
        // 价值
        protected  Price price;

        // 水果的名字
        protected   String name;
        // 颜色
        protected  String colour;

        public Fruit(String name, String colour) {
            this.name = name;
            this.colour = colour;
        }

        
        public   abstract  void setPrice(Price price);

        public Price getPrice(){
            return  price;
        }

        public String getName() {
            return name;
        }
    }

    public  static  class Apple  extends  Fruit {


        public Apple() {
            super("苹果", "红色");
        }

        @Override
        public void setPrice(Price price) {
           this.price=price;
        }
    }
    public  static  class Strawberry  extends  Fruit {


        public Strawberry() {
            super("草莓", "红色");
        }

        @Override
        public void setPrice(Price price) {
            this.price=price;
        }
    }
    public  static  class Mango  extends  Fruit {


        public Mango() {
            super("芒果", "黄色");
        }

        @Override
        public void setPrice(Price price) {
            this.price=price;
        }
    }
}
