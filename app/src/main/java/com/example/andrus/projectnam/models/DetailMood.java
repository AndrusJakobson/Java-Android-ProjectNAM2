package com.example.andrus.projectnam.models;

import java.lang.reflect.Array;
import java.util.List;

public class DetailMood {
    public int offerId;
    public CategoryWithArray category;
    public Company company;
    public int companyId;
    public String companyCode;
    public String companyName;
    public String companyAddress;
    public String companyPhone;
    public byte[] companyLogo;
    public String offerTitle;
    public List<OfferListByCategory> OfferListByCategory;
}
