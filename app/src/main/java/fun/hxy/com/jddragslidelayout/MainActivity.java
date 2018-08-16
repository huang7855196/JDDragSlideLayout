package fun.hxy.com.jddragslidelayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView viewById = (RecyclerView) findViewById(R.id.footer);
        RecyclerView header = (RecyclerView) findViewById(R.id.header);
        viewById.setLayoutManager(new LinearLayoutManager(this));
        header.setLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("我是商品"+i);
        }
        List list2 = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list2.add("我是头部"+i);
        }

        viewById.setAdapter(new MyRecycleAdapter<String>(this,list) {
            @Override
            public void getItemView(MyRecycleViewHolder holder, int position, String item) {
                holder.setText(R.id.tv_name,item);
            }

            @Override
            public int getItemResource() {
                return R.layout.item;
            }
        });
        header.setAdapter(new MyRecycleAdapter<String>(this,list2) {
            @Override
            public void getItemView(MyRecycleViewHolder holder, int position, String item) {
                holder.setText(R.id.tv_name,item);
                holder.getView(R.id.tv_name).setBackgroundColor(Color.YELLOW);
            }

            @Override
            public int getItemResource() {
                return R.layout.item;
            }
        });
    }
}
