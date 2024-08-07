package com.example.passangers.domain;

public enum Pclass {

    A("1"),B("2"),C("3");

    Pclass(String s) {
    }

    public static Pclass fromString(int value) {
        switch(value) {
            case 0:
                return A;
            case 1:
                return B;
            case 2:
                return C;
        }
        return null;
    }
}
