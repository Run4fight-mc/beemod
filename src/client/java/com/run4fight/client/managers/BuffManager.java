package com.run4fight.client.managers;

import com.run4fight.client.model.BuffModel;

import java.util.ArrayList;
import java.util.List;

public class BuffManager {

    private List<BuffModel> buffs = new ArrayList<>();

    public void update(List<BuffModel> newBuffs) {
        this.buffs = newBuffs;
    }

    public List<BuffModel> getBuffs() {
        return buffs;
    }
}

