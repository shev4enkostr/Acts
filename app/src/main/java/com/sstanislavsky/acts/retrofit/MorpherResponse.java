package com.sstanislavsky.acts.retrofit;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;

/**
 * Created by stanislav on 2/8/18.
 */

public class MorpherResponse {
    private String naz;
    @SerializedName("Р")
    @Expose
    private String rod;
    @SerializedName("Д")
    @Expose
    private String dav;
    @SerializedName("З")
    @Expose
    private String zn;
    @SerializedName("О")
    @Expose
    private String or;
    @SerializedName("М")
    @Expose
    private String mis;
    @SerializedName("К")
    @Expose
    private String kl;

    private static final String vidminok_naz = "Називний: ";
    private static final String vidminok_rod = "Родовий: ";
    private static final String vidminok_dav = "Давальний: ";
    private static final String vidminok_zn = "Знахiдний: ";
    private static final String vidminok_or = "Орудний: ";
    private static final String vidminok_mis = "Мiсцевий: ";
    private static final String vidminok_kl = "Кличний: ";

    public void setNaz(String naz) {
        this.naz = naz;
    }

    public String getRod() {
        return rod;
    }

    public void setRod(String rod) {
        this.rod = rod;
    }

    public String getDav() {
        return dav;
    }

    public void setDav(String dav) {
        this.dav = dav;
    }

    public String getZn() {
        return zn;
    }

    public void setZn(String zn) {
        this.zn = zn;
    }

    public String getOr() {
        return or;
    }

    public void setOr(String or) {
        this.or = or;
    }

    public String getMis() {
        return mis;
    }

    public void setMis(String mis) {
        this.mis = mis;
    }

    public String getKl() {
        return kl;
    }

    public void setKl(String kl) {
        this.kl = kl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rod", rod).append("dav", dav).append("zn", zn).append("or", or).append("mis", mis).append("kl", kl).toString();
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(vidminok_naz + naz);
        arrayList.add(vidminok_rod + rod);
        arrayList.add(vidminok_dav + dav);
        arrayList.add(vidminok_zn + zn);
        arrayList.add(vidminok_or + or);
        arrayList.add(vidminok_mis + mis);
        arrayList.add(vidminok_kl + kl);
        return arrayList;
    }
}
