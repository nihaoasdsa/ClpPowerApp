package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clppowerapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 008 on 2017/5/27 0027.
 * 首页数据的适配器
 */
public class ClpPowerAllInforAdapter extends BaseAdapter {
    private Context context;
    private List<String> data=new ArrayList<>();
    //控制checkbox的选中
    public  static HashMap<Integer,Boolean>isSelector;
    private LayoutInflater inflater=null;

    public ClpPowerAllInforAdapter(Context c ,List<String> list){
        this.context=c;
        this.data=list;
        inflater=LayoutInflater.from(context);
        isSelector=new HashMap<>();
        initdata();
    }

    private void initdata(){
        //遍历数据 将集合数据放进hashmap里
        for (int i = 0; i<data.size() ; i++) {
            getIsSelector().put(i,false);
        }
    }

    @Override
    public int getCount() {
        return data!=null &&data.size()>0 ?data.size() :0;
    }

    @Override
    public Object getItem(int position) {
        return data!=null &&data.size()>0 ?data.get(position) :0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.home_list_item,null);
            viewHolder.ll_edit= (LinearLayout) convertView.findViewById(R.id.ll_edit);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.cb_selector= (CheckBox) convertView.findViewById(R.id.item_cb);
            //设置标签
            convertView.setTag(viewHolder);
        }else {
            //取出标签
            viewHolder= (ViewHolder) convertView.getTag();
        }
           viewHolder.tv_name.setText(data.get(position));
        viewHolder.cb_selector.setChecked(getIsSelector().get(position));
        return convertView;
    }

    public  class  ViewHolder{
        public TextView tv_name;//名字
        public CheckBox cb_selector;
        public LinearLayout ll_edit;//编辑按钮
    }
    public  static  HashMap<Integer,Boolean> getIsSelector(){
        return isSelector;
    }
   public static  void setIsSelector(HashMap<Integer,Boolean> isSelected){
    ClpPowerAllInforAdapter.isSelector=isSelected;
   }

}
