package com.example.androidapp.Data.OrderData.OrderTodayData;//package com.example.androidapp.data.order.ordertoday;
//
//import androidx.room.TypeConverter;
//
//import java.io.Serializable;
//import java.lang.reflect.Type;
//import java.util.List;
//
//public class DataConverter implements Serializable {
//
//    //Convert list of dish into string
//    @TypeConverter
//    public static String fromDishList(List<Dish> mListDish) {
//        if (mListDish == null) {
//            return null;
//        }
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Dish>>() {
//        }.getType();
//        String json = gson.toJson(mListDish, type);
//        return json;
//    }
//
//    //Vice-versa of the method above
//    @TypeConverter
//    public static List<Dish> toDishList(String dishString) {
//        if (dishString == null) {
//            return null;
//        }
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Dish>>(){
//        }.getType();
//        List<Dish> orderDishList = gson.fromJson(dishString, type);
//        return orderDishList;
//    }
//}
