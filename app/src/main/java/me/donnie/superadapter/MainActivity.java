package me.donnie.superadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.donnie.adapter.MultiItemAdapter;
import me.donnie.adapter.Walle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TestAdapter adapter = new TestAdapter();
        adapter.setOnItemClickListener(new MultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MultiItemAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }
        });

        Walle wrapper = Walle.newBuilder()
                .wrapperAdapter(adapter)
                .autoLoadMore(false)
                .headerViewRes(R.layout.view_header)
                .emptyViewRes(R.layout.view_empty)
                .footerViewRes(R.layout.view_footer)
                .loadMoreViewRes(R.layout.view_loadmore)
                .build();

        rv.setAdapter(wrapper.getWrapperAdapter());
        adapter.addNewData(getData());

    }

    private List<Test> getData() {
        List<Test> tests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Test test = new Test();
            test.setId(i);
            test.setName("name "+i);
            test.setDesc("desc "+i);
            tests.add(test);
        }
        return tests;
    }


}
