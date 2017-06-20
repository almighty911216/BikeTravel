package com.example.almig.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by almig on 2017-06-11.
 */

public class AreaCategoryModel implements Parcelable {
    private long mId;
    private int mLogoId;
    private int mTitleId;
//    private ArrayList<CulturalAssetCategoryModel> mSubcategories;
    private boolean mComplete;

    public AreaCategoryModel() {
    }

    protected AreaCategoryModel(Parcel in) {
        mId = in.readLong();
        mLogoId = in.readInt();
        mTitleId = in.readInt();
/*        if (in.readByte() == 0x01) {
            mSubcategories = new ArrayList<>();
            in.readList(mSubcategories, CulturalAssetCategoryModel.class.getClassLoader());
        } else {
            mSubcategories = null;
        }*/
        mComplete = in.readByte() != 0x00;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getLogoId() {
        return mLogoId;
    }

    public void setLogoId(int logoId) {
        mLogoId = logoId;
    }

    public int getTitleId() {
        return mTitleId;
    }

    public void setTitleId(int titleId) {
        mTitleId = titleId;
    }

/*    public ArrayList<CulturalAssetCategoryModel> getSubcategories() {
        return mSubcategories;
    }

    public void setSubcategories(ArrayList<CulturalAssetCategoryModel> subcategories) {
        mSubcategories = subcategories;
    }*/

    public boolean isComplete() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeInt(mLogoId);
        dest.writeInt(mTitleId);
/*        if (mSubcategories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mSubcategories);
        }*/
        dest.writeByte((byte) (mComplete ? 0x01 : 0x00));
    }

    public static final Creator<AreaCategoryModel> CREATOR = new Creator<AreaCategoryModel>() {
        @Override
        public AreaCategoryModel createFromParcel(Parcel in) {
            return new AreaCategoryModel(in);
        }

        @Override
        public AreaCategoryModel[] newArray(int size) {
            return new AreaCategoryModel[size];
        }
    };
}
