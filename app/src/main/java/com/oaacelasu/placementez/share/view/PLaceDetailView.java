package com.oaacelasu.placementez.share.view;

import android.view.View;

/**
 * name : PLaceDetailView
 * author : root
 * date : 27/05/18
 * description :
 */
public interface PLaceDetailView {
    void setInformationPlace();
    void showToolbar(String tittle, boolean upButton);
    void goToMaps(View view);
}
