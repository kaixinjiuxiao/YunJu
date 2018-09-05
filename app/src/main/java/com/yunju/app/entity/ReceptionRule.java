package com.yunju.app.entity;

import java.io.Serializable;

/**
 * @author: captain
 * Time:  2018/6/28 0028
 * Describe:
 */
public class ReceptionRule implements Serializable{

    private boolean child;
    private boolean old;
    private boolean man;
    private boolean woman;
    private boolean smooking;
    private boolean pet;
    private boolean cook;
    private boolean party;
    private boolean photo;
    private boolean addBed;
    private boolean addMan;

    public ReceptionRule(boolean child, boolean old, boolean man, boolean woman, boolean smooking,
                         boolean pet, boolean cook, boolean party, boolean photo, boolean addBed, boolean addMan) {
        this.child = child;
        this.old = old;
        this.man = man;
        this.woman = woman;
        this.smooking = smooking;
        this.pet = pet;
        this.cook = cook;
        this.party = party;
        this.photo = photo;
        this.addBed = addBed;
        this.addMan = addMan;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public boolean isWoman() {
        return woman;
    }

    public void setWoman(boolean woman) {
        this.woman = woman;
    }

    public boolean isSmooking() {
        return smooking;
    }

    public void setSmooking(boolean smooking) {
        this.smooking = smooking;
    }

    public boolean isPet() {
        return pet;
    }

    public void setPet(boolean pet) {
        this.pet = pet;
    }

    public boolean isCook() {
        return cook;
    }

    public void setCook(boolean cook) {
        this.cook = cook;
    }

    public boolean isParty() {
        return party;
    }

    public void setParty(boolean party) {
        this.party = party;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public boolean isAddBed() {
        return addBed;
    }

    public void setAddBed(boolean addBed) {
        this.addBed = addBed;
    }

    public boolean isAddMan() {
        return addMan;
    }

    public void setAddMan(boolean addMan) {
        this.addMan = addMan;
    }

}
