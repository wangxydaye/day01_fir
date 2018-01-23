package com.bwei.wangxuyang20171221;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xlistviewlibrary.View.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonder on 2017/12/21.
 */

public class TwoActivity extends AppCompatActivity implements XListView.IXListViewListener{
    ArrayList<Bean1.DataBean> li=new ArrayList<>();
    private XListView xlv;
    private Madapter adapter;
int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        //获取控件
        xlv = findViewById(R.id.xlv);
        //设置适配器
        adapter = new Madapter();
        xlv.setAdapter(adapter);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
        line(i);
    }
    private void line(int i) {
        //设置asynctask
        new Masync().execute("http://120.27.23.105/product/getProductCatagory?cid="+i);
    }
    @Override
    public void onRefresh() {
li.clear();
line(i++);
    }

    @Override
    public void onLoadMore() {
  // line(i);
        Toast.makeText(this,"加载更多",Toast.LENGTH_LONG).show();
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
            Bean1 bean1 = g.fromJson(s, Bean1.class);
            List<Bean1.DataBean> data = bean1.getData();
            //添加到集合
            li.addAll(data);
            //刷新适配器
            adapter.notifyDataSetChanged();
            //停止刷新的方法
              guan();
        }
    }
    class Madapter extends BaseAdapter {

        private holder holder;
        private holder1 holder1;
        private holder2 holder2;

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
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            int j=0;
            if(li.get(position).getList().get(1).getIcon()==null&&li.get(position).getList().get(1).getIcon()==""&&li.get(position).getList().get(2).getIcon()==null&&li.get(position).getList().get(2).getIcon()==""){
                j=2;
            }else if(li.get(position).getList().get(2).getIcon()==null&&li.get(position).getList().get(2).getIcon()==""){
                j=1;
            }else {
                j=0;
            }
            return j;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            switch (itemViewType) {
                case 0:
                    //优化
                    if (view == null) {
                        holder = new holder();
                        //优化布局
                        view = View.inflate(TwoActivity.this, R.layout.two_item, null);
                        //优化控件
                        holder.tvv = view.findViewById(R.id.tvv);
                        holder.iv1 = view.findViewById(R.id.iv1);
                        holder.tv1 = view.findViewById(R.id.tv1);
                        holder.iv2 = view.findViewById(R.id.iv2);
                        holder.tv2 = view.findViewById(R.id.tv2);
                        holder.iv3 = view.findViewById(R.id.iv3);
                        holder.tv3 = view.findViewById(R.id.tv3);
                        view.setTag(holder);
                    } else {
                        holder = (holder) view.getTag();
                    }
                    holder.tvv.setText(li.get(i).getName());
                    ImageLoader.getInstance().displayImage(li.get(i).getList().get(0).getIcon(), holder.iv1);
                    holder.tv1.setText(li.get(i).getList().get(0).getName());
                    ImageLoader.getInstance().displayImage(li.get(i).getList().get(1).getIcon(), holder.iv2);
                    holder.tv2.setText(li.get(i).getList().get(1).getName());
                    ImageLoader.getInstance().displayImage(li.get(i).getList().get(2).getIcon(), holder.iv3);
                    holder.tv3.setText(li.get(i).getList().get(2).getName());
                    break;
                case 1:
                    //优化
                    if (view == null) {
                        holder1 = new holder1();
                        //优化布局
                        view = View.inflate(TwoActivity.this, R.layout.two_item2, null);
                        //优化控件
                        holder1.iv11 = view.findViewById(R.id.iv11);
                        holder1.tv11 = view.findViewById(R.id.tv11);
                        holder1.iv22 = view.findViewById(R.id.iv22);
                        holder1.tv22 = view.findViewById(R.id.tv22);
                        view.setTag(holder1);
                    } else {
                        holder1 = (holder1) view.getTag();
                    }
                    ImageLoader.getInstance().displayImage(li.get(i).getList().get(0).getIcon(), holder1.iv11);
                    holder1.tv11.setText(li.get(i).getList().get(0).getName());
                    ImageLoader.getInstance().displayImage(li.get(i).getList().get(1).getIcon(), holder1.iv22);
                    holder1.tv22.setText(li.get(i).getList().get(1).getName());
                    break;
              case 2:
                  //优化
                if (view == null) {
                    holder2 = new holder2();
                    //优化布局
                    view = View.inflate(TwoActivity.this, R.layout.two_item3, null);
                    //优化控件
                    holder2.iv33 = view.findViewById(R.id.iv33);
                    holder2.tv33 = view.findViewById(R.id.tv33);
                    view.setTag(holder);
                } else {
                    holder2 = (holder2) view.getTag();
                }

                ImageLoader.getInstance().displayImage(li.get(i).getList().get(0).getIcon(), holder2.iv33);
                holder2.tv33.setText(li.get(i).getList().get(0).getName());

                break;
           }
                return view;
            }
        }

        class holder {
            ImageView iv1;
            TextView tv1;
            ImageView iv2;
            TextView tv2;
            ImageView iv3;
            TextView tv3;
            TextView tvv;
        }

        class holder1 {
            ImageView iv11;
            TextView tv11;
            ImageView iv22;
            TextView tv22;
        }

        class holder2 {
            ImageView iv33;
            TextView tv33;

        }

        //停止刷新的方法
        public void guan() {
            xlv.stopLoadMore();
            xlv.stopRefresh();
            xlv.setRefreshTime("刚刚");
        }
    }
