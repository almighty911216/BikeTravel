package com.example.almig.android.util;


import com.example.almig.android.R;
import com.example.almig.android.model.AreaCategoryModel;
import com.example.almig.android.model.DummyModel;

import java.util.ArrayList;

public class DummyContent {
	public static ArrayList<AreaCategoryModel> getAreaCategorieModels() {
		ArrayList<AreaCategoryModel> areaCategoryModels = new ArrayList<AreaCategoryModel>();

		AreaCategoryModel areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(0L);
		areaCategoryModel.setLogoId(R.drawable.logo_seoul);
		areaCategoryModel.setTitleId(R.string.state_seoul);
		areaCategoryModel.setComplete(true);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(1L);
		areaCategoryModel.setLogoId(R.drawable.logo_incheon);
		areaCategoryModel.setTitleId(R.string.state_incheon);
		areaCategoryModel.setComplete(false);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(2L);
		areaCategoryModel.setLogoId(R.drawable.logo_gwangju);
		areaCategoryModel.setTitleId(R.string.state_gwangju);
		areaCategoryModel.setComplete(false);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(3L);
		areaCategoryModel.setLogoId(R.drawable.logo_daegu);
		areaCategoryModel.setTitleId(R.string.state_daegu);
		areaCategoryModel.setComplete(false);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(4L);
		areaCategoryModel.setLogoId(R.drawable.logo_busan);
		areaCategoryModel.setTitleId(R.string.state_busan);
		areaCategoryModel.setComplete(true);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(5L);
		areaCategoryModel.setLogoId(R.drawable.logo_gyeongju);
		areaCategoryModel.setTitleId(R.string.state_gyeongju);
		areaCategoryModel.setComplete(false);
//		areaCategoryModel.setSubcategories(getGyeongJuCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(6L);
		areaCategoryModel.setLogoId(R.drawable.logo_ulsan);
		areaCategoryModel.setTitleId(R.string.state_ulsan);
		areaCategoryModel.setComplete(false);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		areaCategoryModel = new AreaCategoryModel();
		areaCategoryModel.setId(7L);
		areaCategoryModel.setLogoId(R.drawable.logo_jeju);
		areaCategoryModel.setTitleId(R.string.state_jeju);
		areaCategoryModel.setComplete(true);
//		areaCategoryModel.setSubcategories(getSeoulCulturalAssetsCategoryModels());
		areaCategoryModels.add(areaCategoryModel);

		return areaCategoryModels;
	}

	public static ArrayList<DummyModel> getDummyModelListSocial() {
		ArrayList<DummyModel> list = new ArrayList<>();

		list.add(new DummyModel(0, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/0.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(1, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/1.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(2, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/2.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(3, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/3.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(4, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/4.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(5, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/5.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(6, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/6.jpg", "Jane Smith", R.string.fontello_heart_empty));
		list.add(new DummyModel(7, "http://pengaja.com/uiapptemplate/newphotos/listviews/googlecards/travel/7.jpg", "Jane Smith", R.string.fontello_heart_empty));

		return list;
	}
}
