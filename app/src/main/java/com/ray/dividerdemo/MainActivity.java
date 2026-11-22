package com.ray.dividerdemo;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ray.divider.Divider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText inputNumEditText;
    private RecyclerView recyclerView;
    private TestAdapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private List<View> headerViews = new ArrayList<>();
    private List<View> footerViews = new ArrayList<>();
    private Divider divider;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNumEditText = findViewById(R.id.et_input_num);
        recyclerView = findViewById(R.id.recyclerView);
        // 鍒濆鍖栫洃鍚?        initListener();
        // 璁剧疆adapter
        adapter = new TestAdapter(R.layout.item_test, null);
        recyclerView.setAdapter(adapter);
        divider = Divider.builder().color(Color.BLUE).width(10).height(20).build();
        recyclerView.addItemDecoration(divider);
        // 璁剧疆manager
        switchLayoutManager(1);
        // 璁剧疆鏁版嵁
        replaceData(10);
    }

    private void initListener() {
        // 鏂囧瓧鏀瑰彉鐩戝惉
        inputNumEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = inputNumEditText.getText().toString().trim();
                if (TextUtils.isEmpty(trim))
                    return;
                num = Integer.parseInt(trim);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 璁剧疆鍐呭鏁伴噺
        findViewById(R.id.btn_set_num).setOnClickListener(v -> replaceData(num));
        // 澧炲姞鍐呭鏁伴噺
        findViewById(R.id.btn_add_num).setOnClickListener(v -> addData(num));
        // 鍒犻櫎鍐呭鏁伴噺
        findViewById(R.id.btn_remove_num).setOnClickListener(v -> removeData(num));
        // 澧炲姞澶?        findViewById(R.id.btn_header_add).setOnClickListener(v -> {
            View headerView = getHeaderViewOrFooterView();
            adapter.addHeaderView(headerView);
            headerViews.add(headerView);
            divider.setHeaderCount(adapter.getHeaderLayoutCount());
            redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?        });
        // 绉婚櫎澶?        findViewById(R.id.btn_header_remove).setOnClickListener(v -> {
            if (headerViews.size() > 0) {
                adapter.removeHeaderView(headerViews.get(0));
                headerViews.remove(0);
                divider.setHeaderCount(adapter.getHeaderLayoutCount());
                redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?            }
        });
        // 澧炲姞灏?        findViewById(R.id.btn_footer_add).setOnClickListener(v -> {
            View headerView = getHeaderViewOrFooterView();
            adapter.addFooterView(headerView);
            footerViews.add(headerView);
            divider.setFooterCount(adapter.getFooterLayoutCount());
            redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?        });
        // 绉婚櫎灏?        findViewById(R.id.btn_footer_remove).setOnClickListener(v -> {
            if (footerViews.size() > 0) {
                adapter.removeFooterView(footerViews.get(0));
                footerViews.remove(0);
                divider.setFooterCount(adapter.getFooterLayoutCount());
                redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?            }
        });
        // 鍒囨崲LayoutManager
        findViewById(R.id.btn_linear_v).setOnClickListener(v -> switchLayoutManager(1));
        findViewById(R.id.btn_linear_h).setOnClickListener(v -> switchLayoutManager(2));
        findViewById(R.id.btn_grid_v).setOnClickListener(v -> switchLayoutManager(3));
        findViewById(R.id.btn_grid_h).setOnClickListener(v -> switchLayoutManager(4));
        findViewById(R.id.btn_staggered_v).setOnClickListener(v -> switchLayoutManager(5));
        findViewById(R.id.btn_staggered_h).setOnClickListener(v -> switchLayoutManager(6));
    }

    /**
     * 鍒囨崲LayoutManager
     */
    private void switchLayoutManager(int layoutManagerState) {
        switch (layoutManagerState) {
            case 1:
                // LinearLayoutManager VERTICAL
                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                break;
            case 2:
                // LinearLayoutManager HORIZONTAL
                linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case 3:
                // GridLayoutManager VERTICAL
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.VERTICAL, false);
                this.linearLayoutManager = gridLayoutManager;
                setSpanSizeLookup(gridLayoutManager);// 璁剧疆澶淬€佸熬锛屽崰鐢?鍒?                break;
            case 4:
                // GridLayoutManager HORIZONTAL
                GridLayoutManager gridLayoutManagerHorizontal = new GridLayoutManager(getApplicationContext(), 4, LinearLayoutManager.HORIZONTAL, false);
                this.linearLayoutManager = gridLayoutManagerHorizontal;
                setSpanSizeLookup(gridLayoutManagerHorizontal);// 璁剧疆澶淬€佸熬锛屽崰鐢?鍒?                break;
            case 5:
                // StaggeredGridLayoutManager VERTICAL
                this.linearLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
                break;
            case 6:
                // StaggeredGridLayoutManager HORIZONTAL
                this.linearLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
                break;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.setLayoutManagerState(layoutManagerState);
        adapter.notifyDataSetChanged();
    }

    /**
     * GridLayoutManager 璁剧疆澶淬€佸熬锛屽崰鐢?鍒?     */
    private void setSpanSizeLookup(GridLayoutManager gridLayoutManager) {
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < adapter.getHeaderLayoutCount() || position >= adapter.getItemCount() - adapter.getFooterLayoutCount())
                    // 澶存垨鑰呭熬閮紝鍗犵敤3鍒?                    return gridLayoutManager.getSpanCount();
                else
                    return 1;
            }
        });
    }

    /**
     * 鑾峰彇澧炲姞鐨勫ご銆佸熬
     */
    private View getHeaderViewOrFooterView() {
        View headerView = View.inflate(getApplicationContext(), R.layout.item_header_footer, null);
        headerView.setBackgroundColor(Color.GREEN);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return headerView;
    }

    /**
     * 缃崲鏁版嵁
     */
    private void replaceData(int itemCount) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            items.add("鍘熷鏁版嵁" + i);
        }
        adapter.replaceData(items);
    }

    /**
     * 澧炴暟鎹?     */
    private void addData(int position) {
        if (position <= adapter.getData().size()) {
            adapter.addData(position, "鏂板鏁版嵁");
            redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?        }
    }

    /**
     * 鍒犳暟鎹?     */
    private void removeData(int position) {
        if (position < adapter.getData().size()) {
            adapter.remove(position);
            redrawDivider();// 澧炲垹寰楅噸鏂扮殑缁樺埗绾?        }
    }

    /**
     * 閲嶆柊缁樺埗绾?     */
    private void redrawDivider() {
        // 濡傛灉澧炲垹璋冪敤鐨勬槸  adapter.notifyDataSetChanged() 鍒欎笉鐢ㄨ皟鐢ㄤ笅闈㈡柟娉?        recyclerView.invalidateItemDecorations();
    }
}

