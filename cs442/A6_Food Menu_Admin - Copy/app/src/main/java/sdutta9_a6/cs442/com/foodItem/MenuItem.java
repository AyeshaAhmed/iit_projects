package sdutta9_a6.cs442.com.foodItem;

import android.os.Parcel;
import android.os.Parcelable;


public class MenuItem implements Parcelable {

    int id =0;
    String  name = "None";
    String price ="$0";
    String  details = "None";
    String method ="None";
    String ingredients ="None";

    // Constructor for MenuItem
    MenuItem(int id) {
        this.id = id;
    }
    // Text representation of the class
    public String toString () {
        String s =String.format("%2d %-15s%20s", id, name,price);
        return s;
    }

    // Parcelable implementation
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(price);
        out.writeString(details);
        out.writeString(method);
        out.writeString(ingredients);
    }
    public static final Parcelable.Creator<MenuItem> CREATOR
            = new Parcelable.Creator<MenuItem>() {
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };
    private MenuItem(Parcel in) {
        id =in.readInt();
        name = in.readString();
        price=in.readString();
        details=in.readString();
        method=in.readString();
        ingredients = in.readString();
    }
}
