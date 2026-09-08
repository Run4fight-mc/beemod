package com.run4fight.client.model;

public class EffectModel {
    String value;
    String effectName;

    public EffectModel(String value, String effectName){
        this.value = value;
        this.effectName = effectName;
    }

    public String getValue() {
        return value;
    }

    public String getEffectName() {
        return effectName;
    }

}
