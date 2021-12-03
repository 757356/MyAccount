package cn.itcast.myaccount.MyData;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataAccount {
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<AccountItem> accountItemList;

    public DataAccount(Context context) {
        this.context=context;
    }

    @SuppressWarnings("unchecked")
    public List<AccountItem> loadData() {
        accountItemList=new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            accountItemList = (ArrayList<AccountItem>) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return accountItemList;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(accountItemList);
            objectOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
