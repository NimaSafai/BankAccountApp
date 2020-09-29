package com.company;

public interface InterestBaseRate {

    default double getBaseRate() {
        return 4.5;
    }                     //Jag bara satte 4.5% som ränta på måfå
}