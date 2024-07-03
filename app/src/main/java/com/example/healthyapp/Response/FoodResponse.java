package com.example.healthyapp.Response;

import com.example.healthyapp.Model.FoodObjects.food;
import com.example.healthyapp.Model.FoodObjects.hintsList;

import java.util.List;

public class FoodResponse {

    public String getText() {
        return text;
    }

    public List<parsedList> getParsed() {
        return parsed;
    }

    public _linksObject get_links() {
        return _links;
    }

    public List<hintsList> getHints() {
        return hints;
    }

    private List<food> foodList;

    public List<food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<food> foodList) {
        this.foodList = foodList;
    }

    private String text;
    private List<parsedList> parsed;
    private _linksObject _links;
    private List<hintsList> hints;


    public class parsedList{
        private food food;
    }

    public class _linksObject{

        private nextObject next;
        public class nextObject{
            private String title;
            private String href;
        }
    }
}
