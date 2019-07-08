package android.example.cs496.ui.main.fragment2;

import android.graphics.Bitmap;

public class BitmapClass {
    private String id;
    private String type;
    private String path;
    private Bitmap img;

    public BitmapClass(){
        id = "";
        type = "";
        path = "";
        img = null;
    }

    public BitmapClass(String _id, String _type, String _path, Bitmap _img){
        id = _id;
        type = _type;
        path = _path;
        img = _img;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }
}


