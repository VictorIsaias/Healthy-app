package com.example.healthyapp.Model.FoodObjects;

import java.util.List;

public class hintsList{
    public com.example.healthyapp.Model.FoodObjects.food getFood() {
        return food;
    }

    public List<hintsList.measuresList> getMeasures() {
        return measures;
    }

    private food food;
    private List<hintsList.measuresList> measures;

    public class measuresList{
        private String uri;
        private String label;
        private Double weight;
        private List<hintsList.measuresList.qualifiedList> qualified;

        public class qualifiedList{

            private List<hintsList.measuresList.qualifiedList.qualifiersList> qualifiers;
            private Double weight;

            public class qualifiersList{

                private String uri;
                private String label;
            }
        }
    }

}