package com.sofya.primenumbers;

public class NumberButtonClass {


        Integer btnId;
        Integer numberToShow;

    NumberButtonClass(Integer id , Integer arrId)
        {
            this.btnId = arrId;
            this.numberToShow = id;
        }

        public Integer getbtnId() {
            return btnId;
        }

        public Integer getnumberToShow() {
            return numberToShow;
        }


}
