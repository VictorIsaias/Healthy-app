package com.example.healthyapp.Response;

import com.example.healthyapp.Model.FoodObjects.Atributo;
import com.example.healthyapp.Model.FoodObjects.nutrients;

public class DietResponse {
    private Double totalWeight;
    private Double calories;
    private Nutrients totalNutrients;
    private Nutrients totalDaily;

    public Double getTotalWeight() {
        return totalWeight;
    }

    public Double getCalories() {
        return calories;
    }

    public Nutrients getTotalNutrients() {
        return totalNutrients;
    }

    public Nutrients getTotalDaily() {
        return totalDaily;
    }

    public class Nutrients{
        private Atributo ENERC_KCAL;
        private Atributo FAT;
        private Atributo FASAT;
        private Atributo FATRN;
        private Atributo FAMS;
        private Atributo FAPU;
        private Atributo CHOCDF;
        private Atributo CHOCDF_net;
        private Atributo FIBTG;
        private Atributo SUGAR;
        private Atributo PROCNT;
        private Atributo CHOLE;
        private Atributo NA;
        private Atributo CA;
        private Atributo MG;
        private Atributo K;
        private Atributo FE;
        private Atributo ZN;
        private Atributo P;
        private Atributo VITA_RAE;
        private Atributo VITC;
        private Atributo THIA;
        private Atributo RIBF;
        private Atributo NIA;
        private Atributo VITB6A;
        private Atributo FOLDFE;
        private Atributo FOLFD;
        private Atributo FOLAC;
        private Atributo VITB12;
        private Atributo VITD;
        private Atributo TOCPHA;
        private Atributo VITK1;
        private Atributo WATER;

        public Atributo getENERC_KCAL() {
            return ENERC_KCAL;
        }

        public Atributo getFAT() {
            return FAT;
        }

        public Atributo getFASAT() {
            return FASAT;
        }

        public Atributo getFATRN() {
            return FATRN;
        }

        public Atributo getFAMS() {
            return FAMS;
        }

        public Atributo getFAPU() {
            return FAPU;
        }

        public Atributo getCHOCDF() {
            return CHOCDF;
        }

        public Atributo getCHOCDF_net() {
            return CHOCDF_net;
        }

        public Atributo getFIBTG() {
            return FIBTG;
        }

        public Atributo getSUGAR() {
            return SUGAR;
        }

        public Atributo getPROCNT() {
            return PROCNT;
        }

        public Atributo getCHOLE() {
            return CHOLE;
        }

        public Atributo getNA() {
            return NA;
        }

        public Atributo getCA() {
            return CA;
        }

        public Atributo getMG() {
            return MG;
        }

        public Atributo getK() {
            return K;
        }

        public Atributo getFE() {
            return FE;
        }

        public Atributo getZN() {
            return ZN;
        }

        public Atributo getP() {
            return P;
        }

        public Atributo getVITA_RAE() {
            return VITA_RAE;
        }

        public Atributo getVITC() {
            return VITC;
        }

        public Atributo getTHIA() {
            return THIA;
        }

        public Atributo getRIBF() {
            return RIBF;
        }

        public Atributo getNIA() {
            return NIA;
        }

        public Atributo getVITB6A() {
            return VITB6A;
        }

        public Atributo getFOLDFE() {
            return FOLDFE;
        }

        public Atributo getFOLFD() {
            return FOLFD;
        }

        public Atributo getFOLAC() {
            return FOLAC;
        }

        public Atributo getVITB12() {
            return VITB12;
        }

        public Atributo getVITD() {
            return VITD;
        }

        public Atributo getTOCPHA() {
            return TOCPHA;
        }

        public Atributo getVITK1() {
            return VITK1;
        }

        public Atributo getWATER() {
            return WATER;
        }
    }
}
