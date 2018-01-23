package com.bwei.wangxuyang20171221;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xlistviewlibrary.View.XListView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{
ArrayList<Bean.DataBean> li=new ArrayList<>();
String[] str={"https://m.360buyimg.com/n0/jfs/t9004/210/1160833155/647627/ad6be059/59b4f4e1N9a2b1532.jpg!q70.jpg",
        "https://m.360buyimg.com/n0/jfs/t7441/10/64242474/419246/adb30a7d/598e95fbNd989ba0a.jpg!q70.jpg"};
    private XListView xlv;
    private Madapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断网络
        int netype = Netutil.getNetype(this);
        if(netype!=-1){
            Toast.makeText(this,"网络正常",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"请检查网络",Toast.LENGTH_LONG).show();
        }
        //获取控件
        xlv = findViewById(R.id.xlv);
        //获取布局
        View inflate = View.inflate(MainActivity.this, R.layout.bann, null);
        Banner ban=inflate.findViewById(R.id.ban);
        //添加头部
        xlv.addHeaderView(inflate);
        ban.setImageLoader(new lad());
        ban.setImages(Arrays.asList(str));
        //开启
        ban.start();
        //设置适配器
        adapter = new Madapter();
        xlv.setAdapter(adapter);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
        line();
Button but=findViewById(R.id.but);
but.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,TwoActivity.class);
        startActivity(intent);
    }
});
    }
    private void line() {
        //设置asynctask
        new Masync().execute("http://120.27.23.105/product/getProductDetail?pid=1&source=android");
    }
    //下拉刷新的方法
    @Override
    public void onRefresh() {
li.clear();
line();
    }
//上拉加载的方法
    @Override
    public void onLoadMore() {
line();
    }

    private class lad extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage((String) path,imageView);
        }
    }

    private class Masync extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return Netutil.shj(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析数据
            Gson g=new Gson();
            Bean bean = g.fromJson(s, Bean.class);
            Bean.DataBean data = bean.getData();
            //添加到集合
            li.add(data);
            //刷新适配器
            adapter.notifyDataSetChanged();
            //停止刷新的方法
            guan();
        }
    }
    class Madapter extends BaseAdapter{

        private holder holder;

        @Override
        public int getCount() {
            return li.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //优化
            if(view==null){
                holder = new holder();
                //优化布局
                view= View.inflate(MainActivity.this,R.layout.xlv_item,null);
                //优化控件
                holder.tv=view.findViewById(R.id.tv);
                holder.tv1=view.findViewById(R.id.tv1);
                view.setTag(holder);
            }else {
                holder=(holder)view.getTag();
            }
            holder.tv.setText(li.get(i).getTitle());
            holder.tv1.setText(li.get(i).getSubhead());
            return view;
        }
    }
    class holder{
        TextView tv;
        TextView tv1;
    }
    //停止刷新的方法
    public void guan(){
        xlv.stopLoadMore();
        xlv.stopRefresh();
        xlv.setRefreshTime("刚刚");
    }
}
