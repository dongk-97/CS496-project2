package android.example.cs496.ui.main.fragment3;

import android.graphics.Bitmap;

public class Imageclass {
    String id;
    Bitmap bitmap;
    Boolean check;

    public Imageclass(String _id, Bitmap _bitmap){
        id = _id;
        bitmap = _bitmap;
        check = false;
    }
    public Imageclass(){
        id = null;
        bitmap = null;
        check = false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getId() {
        return id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
