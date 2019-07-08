package android.example.cs496.ui.main.fragment3;

import android.example.cs496.ui.main.ClothesSelect;
import android.widget.CheckBox;
import android.widget.ImageView;

public class ImageViewHolder
{
    ImageView ivImage;
    //CheckBox chkImage;
    public ImageViewHolder(){
        this.ivImage = null;
        //this.chkImage = null;
    }


//    public CheckBox getChkImage() {
//        return chkImage;
//    }

    public ImageView getIvImage() {
        return ivImage;
    }

//    public void setChkImage(CheckBox chkImage) {
//        this.chkImage = chkImage;
//    }

    public void setIvImage(ImageView ivImage) {
        this.ivImage = ivImage;
    }
}